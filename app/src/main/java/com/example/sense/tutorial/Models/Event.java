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
    @JsonProperty(Columns.ORMID)
    @DatabaseField(id = true, columnName = Columns.ORMID)
    private long ormId;

    @JsonProperty(Columns.ID)
    @DatabaseField(columnName = Columns.ID)
    private long id;

    @JsonProperty(Columns.ADMIN)
    @DatabaseField(columnName = Columns.ADMIN)
    private long admin;

    @JsonProperty(Columns.NAME)
    @DatabaseField(columnName = Columns.NAME)
    private String name;

    @JsonProperty(Columns.DESCRIPTION)
    @DatabaseField(columnName = Columns.DESCRIPTION)
    private String description;

    @JsonProperty(Columns.CREATEDAT)
    @DatabaseField(columnName = Columns.CREATEDAT)
    private String created_at;

    public Event(){}

    @JsonIgnore
    public Event(long ormId, long id, long admin, String name) {
        this.ormId = ormId;
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
    public long getOrmId() {
        return ormId;
    }
    @JsonIgnore
    public void setOrmId(long ormId) {
        this.ormId = ormId;
    }
    @JsonIgnore
    public String getDescription() {
        return description;
    }
    @JsonIgnore
    public void setDescription(String description) {
        this.description = description;
    }
    @JsonIgnore
    public String getCreated_at() {
        return created_at;
    }
    @JsonIgnore
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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
        String ORMID = "ormId";
        String DESCRIPTION = "description";
        String CREATEDAT = "created_at";
    }
}




