package com.example.androidchatclient.data.model;

import java.util.Objects;

public class Message {
    private String login;
    private String msg;
    private String date;
    private String cmd;
    private String owner;

    public Message() {}

    public Message(String login, String msg, String date) {
        this.login = login;
        this.msg = msg;
        this.date = date;
    }

    public Message(String login, String msg, String date, String cmd) {
        this.login = login;
        this.msg = msg;
        this.date = date;
        this.cmd = cmd;
    }

    public String getOwner() { return owner; }

    public void setOwner(String owner) { this.owner = owner; }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "Message{" +
                "login='" + login + '\'' +
                ", msg='" + msg + '\'' +
                ", date='" + date + '\'' +
                ", cmd='" + cmd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(login, message.login) &&
                Objects.equals(msg, message.msg) &&
                Objects.equals(date, message.date) &&
                Objects.equals(cmd, message.cmd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, msg, date, cmd);
    }
}
