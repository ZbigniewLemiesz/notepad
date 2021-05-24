package notepad;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.stream.Stream;

public class FileRepository {

    private static final ObservableList<Path> RECENTS = FXCollections.observableArrayList();
    private static final String RESENTS_FILE = "resents.xml";
    private static File f;
    private static final Properties properties = new Properties();

    static Stream<String> openFile(File file) throws IOException {
        f = file;
        return Files.readAllLines(f.toPath()).stream();
    }

    static void closeFile() {
        boolean isThere = false;
        for (Path p : RECENTS) {
            if (p.toFile().getAbsolutePath().equals(f.getAbsolutePath())) {
                isThere = true;
                break;
            }
        }
        if (!isThere) {
            RECENTS.add(f.toPath());
        }
        saveRecentsToFile();
    }

    private static void saveRecentsToFile() {
        RECENTS.forEach(recentPath -> properties.setProperty(recentPath.getFileName().toString(), recentPath.toFile().getAbsolutePath()));
    }

     static ObservableList getRecents() {
        return RECENTS;
    }

    static void saveFile(Iterable<CharSequence> i, String charset) throws IOException {
        if (f != null) {
            Charset c = getCharset(charset != null ? charset : "UTF-8");
            Files.write(f.toPath(), i, c, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    static ObservableList<Path> loadRecents() throws IOException {
        if (new File(RESENTS_FILE).exists()) {
            InputStream in = new FileInputStream(RESENTS_FILE);
            properties.loadFromXML(in);
            properties.forEach((x, y) -> RECENTS.add(Paths.get(y.toString())));
        }
        return RECENTS;
    }

    static void newFile(String con) throws IOException {

    }

    private static Charset getCharset(String s) {
        return Charset.forName(s);
    }
}
