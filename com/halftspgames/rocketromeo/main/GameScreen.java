package com.halftspgames.rocketromeo.main;

import android.util.Log;

import java.util.List;
import java.util.Set;

import javax.microedition.khronos.opengles.GL10;

import com.halftspgames.rocketromeo.framework.Game;
import com.halftspgames.rocketromeo.framework.Input.TouchEvent;
import com.halftspgames.rocketromeo.framework.gl.Camera2D;
import com.halftspgames.rocketromeo.framework.gl.SpriteBatcher;
import com.halftspgames.rocketromeo.framework.math.OverlapTester;
import com.halftspgames.rocketromeo.framework.math.Rectangle;
import com.halftspgames.rocketromeo.framework.math.Vector2;
import com.halftspgames.rocketromeo.main.World.WorldListener;

public class GameScreen extends GLScreen {

    static final int GAME_READY = 0;
    static final int GAME_RUNNING = 1;
    static final int GAME_PAUSED = 2;
    static final int GAME_OVER = 4;

    int state;
    Camera2D guiCam;
    Vector2 touchPoint;
    SpriteBatcher batcher;
    World world;
    WorldListener worldListener;
    WorldRenderer renderer;
    int lastScore;
    Rectangle playBounds;
    Rectangle rateBounds;
    Rectangle shareBounds;
    Rectangle leaderBoardBounds;

    public GameScreen(Game game) {
        super(game);

        state = GAME_READY;
        guiCam = new Camera2D(glGraphics, 320, 480);
        touchPoint = new Vector2();
        batcher = new SpriteBatcher(glGraphics, 1000);

        //World Listener, various events happening.
        worldListener = new WorldListener() {

            @Override
            public void hit() {
                Assets.playSound(Assets.chickenSound);
                state = GAME_OVER;
            }

            @Override
            public void burn() {

            }

            @Override
            public void magnetField() {

            }

            @Override
            public void earnedExtraLife() {

            }

            @Override
            public void pointsEarned() {
                Assets.playSound(Assets.buttonClick);
            }

            @Override
            public void fillingTank() {

            }

            @Override
            public void emptySoon() {
                Assets.playSound(Assets.beepSound);
            }

            @Override
            public void collectingEmerald() {
                Assets.playSound(Assets.pointSound);
            }

            @Override
            public void collectingEgg() {

            }

            @Override
            public void shieldOn() {

            }

            @Override
            public void savedByShield() {

            }

            @Override
            public void teleport() {

            }

            @Override
            public void propulsionOn() {
                // TODO Auto-generated method stub
                Log.d("Listene:r", "<==========  PROPULSION ON ===========>");
                Assets.playSound(Assets.propulsionSound);
            }

        };

        world = new World(worldListener);
        renderer = new WorldRenderer(glGraphics, batcher, world);
        playBounds = new Rectangle(64, 105, 192, 96);
        rateBounds = new Rectangle(166, 60, 90, 38);
        shareBounds = new Rectangle(64, 60, 90, 38);
        leaderBoardBounds = new Rectangle(64, 16, 192, 60);
        lastScore = 0;

    }

    public void update(float deltaTime){
        if(deltaTime > 0.1f)
            deltaTime = 0.1f;

        switch(state) {
            case GAME_READY:
                updateReady();
                break;
            case GAME_RUNNING:
                updateRunning(deltaTime);
                break;
            case GAME_PAUSED:
                updatePaused();
                break;
            case GAME_OVER:
                updateGameOver();
                break;
        }

    }

    private void updateReady() {
        if(game.getInput().getTouchEvents().size() > 0)
            state = GAME_RUNNING;
    }

    private void updateRunning(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();

        for(int i = 0; i < len; i++) {

            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP){
                world.propeller(Boolean.FALSE);
                continue;
            }

            if( event.type == TouchEvent.TOUCH_DOWN){
                world.propeller(Boolean.TRUE);
            }

            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

        }

        world.update(deltaTime,game.getInput().getAccelX());
        if(world.score != lastScore) {
            lastScore = world.score;
        }

        if(world.emeraldCount != Settings.lastEmeralds)
            Settings.lastEmeralds = world.emeraldCount;

        if(world.state == World.WORLD_STATE_GAME_OVER) {
            state = GAME_OVER;
            Settings.lastEmeralds = world.emeraldCount;
            if(Settings.highscore < lastScore) {
                Settings.highscore = lastScore;
                world.newHighTrue = Boolean.TRUE;
            }
            setupNewBounds();
        }

    }

    private void setupNewBounds(){

    }

    private void updatePaused() { }

    private void updateGameOver() {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type != TouchEvent.TOUCH_UP)
                continue;
            touchPoint.set(event.x, event.y);
            guiCam.touchToWorld(touchPoint);

            if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {
                Assets.playSound(Assets.buttonClick);
                game.setScreen(new GameScreen(game));
                return ;
            }

            if(OverlapTester.pointInRectangle(shareBounds, touchPoint)) {
                Assets.playSound(Assets.buttonClick);
                //TODO Do some sharing stuff here
                return ;
            }

            if(OverlapTester.pointInRectangle(rateBounds, touchPoint)) {
                Assets.playSound(Assets.buttonClick);
                //TODO Some rating stuff here
                return ;
            }

            if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {
                Assets.playSound(Assets.buttonClick);
                //Todo some leaderboard stuff
                return ;
            }
        }
    }

    public void present(float deltaTime) {
        GL10 gl = glGraphics.getGL();
        //gl.glClearColor(1,1,1,1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        renderer.render();
        guiCam.setViewportAndMatrices();
    }

    public void pause() {
        if(state == GAME_RUNNING)
            state = GAME_PAUSED;
    }

    public void resume() {
        if(state == GAME_PAUSED)
            state = GAME_READY;
    }
    public void dispose() {}


}
