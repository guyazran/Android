package com.example.guyazran.mysmsapp;

/**
 * Created by guyazran on 8/19/15.
 */
public class MySMS {
    private String message, recipient;

    public MySMS(String message, String recipient) {
        this.message = message;
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return "To: " + recipient + " Message: " + message;
    }
}
