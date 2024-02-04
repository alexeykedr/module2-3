package org.example.pojo;

public enum Status {

    ACTIVE("Active"),
    DELETED("Deleted");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
