package com.halftspgames.rocketromeo.main;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.halftspgames.rocketromeo.framework.Game;
import com.halftspgames.rocketromeo.framework.Input.TouchEvent;
import com.halftspgames.rocketromeo.framework.gl.Animation;
import com.halftspgames.rocketromeo.framework.gl.Camera2D;
import com.halftspgames.rocketromeo.framework.gl.SpriteBatcher;
import com.halftspgames.rocketromeo.framework.gl.TextureRegion;
import com.halftspgames.rocketromeo.framework.math.OverlapTester;
import com.halftspgames.rocketromeo.framework.math.Rectangle;
import com.halftspgames.rocketromeo.framework.math.Vector2;

public class MainMenuScreen extends GLScreen{

    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle playBounds;
    Rectangle storyBounds;
    Rectangle rateBounds;
    Vector2 touchPoint;


    public MainMenuScreen(Game game) {
        super(game);
        // TODO Auto-generated constructor stub

        guiCam = new Camera2D(glGraphics, 320, 480);
        batcher = new SpriteBatcher(glGraphics, 100);
        playBounds = new Rectangle(40,72,240,96);
        rateBounds = new Rectangle(166,45,114,46);
        storyBounds = new Rectangle(40,45,114,46);
        touchPoint = new Vector2();

    }


    public void update(float deltaTime) {

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();


        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {

            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);

                if(OverlapTester.pointInRectangle(playBounds, touchPoint)) {

                    Assets.playSound(Assets.buttonSound);
                    //@TODO If user is opening for the first time then, show him the introduction without the
                    //Skip button, or else give the user option of skipping till visit 5. Then remove the intro
                    if(Settings.visitCount>5)
                        game.setScreen(new GameScreen(game));
                    else
                        game.setScreen(new OpeningScreen(game));
                    return ;
                }

                if(OverlapTester.pointInRectangle(storyBounds, touchPoint)) {

                    Assets.playSound(Assets.buttonSound);
                    //@TODO If user is opening for the first time then, show him the introduction without the
                    //Skip button, or else give the user option of skipping till visit 5. Then remove the intro
                    game.setScreen(new StoryScreen(game));
                    return ;
                }

                if(OverlapTester.pointInRectangle(rateBounds, touchPoint)) {

                    Assets.playSound(Assets.buttonSound);
                    //@TODO If user is opening for the first time then, show him the introduction without the
                    //Skip button, or else give the user option of skipping till visit 5. Then remove the intro
                    return ;
                }

            }
        }
    }


    public void present(float deltaTime) {

        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(Assets.background);
        batcher.drawSprite(160, 240, 320, 480, Assets.cyclicBackground1);
        batcher.endBatch("Main menu screen");

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);
        batcher.drawSprite(160, 400, 64, 64, Assets.romeoLarge);
        batcher.drawSprite(180, 280, 240, 140, Assets.rocketRomeoString);
        batcher.drawSprite(160, 150, 240, 96, Assets.playButton);
        batcher.drawSprite(97, 68, 114, 46, Assets.storyButton);
        batcher.drawSprite(223, 68, 114, 46, Assets.rateButton);

        batcher.endBatch("Main menu screen item loading.");

        gl.glEnable(GL10.GL_BLEND);
    }

    private void displayText(){
        String play = "PLAY";
        //Assets.font.drawText(batcher, play,160,172);

    }

    public void pause(){
        //Settings.save(game.getFileIO());
    }

    public void resume() {

    }

    public void dispose() {

    }


}
