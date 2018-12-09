package com.example.angel.timein;

public class Clients {

    String clientId;
    String clientName;
    String userId;

    public Clients(){

    }

    public Clients(String clientId, String clientName, String userId) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

