package edu.fpt.swp391.g2.imageexp.utils;

import com.eclipsesource.json.JsonArray;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class UtilsTest {

    @Test
    void hashMD5() {
        assertEquals("098f6bcd4621d373cade4e832627b4f6", Utils.hashMD5("test"));
    }

    @Test
    void getDate() {
        Date date = Utils.getDate("2020-12-13 23:59:58");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(2020, calendar.get(Calendar.YEAR));
        assertEquals(12, calendar.get(Calendar.MONTH) + 1); // Month starts from 0
        assertEquals(13, calendar.get(Calendar.DATE));
        assertEquals(23, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(59, calendar.get(Calendar.MINUTE));
        assertEquals(58, calendar.get(Calendar.SECOND));
    }

    @Test
    void convertDateToString() {
        String dateString = "2020-12-13 23:59:58";
        Date date = Utils.getDate(dateString);
        assertEquals(dateString, Utils.convertDateToString(date));
    }

    @Test
    void getIntListFromJsonValue() {
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4);
        JsonArray jsonArray = new JsonArray();
        list.forEach(jsonArray::add);
        assertEquals(list, Utils.getIntListFromJsonValue(jsonArray));
    }

    @Test
    void getStringListFromJsonValue() {
        List<String> list = Arrays.asList("test", "test1", "test2");
        JsonArray jsonArray = new JsonArray();
        list.forEach(jsonArray::add);
        assertLinesMatch(list, Utils.getStringListFromJsonValue(jsonArray));
    }
}