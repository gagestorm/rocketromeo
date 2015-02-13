package com.halftspgames.rocketromeo.gamedev2d;

import com.halftspgames.rocketromeo.framework.math.Vector2;

public class DynamicGameObject extends GameObject{
	
	public final Vector2 velocity;
	public final Vector2 accel;

	public DynamicGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
		velocity = new Vector2();
		accel = new Vector2();
	}
	
}
