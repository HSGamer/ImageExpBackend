package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import lombok.Data;

/**
 * The user account
 */
@Data
public class User {
    private final int userId;
    private String email;
    private String username;
    private String avatar;
    private String status;
    private boolean verified;

    /**
     * Convert to the json object
     *
     * @return the json object
     */
    public JsonObject toJsonObject() {
        JsonObject jsonUser = new JsonObject();
        jsonUser.set("id", userId);
        jsonUser.set("name", username);
        jsonUser.set("email", email);
        jsonUser.set("avatar", avatar);
        jsonUser.set("status", status);
        jsonUser.set("verified", verified);
        return jsonUser;
    }

}
