package edu.fpt.swp391.g2.imageexp.utils;

import java.util.HashMap;
import java.util.Map;

public class WebUtils {
    private WebUtils() {
        // EMPTY
    }

    /**
     * Format the raw parameters (key1=value1&key2=value2&...) to map
     *
     * @param rawParams the raw string of parameters
     * @return the map
     */
    public static Map<String, String> formatParameters(String rawParams) {
        Map<String, String> map = new HashMap<>();
        for (String s : rawParams.split("&")) {
            String[] split = s.split("=", 2);
            map.put(split[0], split.length > 1 ? split[1] : "");
        }
        return map;
    }
}
