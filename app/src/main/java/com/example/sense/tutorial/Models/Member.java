package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;

import java.io.IOException;

public class Member
{
    @JsonProperty(Columns.ORMID)
    @DatabaseField(id = true, columnName = Columns.ORMID)
    private long ormId;

    @JsonProperty(Columns.ID)
    @DatabaseField(columnName = Columns.ID)
    private long id;

    @JsonProperty(Columns.NAME)
    @DatabaseField(columnName = Columns.NAME)
    private String name;

    @JsonProperty(Columns.GMAIL)
    @DatabaseField(columnName = Columns.GMAIL)
    private String gmail;

    @JsonProperty(Columns.UDID)
    @DatabaseField(columnName = Columns.UDID)
    private String udId;

    @JsonProperty(Columns.ACCOUNTSTATUS)
    @DatabaseField(columnName = Columns.ACCOUNTSTATUS)
    private boolean account_status;

    public Member() { }

    @JsonIgnore
    public Member(long ormId, long id, String name, String gmail, String udId, boolean account_status) {
        this.ormId = ormId;
        this.id = id;
        this.name = name;
        this.gmail = gmail;
        this.udId = udId;
        this.account_status = account_status;
    }

    @JsonIgnore
    public long getOrmId() {
        return ormId;
    }
    @JsonIgnore
    public void setOrmId(long ormId) {
        this.ormId = ormId;
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
    public String getUdId() {
        return udId;
    }
    @JsonIgnore
    public void setUdId(String udId) {
        this.udId = udId;
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
    public Member getObj(String jsonString)
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
    public String getJsonString(Member member)
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

    public interface Columns {
        String NAME = "name";
        String GMAIL = "gmail";
        String ID = "id";
        String ORMID = "ormId";
        String UDID = "udId";
        String ACCOUNTSTATUS = "account_status";
    }

}

