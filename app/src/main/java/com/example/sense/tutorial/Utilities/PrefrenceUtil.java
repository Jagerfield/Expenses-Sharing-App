package com.example.sense.tutorial.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceUtil
{

    public static void setString(Context context, String key, String val) {
        SharedPreferences.Editor e = context.getSharedPreferences(Util.APP_PREFERENCES, context.MODE_PRIVATE).edit();
        e.putString(key, val);
        e.commit();
    }

    public static void setInt(Context c, String key, int val) {
        SharedPreferences.Editor e = c.getSharedPreferences(Util.APP_PREFERENCES, c.MODE_PRIVATE).edit();
        e.putInt(key, val);
        e.commit();
    }

    public static void setLong(Context c, String key, Long val) {
        SharedPreferences.Editor e = c.getSharedPreferences(Util.APP_PREFERENCES, c.MODE_PRIVATE).edit();
        e.putLong(key, val);
        e.commit();
    }

    public static void setBoolean(Context c, String key, Boolean val)
    {
        SharedPreferences.Editor e = c.getSharedPreferences(Util.APP_PREFERENCES, c.MODE_PRIVATE).edit();
        e.putBoolean(key, val);
        e.commit();
    }

    public static boolean getBoolean(Context c, String key, Boolean onNull)
    {
        SharedPreferences e = c.getSharedPreferences(Util.APP_PREFERENCES, c.MODE_PRIVATE);
        return e.getBoolean(key, onNull);
    }

    public static int getInt(Context c, String key, int onNull)
    {
        SharedPreferences e = c.getSharedPreferences(Util.APP_PREFERENCES, c.MODE_PRIVATE);
        return e.getInt(key, onNull);
    }

    public static Long getLong(Context c, String key, Long onNull)
    {
        SharedPreferences e = c.getSharedPreferences(Util.APP_PREFERENCES, c.MODE_PRIVATE);
        return e.getLong(key, onNull);
    }

    public static String getString(Context c, String key, String onNull)
    {
        SharedPreferences e = c.getSharedPreferences(Util.APP_PREFERENCES, c.MODE_PRIVATE);
        return e.getString(key, onNull);
    }

}


