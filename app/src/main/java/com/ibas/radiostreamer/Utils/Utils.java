package com.ibas.radiostreamer.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ibas on 29/05/2017.
 */

public class Utils {
    private static SharedPreferences sp;
    private static final String PREF_NAME = "stream_sp";
    public static final String IS_STREAM = "stream";

    public static void setDataBooleanToSP(Context context, String to, boolean
            data) {
        sp = context.getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(to, data);
        editor.commit();
    }

    public static boolean getDataBooleanFromSP(Context context, String from) {
        sp = context.getSharedPreferences(PREF_NAME, 0);
        return sp.getBoolean(from, false);
    }
}
