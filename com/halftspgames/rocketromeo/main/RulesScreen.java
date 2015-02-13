package com.halftspgames.rocketromeo.main;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.halftspgames.rocketromeo.framework.Game;
import com.halftspgames.rocketromeo.framework.Input.TouchEvent;
import com.halftspgames.rocketromeo.framework.gl.Camera2D;
import com.halftspgames.rocketromeo.framework.gl.SpriteBatcher;
import com.halftspgames.rocketromeo.framework.math.OverlapTester;
import com.halftspgames.rocketromeo.framework.math.Rectangle;
import com.halftspgames.rocketromeo.framework.math.Vector2;

public class RulesScreen extends GLScreen{

    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle playBounds;
    Vector2 touchPoint;


    public RulesScreen(Game game) {
        super(game);
        // TODO Auto-generated constructor stub

        guiCam = new Camera2D(glGraphics, 320, 480);
        batcher = new SpriteBatcher(glGraphics, 100);
        playBounds = new Rectangle( 128, 48, 64, 64);
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
                    game.setScreen(new GameScreen(game));
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
        batcher.drawSprite(160, 240, 320, 480, Assets.commonBackgroundRegion);
        batcher.endBatch("Rules sCreen");

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);

        batcher.drawSprite( 160, 440, 124, 64, Assets.tileSmall);
        batcher.drawSprite(160, 360, 64, 64, Assets.romeoPropellerOff);
        batcher.drawSprite(240, 204, 48, 48, Assets.fuelBlock);
        //displayText();

        batcher.endBatch("Rules Screen ");

        gl.glEnable(GL10.GL_BLEND);
    }

    private void displayText(){
        String tap = "TAP & HOLD TO PROPEL";
        String play = "PLAY";
        String rate = "RATE";
        String story = "STORY";

    }

    public void pause(){
        //Settings.save(game.getFileIO());
    }

    public void resume() {

    }

    public void dispose() {

    }


}
