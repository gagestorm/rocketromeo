package com.halftspgames.rocketromeo.main;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.halftspgames.rocketromeo.framework.math.OverlapTester;
import com.halftspgames.rocketromeo.framework.math.Rectangle;
import com.halftspgames.rocketromeo.framework.math.Vector2;
import com.halftspgames.rocketromeo.main.stages.Stage;
import com.halftspgames.rocketromeo.main.stages.StageOne;
import com.halftspgames.rocketromeo.main.stages.StageOpening;

public class World {

    public interface WorldListener {
        public void fillingTank();
        public void emptySoon();
        public void collectingEmerald();
        public void collectingEgg();
        public void shieldOn();
        public void savedByShield();
        public void teleport();
        public void propulsionOn();
        public void hit();
        public void burn();
        public void magnetField();
        public void earnedExtraLife();
        public void pointsEarned();
    }

    public static final float WORLD_WIDTH = 10;
    public static final float EACH_WORLD_HEIGHT = 15*21;

    public static final int WORLD_STATE_RUNNING = 0;
    public static final int WORLD_STATE_GAME_OVER = 2;
    public static final int GRAVITY = -15;
;
    public final Romeo romeo;
    public FuelPipe fuelPipe;
    public final WorldListener listener;
    public final Random rand;
    public Stage currStage;
    public float fallSoFar;
    public int score;
    public int emeraldCount;
    public int state;
    public Boolean newHighTrue= Boolean.FALSE;

    public World(WorldListener listener) {
        this.romeo = new Romeo(5,-2);
        this.listener = listener;
        rand = new Random();
        generateLevel();
        this.fallSoFar = 0;
        this.score = 0;
        this.emeraldCount = Settings.lastEmeralds;
        this.state = WORLD_STATE_RUNNING;
        this.fuelPipe = new FuelPipe(0,0);
    }

    public void propeller(Boolean active){
        if(romeo.propeller.state != Romeo.Propeller.PROPELLER_FILLING || !active) {
            romeo.propeller.updateState((active) ? Romeo.Propeller.PROPELLER_ON : Romeo.Propeller.PROPELLER_OFF);
            if(active == Boolean.TRUE)
                listener.propulsionOn();
        }
    }

    private void generateLevel(){

        currStage = new StageOpening(this);

        if (romeo.position.y <=0 || romeo.position.y > - EACH_WORLD_HEIGHT ){
            currStage = new StageOne(this);
        }else if (romeo.position.y > - EACH_WORLD_HEIGHT*2 ){
            //currStage = new StageTwo(this);
        }else if (romeo.position.y > - EACH_WORLD_HEIGHT*3 ){
           // currStage = new StageThree(this);
        }else if (romeo.position.y > - EACH_WORLD_HEIGHT*4 ){
            //currStage = new StageFour(this);
        }

        currStage.generateStageEnvironment();
    }

    public void update(float deltaTime,float accelX) {

        updateRomeo(deltaTime,accelX);
        fuelPipe.update(deltaTime,accelX);
        currStage.update(deltaTime);
        if(romeo.state != Romeo.ROMEO_STATE_HIT)
            checkCollisions();
        checkGameOver();
    }

    private void updateRomeo(float deltaTime,float accelX) {
        if(romeo.position.y==0){
            romeo.velocity.set(0,0);
        }

        if(romeo.state != Romeo.ROMEO_STATE_STILL)
            romeo.velocity.x = -accelX /10 * romeo.ROMEO_MOVE_VELOCITY;
        else
            romeo.velocity.x = 0;
        romeo.update(deltaTime);
        romeo.propeller.tilt(accelX);

        fallSoFar = Math.min(romeo.position.y,fallSoFar);

        if(fallSoFar<-score && fallSoFar < -12){
            int newScore = (int)(-1*fallSoFar - 12)/8;
            if( newScore > score )
                listener.pointsEarned();
            score = newScore;
        }else
            score = 0;
    }

    public void updateForFuelRefill(float x, float y){
        romeo.position.set(x , y + Platform.PLATFORM_HEIGHT/2 + Romeo.ROMEO_HEIGHT/2 +0.1f );
        romeo.velocity.set(0, 0);
        romeo.propeller.updateState(Romeo.Propeller.PROPELLER_FILLING);
        fuelPipe = new FuelPipe(x,y + Platform.PLATFORM_HEIGHT/2 + FuelPipe.FUEL_PIPE_HEIGHT/2);
    }

    private void checkCollisions() {
        checkEmeraldCollisions();
        currStage.stageAI();
    }


    private void checkEmeraldCollisions(){
        if(romeo.position.y > 0)
            return ;

        Emerald emerald;
        int len = currStage.emeralds.size();
        for(int i = 0; i < len; i++) {
            emerald = currStage.emeralds.get(i);
            if( Math.abs( romeo.position.y - emerald.position.y ) <= Romeo.ROMEO_HEIGHT ){
                if(OverlapTester.overlapRectangles(romeo.bounds, emerald.bounds)) {

                    currStage.emeralds.remove(emerald);
                    len-=1;
                    emeraldCount+=1;

                    if(emeraldCount==100){
                        romeo.extraLife();
                        emeraldCount-=100;
                        listener.earnedExtraLife();
                    }
                    listener.collectingEmerald();
                }
            }
        }
    }

    private void checkGameOver() {
        if(romeo.state == Romeo.ROMEO_STATE_HIT)
            state = WORLD_STATE_GAME_OVER;
    }


}
