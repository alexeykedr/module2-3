package org.example.model;

public enum Status {

    ERR_INPUT("Incorrect input! Try again!"),
    ERR_COMMAND("Incorrect command! Select correct command!"),
    ERR_ID("Incorrect ID! Try again!");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
