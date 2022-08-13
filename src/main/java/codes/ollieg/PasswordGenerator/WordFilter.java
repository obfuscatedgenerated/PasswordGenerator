package codes.ollieg.PasswordGenerator;

import opennlp.tools.stemmer.snowball.SnowballStemmer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class WordFilter {
    private static ArrayList<String> is2al(InputStream is) throws IOException {
        BufferedReader br;
        ArrayList<String> arr = new ArrayList<>();
        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null)
            arr.add(line);
        return arr;
    }

    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("filter_dict.txt");
        String newLine = System.getProperty("line.separator");

        InputStream dict_is = WordFilter.class.getResourceAsStream("dictionary.txt");
        ArrayList<String> dictionary = is2al(dict_is);
        InputStream bw_is = WordFilter.class.getResourceAsStream("badwords.txt");
        ArrayList<String> badwords = is2al(bw_is);

        ArrayList<String> seen = new ArrayList<>();

        SnowballStemmer stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);

        wordIter:
        for (String i : dictionary) {
            if (!seen.contains(i)) {
                seen.add(i);
                for (String j : badwords) {
                    if (i.toLowerCase().matches(j)) {
                        System.out.println("filtered "+i);
                        continue wordIter;
                    }
                }
                String s = (String) stemmer.stem(i);
                s = s.replaceAll("i$", "y").toLowerCase();
                if (s.length() < 4 || s.length() > 7) {
                    continue;
                }
                for (String j : badwords) {
                    if (s.matches(j)) {
                        System.out.println("Filtered stem: "+s);
                        continue wordIter;
                    }
                }
                fw.write(s + newLine);
            }
        }
        fw.close();
    }
}
