package com.pushnotify.cipher.sqlite;


public class pushsave {
    private int id;
    private String title;
    private String message;
    private byte[] image;

    public pushsave(String title, String message, byte[] image, int id) {
        this.title = title;
        this.message = message;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
