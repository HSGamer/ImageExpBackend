package edu.fpt.swp391.g2.imageexp.entity;
import edu.fpt.swp391.g2.imageexp.utils.Utils;
import lombok.Data;
import com.eclipsesource.json.JsonObject;

import java.util.Date;

@Data
public class Comment {
    private final int commentID;
    private int postId;
    private int userId;
    private String comment;
    private Date createdAt;
    private Date updatedAt;

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
