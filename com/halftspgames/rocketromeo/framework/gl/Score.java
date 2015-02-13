package com.halftspgames.rocketromeo.framework.gl;

import android.util.Log;

public class Score {
    public final Texture texture;

    public static final int LARGE_GLYPH=1;
    public static final int SMALL_BLACK_GLYPH=2;
    public static final int SMALL_WHITE_GLYPH=3;

    public final TextureRegion[] smallBlackglyphs = new TextureRegion[10];
    public final TextureRegion[] smallWhiteglyphs = new TextureRegion[10];
    public final TextureRegion[] largeglyphs      = new TextureRegion[10];

    public Score(Texture texture) {

        this.texture = texture;
        //All the large font Molot Regular
        largeglyphs[0] = new TextureRegion(texture,96,832,48,64);
        largeglyphs[1] = new TextureRegion(texture,0,768,48,64);
        largeglyphs[2] = new TextureRegion(texture,0,832,48,64);
        largeglyphs[3] = new TextureRegion(texture,0,896,48,64);
        largeglyphs[4] = new TextureRegion(texture,0,960,48,64);
        largeglyphs[5] = new TextureRegion(texture,48,768,48,64);
        largeglyphs[6] = new TextureRegion(texture,48,832,48,64);
        largeglyphs[7] = new TextureRegion(texture,48,896,48,64);
        largeglyphs[8] = new TextureRegion(texture,48,960,48,64);
        largeglyphs[9] = new TextureRegion(texture,96,768,48,64);

        smallBlackglyphs[0] = new TextureRegion(texture,224,792,20,24);
        smallBlackglyphs[1] = new TextureRegion(texture,144,768,20,24);
        smallBlackglyphs[2] = new TextureRegion(texture,164,768,20,24);
        smallBlackglyphs[3] = new TextureRegion(texture,184,768,20,24);
        smallBlackglyphs[4] = new TextureRegion(texture,204,768,20,24);
        smallBlackglyphs[5] = new TextureRegion(texture,224,768,20,24);
        smallBlackglyphs[6] = new TextureRegion(texture,144,792,20,24);
        smallBlackglyphs[7] = new TextureRegion(texture,164,792,20,24);
        smallBlackglyphs[8] = new TextureRegion(texture,184,792,20,24);
        smallBlackglyphs[9] = new TextureRegion(texture,204,792,20,24);

        smallWhiteglyphs[0] = new TextureRegion(texture,224,840,20,24);
        smallWhiteglyphs[1] = new TextureRegion(texture,144,816,20,24);
        smallWhiteglyphs[2] = new TextureRegion(texture,164,816,20,24);
        smallWhiteglyphs[3] = new TextureRegion(texture,184,816,20,24);
        smallWhiteglyphs[4] = new TextureRegion(texture,204,816,20,24);
        smallWhiteglyphs[5] = new TextureRegion(texture,224,816,20,24);
        smallWhiteglyphs[6] = new TextureRegion(texture,144,840,20,24);
        smallWhiteglyphs[7] = new TextureRegion(texture,164,840,20,24);
        smallWhiteglyphs[8] = new TextureRegion(texture,184,840,20,24);
        smallWhiteglyphs[9] = new TextureRegion(texture,204,840,20,24);
    }


    public void drawScore(SpriteBatcher batcher,int count, float x, float y, int type, float size) {

        TextureRegion[] glyphs;
        float glyphWidth,glyphHeight;
        switch (type){
            case 1: glyphs = largeglyphs;
                    glyphHeight = 64*size;
                    glyphWidth  = 48*size;
                    break;
            case 2: glyphs = smallBlackglyphs;
                    glyphHeight = 24*size;
                    glyphWidth  = 20*size;
                    break;
           default: glyphs = smallWhiteglyphs;
                    glyphHeight = 24*size;
                    glyphWidth  = 20*size;
        }

        int last;
        if( count > 0 ){
            while(count>0){
                last = count%10;
                batcher.drawSprite(x, y, glyphWidth*0.03125f, glyphHeight*0.03125f, glyphs[last]);
                x-= glyphWidth*0.03125f;
                count -= last;
                count /= 10;
            }
        }else
            batcher.drawSprite(x, y, glyphWidth*0.03125f, glyphHeight*0.03125f, glyphs[0]);


    }

    public void drawScoreCentre(SpriteBatcher batcher,int count, float x, float y, int type, float size) {
        Log.d("Score Class", "To render"+count);
        TextureRegion[] glyphs;
        float glyphWidth,glyphHeight;
        switch (type){
            case 1: glyphs = largeglyphs;
                glyphHeight = 64*size;
                glyphWidth  = 48*size;
                break;
            case 2: glyphs = smallBlackglyphs;
                glyphHeight = 24*size;
                glyphWidth  = 20*size;
                break;
            default: glyphs = smallWhiteglyphs;
                glyphHeight = 24*size;
                glyphWidth  = 20*size;
        }

        int last;
        double len;
        if(count>0) {
            len = Math.floor(Math.log10(count) + 1) * glyphWidth * 0.03125f / 2;
            x = x + (float) len - glyphWidth * 0.03125f / 2;
        }

        if( count > 0 ){
            while(count>0){
                last = count%10;
                batcher.drawSprite(x, y, glyphWidth*0.03125f, glyphHeight*0.03125f, glyphs[last]);
                x-= glyphWidth*0.03125f;
                count -= last;
                count /= 10;
            }
        }else
            batcher.drawSprite(x, y, glyphWidth*0.03125f, glyphHeight*0.03125f, glyphs[0]);


    }
}
