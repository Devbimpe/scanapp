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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class FileSearchUtils implements Serializable {

    private File recordFile ;

    public void setRecordFilePath(String recordFilePath) {
        this.recordFile = new File(recordFilePath+"testLog" + new SimpleDateFormat("ddMMyy").format(new Date()) + ".log") ;
    }

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


        if (Files.isReadable(directory.toPath())) {

            if (directory.isDirectory()) {
                search(directory);
            }

        }
    }


    private void search(File file) {

        try {
            if (file.isDirectory()) {

                // System.out.println("Searching directory ... " + file.getAbsoluteFile());

                //do you have permission to read this directory?
                // is the file empty or a recycled bin
                if (file.listFiles() != null && file.canRead()) {

                    List<File> fileList = Arrays.stream(Objects.requireNonNull(file.listFiles())).filter(i -> Files.isReadable(i.toPath())).collect(Collectors.toList());

                    for (File temp : fileList) {
                        if (temp == null)
                            continue;

                        try {
                            if (!Files.isReadable(temp.toPath())) {
                                continue;
                            }
                        } catch (Exception e) {
                            //log.error(e.getLocalizedMessage(), e);
                        }

                        if (temp.isDirectory()) {
                            search(temp);
                        } else {
                            if (accept(temp)) {

                                Set<String> cards = getCreditCardsFromFile(temp.getAbsolutePath());
                                System.out.println("cards seen " + cards);
                                if (cards.size() > 0) {
                                    try {

                                        writeToFile(new ArrayList<>(cards), temp.getAbsolutePath());

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

        } catch (Exception e) {
            //log.error("exception occurred while reading file " + e.getLocalizedMessage(), e);
        }


    }


    private boolean accept(File file) {
        if (Files.isReadable(file.toPath())) {
            String fileName = file.getAbsolutePath();
            Set<String> fileExtensionList = StringUtils.commaDelimitedListToSet(commaSeparatedListOfExtensions);
            return fileExtensionList.stream().anyMatch(fileName::endsWith);
        }
        return false;

    }


    private boolean accept(String fileName) {
        Set<String> fileExtensionList = StringUtils.commaDelimitedListToSet(commaSeparatedListOfExtensions);
        return fileExtensionList.stream().anyMatch(fileName::endsWith);

    }


    public void listFoundFiles() {
        Path rootDir = Paths.get(rootDirectory());
        searchDirectory(rootDir.toFile());


    }




    private static String extractContentUsingParser(InputStream stream)
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
        FileInputStream fileInputStream = null;
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


        } finally {
            if (fileInputStream != null) {

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


    private void writeToFile(List<String> creditCards, String fileName) {
        try {

            creditCards
                    .stream()
                    .forEach(creditCard -> {
                        try {
                            FileUtils.writeStringToFile(recordFile, String.format("card pan (%s ) , file name (%s)", creditCard, fileName), true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });


        } catch (Exception e) {
            //log.error("error occurred while writing to file " + e.getLocalizedMessage(), e);

        }

    }

}
