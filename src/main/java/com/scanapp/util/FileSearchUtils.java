package com.scanapp.util;

import akka.dispatch.Foreach;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import scala.Tuple2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Slf4j
public class FileSearchUtils implements Serializable {

    private String commaSeparatedListOfExtensions;

    public FileSearchUtils(String commaSeparatedListOfExtensions) {
        this.commaSeparatedListOfExtensions = commaSeparatedListOfExtensions;
    }

    private List<String> result = new ArrayList<String>();

    /**
     * @return root directory file path
     */
    private  String rootDirectory() {
        return File.listRoots()[0].getAbsolutePath();
    }


    private void searchDirectory(File directory) {


        if (directory.isDirectory()) {
            search(directory);
        } else {
            System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }


    private void search(File file) {

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

                            sparkWordCount(temp.getAbsoluteFile().toString());

                        }

                    }
                }

            } else {
               // System.out.println(file.getAbsoluteFile() + " Permission Denied");
            }
        }

    }


    public String ReadStringFromFileLineByLine(String fil){
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


    public  List<String> listFoundFiles(){
        Path rootDir = Paths.get(rootDirectory());
        searchDirectory(rootDir.toFile());

        return result;
    }




    public static void main(String ...args){
        FileSearchUtils fileSearchUtils=new FileSearchUtils(".txt");
        List<String> ff = fileSearchUtils.listFoundFiles();

    }



    public void sparkWordCount(String filename){

        JavaSparkContext sc = new JavaSparkContext("local", "Word Count");
        JavaRDD<String> input = sc.textFile(filename);
        JavaRDD<String> words = input.flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split(" ")));
        JavaPairRDD<String, Integer> counts = words.mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2(s, 1));

       // JavaPairRDD<String, Integer> reducedCounts = counts.reduceByKey((Function2<Integer, Integer, Integer>) (x, y) -> x + y);

        System.out.println("------------------------got here--------");
       // System.out.println(reducedCounts);
        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<?, ?> tuple : output) {
            if (CreditCardUtils.isCreditCardNumber(tuple._1().toString()))
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        sc.stop();
        //reducedCounts.saveAsTextFile("output");

    }

}
