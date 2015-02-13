package com.halftspgames.rocketromeo.main;

import com.halftspgames.rocketromeo.gamedev2d.DynamicGameObject;

public class Platform extends DynamicGameObject {

    public static final float PLATFORM_HEIGHT = 1.5f;

    public static final int STONE_BRIDGE=0;
    public static final int STEEL_DOUBLE=1;
    public static final int STEEL_SINGLE=2;
    public static final int CUTTER_BRIDGE=3;
    public static final int STEEL_BRIDGE=4;

    public int type;
    public Boolean fuel;
    public Boolean fuelEmpty;
    public Boolean stateHit=Boolean.FALSE;
    public float stateTime;
    public float platformWidth;
    public final float cachedVerticalLocation;

    public Platform(int type, float x, float y, float width,float velocity) {
        super(x, y, width, PLATFORM_HEIGHT);
        // TODO Auto-generated constructor stub
        this.type = type;
        this.velocity.set(velocity,0);
        this.stateTime = 0;
        this.platformWidth = width;
        this.fuel = Boolean.FALSE;
        this.fuelEmpty = Boolean.FALSE;
        this.cachedVerticalLocation = y;
    }

    public void update(float deltaTime) {
        position.add(velocity.x*deltaTime+0.5f*accel.x*deltaTime*deltaTime,
                velocity.y*deltaTime+0.5f*accel.y*deltaTime*deltaTime
                );
        bounds.lowerLeft.set(position).sub( platformWidth/ 2, PLATFORM_HEIGHT / 2);

        if(Math.abs(cachedVerticalLocation-position.y) > 2f){
            //Reverse the acceleration
            if(accel.y!=0){
                accel.y = -accel.y;
                velocity.y = -velocity.y;
            }
            //Reverse the velocity if no acceleration
            if(accel.y==0)
                velocity.y = -velocity.y;
        }

        if(position.x < platformWidth / 2) {
            velocity.x = - velocity.x;
            position.x = platformWidth / 2;
        }

        if(position.x > World.WORLD_WIDTH - platformWidth / 2) {
            velocity.x = -velocity.x;
            position.x = World.WORLD_WIDTH - platformWidth / 2;
        }
    }

    public void setFuel(){
        this.fuel = Boolean.TRUE;
    }
    public void setHit(Boolean active){
        if(stateHit!=active){
            stateHit = active;
            stateTime =0;
        }
    }
    public void removeFuel() {
        this.fuelEmpty = Boolean.TRUE;
    }
}
