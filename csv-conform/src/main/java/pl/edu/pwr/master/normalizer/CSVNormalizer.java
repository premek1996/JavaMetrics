package pl.edu.pwr.master.normalizer;

import pl.edu.pwr.master.input.Row;
import pl.edu.pwr.master.mapper.ConformedRowMapper;
import pl.edu.pwr.master.output.ConformedRow;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CSVNormalizer {

    private static final Logger LOGGER = Logger.getLogger(CSVNormalizer.class.getName());

    public static List<Row> cleanseCSV(List<Row> rows) {
        return rows.stream()
                .filter(Row::isCorrect)
                .map(CSVNormalizer::getRowWithCorrectCodeName)
                .collect(Collectors.toList());
    }

    private static Row getRowWithCorrectCodeName(Row row) {
        if (row.getCodeName().contains("<") && row.getCodeName().contains(">")) {
            LOGGER.warning("Found generic type usage (<...>): " + row.getCodeName() + " in " + row.toString() + ". Cleansing...");
        }
        String codeName = row.getCodeName().replaceAll("<.*?>", "");
        row.setCodeName(codeName);
        return row;
    }

    public static List<ConformedRow> conformCSV(List<Row> rows) {
        return rows.stream()
                .map(ConformedRowMapper::from)
                .collect(Collectors.toList());
    }

}
