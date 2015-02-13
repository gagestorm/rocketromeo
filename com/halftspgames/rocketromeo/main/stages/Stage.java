package com.halftspgames.rocketromeo.main.stages;

import com.halftspgames.rocketromeo.framework.gl.SpriteBatcher;
import com.halftspgames.rocketromeo.framework.math.Vector2;
import com.halftspgames.rocketromeo.main.Emerald;
import com.halftspgames.rocketromeo.main.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mareenator on 27/1/15.
 */
public abstract class Stage {
    public float stateTime;
    public float worldEnd;
    public final List<Platform> platforms;
    public final List<Emerald> emeralds;
    public Stage(float end){
        this.platforms = new ArrayList<Platform>();
        this.emeralds = new ArrayList<Emerald>();
        this.stateTime = 0;
        this.worldEnd = end;
    }
    public abstract void generateStageEnvironment();
    public abstract void renderObjects(SpriteBatcher batcher);
    public abstract void update(float deltaTime);
    public abstract void stageAI();
}
