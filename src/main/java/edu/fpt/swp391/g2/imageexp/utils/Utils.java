package edu.fpt.swp391.g2.imageexp.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The utility class for general purposes
 */
public class Utils {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

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

    /**
     * Parse date from string
     *
     * @param raw the string
     * @return the date
     */
    public static Date getDate(String raw) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.parse(raw);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * Parse string from date
     *
     * @param date the date
     * @return the string
     */
    public static String convertDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }
}
