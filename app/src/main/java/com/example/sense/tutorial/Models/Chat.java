package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.IOException;

@DatabaseTable(tableName = "Chat")
public class Chat
{

    @JsonProperty(Columns.ORMID)
    @DatabaseField(id = true, columnName = Columns.ORMID)
    private long ormId;

    @JsonProperty(Columns.CHA_TID)
    @DatabaseField(columnName = Columns.CHA_TID)
    private long chat_id;

    @JsonProperty(Columns.CHAT_ISSUER)
    @DatabaseField(columnName = Columns.CHAT_ISSUER)
    private long chat_issuer;

    @JsonProperty(Columns.EVENT_ID)
    @DatabaseField(columnName = Columns.EVENT_ID)
    private long event_id;

    @JsonProperty(Columns.CHAT_MESSAGE)
    @DatabaseField(columnName = Columns.CHAT_MESSAGE)
    private String chat_message;

    public Chat() {
    }

    @JsonIgnore
    public Chat(long ormId, long chat_id, long chat_issuer, long event_id, String chat_message) {
        this.ormId = ormId;
        this.chat_id = chat_id;
        this.chat_issuer = chat_issuer;
        this.event_id = event_id;
        this.chat_message = chat_message;
    }

    @JsonIgnore
    public static Chat getObjFromJackson(String jsonString)
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
    public static String getJsonString(Chat chat)
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
        String CHA_TID = "chat_id";
        String ORMID = "ormId";
        String CHAT_ISSUER = "chat_issuer";
        String EVENT_ID = "event_id";
        String CHAT_MESSAGE = "chat_message";
    }
}
