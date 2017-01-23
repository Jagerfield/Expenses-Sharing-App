package jagerfield.expense.sharing.Utilities;

import android.content.Context;
import jagerfield.expense.sharing.Models.Member;

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
        PreferenceUtil.setLong(context, ID_KEY, member.getMember_id());
        PreferenceUtil.setString(context, GMAIL_KEY, member.getGmail());
        PreferenceUtil.setString(context, NAME_KEY, member.getMember_name());
    }

    public Member regtAppOwner(){

        return new Member();
    }

    public boolean getHasLogedIn()
    {
        return PreferenceUtil.getBoolean(context, HASLOGEDN_KEY, false);
    }

    public void setHasLogedIn(boolean status)
    {
        PreferenceUtil.setBoolean(context, HASLOGEDN_KEY, status);
    }
}
