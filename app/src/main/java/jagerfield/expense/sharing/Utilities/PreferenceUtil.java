package jagerfield.expense.sharing.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil
{
    private static final String PREFERENCES_GENERAL = "PREFERENCES_GENERAL";

    public static void setString(Context context, String key, String val)
    {
        SharedPreferences.Editor e = context.getSharedPreferences(PREFERENCES_GENERAL, context.MODE_PRIVATE).edit();
        e.putString(key, val);
        e.commit();
    }

    public static void setInt(Context c, String key, int val) {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE).edit();
        e.putInt(key, val);
        e.commit();
    }

    public static void setLong(Context c, String key, Long val) {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE).edit();
        e.putLong(key, val);
        e.commit();
    }

    public static void setBoolean(Context c, String key, Boolean val)
    {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE).edit();
        e.putBoolean(key, val);
        e.commit();
    }

    public static boolean getBoolean(Context c, String key, Boolean onNull)
    {
        SharedPreferences e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE);
        return e.getBoolean(key, onNull);
    }

    public static int getInt(Context c, String key, int onNull)
    {
        SharedPreferences e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE);
        return e.getInt(key, onNull);
    }

    public static Long getLong(Context c, String key, Long onNull)
    {
        SharedPreferences e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE);
        return e.getLong(key, onNull);
    }

    public static String getString(Context c, String key, String onNull)
    {
        SharedPreferences e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE);
        return e.getString(key, onNull);
    }

    public static void clearAllPreferences(Context c)
    {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE).edit();
        e.clear();
        e.commit();
    }

    public static void clearPreference(Context c, String key)
    {
        SharedPreferences.Editor e = c.getSharedPreferences(PREFERENCES_GENERAL, c.MODE_PRIVATE).edit();
        e.remove(key);
        e.commit();
    }

}


