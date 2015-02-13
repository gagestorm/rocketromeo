package com.halftspgames.rocketromeo.main;

import android.util.Log;

import javax.microedition.khronos.opengles.GL10;

import com.halftspgames.rocketromeo.framework.gl.Animation;
import com.halftspgames.rocketromeo.framework.gl.Camera2D;
import com.halftspgames.rocketromeo.framework.gl.FPSCounter;
import com.halftspgames.rocketromeo.framework.gl.SpriteBatcher;
import com.halftspgames.rocketromeo.framework.gl.Text;
import com.halftspgames.rocketromeo.framework.gl.Texture;
import com.halftspgames.rocketromeo.framework.gl.TextureRegion;
import com.halftspgames.rocketromeo.framework.impl.GLGraphics;
import com.halftspgames.rocketromeo.framework.math.Vector2;


public class WorldRenderer {

    static final float FRUSTUM_WIDTH = 10;
    static final float FRUSTUM_HEIGHT = 15;

    GLGraphics glGraphics;
    World world;
    Camera2D cam;
    SpriteBatcher batcher;
    FPSCounter fps;
    GL10 gl;


    public WorldRenderer(GLGraphics glGraphics, SpriteBatcher batcher, World world) {
        this.glGraphics = glGraphics;
        this.gl = glGraphics.getGL();
        this.world = world;
        this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
        this.batcher = batcher;
        this.fps = new FPSCounter();

    }

    public void render(){

        if( world.romeo.position.y < -5f  && world.romeo.velocity.y < 0 ) {
            cam.position.y = world.romeo.position.y-2.5f;
        }else if( world.romeo.position.y > 0 )
            cam.position.y = FRUSTUM_HEIGHT;
        else if(world.romeo.position.y > -5f)
            cam.position.y = -FRUSTUM_HEIGHT/2;
        else if( world.romeo.velocity.y >0 && (world.romeo.position.y - cam.position.y) > 7.5f ){
            cam.position.y += 7.5f;
        }

        cam.setViewportAndMatrices();
        renderBackground();
        renderObjects();
        fps.logFrame();
    }

    public void renderBackground() {

        batcher.beginBatch(Assets.background);
//
//        if(world.romeo.position.y >0){
//            batcher.drawSprite(cam.position.x, cam.position.y,
//                    FRUSTUM_WIDTH, FRUSTUM_HEIGHT,
//                    Assets.commonBackgroundRegion);
//        }else{
        TextureRegion keyFrame1,keyFrame2,keyframe3;

        int screenNumber = -(int)(world.romeo.position.y/15);

        switch(screenNumber%3){

            case 0: keyFrame1 = Assets.cyclicBackground1;
                    keyFrame2 = Assets.cyclicBackground2;
                    keyframe3 = Assets.cyclicBackground3;
                    break;
            case 1: keyFrame1 = Assets.cyclicBackground2;
                    keyFrame2 = Assets.cyclicBackground3;
                    keyframe3 = Assets.cyclicBackground1;
                    break;
            case 2:
            default:
                    keyFrame1 = Assets.cyclicBackground3;
                    keyFrame2= Assets.cyclicBackground1;
                    keyframe3 = Assets.cyclicBackground2;
                    break;
        }

        batcher.drawSprite(cam.position.x, -(screenNumber*15 + FRUSTUM_HEIGHT/2),
                FRUSTUM_WIDTH, FRUSTUM_HEIGHT, keyFrame1);
        batcher.drawSprite(cam.position.x, -((screenNumber+1)*15 + FRUSTUM_HEIGHT/2),
                FRUSTUM_WIDTH, FRUSTUM_HEIGHT, keyFrame2);
        if(screenNumber > 0)
            batcher.drawSprite(cam.position.x, -((screenNumber-1)*15 + FRUSTUM_HEIGHT/2),
                FRUSTUM_WIDTH, FRUSTUM_HEIGHT,keyframe3);

//        }
        batcher.endBatch("Background");
    }

    public void renderObjects() {

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);

        world.currStage.renderObjects(batcher);
        //Log.d("world Renderre","State of propeller"+world.romeo.propeller.state);
        if(world.romeo.propeller.state == Romeo.Propeller.PROPELLER_FILLING){
            batcher.drawSprite(world.fuelPipe.position.x, world.fuelPipe.position.y,
                    FuelPipe.FUEL_PIPE_WIDTH, FuelPipe.FUEL_PIPE_HEIGHT,
                    Assets.fuelPipe.getKeyFrame(world.fuelPipe.stateTime,
                            Animation.ANIMATION_LOOPING));
        }
        renderromeo();
        renderFuelMeter();
        renderEmeraldCount();

