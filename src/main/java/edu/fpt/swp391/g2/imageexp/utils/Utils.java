package edu.fpt.swp391.g2.imageexp.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The utility class for general purposes
 */
public class Utils {
    private Utils() {
        // EMPTY
    }

    /**
     * Hash the string with MD5-Hex
     *
     * @param input the input string
     * @return the hashed string
     */
    public static String hashMD5(String input) {
        return DigestUtils.md5Hex(input);
    }
}
