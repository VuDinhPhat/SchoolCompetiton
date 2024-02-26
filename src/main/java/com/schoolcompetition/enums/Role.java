package com.schoolcompetition.enums;

public enum Role {
    SUPER_ADMIN("Supper_Admin"),
    ADMIN("Admin"),
    TRAINER("Trainer");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }


}
