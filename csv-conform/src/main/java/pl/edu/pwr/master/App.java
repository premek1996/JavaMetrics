package pl.edu.pwr.master;

import pl.edu.pwr.master.input.CSVReader;
import pl.edu.pwr.master.input.Row;
import pl.edu.pwr.master.normalizer.CSVNormalizer;
import pl.edu.pwr.master.output.ConformedRow;
import pl.edu.pwr.master.output.CSVWriter;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        setLoggingLevel();
        if (args.length == 1) {
            List<Row> rows = CSVReader.readCSV(args[0]);
            rows = CSVNormalizer.cleanseCsv(rows);

            List<ConformedRow> conformedRows = CSVNormalizer.conformCsv(rows);
            CSVWriter.writeCsv(args[0], conformedRows);
        } else if (args.length == 2) {
            List<Row> rows = CSVReader.readCSV(args[0]);
            rows = CSVNormalizer.cleanseCsv(rows);

            List<ConformedRow> conformedRows = CSVNormalizer.conformCsv(rows);
            CSVWriter.writeCsv(args[1], conformedRows);
        } else {
            System.err.println("Usage: java -jar CsvConform <input_csv> [<output_csv>]");
            System.exit(1);
        }
        System.exit(0);
    }

    private static void setLoggingLevel() {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.WARNING);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.WARNING);
        }
    }

}
