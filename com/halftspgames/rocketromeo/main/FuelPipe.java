package com.halftspgames.rocketromeo.main;

import com.halftspgames.rocketromeo.gamedev2d.DynamicGameObject;

public class FuelPipe extends DynamicGameObject{

    public static final float FUEL_PIPE_WIDTH  = 12*0.03215f;
    public static final float FUEL_PIPE_HEIGHT = 24*0.03215f;
    public float stateTime = 0;
    public Boolean right = Boolean.FALSE;

    public FuelPipe(float x, float y) {
        super(x-0.4f, y, FUEL_PIPE_WIDTH, FUEL_PIPE_HEIGHT);
        // TODO Auto-generated constructor stub
        y -= FUEL_PIPE_HEIGHT;
    }

    public void update(float deltaTime,float tilt) {
        stateTime += deltaTime;
        if(tilt < 0 && right ){
            position.x -= 0.8f;
            right = Boolean.FALSE;
        }else if(tilt >= 0 && !right ){
            position.x += 0.8f;
            right = Boolean.TRUE;
        }
    }




}
