package pl.edu.pwr.master;

public class Row {

    public static final String CLASS_TYPE = "class";
    public static final String METHOD_TYPE = "function";

    private Integer id;
    private String type;
    private String codeName;
    private String repository;
    private String commitHash;
    private String link;
    private String startLine;
    private String endLine;
    private String filePath;

    public Row(Integer id,
               String type,
               String codeName,
               String repository,
               String commitHash,
               String link,
               String startLine,
               String endLine,
               String filePath) {
        this.id = id;
        this.type = type;
        this.codeName = codeName;
        this.repository = repository;
        this.commitHash = commitHash;
        this.link = link;
        this.startLine = startLine;
        this.endLine = endLine;
        this.filePath = filePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getCommitHash() {
        return commitHash;
    }

    public void setCommitHash(String commitHash) {
        this.commitHash = commitHash;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStartLine() {
        return startLine;
    }

    public void setStartLine(String startLine) {
        this.startLine = startLine;
    }

    public String getEndLine() {
        return endLine;
    }

    public void setEndLine(String endLine) {
        this.endLine = endLine;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", codeName='" + codeName + '\'' +
                ", repository='" + repository + '\'' +
                ", commitHash='" + commitHash + '\'' +
                ", link='" + link + '\'' +
                ", startLine='" + startLine + '\'' +
                ", endLine='" + endLine + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }

}
