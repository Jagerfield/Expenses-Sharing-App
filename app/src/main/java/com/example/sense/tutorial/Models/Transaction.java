package com.example.sense.tutorial.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.IOException;

@DatabaseTable(tableName = "Transaction")
public class Transaction
{
    @JsonProperty(Columns.ORMID)
    @DatabaseField(id = true, columnName = Columns.ORMID)
    private long ormId;

    @JsonProperty(Columns.TRANSACTION_ID)
    @DatabaseField(columnName = Columns.TRANSACTION_ID)
    private long transaction_id;

    @JsonProperty(Columns.TRANSACTION_ISSUER)
    @DatabaseField(columnName = Columns.TRANSACTION_ISSUER)
    private long transaction_issuer;

    @JsonProperty(Columns.TRANSACTION_RECEIVER)
    @DatabaseField(columnName = Columns.TRANSACTION_RECEIVER)
    private long transaction_receiver;

    @JsonProperty(Columns.TRANSACTION_AMOUNT)
    @DatabaseField(columnName = Columns.TRANSACTION_AMOUNT)
    private long transaction_amount;

    @JsonProperty(Columns.TRANSACTION_DESCRIPTION)
    @DatabaseField(columnName = Columns.TRANSACTION_DESCRIPTION)
    private long transaction_description;

    @JsonProperty(Columns.TRANSACTION_IMAGE)
    @DatabaseField(columnName = Columns.TRANSACTION_IMAGE)
    private String transaction_image;

    @JsonProperty(Columns.TRANSACTION_CREATEDAT)
    @DatabaseField(columnName = Columns.TRANSACTION_CREATEDAT)
    private long transaction_created_at;

    @JsonProperty(Columns.TRANSACTION_UPDATEDAT)
    @DatabaseField(columnName = Columns.TRANSACTION_UPDATEDAT)
    private long transaction_updated_at;

    public Transaction() {
    }

    @JsonIgnore
    public Transaction(long ormId, long transaction_id, long transaction_issuer, long transaction_receiver, long transaction_amount, long transaction_description, String transaction_image, long transaction_created_at, long transaction_updated_at) {
        this.ormId = ormId;
        this.transaction_id = transaction_id;
        this.transaction_issuer = transaction_issuer;
        this.transaction_receiver = transaction_receiver;
        this.transaction_amount = transaction_amount;
        this.transaction_description = transaction_description;
        this.transaction_image = transaction_image;
        this.transaction_created_at = transaction_created_at;
        this.transaction_updated_at = transaction_updated_at;
    }

    @JsonIgnore
    public static Transaction getObjFromJackson(String jsonString)
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
    public static String getJsonString(Transaction transaction)
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
        String TRANSACTION_ID = "transaction_id";
        String ORMID = "ormId";
        String TRANSACTION_ISSUER = "transaction_issuer";
        String TRANSACTION_RECEIVER = "transaction_receiver";
        String TRANSACTION_AMOUNT = "transaction_amount";
        String TRANSACTION_DESCRIPTION = "transaction_description";
        String TRANSACTION_IMAGE = "transaction_image";
        String TRANSACTION_CREATEDAT = "transaction_created_at";
        String TRANSACTION_UPDATEDAT = "transaction_updated_at";
    }

}













