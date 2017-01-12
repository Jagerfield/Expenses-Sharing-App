package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.IOException;

@DatabaseTable(tableName = "EventParticipant")
public class EventParticipant
{
    @JsonProperty(Columns.ORMID)
    @DatabaseField(id = true, columnName = Columns.ORMID)
    private long ormId;

    @JsonProperty(Columns.ID)
    @DatabaseField(columnName = Columns.ID)
    private long id;

    @JsonProperty(Columns.EVENTID)
    @DatabaseField(columnName = Columns.EVENTID)
    private long event_id;

    @JsonProperty(Columns.MEMBERID)
    @DatabaseField(columnName = Columns.MEMBERID)
    private long member_id;

    public EventParticipant() { }

    @JsonIgnore
    public EventParticipant(long ormId, long id, long event_id, long member_id) {
        this.ormId = ormId;
        this.id = id;
        this.event_id = event_id;
        this.member_id = member_id;
    }

    @JsonIgnore
    public long getMember_id() {
        return member_id;
    }
    @JsonIgnore
    public void setMember_id(long member_id) {
        this.member_id = member_id;
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
    public long getEvent_id() {
        return event_id;
    }
    @JsonIgnore
    public void setEvent_id(long event_id) {
        this.event_id = event_id;
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
    public EventParticipant getObj(String jsonString)
    {
        EventParticipant eventParticipant = null;
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            eventParticipant = mapper.readValue(jsonString, EventParticipant.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return eventParticipant;
    }

    @JsonIgnore
    public String getJsonString(EventParticipant eventParticipant)
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString= "";

        try
        {
            jsonString = mapper.writeValueAsString(eventParticipant);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return jsonString;
    }

    public interface Columns {
        String MEMBERID = "member_id";
        String EVENTID = "event_id";
        String ID = "id";
        String ORMID = "ormId";
    }
}
