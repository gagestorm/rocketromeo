package com.halftspgames.rocketromeo.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable{

	AndroidGame game;
	Bitmap framebuffer;
	Thread renderThread = null;
	SurfaceHolder holder;
	volatile boolean running = false;
	
	
	public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer) {
		super(game);
		// TODO Auto-generated constructor stub
		this.game = game;
		this.framebuffer = framebuffer;
		this.holder = getHolder();
	}
	//AndroidGame game;

	public void resume(){
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Rect dstRect = new Rect();
		long startTime = System.nanoTime();
		while(running){
			if(!holder.getSurface().isValid())
				continue;
			float deltaTime = (System.nanoTime()-startTime)/1000000000.0f;
			startTime = System.nanoTime();
			
			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().present(deltaTime);
			
			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(dstRect);
			canvas.drawBitmap(framebuffer, null, dstRect, null);
			holder.unlockCanvasAndPost(canvas);
			//Log.v("!", "OK");
			
	
		}
	}
	
	
	
	public void pause(){
		running = false;
		while(true){
			try {
				renderThread.join();
				break;
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
}
