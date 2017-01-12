package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;

import java.io.IOException;

public class Transaction
{
    @JsonProperty(Columns.ORMID)
    @DatabaseField(id = true, columnName = Columns.ORMID)
    private long ormId;

    @JsonProperty(Columns.ID)
    @DatabaseField(columnName = Columns.ID)
    private long id;

    @JsonProperty(Columns.ISSURE)
    @DatabaseField(columnName = Columns.ISSURE)
    private long issuer;

    @JsonProperty(Columns.RECEIVER)
    @DatabaseField(columnName = Columns.RECEIVER)
    private long receiver;

    @JsonProperty(Columns.AMOUNT)
    @DatabaseField(columnName = Columns.AMOUNT)
    private long amount;

    @JsonProperty(Columns.DESCRIPTION)
    @DatabaseField(columnName = Columns.DESCRIPTION)
    private long description;

    @JsonProperty(Columns.IMAGE)
    @DatabaseField(columnName = Columns.IMAGE)
    private long image;

    @JsonProperty(Columns.CREATEDAT)
    @DatabaseField(columnName = Columns.CREATEDAT)
    private long created_at;

    @JsonProperty(Columns.UPDATEDAT)
    @DatabaseField(columnName = Columns.UPDATEDAT)
    private long updated_at;


    @JsonIgnore
    public Transaction getAppOwnerObj(String jsonString)
    {
        Transaction transaction = null;
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            transaction = mapper.readValue(jsonString, Transaction.class);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return transaction;
    }

    @JsonIgnore
    public String getAppOwnerObjJson(Transaction transaction)
    {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString= "";

        try
        {
            jsonString = mapper.writeValueAsString(transaction);

        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }

        return jsonString;
    }


    public interface Columns {
        String ID = "id";
        String ORMID = "ormId";
        String ISSURE = "issuer";
        String RECEIVER = "receiver";
        String AMOUNT = "amount";
        String DESCRIPTION = "description";
        String IMAGE = "image";
        String CREATEDAT = "created_at";
        String UPDATEDAT = "updated_at";
    }

}













