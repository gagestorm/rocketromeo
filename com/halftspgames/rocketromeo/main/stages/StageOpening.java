package com.halftspgames.rocketromeo.main.stages;

import com.halftspgames.rocketromeo.framework.gl.SpriteBatcher;
import com.halftspgames.rocketromeo.main.Assets;
import com.halftspgames.rocketromeo.main.Platform;
import com.halftspgames.rocketromeo.main.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mareenator on 2/2/15.
 */
public class StageOpening extends Stage {

    public static final float STAGE_ORIGIN=15;

    public static World world;
    public final Random rand;

    public StageOpening(World world){
        super(0);
        this.world = world;
        rand = new Random();
    }

    public void renderObjects(SpriteBatcher batcher){
        batcher.drawSprite(5f,5f,4f,1f,Assets.ready);
    }

    @Override
    public void generateStageEnvironment() {
        return;
    }

    @Override
    public void update(float deltaTime) {
        return;
    }

    @Override
    public void stageAI() {
        return;
    }

}
