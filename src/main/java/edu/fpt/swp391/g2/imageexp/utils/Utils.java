package edu.fpt.swp391.g2.imageexp.utils;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import org.apache.commons.codec.digest.DigestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;

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
     * Get the value list from the json value
     *
     * @param jsonValue      the json value
     * @param checkPredicate the check-value predicate
     * @param getFunction    the get-value function
     * @param <T>            the type of the value
     * @return the value list
     */
    public static <T> List<T> getListFromJsonValue(JsonValue jsonValue, Predicate<JsonValue> checkPredicate, Function<JsonValue, T> getFunction) {
        List<T> list = new ArrayList<>();
        if (checkPredicate.test(jsonValue)) {
            list.add(getFunction.apply(jsonValue));
        } else if (jsonValue.isArray()) {
            JsonArray jsonArray = jsonValue.asArray();
            for (JsonValue value : jsonArray) {
                if (checkPredicate.test(value)) {
                    list.add(getFunction.apply(value));
                }
            }
        }
        return list;
    }

    /**
     * Get the integer list from the json value
     *
     * @param jsonValue the json value
     * @return the integer list
     */
    public static List<Integer> getIntListFromJsonValue(JsonValue jsonValue) {
        return getListFromJsonValue(jsonValue, JsonValue::isNumber, JsonValue::asInt);
    }

    /**
     * Get the string list from the json value
     *
     * @param jsonValue the json value
     * @return the string list
     */
    public static List<String> getStringListFromJsonValue(JsonValue jsonValue) {
        return getListFromJsonValue(jsonValue, JsonValue::isString, JsonValue::asString);
    }

    /**
     * Get the random 6-digit string
     *
     * @return the string
     */
    public static String getRandomDigitString() {
        int number = ThreadLocalRandom.current().nextInt(999999);
        return String.format("%06d", number);
    }
}
