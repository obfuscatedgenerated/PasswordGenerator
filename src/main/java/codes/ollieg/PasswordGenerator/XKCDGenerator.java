package codes.ollieg.PasswordGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;

public class XKCDGenerator {
    private final SecureRandom rng = new SecureRandom();
    private final ArrayList<String> dictionary;

    public XKCDGenerator() throws IOException {
        dictionary = is2al(getClass().getResourceAsStream("filter_dict.txt"));
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

    public String generate(int length, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int line = rng.nextInt(dictionary.size());
            sb.append(dictionary.get(line).toLowerCase());
            if (i < length-1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
