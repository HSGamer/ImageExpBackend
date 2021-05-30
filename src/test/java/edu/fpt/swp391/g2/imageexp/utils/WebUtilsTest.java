package edu.fpt.swp391.g2.imageexp.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class WebUtilsTest {
    private static Map<String, String> testMap = new HashMap<>();
    private static String testString;
    private static String testFormattedString;

    @BeforeAll
    static void setUp() throws UnsupportedEncodingException {
        testMap.put("test1", "value1");
        testMap.put("test2", "");
        testMap.put("test3", "==");
        testMap.put("test4", "````");
        testString = testMap.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));
        testFormattedString = URLEncoder.encode(testString, StandardCharsets.UTF_8.toString());
    }

    @Test
    void testSimpleString() {
        try {
            Map<String, String> formattedMap = WebUtils.formatParameters(testString);
            Assertions.assertEquals(testMap, formattedMap);
        } catch (UnsupportedEncodingException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void testFormatString() {
        try {
            Map<String, String> formattedMap = WebUtils.formatParameters(testFormattedString);
            Assertions.assertEquals(testMap, formattedMap);
        } catch (UnsupportedEncodingException e) {
            Assertions.fail(e);
        }
    }

    @AfterAll
    static void clear() {
        testMap.clear();
    }
}