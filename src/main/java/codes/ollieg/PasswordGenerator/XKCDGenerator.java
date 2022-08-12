package codes.ollieg.PasswordGenerator;

import java.io.IOException;

public class XKCDGenerator {
    private final CleanWordGenerator wordgen = new CleanWordGenerator();

    public XKCDGenerator() throws IOException {
    }

    public String generate(int length, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(wordgen.generate_filtered());
            if (i < length-1) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
