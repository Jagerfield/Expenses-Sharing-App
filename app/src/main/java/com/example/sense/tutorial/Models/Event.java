package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.IOException;

@DatabaseTable(tableName = "Event")
public class Event
{
    @JsonProperty("dbId")
    @DatabaseField(id = true, columnName = Columns.DBID)
    private long dbId;

    @JsonProperty("id")
    @DatabaseField(columnName = Columns.ID)
    private long id;

    @JsonProperty("admin")
    @DatabaseField(columnName = Columns.ADMIN)
    private long admin;

    @JsonProperty("name")
    @DatabaseField(columnName = Columns.NAME)
    private String name;

    public Event(){}

    @JsonIgnore
    public Event(long dbId, long id, long admin, String name) {
        this.dbId = dbId;
        this.id = id;
        this.admin = admin;
        this.name = name;
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
    public long getAdmin() {
        return admin;
    }
    @JsonIgnore
    public void setAdmin(long admin) {
        this.admin = admin;
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
    public long getDbId() {
        return dbId;
    }
    @JsonIgnore
    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    @JsonIgnore
    public Event getAppOwnerObj(String jsonString)
    {
        Event event = null;
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            event = mapper.readValue(jsonString, Event.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return event;
    }

    @JsonIgnore
    public String getAppOwnerObjJson(Event event)
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString= "";

        try
        {
            jsonString = mapper.writeValueAsString(event);

        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return jsonString;
    }

    public interface Columns {
        String NAME = "name";
        String ADMIN = "admin";
        String ID = "id";
        String DBID = "dbId";
    }
}




