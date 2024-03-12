package org.example.pojo;

public enum Status {

    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
