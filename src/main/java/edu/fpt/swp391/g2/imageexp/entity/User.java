package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import lombok.Data;

@Data
public class User {
    private final int userId;
    private String email;
    private String username;
    private String avatar;
    private String status;

    public JsonObject toJsonObject() {
        JsonObject jsonUser = new JsonObject();
        jsonUser.set("id", userId);
        jsonUser.set("name", username);
        jsonUser.set("email", email);
        jsonUser.set("avatar", avatar);
        jsonUser.set("status", status);
        return jsonUser;
    }
}
