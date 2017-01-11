package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.IOException;

@DatabaseTable(tableName = "Group")
public class Group
{
    @JsonProperty("dbId")
    @DatabaseField(id = true, columnName = Columns.DBID)
    private long dbId;

    @JsonProperty("id")
    @DatabaseField(columnName = Columns.ID)
    private long id;

    @JsonProperty("event_id")
    @DatabaseField(columnName = Columns.EVENTID)
    private long event_id;

    @JsonProperty("member_id")
    @DatabaseField(columnName = Columns.MEMBERID)
    private long member_id;

    public Group() { }

    @JsonIgnore
    public Group(long dbId, long id, long event_id, long member_id) {
        this.dbId = dbId;
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
    public long getDbId() {
        return dbId;
    }
    @JsonIgnore
    public void setDbId(long dbId) {
        this.dbId = dbId;
    }


    @JsonIgnore
    public Group getAppOwnerObj(String jsonString)
    {
        Group group = null;
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            group = mapper.readValue(jsonString, Group.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return group;
    }

    @JsonIgnore
    public String getAppOwnerObjJson(Group group)
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString= "";

        try
        {
            jsonString = mapper.writeValueAsString(group);
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
        String DBID = "dbId";
    }
}
