package org.example.models;

import java.util.Date;

public class Message {
    private String id;
    private String clientId;
    private String message;
    private Date timestamp;

    public Message(String clientId, String message) {
        this.clientId = clientId;
        this.message = message;
        this.timestamp = new Date();
    }

    public Message(String id, String clientId, String message, Date timestamp) {
        this.id = id;
        this.clientId = clientId;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
