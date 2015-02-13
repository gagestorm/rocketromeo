package com.halftspgames.rocketromeo.main.stages;

import android.util.Log;

import com.halftspgames.rocketromeo.framework.gl.Animation;
import com.halftspgames.rocketromeo.framework.gl.SpriteBatcher;
import com.halftspgames.rocketromeo.framework.gl.TextureRegion;
import com.halftspgames.rocketromeo.framework.math.OverlapTester;
import com.halftspgames.rocketromeo.gamedev2d.DynamicGameObject;
import com.halftspgames.rocketromeo.gamedev2d.GameObject;
import com.halftspgames.rocketromeo.main.Assets;
import com.halftspgames.rocketromeo.main.Emerald;
import com.halftspgames.rocketromeo.main.FuelPipe;
import com.halftspgames.rocketromeo.main.Guard;
import com.halftspgames.rocketromeo.main.Platform;
import com.halftspgames.rocketromeo.main.Romeo;
import com.halftspgames.rocketromeo.main.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mareenator on 2/2/15.
 */
public class StageOne extends Stage {

    public static final float STAGE_ORIGIN=0;

    public static World world;
    public final Random rand;
    public final List<Guard> guards;

    public StageOne(World world){
        super(-15*21);
        this.guards = new ArrayList<Guard>();
        this.world = world;
        rand = new Random();
    }


    //GENERATING THE ENVIRONMENT
    @Override
    public void generateStageEnvironment() {
        float y = -(12);
        float r,lastFuelLocation=0;

        DynamicGameObject property= new DynamicGameObject(0,0,0,0);
        property.accel.set(0,0);
        property.velocity.set(0,0);

        //In the first Stage we aren't placing anything for the first 12 parts
        while(y>worldEnd){

            if(y>-52){
                //Only LavaBridge
                if( y < -20){
                    //Bridge should have horizontal movement
                    if( rand.nextFloat() > 0.6f )
                        property.velocity.set(2,0);
                }
                    constructBridge(Platform.STONE_BRIDGE, y, 0, property);
            }else{
                r = rand.nextFloat();
                if(r<0.4) {
                    if(-y+lastFuelLocation > 48f) {
                        constructBridge( Platform.STONE_BRIDGE, y, -1, property);
                        lastFuelLocation = y;
                    }else
                        constructBridge( Platform.STONE_BRIDGE, y, 0, property);
                }else if(r<0.7) {
                    if( rand.nextFloat() > 0.5){
                        property.velocity.set(0,5);
                        property.accel.set(0,0);
                    }
                    constructBridge(Platform.CUTTER_BRIDGE,y,0, property);
                }else{
                    if( rand.nextFloat() > 0.5){
                        property.velocity.set(2,0);
                    }else{
                        property.velocity.set(1,1);
                    }
                    constructBridge(Platform.STEEL_BRIDGE,y,0, property);
                }
            }
            placeEmerald(-1,y-4,rand.nextInt(3));
            y-=8.0f;

            //Reset the property values.
            property.accel.set(0,0);
            property.velocity.set(0,0);
        }

    }

    public void constructBridge(int type,float y, float velocity, DynamicGameObject property){
        int i,j,k,fuel=-1;
        int x=0;
        i = rand.nextInt(6); //POSITION AT WHICH the opening is placed.
        //if velocity is negative then this bridge should have fuel
        if(velocity==-1){ //actually this means zero velocity
            fuel = i+4;
            velocity =0;
            if(fuel>6)
                fuel = fuel-6;
        }
        j = 0; k=i;
        while(j < 6){
            while(j<k){
                x+=24;
                Platform platform = new Platform(type,x*0.03125f,y,1.5f,velocity);
                //Adding some behaviour to the platform.
                platform.velocity.set(property.velocity.x, property.velocity.y);
                platform.accel.set(property.accel.x, property.accel.y);

                if(j==fuel) {
                    platform.setFuel();
                }
                platforms.add(platform);
                j+=1;x+=24;
            }
            k=6; x+=80; j++;
            if(j==6 && rand.nextFloat() > 0.4)
                placeEmerald(i*1.5f+1.2f,y,1);
        }
    }


    public void placeEmerald(float x,float y, int count){
        int type;
        if( x < 0){
            x = rand.nextInt(10);
            while(count>0){
                if( x >= 10 || x < 1)
                    x = 1;
                type = rand.nextInt(2)+1;
                Emerald emerald = new Emerald(x,y,type);
                emeralds.add(emerald);
                count-=1;
                x+=2;
            }
        }else{
            while(count>0){
                type = rand.nextInt(2)+1;
                Emerald emerald = new Emerald(x,y,type);
                emeralds.add(emerald);
                y += count;
                count--;
            }
        }
    }


    //RENDERING THE ENVIRONMENT
    @Override
    public void renderObjects(SpriteBatcher batcher){
        renderEmeralds(batcher);
        renderPlatforms(batcher);
    }

