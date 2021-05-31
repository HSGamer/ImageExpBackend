package edu.fpt.swp391.g2.imageexp.entity;

import lombok.Data;

@Data
public class User {
    private final int userId;
    private String email;
    private String username;
    private String avatar;
    private String status;
}
