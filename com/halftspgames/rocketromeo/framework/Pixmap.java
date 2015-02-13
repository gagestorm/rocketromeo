package com.halftspgames.rocketromeo.framework;

import com.halftspgames.rocketromeo.framework.Graphics.PixmapFormat;

public interface Pixmap {
	public int getWidth();
	public int getHeight();
	public PixmapFormat getFormat();
	public void dispose();
}
