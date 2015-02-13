package com.halftspgames.rocketromeo.main;

import android.util.Log;

import com.halftspgames.rocketromeo.framework.Music;
import com.halftspgames.rocketromeo.framework.Sound;
import com.halftspgames.rocketromeo.framework.gl.Animation;
import com.halftspgames.rocketromeo.framework.gl.Font;
import com.halftspgames.rocketromeo.framework.gl.Score;
import com.halftspgames.rocketromeo.framework.gl.Text;
import com.halftspgames.rocketromeo.framework.gl.Texture;
import com.halftspgames.rocketromeo.framework.gl.TextureRegion;
import com.halftspgames.rocketromeo.framework.impl.GLGame;

public class Assets {
    public static Texture background;
    public static TextureRegion depthBackground1;
    public static TextureRegion depthBackground2;
    public static TextureRegion commonBackgroundRegion;
    public static TextureRegion cyclicBackground1;
    public static TextureRegion cyclicBackground2;
    public static TextureRegion cyclicBackground3;

    public static Texture items;
    public static TextureRegion playButton;
    public static TextureRegion rateButton;
    public static TextureRegion shareButton;
    public static TextureRegion leaderboardButton;
    public static TextureRegion storyButton;
    public static TextureRegion newIcon;
    public static TextureRegion nextArrow;
    public static TextureRegion whiteBoard;
    public static TextureRegion tapImage;
    public static TextureRegion tiltImage;
    public static TextureRegion redEmeraldWithoutBubble;

    public static TextureRegion romeoFalling;
    public static TextureRegion redEmerald;
    public static TextureRegion greenEmerald;
    public static TextureRegion rulesText;
    public static TextureRegion gameOverString;
    public static TextureRegion rocketRomeoString;
    public static TextureRegion romeoLarge;
    public static TextureRegion highScoreButton;
    public static TextureRegion tileBig;
    public static TextureRegion tileSmall;
    public static Animation cutterBridge;
    public static TextureRegion lavaBridge;
    public static TextureRegion glitter;
    public static TextureRegion steelBridge;
    public static TextureRegion stoneBridge;
    public static TextureRegion groundBridge1;
    public static TextureRegion groundBridge2;
    public static TextureRegion fuelRocket;
    public static TextureRegion rule1a;
    public static TextureRegion rule1b;
    public static TextureRegion rule2;
    public static TextureRegion rule3;
    public static TextureRegion rule4;
    public static TextureRegion story;
    public static TextureRegion ready;
    public static TextureRegion best;
    public static TextureRegion fuelLevel;
    public static TextureRegion fuelLevelMarker;
    public static TextureRegion teleporter;
    public static TextureRegion fuelEmptyLevel;
    public static Score score;
    public static TextureRegion romeoHit;
    public static TextureRegion romeoNormalState;
    public static TextureRegion fuelBlock;

    public static TextureRegion romeoPropellerOff;
    public static Animation romeoWalk;
    public static Animation romeoDuringPropulsion;
    public static Animation smokeDuringPropulsion;
    public static Animation dragonFly;
    public static Animation guardFlight;
    public static Animation dragonFire;
    public static Animation fuelPipe;
    public static Animation fuelBlockChange;
//    public static Text molotText;
//    public static Text colabText;

    //public static Music music;
    public static Sound beeEntry;
    public static Sound buttonSound;
    public static Sound dragonFireSound;
    public static Sound hammeringSound;
    public static Sound hittingBlocks;
    public static Sound pointSound;
    public static Sound rechargedRocketSound;
    public static Sound storySound;
    public static Sound tickTokSound;
    public static Sound propulsionSound;
    public static Sound beepSound;
    public static Sound chickenSound;
    public static Sound buttonClick;

