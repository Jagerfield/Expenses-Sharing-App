package jagerfield.expense.sharing.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.IOException;

@DatabaseTable(tableName = "Member")
public class Member
{
    @JsonProperty(Columns.ORMID)
    @DatabaseField(id = true, columnName = Columns.ORMID)
    private long ormId;

    @JsonProperty(Columns.MEMBER_ID)
    @DatabaseField(columnName = Columns.MEMBER_ID)
    private long member_id;

    @JsonProperty(Columns.MEMBER_NAME)
    @DatabaseField(columnName = Columns.MEMBER_NAME)
    private String member_name;

    @JsonProperty(Columns.GMAIL)
    @DatabaseField(columnName = Columns.GMAIL)
    private String gmail;

    @JsonProperty(Columns.UDID)
    @DatabaseField(columnName = Columns.UDID)
    private String udId;

    @JsonProperty(Columns.MEMBER_ACCOUNT_STATUS)
    @DatabaseField(columnName = Columns.MEMBER_ACCOUNT_STATUS)
    private boolean member_account_status;

    @JsonProperty(Columns.MEMBER_IMAGE)
    @DatabaseField(columnName = Columns.MEMBER_IMAGE)
    private String member_image;

    public Member() { }

    @JsonIgnore
    public Member(long ormId, long member_id, String member_name, String gmail, String udId, boolean member_account_status, String member_image) {
        this.ormId = ormId;
        this.member_id = member_id;
        this.member_name = member_name;
        this.gmail = gmail;
        this.udId = udId;
        this.member_account_status = member_account_status;
        this.member_image = member_image;
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
    public long getMember_id() {
        return member_id;
    }
    @JsonIgnore
    public void setMember_id(long member_id) {
        this.member_id = member_id;
    }
    @JsonIgnore
    public String getMember_name() {
        return member_name;
    }
    @JsonIgnore
    public void setMember_name(String member_name) {
        this.member_name = member_name;
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
    public boolean isMember_account_status() {
        return member_account_status;
    }
    @JsonIgnore
    public void setMember_account_status(boolean member_account_status) {
        this.member_account_status = member_account_status;
    }
    @JsonIgnore
    public String getMember_image() {
        return member_image;
    }
    @JsonIgnore
    public void setMember_image(String member_image) {
        this.member_image = member_image;
    }

    @JsonIgnore
    public static Member getObjFromJackson(String jsonString)
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
    public static String getJsonString(Member member)
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
        String MEMBER_ID = "member_id";
        String ORMID = "ormId";
        String MEMBER_NAME = "member_name";
        String GMAIL = "gmail";
        String UDID = "udId";
        String MEMBER_ACCOUNT_STATUS = "member_account_status";
        String MEMBER_IMAGE = "member_image";
    }

}

