package com.example.sense.tutorial.Retrofit.Models;

import java.util.ArrayList;

public class RestEventResponse
{
    private ArrayList<EventChat> eventChat;

    private ArrayList<Event> events;

    private ArrayList<String> errors;

    private ArrayList<EventParticipant> eventParticipants;

    private ArrayList<EventTransaction> eventTransactions;

    public ArrayList<EventChat> getEventChat ()
    {
        return eventChat;
    }

//    public void setEventChat (EventChat[] eventChat)
//    {
//        this.eventChat = eventChat;
//    }

    public ArrayList<Event> getEvents ()
    {
        return events;
    }

//    public void setEvent (Event[] event)
//    {
//        this.event = event;
//    }

    public ArrayList<String> getErrors()
    {
        return errors;
    }

//    public void setRemarks (String[] remarks)
//    {
//        this.remarks = remarks;
//    }

    public ArrayList<EventParticipant> getEventParticipants()
    {
        return eventParticipants;
    }

//    public void setEventParticipant (EventParticipant[] eventParticipant)
//    {
//        this.eventParticipant = eventParticipant;
//    }

    public ArrayList<EventTransaction> getEventTransactions()
    {
        return eventTransactions;
    }

//    public void setEvenTransaction (EventTransaction[] eventTransaction)
//    {
//        this.eventTransaction = eventTransaction;
//    }

//    @Override
//    public String toString()
//    {
//        return "ClassPojo [eventChat = "+eventChat+", event = "+event+", remarks = "+remarks+", eventParticipant = "+eventParticipant+", eventTransaction = "+eventTransaction+"]";
//    }
}