        if(world.state == World.WORLD_STATE_GAME_OVER) {
            gameOverObjects();
        }else{
            if(world.romeo.position.y<0) {
                renderScore();
            }
        }

        batcher.endBatch("Object rendering");
        gl.glDisable(GL10.GL_BLEND);
    }


    private void renderromeo() {

        TextureRegion keyFrame;
        float side = (world.romeo.propeller.propellerAngleRadian < 0)? 1:-1;
        switch(world.romeo.state) {

            case Romeo.ROMEO_STATE_FALL:
                keyFrame = Assets.romeoNormalState;
                break;
            case Romeo.ROMEO_STATE_PROPELLER_ON:
                keyFrame = Assets.smokeDuringPropulsion.getKeyFrame(world.romeo.stateTime,Animation.ANIMATION_LOOPING);
                batcher.drawSprite(world.romeo.position.x-0.6f*side, world.romeo.position.y-0.8f, side*1.5f, 1.5f,world.romeo.propeller.propellerAngleRadian,
                        keyFrame);
                keyFrame = Assets.romeoDuringPropulsion.getKeyFrame(world.romeo.stateTime, Animation.ANIMATION_LOOPING);
                break;
            case Romeo.ROMEO_STATE_STILL:
                if(world.romeo.velocity.x!=0){
                    //make me walk
                    keyFrame = Assets.romeoWalk.getKeyFrame(world.romeo.stateTime,Animation.ANIMATION_LOOPING);
                }else
                    keyFrame = Assets.romeoWalk.getKeyFrame(0,Animation.ANIMATION_LOOPING);
                break;
            case Romeo.ROMEO_STATE_HIT:
            default:
                keyFrame = Assets.romeoHit;
        }

        batcher.drawSprite(world.romeo.position.x, world.romeo.position.y, side*1.5f, 1.5f,world.romeo.propeller.propellerAngleRadian,
                    keyFrame);
    }

    private void renderFuelMeter(){
        int level = world.romeo.propeller.fuelLevel();
        int count = 1;
        float x = 1f;
        if(level<2){
            world.listener.emptySoon();
        }

        batcher.drawSprite(0.55f,cam.position.y + FRUSTUM_HEIGHT/2 - 0.4f, 1f, 0.5f, Assets.fuelLevelMarker);
        while(count<11){
            x += 0.2f;
            if(count <= level)
                batcher.drawSprite(x, cam.position.y + FRUSTUM_HEIGHT/2 - 0.4f, 0.125f, 0.5f, Assets.fuelLevel);
            else
                batcher.drawSprite(x, cam.position.y + FRUSTUM_HEIGHT/2 - 0.4f, 0.125f, 0.5f, Assets.fuelEmptyLevel);
            count++;
        }
    }

    private void renderEmeraldCount(){
        Assets.score.drawScore(batcher,world.emeraldCount,8.8f, cam.position.y + FRUSTUM_HEIGHT/2 - 0.5f,0,0.75f);
        batcher.drawSprite(9.5f, cam.position.y + FRUSTUM_HEIGHT/2 - 0.4f, 1f, 1f, Assets.redEmeraldWithoutBubble);
    }

    private void renderScore(){
        Assets.score.drawScoreCentre(batcher,world.score, 5f, cam.position.y - FRUSTUM_HEIGHT/2 + 1f,1,0.75f);
    }

    private void gameOverObjects(){
        float currentWorld = cam.position.y;

        batcher.drawSprite(5f,currentWorld+5.5f,7.5f,1.25f,Assets.gameOverString);
        batcher.drawSprite(5f,currentWorld+0.5f,8.5f,8.5f,Assets.whiteBoard);
        batcher.drawSprite(5f,currentWorld+3f,1.5f,1.5f,1.7f,Assets.romeoHit);
        batcher.drawSprite(5f, currentWorld-3f, 6f, 2.4f, Assets.playButton);
        batcher.drawSprite(3.4f, currentWorld - 5f, 2.8f, 1.2f, Assets.shareButton);
        batcher.drawSprite(6.6f, currentWorld - 5f, 2.8f, 1.2f, Assets.rateButton);
        batcher.drawSprite(5f, currentWorld - 6.4f, 6f, 1.2f, Assets.leaderboardButton);
        Assets.score.drawScoreCentre(batcher,world.score, 5f,currentWorld+1f,1,0.75f);
        batcher.drawSprite(5f, currentWorld-0.25f, 1f, 0.5f, Assets.best);
        Log.d("Rendered Class","Rendering high score"+Settings.highscore);

        if(world.newHighTrue)
            batcher.drawSprite(6f, currentWorld + 1.5f, 1.5f, 1f, Assets.newIcon);

        Assets.score.drawScoreCentre(batcher,Settings.highscore,5f,currentWorld-1f,2,1f);

    }

}
