package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Post {
    private final int id;
    private int userId;
    private int picId;
    private int categoryId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String keyword;
    private String status;
    private int likes;

    public JsonObject toJsonObject() {
        JsonObject postJson = new JsonObject();
        postJson.set("id", id);
        postJson.set("userId", userId);
        postJson.set("categoryId", categoryId);
        postJson.set("createdAt", createdAt.toString());
        postJson.set("updatedAt", updatedAt.toString());
        postJson.set("keyword", keyword);
        postJson.set("status", status);
        postJson.set("likes", likes);
        return postJson;
    }
}
