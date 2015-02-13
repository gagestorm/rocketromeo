package com.halftspgames.rocketromeo.framework.gl;

import com.halftspgames.rocketromeo.framework.FileIO;
import com.halftspgames.rocketromeo.framework.impl.GLGame;
import com.halftspgames.rocketromeo.framework.impl.GLGraphics;

/**
 * Created by mareenator on 20/1/15.
 */
public class Sketch {

    GLGraphics glGraphics;
    float width;
    float height;

    public Sketch(GLGame glGame) {
        this.glGraphics = glGame.getGLGraphics();
        load();
    }

    public void load(){

    }
}