    public static void load(GLGame game) {

        background = new Texture(game, "atlas-new-background.png");
        cyclicBackground1 = new TextureRegion(background, 0, 0, 320, 480);
        cyclicBackground2 = new TextureRegion(background, 320, 0, 320, 480);
        cyclicBackground3 = new TextureRegion(background, 640, 0, 320, 480);

        depthBackground1 = new TextureRegion(background, 0, 0, 320, 480);
        depthBackground2 = new TextureRegion(background, 640, 0, 320, 480);
        commonBackgroundRegion = new TextureRegion(background, 320, 0, 320, 480);

        items = new Texture(game, "atlas-items-new.png");

        playButton = new TextureRegion(items,704,640,320,128);
        shareButton = new TextureRegion(items,704,768,160,64);
        rateButton = new TextureRegion(items,864,768,160,64);
        leaderboardButton = new TextureRegion(items,704,832,320,64);
        storyButton = new TextureRegion(items,704,896,160,64);
        newIcon = new TextureRegion(items,944,896,80,64);
        nextArrow = new TextureRegion(items,864,896,80,64);
        whiteBoard = new TextureRegion(items,256,576,448,448);
        teleporter = new TextureRegion(items,256,384,128,128);
        redEmerald   = new TextureRegion(items,0,512,64,64);
        greenEmerald = new TextureRegion(items,0,577,64,64);
        rulesText = new TextureRegion(items,896,992,128,32);
        gameOverString = new TextureRegion(items,640,0,384,64);
        rocketRomeoString = new TextureRegion(items,640,64,384,224);
        romeoLarge = new TextureRegion(items,0,0,128,128);
        tapImage = new TextureRegion(items,832,960,64,64);
        tiltImage = new TextureRegion(items,704,960,96,64);
        romeoFalling = new TextureRegion(items,128,0,64,44);
        romeoNormalState = new TextureRegion(items,128,64,64,64);

        groundBridge1 = new TextureRegion(items,128,576,64,64);
        groundBridge2 = new TextureRegion(items,128,576,64,64);

        rule1a = new TextureRegion(items,256,128,140,64);
        rule1b = new TextureRegion(items,256,192,128,64);
        rule2 = new TextureRegion(items,256,0,192,128);
        rule3 = new TextureRegion(items,448,0,192,64);
        rule4 = new TextureRegion(items,448,64,192,128);
        story = new TextureRegion(items,640,284,384,280);
        ready = new TextureRegion(items,768,576,256,64);
        best = new TextureRegion(items,960,960,64,32);

        redEmeraldWithoutBubble = new TextureRegion(items,192,448,64,64);
        fuelLevelMarker = new TextureRegion(items,0,256,64,32);
        fuelLevel = new TextureRegion(items,96,704,16,64);
        fuelEmptyLevel = new TextureRegion(items,112,704,16,64);

        steelBridge = new TextureRegion(items,192,192,64,64);
        fuelRocket = new TextureRegion(items,384,256,64,64);
        stoneBridge = new TextureRegion(items,0,448,64,64);
        romeoHit = new TextureRegion(items,192,0,64,64);
        glitter = new TextureRegion(items,320,256,64,64);

        smokeDuringPropulsion = new Animation(0.4f,
                new TextureRegion(items, 0, 384, 64, 64),
                new TextureRegion(items, 64, 384, 64, 64),
                new TextureRegion(items, 128, 384, 64, 64),
                new TextureRegion(items, 192, 384, 64, 64),
                new TextureRegion(items, 0, 448, 64, 64),
                new TextureRegion(items, 64, 448, 64, 64),
                new TextureRegion(items, 128, 448, 64, 64),
                new TextureRegion(items, 192, 448, 60, 60),
                new TextureRegion(items, 256, 448, 60, 60)
        );

        romeoDuringPropulsion = new Animation(0.2f,
                new TextureRegion(items, 0, 320, 64, 64),
                new TextureRegion(items, 64, 320, 64, 64),
                new TextureRegion(items, 128, 320, 64, 64),
                new TextureRegion(items, 192, 320, 64, 64),
                new TextureRegion(items, 128, 320, 64, 64),
                new TextureRegion(items, 192, 320, 64, 64)
        );

        romeoWalk = new Animation(0.3f,
                new TextureRegion(items, 128, 64, 64, 64),
                new TextureRegion(items, 192, 64, 64, 64),
                new TextureRegion(items, 128, 128, 64, 64)
                );

        dragonFly = new Animation(0.1f,
                new TextureRegion(items, 128, 256, 96, 64),
                new TextureRegion(items, 128, 256, 96, 64)
        );

        guardFlight = new Animation(0.1f,
                new TextureRegion(items, 512, 320, 96, 64),
                new TextureRegion(items, 512, 400, 96, 64)
        );

        dragonFire = new Animation(0.1f,
                new TextureRegion(items, 384, 512, 48, 64),
                new TextureRegion(items, 432, 400, 48, 64),
                new TextureRegion(items, 480, 400, 48, 64),
                new TextureRegion(items, 528, 400, 48, 64)
        );

        cutterBridge = new Animation(0.5f,
                new TextureRegion(items,   0, 192, 64, 64),
                new TextureRegion(items,  64, 128, 64, 64),
                new TextureRegion(items,  64, 192, 64, 64),
                new TextureRegion(items,  64, 256, 64, 64)
        );

        fuelPipe = new Animation(0.4f,
                new TextureRegion(items,  0, 512, 16, 32),
                new TextureRegion(items, 16, 512, 16, 32),
                new TextureRegion(items, 32, 512, 16, 32),
                new TextureRegion(items, 48, 512, 16, 32),
                new TextureRegion(items,  0, 544, 16, 32),
                new TextureRegion(items, 16, 544, 16, 32),
                new TextureRegion(items, 32, 544, 16, 32),
                new TextureRegion(items, 48, 544, 16, 32)
                );

        fuelBlockChange = new Animation(2f,
                new TextureRegion(items, 64, 448, 32, 32),
                new TextureRegion(items, 96, 448, 32, 32),
                new TextureRegion(items, 64, 480, 32, 32),
                new TextureRegion(items, 96, 480, 32, 32)
        );

        score = new Score(Assets.items);

        //music = game.getAudio().newMusic("music.mp3");
        //music.setLooping(true);
        //music.setVolume(0.5f);

        //if(Settings.soundEnabled)
            //music.play();

        beeEntry = game.getAudio().newSound("beeflight.wav");
        buttonSound = game.getAudio().newSound("button.wav");
        dragonFireSound = game.getAudio().newSound("dragonfire.wav");
        hammeringSound = game.getAudio().newSound("hitWalls.wav");
        hittingBlocks = game.getAudio().newSound("hitWalls.wav");
        pointSound = game.getAudio().newSound("points.wav");
        rechargedRocketSound = game.getAudio().newSound("recharged.wav");
        storySound = game.getAudio().newSound("story.wav");
        tickTokSound = game.getAudio().newSound("ticktock.wav");
        propulsionSound = game.getAudio().newSound("hitting.wav");
        beepSound = game.getAudio().newSound("beep.wav");
        chickenSound = game.getAudio().newSound("deadchicken.wav");
        buttonClick = game.getAudio().newSound("buttonClick.WAV");
    }

    public static void reload() {

        background.reload();
        items.reload();
//        if(Settings.soundEnabled)
//            music.play();
    }

    public static void playSound(Sound sound) {
        //if(Settings.soundEnabled)
            sound.play(1);
    }



}
