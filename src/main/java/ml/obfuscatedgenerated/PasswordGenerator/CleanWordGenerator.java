package ml.obfuscatedgenerated.PasswordGenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

public class CleanWordGenerator {
    private static ArrayList<String> is2al(InputStream is) throws IOException {
        BufferedReader br = null;
        ArrayList<String> arr = new ArrayList<>();
        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        while((line = br.readLine()) != null)
            arr.add(line);
        return arr;
    }
    public static String generate_unfiltered() throws IOException { // this is probably very inefficient (actually, not terrible, but can still be improved at a later date)
        InputStream is = CleanWordGenerator.class.getResourceAsStream("dictionary.txt");
        BufferedReader br = null;
        ArrayList<String> arr = is2al(is);
        Random rng = new Random(); // would use RandomAccessFile, but we are using resources
        int line = rng.nextInt(arr.size());
        return arr.get(line).toLowerCase();
    }
    public static String generate_filtered() throws IOException { // could just provide a filtered wordlist, but this is fine for now
        InputStream is = CleanWordGenerator.class.getResourceAsStream("badwords.txt");
        ArrayList<String> arr = is2al(is);
        genLoop: while (true) {
            String s = generate_unfiltered();
            for (String i : arr) {
                if (s.matches(i)) {
                    continue genLoop;
                }
            }
            return s;
        }
    }
}
