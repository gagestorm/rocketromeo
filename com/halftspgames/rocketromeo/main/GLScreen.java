package com.halftspgames.rocketromeo.main;

import com.halftspgames.rocketromeo.framework.Game;
import com.halftspgames.rocketromeo.framework.Screen;
import com.halftspgames.rocketromeo.framework.impl.GLGame;
import com.halftspgames.rocketromeo.framework.impl.GLGraphics;

public class GLScreen extends Screen{

    protected final GLGraphics glGraphics;
    protected final GLGame glGame;


    public GLScreen(Game game) {
        super(game);
        // TODO Auto-generated constructor stub

        glGame = (GLGame)game;
        glGraphics = ((GLGame)game).getGLGraphics();


    }



    @Override
    public void update(float deltaTime) {
        // TODO Auto-generated method stub

    }

    @Override
    public void present(float deltaTime) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
}
