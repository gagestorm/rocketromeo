package com.halftspgames.rocketromeo.main;

import android.util.Log;

import com.halftspgames.rocketromeo.framework.Screen;
import com.halftspgames.rocketromeo.framework.impl.GLGame;



public class RocketRomeo extends GLGame {

    public static boolean firsttime = true;

    public Screen getStartScreen() {

        if(firsttime == true) {
            Assets.load(this);
            firsttime = false;
        }
        else
            Assets.reload();
        Log.d("RocketRomeo Class:","SEttings loading.");
        Settings.load(this);
        return new MainMenuScreen(this);
    }


    public void onPause(){
        super.onPause();
        Settings.save(this);
        Log.d("RocketRomeo Class:","Save game scores.");
        Log.d("RocketRomeo Class: ", " Game Paused.");
        //Assets.music.pause();
    }
	
	public void onResume() {
		super.onResume();
        Log.d("RocketRomeo Class: ", " Game Resumed.");
		//Assets.music.play();
	}

}