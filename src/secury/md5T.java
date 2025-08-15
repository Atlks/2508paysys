package secury;

import java.util.Arrays;

import static org.apache.commons.codec.digest.DigestUtils.md5;

public class md5T {

    public static void main(String[] args) {
        System.out.println(bin2hex(md5(md5("666"))));;
        System.out.println(bin2hex(md5("fae0b27c451c728867a567e8c1bb4e53")));
    }

    private static String bin2hex(byte[] bytes) {
        if (bytes == null || bytes.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
