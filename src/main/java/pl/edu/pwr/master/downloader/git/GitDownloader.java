package pl.edu.pwr.master.downloader.git;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitDownloader {

    private static final Logger LOGGER = Logger.getLogger(GitDownloader.class.getName());
    private static final String DEFAULT_OUTPUT_REPOSITORY_NAME = "unknown-repository";
    public static final String DEFAULT_OUTPUT_REPOSITORY_DIR;

    private final String repositoryUri;
    private String hash;
    private String outputDirectory = DEFAULT_OUTPUT_REPOSITORY_DIR;

    static {
        DEFAULT_OUTPUT_REPOSITORY_DIR = System.getProperty("user.home") + File.separator + "java-metrics-source-repos";
    }

    public GitDownloader(String repositoryUri) {
        this.repositoryUri = repositoryUri;
    }

    public GitDownloader(String repositoryUri, String hash) {
        this.repositoryUri = repositoryUri;
        this.hash = hash;
    }

    public void setOutputDirectory(String outputDirectory) {
        if (outputDirectory.endsWith(File.separator)) {
            outputDirectory = outputDirectory.substring(0, outputDirectory.length() - 2);
        }
        this.outputDirectory = outputDirectory;
    }

    public void checkout() {
        String repositoryName = getRepositoryName(repositoryUri);
        String outputPath = this.outputDirectory + File.separator + repositoryName;

        try {
            prepareDirectoryTree(outputPath);
            checkoutRepository(this.repositoryUri, outputPath);
            if (this.hash != null && !this.hash.isEmpty()) {
                resetHardToHash(outputPath, this.hash);
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private String getRepositoryName(String repositoryUri) {
        Pattern pattern = Pattern.compile(".*:(.*)\\.git");
        Matcher matcher = pattern.matcher(repositoryUri);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return DEFAULT_OUTPUT_REPOSITORY_NAME;
        }
    }

    private void prepareDirectoryTree(String path) throws IOException {
        File f = new File(path);
        if (f.mkdirs()) {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.info("Directory created: " + path);
            }
        } else {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.severe(String.format("Directory %s cannot be created! It might already exist!", path));
            }
        }
    }

    private void checkoutRepository(String repositoryUri, String outputPath) throws IOException, InterruptedException {
        Process process = new ProcessBuilder("git", "clone", repositoryUri, outputPath).redirectErrorStream(true).start();

        showProcessLog(process);

        int exitCode = process.waitFor();
        process.destroy();
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info(String.format("Checkout process exited with code %d. You can find the output directory at %s.", exitCode, outputPath));
        }
    }

    private void resetHardToHash(String outputPath, String hash) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("git", "reset", "--hard", hash);
        pb.directory(new File(outputPath));
        pb.redirectErrorStream(true);

        Process process = pb.start();

        showProcessLog(process);

        int exitCode = process.waitFor();
        process.destroy();
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.info(String.format("Getting hash process exited with code %d.", exitCode));
        }
    }

    private void showProcessLog(Process process) throws IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (LOGGER.isLoggable(Level.INFO)) {
                LOGGER.info(line);
            }
        }
    }

}
