package com.halftspgames.rocketromeo.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Settings {
    //public static boolean soundEnabled = true;
    public static int highscore=0;
    public static int lastEmeralds=0;
    public static int visitCount;

    public static void load(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("RR_PREFS", Context.MODE_PRIVATE);
        highscore = prefs.getInt("RR_HIGHSCORE", 0);
        lastEmeralds = prefs.getInt("RR_EMERALDS",0);
        visitCount = prefs.getInt("RR_VISIT_NUMBER",0);
        //Log.d("Settings Class:", "Loaded settings:"+lastEmeralds);
    }


    public static void save(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("RR_PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        visitCount++;
        editor.putInt("RR_HIGHSCORE", highscore);
        editor.putInt("RR_EMERALDS", lastEmeralds);
        editor.putInt("RR_VISIT_NUMBER", visitCount);
        editor.commit();
        //Log.d("Settings Class:", "LastEmeralds Saved:"+lastEmeralds);
    }

}
