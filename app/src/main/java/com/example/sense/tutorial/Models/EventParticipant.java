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

    @JsonProperty(Columns.EP_ID)
    @DatabaseField(columnName = Columns.EP_ID)
    private long ep_id;

    @JsonProperty(Columns.EVEN_TID)
    @DatabaseField(columnName = Columns.EVEN_TID)
    private long event_id;

    @JsonProperty(Columns.MEMBER_ID)
    @DatabaseField(columnName = Columns.MEMBER_ID)
    private long member_id;

    @JsonProperty(Columns.EP_STATUS)
    @DatabaseField(columnName = Columns.EP_STATUS)
    private long ep_status;

    public EventParticipant() { }

    @JsonIgnore
    public EventParticipant(long ormId, long ep_id, long event_id, long member_id, long ep_status) {
        this.ormId = ormId;
        this.ep_id = ep_id;
        this.event_id = event_id;
        this.member_id = member_id;
        this.ep_status = ep_status;
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
    public long getEp_id() {
        return ep_id;
    }
    @JsonIgnore
    public void setEp_id(long ep_id) {
        this.ep_id = ep_id;
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
    public long getEp_status() {
        return ep_status;
    }
    @JsonIgnore
    public void setEp_status(long ep_status) {
        this.ep_status = ep_status;
    }

    @JsonIgnore
    public static EventParticipant getObjFromJackson(String jsonString)
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
    public static String getJsonString(EventParticipant eventParticipant)
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
        String EP_ID = "ep_id";
        String ORMID = "ormId";
        String EVEN_TID = "event_id";
        String MEMBER_ID = "member_id";
        String EP_STATUS = "ep_status";


    }
}
