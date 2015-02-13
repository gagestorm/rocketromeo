package com.halftspgames.rocketromeo.main;

import com.halftspgames.rocketromeo.gamedev2d.DynamicGameObject;

public class Guard extends DynamicGameObject{

    public static final float GUARD_WIDTH = 2.4f;
    public static final float GUARD_HEIGHT = 1.5f;
    //public static float guardVelocity;

    public float stateTime = 0;


    public Guard(float x, float y) {
        super(x, y, GUARD_WIDTH, GUARD_HEIGHT);
        // TODO Auto-generated constructor stub
        //Based on the y let's set velocity
        velocity.set(-1*y/100, 0);
    }

    public void update(float deltaTime) {

        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        bounds.lowerLeft.set(position).sub(GUARD_WIDTH / 2, GUARD_HEIGHT / 2);

        if(position.x < GUARD_WIDTH / 2) {
            position.x = GUARD_WIDTH / 2;
            velocity.x = -velocity.x;
        }

        if(position.x > World.WORLD_WIDTH - GUARD_WIDTH / 2) {
            position.x = World.WORLD_WIDTH - GUARD_WIDTH / 2;
            velocity.x = -velocity.x;
        }
        stateTime += deltaTime;

    }




}
