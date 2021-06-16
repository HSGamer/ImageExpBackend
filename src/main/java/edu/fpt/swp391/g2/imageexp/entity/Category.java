package edu.fpt.swp391.g2.imageexp.entity;

import com.eclipsesource.json.JsonObject;
import lombok.Data;

@Data
public class Category {
    private final int id;
    private String name;

    public JsonObject toJsonObject() {
        JsonObject categoryJson = new JsonObject();
        categoryJson.set("id", id);
        categoryJson.set("name", name);
        return categoryJson;
    }
}
