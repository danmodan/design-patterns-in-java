package solid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Single Responsability Principle
 * 
 * Cada classe deve possuir apenas uma responsabilidade.
 */
public class SRP {

    public static void main(String[] args) throws Exception {
        
        Journal journal = new Journal();
        journal.addEntry("Vai");
        journal.addEntry("Fui");
        System.out.println(journal);

        Persistence persistence = new Persistence();
        String filename = "/Users/tunas/VSCodeProjects/design-patterns-in-java/solid/journal.txt";
        persistence.saveToFile(journal, filename, true);
    }
}

class Journal {

    private final List<String> entries = new ArrayList<>();
    private static int count = 0;

    void addEntry(String text) {
        entries.add(++count + ": " + text);
    }

    void removeEntry(int index) {
        entries.remove(index);
    }

    @Override
    public String toString() {
        return String.join(System.lineSeparator(), entries);
    }

    /**
     * 
     * quebrando o princípio de responsabilidade única
     * 
     */
    void save(String filename) throws IOException {

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filename))) {
            bw.write(toString());
        }
    }
    // void load(String filename){}
    // void load(URI uri){}
}

class Persistence {

    void saveToFile(Journal journal, String filename, boolean overwrite) throws IOException {
        if(overwrite || new File(filename).exists()) {
            try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filename))) {
                bw.write(journal.toString());
            }
        }
    }
}