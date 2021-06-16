package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import lombok.Data;

import java.util.Date;

@Data
public class Post {
    private final int id;
    private int userId;
    private int picId;
    private int categoryId;
    private Date createdAt;
    private Date updatedAt;
    private String keyword;
    private String status;
    private int likes;

    public JsonObject toJsonObject() {
        JsonObject postJson = new JsonObject();
        postJson.set("id", id);
        postJson.set("userId", userId);
        postJson.set("categoryId", categoryId);
        postJson.set("createdAt", Utils.convertDateToString(createdAt));
        postJson.set("updatedAt", Utils.convertDateToString(updatedAt));
        postJson.set("keyword", keyword);
        postJson.set("status", status);
        postJson.set("likes", likes);
        return postJson;
    }
}
