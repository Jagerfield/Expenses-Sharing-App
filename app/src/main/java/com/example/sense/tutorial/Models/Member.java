package com.example.sense.tutorial.Models;

public class Member
{
    private long dbId;
    private long id;
    private String name;
    private String gmail;
    private String udid;
    private boolean account_status;

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public boolean isAccount_status() {
        return account_status;
    }

    public void setAccount_status(boolean account_status) {
        this.account_status = account_status;
    }

}
