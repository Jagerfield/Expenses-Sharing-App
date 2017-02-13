package jagerfield.expense.sharing.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Admin
{
    @JsonProperty(Columns.MEMBER_ID)
    private long member_id;

    @JsonProperty(Columns.ADMIN_NAME)
    private String admin_name;

    @JsonProperty(Columns.PASSWORD)
    private String password;

    @JsonProperty(Columns.GMAIL)
    private String gmail;

    @JsonProperty(Columns.UDID)
    private String udId;

    @JsonProperty(Columns.ADMIN_IMAGE)
    private String admin_image;

    public Admin() { }

    @JsonIgnore
    public Admin(long member_id, String admin_name, String gmail, String udId, String admin_image)
    {
        this.member_id = member_id;
        this.admin_name = admin_name;
        this.gmail = gmail;
        this.udId = udId;
        this.admin_image = admin_image;
    }

    @JsonIgnore
    public long getMemberId() {
        return member_id;
    }
    @JsonIgnore
    public void setMemberId(long member_id) {
        this.member_id = member_id;
    }

    @JsonIgnore
    public String getAdminName() {
        return admin_name;
    }
    public void setAdminName(String admin_name) {
        this.admin_name = admin_name;
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
    public String getAdminImage() {
        return admin_image;
    }
    @JsonIgnore
    public void setAdminImage(String admin_image) {
        this.admin_image = admin_image;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonIgnore
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public static Admin convertJsonStrToObj(String jsonString)
    {
        Admin admin = null;
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            admin = mapper.readValue(jsonString, Admin.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return admin;
    }

    @JsonIgnore
    public static String convertObjToJsonStr(Admin admin)
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString= "";

        try
        {
            jsonString = mapper.writeValueAsString(admin);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return jsonString;
    }

    public boolean checkObjectValues()
    {
        // We are not checking for the password cause if we login through social media then there is no password
        if (getAdminName().isEmpty() || getGmail().isEmpty() || getUdId().isEmpty())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public interface Columns
    {
        String MEMBER_ID = "member_id";
        String ADMIN_NAME = "admin_name";
        String GMAIL = "gmail";
        String PASSWORD = "PASSWORD";
        String UDID = "udId";
        String ADMIN_IMAGE = "admin_image";
    }

}
