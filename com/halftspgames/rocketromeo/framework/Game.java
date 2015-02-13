package com.halftspgames.rocketromeo.framework;

import com.halftspgames.rocketromeo.framework.impl.GLGraphics;

public interface Game {
	public Input getInput();
	public FileIO getFileIO();
	public Graphics getGraphics();
	public Audio getAudio();
	public void setScreen(Screen screen);
	public Screen getCurrentScreen();
	public Screen getStartScreen();
}
