package com.semrekavgaci.bilknot;

import com.google.firebase.Timestamp;

public class Item {

    public String userName;
    public String description;
    public String downloadUrl;
    public Timestamp date;

    public Item(String userName, String description, String downloadUrl, Timestamp date) {
        this.userName = userName;
        this.description = description;
        this.downloadUrl = downloadUrl;
        this.date = date;
    }
}
