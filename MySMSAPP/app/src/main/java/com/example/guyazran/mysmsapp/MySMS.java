package com.example.guyazran.mysmsapp;

/**
 * Created by guyazran on 8/19/15.
 */
public class MySMS {
    private String message, recipient;
    private boolean isSent, isDelivered;

    public MySMS(String message, String recipient) {
        this.message = message;
        this.recipient = recipient;
        this.isSent = false;
        this.isDelivered = false;
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

    public boolean isSent() {
        return isSent;
    }

    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    @Override
    public String toString() {
        return "To: " + recipient + " Message: " + message;
    }
}
