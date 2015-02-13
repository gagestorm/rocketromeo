package com.halftspgames.rocketromeo.main;

import com.halftspgames.rocketromeo.gamedev2d.GameObject;

public class Emerald extends GameObject {

    public static final int RED_EMERALD=1;
    public static final int GREEN_EMERALD=2;
    public static final float EM_WIDTH = 1.5f;
    public static final float EM_HEIGHT = 1.5f;
    public int type;

    public Emerald(float x, float y,int type) {
        super(x, y, EM_WIDTH, EM_HEIGHT);
        this.type = type;
    }

}
