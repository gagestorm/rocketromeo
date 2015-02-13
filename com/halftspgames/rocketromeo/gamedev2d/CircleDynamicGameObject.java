package com.halftspgames.rocketromeo.gamedev2d;

import com.halftspgames.rocketromeo.framework.math.Vector2;

public class CircleDynamicGameObject extends CircleGameObject{

    public final Vector2 velocity;
    public final Vector2 accel;

    public CircleDynamicGameObject(float x, float y, float radius) {
        super(x, y, radius);
        // TODO Auto-generated constructor stub
        velocity = new Vector2();
        accel = new Vector2();
    }

}
