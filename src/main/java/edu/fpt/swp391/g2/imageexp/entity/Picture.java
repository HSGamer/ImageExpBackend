package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import lombok.Data;

@Data
public class Picture {
    private final int id;
    private int userId;
    private String picture;


    public JsonObject toJsonObject() {
        JsonObject pictureJson = new JsonObject();
        pictureJson.set("id", id);
        pictureJson.set("userId", userId);
        pictureJson.set("picture", picture);
        return pictureJson;
    }

}
