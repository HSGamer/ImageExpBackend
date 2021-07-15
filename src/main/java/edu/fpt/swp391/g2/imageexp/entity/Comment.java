package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import lombok.Data;

import java.util.Date;

/**
 * The comment of the {@link Post}
 */
@Data
public class Comment {
    private final int commentID;
    private int postId;
    private int userId;
    private String comment;
    private Date createdAt;
    private Date updatedAt;

    /**
     * Convert to the json object
     *
     * @return the json object
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("commentID", commentID);
        jsonObject.set("postId", postId);
        jsonObject.set("userId", userId);
        jsonObject.set("comment", comment);
        jsonObject.set("createdAt", Utils.convertDateToString(createdAt));
        jsonObject.set("updatedAt", Utils.convertDateToString(updatedAt));
        return jsonObject;
    }
}
