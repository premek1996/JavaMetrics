package pl.edu.pwr.master.output;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    private static CSVPrinter printer;

    private CSVWriter() {
    }

    public static void writeCSV(String fileName, List<ConformedRow> rows) {
        try (FileWriter out = new FileWriter(fileName)) {
            printer = getPrinter(out);
            rows.forEach(CSVWriter::printRow);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static CSVPrinter getPrinter(FileWriter out) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(ConformedRowHeader.class)
                .print(out);
    }

    private static void printRow(ConformedRow row) {
        try {
            printer.printRecord(
                    row.getType(),
                    row.getPackageName(),
                    row.getOuterClassName(),
                    row.getClassName(),
                    row.getMethodName(),
                    row.getParameters(),
                    row.getRepository(),
                    row.getCommitHash(),
                    row.getGitSourceFileUrl(),
                    row.getStartLine(),
                    row.getEndLine(),
                    row.getFilePath()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
