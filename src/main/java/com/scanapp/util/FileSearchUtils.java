package com.scanapp.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import scala.Tuple2;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class FileSearchUtils implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Set<String> creditCardsList = new HashSet<>();

    private String commaSeparatedListOfExtensions;

    public FileSearchUtils(String commaSeparatedListOfExtensions) {
        this.commaSeparatedListOfExtensions = commaSeparatedListOfExtensions;
    }

    private List<String> result = new ArrayList<String>();

    /**
     * @return root directory file path
     */
    private String rootDirectory() {
        return File.listRoots()[0].getAbsolutePath();
    }


    private void searchDirectory(File directory) throws IOException {


        if (directory.isDirectory()) {
            search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }


    private void search(File file) throws IOException {

        if (file.isDirectory()) {

            // System.out.println("Searching directory ... " + file.getAbsoluteFile());

            //do you have permission to read this directory?
            if (file.canRead()) {
                for (File temp : Objects.requireNonNull(file.listFiles())) {
                    if (temp.isDirectory()) {
                        search(temp);
                    } else {
                        if (accept(temp)) {
                            result.add(temp.getAbsoluteFile().toString());
                            List<String> cards = sparkWordCount(temp.getAbsoluteFile().toString());
                            if (cards.size() > 0) {
                                try {
                                    writeToFile(cards.stream().collect(Collectors.toList()), temp.getAbsoluteFile().toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                    }
                }

            } else {
                // System.out.println(file.getAbsoluteFile() + " Permission Denied");
            }
        }

    }


    public String ReadStringFromFileLineByLine(String fil) {
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader br;
        List<String> list;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(fil));
            while ((sCurrentLine = br.readLine()) != null) {
                stringBuffer.append(sCurrentLine);
            }
            String[] words = stringBuffer.toString().split("\\s");
            list = Arrays.asList(words);
            br.close();
            System.out.println("Contents of file:");
            for (String string : list) {
                System.out.println(string);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


    private boolean accept(File file) {
        String fileName = file.getAbsolutePath();
        Set<String> fileExtensionList = StringUtils.commaDelimitedListToSet(commaSeparatedListOfExtensions);
        boolean shouldAccept = fileExtensionList.stream().anyMatch(i -> fileName.endsWith(i));
        return shouldAccept;

    }


    public List<String> listFoundFiles() throws IOException {
        Path rootDir = Paths.get(rootDirectory());
        searchDirectory(rootDir.toFile());

        return result;
    }


    public static void main(String... args) throws IOException {
        FileSearchUtils fileSearchUtils = new FileSearchUtils(".docx");
        List<String> ff = fileSearchUtils.listFoundFiles();

    }


    private Set<String> getCreditCardsFromFile(String fileName) throws IOException {
        FileInputStream inputStream = null;
        Scanner input = null;
       try {

           File file = new File(fileName);
           inputStream = new FileInputStream(file);
            input = new Scanner(file);


           while (input.hasNext()) {
               String word = input.next();
               System.out.println(String.format("checking if word %s is a credit card" ,word));
               if (CreditCardUtils.isCreditCardNumber(word)) {
                   creditCardsList.add(word);
               }

           }
       }catch (Exception e){
           System.out.println(e.getLocalizedMessage());
           e.printStackTrace();


       }finally {
           if (inputStream != null) {
               inputStream.close();
           }
           if (input != null) {
               input.close();
           }
       }

        System.out.println("current records "+creditCardsList);
        return creditCardsList;
    }


    private ArrayList<String> sparkWordCount(String filename) {
       ArrayList<String> cards = new ArrayList<>();
        System.out.println("filename is------------------" + filename);
        JavaSparkContext sc = new JavaSparkContext("local", "Word Count");
        JavaRDD<String> input = sc.textFile(filename);
        JavaRDD<String> words = input.flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split(" ")));
        JavaPairRDD<String, Integer> counts = words.mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2(s, 1));
        System.out.println("------------------------got here--------");
        List<Tuple2<String, Integer>> output = counts.collect();

        for (Tuple2<?, ?> tuple : output) {

            System.out.println(String.format("checking if word %s is a credit card" ,tuple._1()));
            if (CreditCardUtils.isCreditCardNumber(""+tuple._1())) {
               cards.add(tuple._1().toString());
                creditCardsList.add(""+tuple._1());
            }

            }
        System.out.println("current data "+creditCardsList);
        sc.stop();
        return cards;
    }


    public void writeToFile(List<String> results, String filename) throws IOException {
        File outFile = new File("tofile" + generateNum());
        FileWriter fWriter = new FileWriter(outFile);
        PrintWriter pWriter = new PrintWriter(fWriter);
        pWriter.println(filename);
        for (String result : results) {
            pWriter.println(result);
        }

        pWriter.close();
    }

    private String generateNum() {

        Random r = new Random(System.currentTimeMillis());
        int random = 1000000000 + r.nextInt(9999999);
        return Integer.toString(random);
    }

}
