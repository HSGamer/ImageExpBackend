package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import lombok.Data;

/**
 * The picture from the {@link User}'s gallery
 */
@Data
public class Picture {
    private final int id;
    private int userId;
    private String content;

    /**
     * Convert to the json object
     *
     * @param withContent should the picture content be included ?
     * @return the json object
     */
    public JsonObject toJsonObject(boolean withContent) {
        JsonObject pictureJson = new JsonObject();
        pictureJson.set("id", id);
        pictureJson.set("userId", userId);
        if (withContent) {
            pictureJson.set("picture", content);
        }
        return pictureJson;
    }

    /**
     * Convert to the json object
     *
     * @return the json object
     */
    public JsonObject toJsonObject() {
        return toJsonObject(true);
    }
}
