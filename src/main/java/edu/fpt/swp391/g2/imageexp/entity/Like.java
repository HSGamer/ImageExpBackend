package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import lombok.Data;

@Data
public class Like {
    private final int id;
    private int postId;
    private int userId;

    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("id", id);
        jsonObject.set("postId", postId);
        jsonObject.set("userId", userId);
        return jsonObject;
    }
}
