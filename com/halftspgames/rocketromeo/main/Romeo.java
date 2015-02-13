package com.halftspgames.rocketromeo.main;

import com.halftspgames.rocketromeo.framework.math.Vector2;
import com.halftspgames.rocketromeo.gamedev2d.DynamicGameObject;

public class Romeo extends DynamicGameObject {

    public static final int ROMEO_STATE_PROPELLER_ON = 0;
    public static final int ROMEO_STATE_FALL = 1;
    public static final int ROMEO_STATE_HIT = 2;
    public static final int ROMEO_STATE_STILL = 3;
    public static final float ROMEO_MOVE_VELOCITY =14;
    public static Boolean romeoSafe = Boolean.FALSE;
    public static final float ROMEO_WIDTH = 1.5f;
    public static final float ROMEO_HEIGHT = 1.5f;

    public static float romeo_mass = 40;
    public static int lifeCount =0;
    //public static final float NEGATIVE_ACCEL = 5;

    public int state;
    public float stateTime;
    public Propeller propeller;
    public Propeller.PropellerListener propellerListener;

    public static class Propeller{

        public interface PropellerListener{
            public void fillingDone();
        }

        public static float mass_exhaust;
        public static final int EXHAUST_VELOCITY = 20;
        public static final int MAX_EXHAUST = 100;
        public static final float MASS_EXIT_RATE = 5;
        public static final int PROPELLER_OFF =0;
        public static final int PROPELLER_ON =1;
        public static final int PROPELLER_END =2;
        public static final int PROPELLER_FILLING =3;
        public static float propellerAngleRadian;

        public float stateTime;
        public final PropellerListener listener;
        public int state;
        public Platform fillingBlock;

        public Propeller(float mass_exhaust, PropellerListener listener){
            this.mass_exhaust = mass_exhaust;
            stateTime =0;
            state=PROPELLER_OFF;
            this.propellerAngleRadian =0;
            this.listener = listener;
            this.fillingBlock = new Platform(0,0,0,0,0);
        }

        public void updateState(int state){
            if( this.state != PROPELLER_END )
                this.state = state;
            stateTime =0;
        }

        public void tilt(float tilt){
            propellerAngleRadian = tilt*9*Vector2.TO_RADIANS;
        }

        public void update(float deltaTime){
            if( state == PROPELLER_ON){
                mass_exhaust -= MASS_EXIT_RATE*deltaTime;
                if(mass_exhaust<0)
                    state = PROPELLER_END;
                stateTime += deltaTime;
            }

            if( state == PROPELLER_FILLING){
                if(stateTime>2f) {
                    rechargeFuel();
                    fillingBlock.removeFuel();
                    state = PROPELLER_OFF;
                    fillingBlock = new Platform(0,0,0,0,0);
                    listener.fillingDone();
                }
                stateTime += deltaTime;
            }
        }

        public void setFillingBlock(Platform block){
                fillingBlock = block;
        }

        public int fuelLevel(){
            return (int)(mass_exhaust/MAX_EXHAUST*10);
        }

        public void rechargeFuel(){
            if(mass_exhaust < MAX_EXHAUST)
                mass_exhaust = MAX_EXHAUST;
        }
    }

    public Romeo(float x, float y) {
        super(x, y, ROMEO_WIDTH*0.8f , ROMEO_HEIGHT);
        // TODO Auto-generated constructor stub
        state = ROMEO_STATE_FALL;
        stateTime = 0;
        this.propellerListener = new Propeller.PropellerListener() {
            @Override
            public void fillingDone() {
                //velocity.set(0,10);
//                position.add(0,-ROMEO_HEIGHT*2);
//                velocity.set(0,-1);
                //propeller.updateState(Propeller.PROPELLER_ON);
            }
        };
        propeller = new Propeller(100,propellerListener);
    }

    public void extraLife(){
        //Increase the life of romeo
        lifeCount+=1;
    }
    public void update(float deltaTime) {

          propeller.update(deltaTime);
          if(stateTime > 3f){
              romeoSafe = Boolean.FALSE;
          }

       if(state != ROMEO_STATE_HIT && propeller.state != Propeller.PROPELLER_FILLING) {
            //Propulsion theory
            if(propeller.state == Propeller.PROPELLER_ON){
                state = ROMEO_STATE_PROPELLER_ON;
//                Log.d("Values : ","Romeo mass:"+romeo_mass+" Propeller exhaust "+propeller.mass_exhaust+"rate"+Propeller.MASS_EXIT_RATE+" deltaTime"+deltaTime+"vecloty"+Propeller.EXHAUST_VELOCITY);
                if(velocity.y<-3)
                    velocity.y=-3;
                else if(velocity.y>10) {
                    velocity.y = 10;
                    //velocity.x = 0;
                }else {
                    velocity.y = (velocity.y * (romeo_mass + propeller.mass_exhaust + Propeller.MASS_EXIT_RATE) + Propeller.MASS_EXIT_RATE * Propeller.EXHAUST_VELOCITY) / (romeo_mass + propeller.mass_exhaust) + World.GRAVITY * deltaTime;
                }
            }else{
                state = ROMEO_STATE_FALL;
//                if(velocity.y >0) {
//                    velocity.y = -2;
//                    //velocity.x = 0;
//                }else if(velocity.y<-10)
//                    velocity.y =-20;
//                else {
                    velocity.y = velocity.y + World.GRAVITY * deltaTime;

//                }
            }

            position.add(velocity.x * deltaTime, velocity.y * deltaTime);
            bounds.lowerLeft.set(position).sub(bounds.width / 2, bounds.height / 2);
        }

        if(position.x < 0)
            position.x = World.WORLD_WIDTH;
        if(position.x > World.WORLD_WIDTH)
            position.x = 0;
        stateTime += deltaTime;

        //Log.d("Update Romeo:", "Acceleration-x:" + accel.x + " AND Acceleration-y:" + accel.y);
        //Log.d("Update Romeo:","velocity-x:"+velocity.x+" AND velocity-y:"+velocity.y);
    }

    public void hitGuard() {
        accel.set(0,0);
        velocity.set(0,0);
        state = ROMEO_STATE_HIT;
        stateTime = 0;
    }

    public void hitPlatform() {
        if(lifeCount>0 || romeoSafe ){
            if(!romeoSafe){
                romeoSafe = Boolean.TRUE;
                stateTime=0;
                lifeCount--;
            }
        }else{
            accel.set(0,0);
            velocity.set(0,0);
            state = ROMEO_STATE_HIT;
            stateTime = 0;
        }
    }

    public void hitSpring() {
//        velocity.y = ROMEO_JUMP_VELOCITY * 1.5f;
//        state = ROMEO_STATE_JUMP;
        stateTime = 0;
    }



}
