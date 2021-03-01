package pl.edu.pwr.master;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<Row> readCsv(String filename) {
        List<Row> rows = new ArrayList<>();
        try (Reader in = new FileReader(filename)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(RowHeader.class).withSkipHeaderRecord().withDelimiter(';').parse(in);
            for (CSVRecord record : records) {
                Integer id = Integer.parseInt(record.get(RowHeader.ID));
                String type = record.get(RowHeader.TYPE);
                String codeName = record.get(RowHeader.CODE_NAME);
                String repository = record.get(RowHeader.REPOSITORY);
                String commitHash = record.get(RowHeader.COMMIT_HASH);
                String link = record.get(RowHeader.LINK);
                String startLine = record.get(RowHeader.START_LINE);
                String endLine = record.get(RowHeader.END_LINE);
                String filePath = record.get(RowHeader.PATH);

                rows.add(new Row(id, type, codeName, repository, commitHash, link, startLine, endLine, filePath));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return rows;
    }

}
