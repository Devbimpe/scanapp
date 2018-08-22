package com.scanapp.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.util.StringUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class FileSearchUtils implements Serializable {
private File recordFile = new File("/Users/user/Documents/testLog"+new SimpleDateFormat("ddMMyy").format(new Date())+".log");

    private String commaSeparatedListOfExtensions;

    public FileSearchUtils(String commaSeparatedListOfExtensions) {
        this.commaSeparatedListOfExtensions = commaSeparatedListOfExtensions;
    }


    /**
     * @return root directory file path
     */
    public static String rootDirectory() {
        return File.listRoots()[0].getAbsolutePath();
    }


    private void searchDirectory(File directory) {


        if (directory.isDirectory()) {
            search(directory);
        } else {
            //System.out.println(directory.getAbsoluteFile() + " is not a directory!");
        }

    }


    private void search(File file) {

        if (file.isDirectory()) {

            // System.out.println("Searcmnehing directory ... " + file.getAbsoluteFile());

            //do you have permission to read this directory?
            if (file.canRead()) {
                if (file.listFiles() != null) {
                    for (File temp : Objects.requireNonNull(file.listFiles())) {
                        if (temp.isDirectory()) {
                            search(temp);
                        } else {
                            if (accept(temp)) {

                                Set<String> cards = getCreditCardsFromFile(temp.getAbsolutePath());
                                System.out.println("cards seen " + cards);
                                if (cards.size() > 0) {
                                    try {
                                        writeToFile(cards.stream().collect(Collectors.toList()), temp.getAbsolutePath());
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

    }


    public boolean accept(File file) {
        String fileName = file.getAbsolutePath();
        Set<String> fileExtensionList = StringUtils.commaDelimitedListToSet(commaSeparatedListOfExtensions);
        return fileExtensionList.stream().anyMatch(fileName::endsWith);

    }


    public boolean accept(String fileName) {
        Set<String> fileExtensionList = StringUtils.commaDelimitedListToSet(commaSeparatedListOfExtensions);
        return fileExtensionList.stream().anyMatch(fileName::endsWith);

    }


    public void listFoundFiles() throws IOException {
        Path rootDir = Paths.get(rootDirectory());
        searchDirectory(rootDir.toFile());


    }


    public static void main(String... args) throws IOException {

        FileSearchUtils fileSearchUtils = new FileSearchUtils(".docx");
        fileSearchUtils.listFoundFiles();

    }

    public static String extractContentUsingParser(InputStream stream)
            throws IOException, TikaException, SAXException {

        Parser parser = new AutoDetectParser();
        ContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();

        parser.parse(stream, handler, metadata, context);
        return handler.toString();
    }

    private Set<String> getCreditCardsFromFile(String fileName) {
        Set<String> creditCardsList = new HashSet<>();
        FileInputStream fileInputStream=null;
        try {

            File file = new File(fileName);
           fileInputStream = new FileInputStream(file);

            String data = extractContentUsingParser(fileInputStream);

            String[] words = data.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                // You may want to check for a non-word character before blindly
                // performing a replacement
                // It may also be necessary to adjust the character class
                words[i] = words[i].replaceAll("[^\\w]", "");
            }

            Arrays.asList(words)
                    .stream()
                    .forEach(
                            i -> {

                                if (CreditCardUtils.isCreditCardNumber(i)) {
                                    System.out.println(String.format("%s is a credit card", i));
                                    creditCardsList.add(i);
                                }

                            }


                    );

            System.out.println(String.format("cards found in file  %s ", fileName) + "  " + creditCardsList);


        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();

        }finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {

                }
            }
        }


        System.out.println("current records " + creditCardsList);
        return creditCardsList;
    }


    /**
     * public void writeToFile(List<String> results, String filename) throws IOException {
     * File outFile = new File("tofile" + generateNum());
     * FileWriter fWriter = new FileWriter(outFile);
     * PrintWriter pWriter = new PrintWriter(fWriter);
     * pWriter.println(filename);
     * for (String result : results) {
     * pWriter.println(result);
     * }
     * <p>
     * pWriter.close();
     * }
     **/
    private String generateNum() {

        Random r = new Random(System.currentTimeMillis());
        int random = 1000000000 + r.nextInt(9999999);
        return Integer.toString(random);
    }


    private  void writeToFile(List<String> creditCards , String fileName){
        try{

            creditCards
                    .stream()
                    .forEach(creditCard -> {
                        try {
                            FileUtils.writeStringToFile(recordFile, String.format("card pan (%s ) , file name (%s)",creditCard,fileName), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });






        }catch (Exception e){
            //log.error("error occurred while writting to file "+e.getLocalizedMessage(),e);
        }

    }

}
