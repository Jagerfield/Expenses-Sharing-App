package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;

import java.io.IOException;

public class Chat
{

    @JsonProperty(Columns.ORMID)
    @DatabaseField(id = true, columnName = Columns.ORMID)
    private long ormId;

    @JsonProperty(Columns.ID)
    @DatabaseField(columnName = Columns.ID)
    private long id;

    @JsonProperty(Columns.ISSUER)
    @DatabaseField(columnName = Columns.ISSUER)
    private long issuer;

    @JsonProperty(Columns.EVENTID)
    @DatabaseField(columnName = Columns.EVENTID)
    private long event_id;

    @JsonProperty(Columns.MESSAGE)
    @DatabaseField(columnName = Columns.MESSAGE)
    private long message;

    public Chat() {
    }

    @JsonIgnore
    public Chat(long ormId, long id, long issuer, long event_id, long message) {
        this.ormId = ormId;
        this.id = id;
        this.issuer = issuer;
        this.event_id = event_id;
        this.message = message;
    }

    @JsonIgnore
    public Chat getAppOwnerObj(String jsonString)
    {
        Chat chat = null;
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            chat = mapper.readValue(jsonString, Chat.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return chat;
    }

    @JsonIgnore
    public String getAppOwnerObjJson(Chat chat)
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString= "";

        try
        {
            jsonString = mapper.writeValueAsString(chat);

        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return jsonString;
    }

    public interface Columns
    {
        String ID = "id";
        String ORMID = "ormId";
        String ISSUER = "issuer";
        String EVENTID  = "event_id";
        String MESSAGE = "message";
    }
}
