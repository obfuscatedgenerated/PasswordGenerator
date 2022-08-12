package ml.obfuscatedgenerated.PasswordGenerator;

import opennlp.tools.stemmer.snowball.SnowballStemmer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.security.SecureRandom;

public class CleanWordGenerator {
    private final SnowballStemmer stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
    private final ArrayList<String> dictionary;
    private final ArrayList<String> badwords;

    public CleanWordGenerator() throws IOException {
        InputStream dict_is = CleanWordGenerator.class.getResourceAsStream("dictionary.txt");
        dictionary = is2al(dict_is);
        InputStream bw_is = CleanWordGenerator.class.getResourceAsStream("dictionary.txt");
        badwords = is2al(bw_is);
    }

    private ArrayList<String> is2al(InputStream is) throws IOException {
        BufferedReader br;
        ArrayList<String> arr = new ArrayList<>();
        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        while((line = br.readLine()) != null)
            arr.add(line);
        return arr;
    }
    public String generate_unfiltered() { // this is probably very inefficient (actually, not terrible, but can still be improved at a later date)
        SecureRandom rng = new SecureRandom(); // would use RandomAccessFile, but we are using resources
        int line = rng.nextInt(dictionary.size());
        return (String) stemmer.stem(dictionary.get(line).toLowerCase());
    }
    public String generate_filtered() { // could just provide a filtered wordlist, but this is fine for now
        genLoop: while (true) {
            String s = generate_unfiltered();
            for (String i : badwords) {
                if (s.matches(i)) {
                    continue genLoop;
                }
            }
            return s;
        }
    }
}
