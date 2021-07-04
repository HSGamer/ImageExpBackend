package edu.fpt.swp391.g2.imageexp.entity;
import lombok.Data;
import com.eclipsesource.json.JsonObject;

@Data
public class Comment {
    private final int commentID;
    private int postId;
    private int userId;
    private String comment;
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.set("commentID", commentID);
        jsonObject.set("postId", postId);
        jsonObject.set("userId", userId);
        jsonObject.set("comment", comment);
        return jsonObject;
    }
}
