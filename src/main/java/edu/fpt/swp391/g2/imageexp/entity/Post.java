package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Data
public class Post {
    private final int id;
    private int userId;
    private int picId;
    private String title;
    private String description;
    private List<Integer> categoryIdList = Collections.emptyList();
    private Date createdAt;
    private Date updatedAt;
    private String keyword;
    private String status;

    public JsonObject toJsonObject() {
        JsonObject postJson = new JsonObject();
        postJson.set("id", id);
        postJson.set("userId", userId);
        postJson.set("title", title);
        postJson.set("description", description);
        JsonArray categoryIdJson = new JsonArray();
        for (Integer categoryId : categoryIdList) {
            categoryIdJson.add(categoryId);
        }
        postJson.set("categoryId", categoryIdJson);
        postJson.set("createdAt", Utils.convertDateToString(createdAt));
        postJson.set("updatedAt", Utils.convertDateToString(updatedAt));
        postJson.set("keyword", keyword);
        postJson.set("status", status);
        return postJson;
    }
}
