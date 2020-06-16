package com.example.androidchatclient.data.model;

public abstract class BaseModel {
    private String name;
    private String isComnect;
    private String lastDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsComnect() {
        return isComnect;
    }

    public void setIsComnect(String isComnect) {
        this.isComnect = isComnect;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }
}
