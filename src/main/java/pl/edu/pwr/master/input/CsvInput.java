package pl.edu.pwr.master.input;

import java.util.Objects;

public class CsvInput {

    private final String packagePath;
    private final String outerClass;
    private final int startLine;
    private final int endLine;
    private final String filePath;

    public CsvInput(String packagePath,
                    String outerClass,
                    int startLine,
                    int endLine,
                    String filePath) {
        this.packagePath = packagePath;
        this.outerClass = outerClass;
        this.startLine = startLine;
        this.endLine = endLine;
        this.filePath = filePath;
    }

    public String getClosestOuterClass() {
        String[] split = this.getOuterClass().split("\\.");
        return split[split.length - 1];
    }

    public String getPackagePath() {
        return packagePath;
    }

    public String getOuterClass() {
        return outerClass;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public String getFilePath() {
        return filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvInput csvInput = (CsvInput) o;
        return startLine == csvInput.startLine &&
                endLine == csvInput.endLine &&
                Objects.equals(packagePath, csvInput.packagePath) &&
                Objects.equals(outerClass, csvInput.outerClass) &&
                Objects.equals(filePath, csvInput.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packagePath, outerClass, startLine, endLine, filePath);
    }

}
