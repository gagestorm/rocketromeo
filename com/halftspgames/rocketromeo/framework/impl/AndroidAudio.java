package com.halftspgames.rocketromeo.framework.impl;

import java.io.IOException;

import com.halftspgames.rocketromeo.framework.Audio;
import com.halftspgames.rocketromeo.framework.Music;
import com.halftspgames.rocketromeo.framework.Sound;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class AndroidAudio implements Audio{
	AssetManager assets;
	SoundPool soundPool;
	
	public AndroidAudio(Activity activity){
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}

	@Override
	public Music newMusic(String filename) {
		// TODO Auto-generated method stub
		try {
			

			
			Log.d("!!", "fuck");
			
			
			
			
			
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			return new AndroidMusic(assetDescriptor);
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException("Couldn't load music '"+filename+"'");
		}
	}

	@Override
	public Sound newSound(String filename) {
		// TODO Auto-generated method stub
		
		try {
			AssetFileDescriptor assetFileDescriptor = assets.openFd(filename);
			int soundId = soundPool.load(assetFileDescriptor,0);  //bug
			return new AndroidSound(soundPool, soundId);
			
			
		} catch (IOException e) {
			// TODO: handle exception
			throw new RuntimeException("Couldn't load sound '"+filename+"'");
		}
	}
	
}
