package pl.edu.pwr.master.input;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for reading input CSV to the metrics tool.
 */
public class CsvReader {

    private static final Logger LOGGER = Logger.getLogger(CsvReader.class.getName());

    private CsvReader() {
    }

    /**
     * Gets input from the CSV file to be parsed. Looks for an input in columns 'type' and 'code_name'.
     *
     * @param inputPath path to the CSV file
     * @return Methods and classes to be parsed
     */
    public static Input getInputToParse(String inputPath) {
        Set<ClassInput> classes = new LinkedHashSet<>();
        Set<MethodInput> methods = new LinkedHashSet<>();

        try (Reader in = new FileReader(inputPath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(CsvHeader.class).withSkipHeaderRecord().parse(in);

            records.forEach(r -> {
                String type = r.get(CsvHeader.TYPE);
                String packageName = r.get(CsvHeader.PACKAGE);
                String outerClass = r.get(CsvHeader.OUTER_CLASS);
                int startLine = Integer.parseInt(r.get(CsvHeader.START_LINE));
                int endLine = Integer.parseInt(r.get(CsvHeader.END_LINE));
                String className = r.get(CsvHeader.CLASS);
                String methodName = r.get(CsvHeader.METHOD);
                List<String> parameters = Arrays.asList(r.get(CsvHeader.PARAMETERS).split("\\|"));

                if (type.equals(ClassInput.CLASS_TYPE)) {
                    classes.add(new ClassInput(packageName, outerClass, startLine, endLine, className));
                } else if (type.equals(MethodInput.METHOD_TYPE) || type.equals(MethodInput.CONSTRUCTOR_TYPE)) {
                    methods.add(new MethodInput(packageName, outerClass, startLine, endLine, className, methodName, parameters));
                } else {
                    if (LOGGER.isLoggable(Level.INFO)) {
                        LOGGER.info("Found unknown value " + type + " in column 'type'!");
                    }
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new Input(new ArrayList<>(classes), new ArrayList<>(methods));
    }

    /**
     * Gets repositories from the CSV file with hashes. Hashes are deduplicated.
     *
     * @param inputPath path to the CSV file
     * @return Map of repositories and set of hashes related to the repository
     */
    public static Map<String, Set<String>> getRepositories(String inputPath) {
        Map<String, Set<String>> output = new HashMap<>();
        try (Reader in = new FileReader(inputPath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(CsvHeader.class).parse(in);
            for (CSVRecord record : records) {
                String repository = record.get(CsvHeader.REPOSITORY);
                String hash = record.get(CsvHeader.COMMIT_HASH);

                if (output.containsKey(repository))
                    output.get(repository).add(hash);
                else
                    output.put(repository, new HashSet<>());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return output;
    }

}
