package ml.obfuscatedgenerated.PasswordGenerator;

import java.security.SecureRandom;
import java.util.List;

public class Generator {
    private SecureRandom rng = new SecureRandom();

    private char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private char[] upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private char[] numeral = "1234567890".toCharArray();
    private char[] symbol = "~`!@#$%^&*()_-+={[}]|\\:;\"'<,>.?/".toCharArray();

    private char[] push_char(char[] array, char push) {
        char[] pArr = {push};
        return concat_char_arr(array,pArr);
    }

    private char[] concat_char_arr(char[] a1, char[] a2) {
        char[] result = new char[a1.length + a2.length];
        System.arraycopy(a1, 0, result, 0, a1.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }

    public String generate(List<String> include, int length) {
        char[] staged = {};
        char[] result = {};
        if (include.contains("alpha")) {
            staged = concat_char_arr(staged, alpha);
        }
        if (include.contains("upper")) {
            staged = concat_char_arr(staged, upper);
        }
        if (include.contains("numeral")) {
            staged = concat_char_arr(staged, numeral);
        }
        if (include.contains("symbol")) {
            staged = concat_char_arr(staged, symbol);
        }
        for (int i = 0; i < length; i++) {
            result = push_char(result,staged[rng.nextInt(staged.length)]);
        }
        return new String(result);
    }
}
