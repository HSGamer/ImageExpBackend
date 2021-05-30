package edu.fpt.swp391.g2.imageexp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * The utility class for web handling
 */
public class WebUtils {
    private WebUtils() {
        // EMPTY
    }

    /**
     * Format the raw parameters (key1=value1&key2=value2&...) to map
     *
     * @param rawParams the raw string of parameters
     * @return the map
     * @throws UnsupportedEncodingException if the encoding is not supported
     */
    public static Map<String, String> formatParameters(String rawParams) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        for (String s : rawParams.split("&")) {
            String[] split = s.split("=", 2);
            map.put(split[0], URLDecoder.decode(split.length > 1 ? split[1] : "", StandardCharsets.UTF_8.toString()));
        }
        return map;
    }
}
