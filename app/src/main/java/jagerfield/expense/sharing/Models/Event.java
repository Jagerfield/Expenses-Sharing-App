package jagerfield.expense.sharing.Models;

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

    @JsonProperty(Columns.EVENT_ID)
    @DatabaseField(columnName = Columns.EVENT_ID)
    private long event_id;

    @JsonProperty(Columns.EVENT_ADMIN)
    @DatabaseField(columnName = Columns.EVENT_ADMIN)
    private long event_admin;

    @JsonProperty(Columns.EVENT_NAME)
    @DatabaseField(columnName = Columns.EVENT_NAME)
    private String event_name;

    @JsonProperty(Columns.EVENT_DESCRIPTION)
    @DatabaseField(columnName = Columns.EVENT_DESCRIPTION)
    private String event_description;

    @JsonProperty(Columns.EVENT_CREATED_AT)
    @DatabaseField(columnName = Columns.EVENT_CREATED_AT)
    private String event_created_at;

    @JsonProperty(Columns.EVENT_IMAGE)
    @DatabaseField(columnName = Columns.EVENT_IMAGE)
    private String event_image;


    public Event(){}

    @JsonIgnore
    public Event(long ormId, long event_id, long event_admin, String event_name, String event_description, String event_created_at, String event_image) {
        this.ormId = ormId;
        this.event_id = event_id;
        this.event_admin = event_admin;
        this.event_name = event_name;
        this.event_description = event_description;
        this.event_created_at = event_created_at;
        this.event_image = event_image;
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
    public long getEvent_admin() {
        return event_admin;
    }

    @JsonIgnore
    public void setEvent_admin(long event_admin) {
        this.event_admin = event_admin;
    }

    @JsonIgnore
    public String getEvent_name() {
        return event_name;
    }
    @JsonIgnore
    public void setEvent_name(String event_name) {
        this.event_name = event_name;
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
    public String getEvent_description() {
        return event_description;
    }
    @JsonIgnore
    public void setEvent_description(String event_description) {
        this.event_description = event_description;
    }
    @JsonIgnore
    public String getEvent_created_at() {
        return event_created_at;
    }
    @JsonIgnore
    public void setEvent_created_at(String event_created_at) {
        this.event_created_at = event_created_at;
    }
    @JsonIgnore
    public String getEvent_image() {
        return event_image;
    }
    @JsonIgnore
    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    @JsonIgnore
    public static Event convertJsonStrToObj(String jsonString)
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
    public static String convertObjToJsonStr(Event event)
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
        String EVENT_ID = "event_id";
        String ORMID = "ormId";
        String EVENT_NAME = "event_name";
        String EVENT_ADMIN = "event_admin";
        String EVENT_DESCRIPTION = "event_description";
        String EVENT_CREATED_AT = "event_created_at";
        String EVENT_IMAGE = "event_image";
    }
}




