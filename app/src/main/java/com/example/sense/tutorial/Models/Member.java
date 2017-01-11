package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Member
{
    @JsonProperty("dbId")
    private long dbId;
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("gmail")
    private String gmail;
    @JsonProperty("udid")
    private String udid;
    @JsonProperty("account_status")
    private boolean account_status;

    public Member() { }

    @JsonIgnore
    public Member(long dbId, long id, String name, String gmail, String udid, boolean account_status) {
        this.dbId = dbId;
        this.id = id;
        this.name = name;
        this.gmail = gmail;
        this.udid = udid;
        this.account_status = account_status;
    }

    @JsonIgnore
    public long getDbId() {
        return dbId;
    }
    @JsonIgnore
    public void setDbId(long dbId) {
        this.dbId = dbId;
    }
    @JsonIgnore
    public long getId() {
        return id;
    }
    @JsonIgnore
    public void setId(long id) {
        this.id = id;
    }
    @JsonIgnore
    public String getName() {
        return name;
    }
    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }
    @JsonIgnore
    public String getGmail() {
        return gmail;
    }
    @JsonIgnore
    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
    @JsonIgnore
    public String getUdid() {
        return udid;
    }
    @JsonIgnore
    public void setUdid(String udid) {
        this.udid = udid;
    }
    @JsonIgnore
    public boolean isAccount_status() {
        return account_status;
    }
    @JsonIgnore
    public void setAccount_status(boolean account_status) {
        this.account_status = account_status;
    }

    @JsonIgnore
    public Member getAppOwnerObj(String jsonString)
    {
        Member member = null;
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            member = mapper.readValue(jsonString, Member.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return member;
    }

    @JsonIgnore
    public String getAppOwnerObjJson(Member member)
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString= "";

        try
        {
            jsonString = mapper.writeValueAsString(member);

        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return jsonString;
    }

}

