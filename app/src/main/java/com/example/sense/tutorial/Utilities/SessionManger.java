package com.example.sense.tutorial.Utilities;

import android.content.Context;
import com.example.sense.tutorial.Models.Member;

public class SessionManger
{
    private Context context;
    private String HASLOGEDN_KEY = "HASLOGEDN_KEY";

    private String ID_KEY = "ID_KEY";
    private String GMAIL_KEY = "GMAIL_KEY";
    private String NAME_KEY = "NAME_KEY";

    public SessionManger(Context context) {
        this.context = context;
    }

    public void setAppOwner(Member member)
    {
        PrefrenceUtil.setLong(context, ID_KEY, member.getMember_id());
        PrefrenceUtil.setString(context, GMAIL_KEY, member.getGmail());
        PrefrenceUtil.setString(context, NAME_KEY, member.getMember_name());
    }

    public Member regtAppOwner(){

        return new Member();
    }

    public boolean getHasLogedIn()
    {
        return PrefrenceUtil.getBoolean(context, HASLOGEDN_KEY, false);
    }

    public void setHasLogedIn(boolean status)
    {
        PrefrenceUtil.setBoolean(context, HASLOGEDN_KEY, status);
    }
}