    public void renderPlatforms(SpriteBatcher batcher){
        int len = platforms.size();
        float cachedPos=world.romeo.position.y;

        Platform platform;
        TextureRegion keyFrame;
        for (int i=0;i<len;i++){
            platform = platforms.get(i);

            if( Math.abs(platform.position.y - cachedPos) < 10f ){

                switch (platform.type){
                    case Platform.STEEL_BRIDGE:
                        keyFrame = Assets.groundBridge1;
                        break;
                    case Platform.CUTTER_BRIDGE:
                        keyFrame = Assets.cutterBridge.getKeyFrame(stateTime,
                                Animation.ANIMATION_LOOPING);
                        break;
                    case Platform.STONE_BRIDGE:
                    default:
                        keyFrame = Assets.groundBridge1;

                }
                batcher.drawSprite(platform.position.x, platform.position.y,
                        platform.platformWidth, Platform.PLATFORM_HEIGHT,
                        keyFrame);

                if(platform.fuel){

                    if(world.romeo.propeller.fillingBlock.equals(platform))
                        keyFrame = Assets.fuelBlockChange.getKeyFrame(world.fuelPipe.stateTime, Animation.ANIMATION_LOOPING);
                    else
                        keyFrame = Assets.fuelBlockChange.getKeyFrame(0, Animation.ANIMATION_NONLOOPING);

                    batcher.drawSprite(platform.position.x, platform.position.y,
                            0.8f, 0.8f, keyFrame);
                }

            }
        }
    }

    public void renderEmeralds(SpriteBatcher batcher){
        int len = emeralds.size();
        float cachedPos=world.romeo.position.y;
        Emerald emerald;
        for (int i=0;i<len;i++){
            emerald = emeralds.get(i);
            //Log.d("StageOne Class","PLATFORM:"+i);
            if( Math.abs(emerald.position.y - cachedPos) < 10f ){
                batcher.drawSprite(emerald.position.x, emerald.position.y,
                        emerald.EM_WIDTH, Emerald.EM_HEIGHT,
                        (emerald.type==Emerald.GREEN_EMERALD)?Assets.greenEmerald:Assets.redEmerald);
            }
        }
    }

    //UPDATING THE VALUES
    @Override
    public void update(float deltaTime) {
        updatePlatforms(deltaTime);
        this.stateTime+=deltaTime;
    }

    //GAME AI
    @Override
    public void stageAI() {
        checkPlatformCollisions();
    }

    private void updatePlatforms(float deltaTime) {
        int len = platforms.size();
        for(int i = 0; i < len; i++) {
            Platform platform = platforms.get(i);
            platform.update(deltaTime);
        }
    }
    private void checkPlatformCollisions() {
        if(world.romeo.position.y > 0)
            return ;
        Platform platform;
        int len = platforms.size();
        for(int i = 0; i < len; i++) {
            platform = platforms.get(i);

            if( Math.abs(world.romeo.position.y - platform.position.y) <= world.romeo.ROMEO_HEIGHT ) {
                if(OverlapTester
                        .overlapRectangles(world.romeo.bounds, platform.bounds)) {
                    //Fuel Block don't kill they are solid blocks
                    if(platform.fuel){
                        world.romeo.position.set(world.romeo.position.x,
                                (world.romeo.position.y>platform.position.y)?
                                        platform.position.y+Platform.PLATFORM_HEIGHT/2+Romeo.ROMEO_HEIGHT/2
                                        :platform.position.y-Platform.PLATFORM_HEIGHT/2-Romeo.ROMEO_HEIGHT/2);
                        world.romeo.state = Romeo.ROMEO_STATE_STILL;
                    }else{
                        world.romeo.hitPlatform();
                        world.listener.hit(); //For music component

                        //Do let romeo cross the bricks
                        world.romeo.position.set(world.romeo.position.x,
                                (world.romeo.position.y>platform.position.y)?
                                        platform.position.y+Platform.PLATFORM_HEIGHT/2+Romeo.ROMEO_HEIGHT/2
                                        :platform.position.y-Platform.PLATFORM_HEIGHT/2-Romeo.ROMEO_HEIGHT/2);
                    }
                }
            }

            if(platform.fuel && !platform.fuelEmpty){

                if(world.romeo.position.y - platform.position.y < 1.2f+Romeo.ROMEO_HEIGHT/2
                        && world.romeo.position.y - platform.position.y > 0
                        && Math.abs(world.romeo.position.x - platform.position.x) < 0.8f
                        && world.romeo.propeller.state != Romeo.Propeller.PROPELLER_FILLING ){
                    //Romeo is near the fuel block
                    if(world.romeo.velocity.y!=0){
                        world.romeo.propeller.setFillingBlock(platform);
                        world.updateForFuelRefill(platform.position.x, platform.position.y);
                    }
                }
            }
        }
    }
    public void checkCollisions(){
//        checkPlatformCollisions();
//        checkEmeraldCollisions();
    }
}
