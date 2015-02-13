package com.halftspgames.rocketromeo.main;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.halftspgames.rocketromeo.framework.Game;
import com.halftspgames.rocketromeo.framework.Input.TouchEvent;
import com.halftspgames.rocketromeo.framework.gl.Camera2D;
import com.halftspgames.rocketromeo.framework.gl.FPSCounter;
import com.halftspgames.rocketromeo.framework.gl.SpriteBatcher;
import com.halftspgames.rocketromeo.framework.gl.TextureRegion;
import com.halftspgames.rocketromeo.framework.math.OverlapTester;
import com.halftspgames.rocketromeo.framework.math.Rectangle;
import com.halftspgames.rocketromeo.framework.math.Vector2;

public class OpeningScreen extends GLScreen{

    Camera2D guiCam;
    SpriteBatcher batcher;
    Rectangle nextBounds;
    Rectangle prevBounds;
    Vector2 touchPoint;
    public float stateTime;
    Vector2 whiteBoardPosition;
    Boolean whiteBoradinTransition=Boolean.TRUE;
    public static int currentPage=1;
    GL10 gl;
    FPSCounter fps;

    public OpeningScreen(Game game) {
        super(game);
        // TODO Auto-generated constructor stub

        guiCam = new Camera2D(glGraphics, 320, 480);
        batcher = new SpriteBatcher(glGraphics, 100);
        nextBounds = new Rectangle(160,0,160,480);
        prevBounds = new Rectangle(0,0,160,480);
        touchPoint = new Vector2();
        this.stateTime =0;
        whiteBoardPosition = new Vector2(480,240);
        this.gl = glGraphics.getGL();
        this.fps = new FPSCounter();
    }


    public void update(float deltaTime) {

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        stateTime += deltaTime;

        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {

            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {
                touchPoint.set(event.x, event.y);
                guiCam.touchToWorld(touchPoint);

                if(OverlapTester.pointInRectangle(nextBounds, touchPoint)) {
                    Assets.playSound(Assets.buttonSound);
                    if(currentPage<4) {
                        currentPage += 1; //Increase and change page
                        whiteBoardPosition.set(480, 240);
                        this.stateTime = 0;
                        whiteBoradinTransition = Boolean.TRUE;
                    }

                    if(currentPage==4){
                        game.setScreen(new GameScreen(game));
                        return ;
                    }
                }
                if(OverlapTester.pointInRectangle(prevBounds, touchPoint)) {
                    if(currentPage>1) {
                        currentPage -= 1; //Increase and change page
                        whiteBoardPosition.set(-160, 240);
                        this.stateTime = 0;
                        whiteBoradinTransition = Boolean.TRUE;
                        Assets.playSound(Assets.buttonSound);
                    }
                }
            }
        }

        updateWhiteBoard(deltaTime);
    }

    public void changePage(){
        switch (currentPage){
            case 1: pageOne();
                break;
            case 2: pageTwo();
                break;
            case 3: pageThree();
                break;
            default:
                pageOne();
        }
    }

    public void present(float deltaTime) {

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(Assets.background);
        batcher.drawSprite(160, 240, 320, 480, Assets.commonBackgroundRegion);
        batcher.endBatch("Opening Screen");

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.items);

        batcher.drawSprite( 160, 420, 128, 32, Assets.rulesText);
        batcher.drawSprite(whiteBoardPosition.x, whiteBoardPosition.y, 290, 290, Assets.whiteBoard);
        changePage();
        fps.logFrame();
    }

    public void updateWhiteBoard(float deltaTime){
        if( whiteBoardPosition.x < 0 && whiteBoardPosition.x < 160)
            whiteBoardPosition.add(1200*deltaTime,0);
        else if(whiteBoardPosition.x > 320 && whiteBoardPosition.x > 160)
            whiteBoardPosition.add(-1200*deltaTime,0);
        else {
            whiteBoardPosition.set(160, 240);
            whiteBoradinTransition = Boolean.FALSE;
        }
    }

    public void pageOne(){
        if(whiteBoradinTransition){
            batcher.endBatch("Opening Screen");
            gl.glEnable(GL10.GL_BLEND);
            return;
        }

        batcher.drawSprite( 275,80, 40, 32, Assets.nextArrow);
        batcher.drawSprite(85, 320, 64, 64, Assets.romeoLarge);
        batcher.drawSprite(235, 320, 64, 64, -0.3f,Assets.romeoLarge);
        batcher.drawSprite(85, 145, 64, 64, Assets.tapImage);
        batcher.drawSprite(235, 145, 96, 64, Assets.tiltImage);
        batcher.drawSprite(85, 145, 70, 32, Assets.rule1a);
        batcher.drawSprite(235, 145, 64, 32, Assets.rule1b);

        batcher.endBatch("Opening Screen");
        gl.glEnable(GL10.GL_BLEND);

    }

    public void pageTwo(){
        if(whiteBoradinTransition){
            batcher.endBatch("Opening Screen");
            gl.glEnable(GL10.GL_BLEND);
            return;
        }

        batcher.drawSprite( 275,80, 40, 32, Assets.nextArrow);
        batcher.drawSprite( 35, 80, -40, 32, Assets.nextArrow);
        batcher.drawSprite(85,296, 64, 64, Assets.romeoNormalState);
        batcher.drawSprite(85,264, 64, 64, Assets.stoneBridge);
        batcher.drawSprite(52,145,64,64,Assets.redEmerald);
        batcher.drawSprite(116,145,64,64,Assets.greenEmerald);

        batcher.drawSprite(180, 280, 96, 64, Assets.rule2);
        batcher.drawSprite(180, 145, 6964, 32, Assets.rule3);
        batcher.endBatch("Opening Screen Page Two");
        gl.glEnable(GL10.GL_BLEND);

    }

    public void pageThree(){

        if(whiteBoradinTransition){
            batcher.endBatch("Opening Screen");
            gl.glEnable(GL10.GL_BLEND);
            return;
        }

        batcher.drawSprite( 275,80, 40, 32, Assets.nextArrow);
        batcher.drawSprite( 35, 80, -40, 32, Assets.nextArrow);
        batcher.drawSprite( 160, 320, 96, 96, Assets.teleporter);
        batcher.drawSprite( 160, 200, 96, 64, Assets.rule4);
        batcher.endBatch("Opening Screen Page Two");
        gl.glEnable(GL10.GL_BLEND);

    }


    public void pause(){
        //Settings.save(game.getFileIO());
    }

    public void resume() {

    }

    public void dispose() {

    }


}