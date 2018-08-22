package csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

class Application {
    private static final String SAMPLE_CSV_FILE = "c:/temp/b.csv";
    private static final String[] CSV_HEADER = {"Id", "Name", "Description", "Company", "Color", "Creation Date"};

    // This csv library is very fast for 200K record works less than 1sec  in comparison to open csv with 16sec
    // This library handles special characters of csv like comma and \n
    public static void main(String[] args) throws IOException {

        // create csv file
        generateCsvFile();

        File from = new File("csv-file/src/main/resources/b.csv");
        File to = new File("csv-file/src/main/resources/c.csv");


        try ( FileChannel in = new FileInputStream( from ).getChannel();
              FileChannel out = new FileOutputStream( to, true ).getChannel() ) {
            // Appending b.csv into c.csv
            out.transferFrom( in, out.size(), in.size() );
        }


        File to2 = new File("csv-file/src/main/resources/d.csv");
        if ( !to2.exists() ) { to2.createNewFile(); }
        try(final FileReader fileReader = new FileReader("csv-file/src/main/resources/b.csv");
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT)) {
            final List<CSVRecord> records = csvParser.getRecords();

            final List<CSVRecord> headFile = records.stream().limit(5).collect(Collectors.toList());
            final List<CSVRecord> tailFile = records.stream().skip(5).collect(Collectors.toList());
            try (OutputStreamWriter writer = new FileWriter("csv-file/src/main/resources/d.csv", true);
                 CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
                csvPrinter.printRecords(tailFile);
                csvPrinter.flush();
                writer.flush();
            }
        }
    }

    public static void generateCsvFile() throws IOException {

        Date d = new Date();

        ArrayList<String[]> list = new ArrayList<String[]>();
        list.add(new Customer("1", "Sundar, Pichai", "CEO", "Google", CustomerColor.Green, d).toArray());
        list.add(new Customer("2", "Satya\n Nadella", "CEO", "Microsoft", CustomerColor.Yellow, d).toArray());
        list.add(new Customer("3", "Tim cook", "CEO", "Apple", CustomerColor.Blue, d).toArray());
        list.add(new Customer("4", "Mark Zuckerberg", "CEO", "Facebook", CustomerColor.Red, d).toArray());

        saveToCSV(list, SAMPLE_CSV_FILE);
    }

    public static void saveToCSV(List<?> entityToWrite, String csvFileName) throws IOException {
        try(OutputStreamWriter writer = new FileWriter(csvFileName);
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecords(entityToWrite);
            csvPrinter.flush();
            writer.flush();
        }
    }
}