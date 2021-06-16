package edu.fpt.swp391.g2.imageexp.utils;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * Get the integer list from the json value
     *
     * @param jsonValue the json value
     * @return the integer list
     */
    public static List<Integer> getIntListFromJsonValue(JsonValue jsonValue) {
        List<Integer> integerList = new ArrayList<>();
        if (jsonValue.isNumber()) {
            integerList.add(jsonValue.asInt());
        } else if (jsonValue.isArray()) {
            JsonArray jsonArray = jsonValue.asArray();
            for (JsonValue value : jsonArray) {
                if (jsonValue.isNumber()) {
                    integerList.add(value.asInt());
                }
            }
        }
        return integerList;
    }
}
