package com.halftspgames.rocketromeo.framework.gl;

import javax.microedition.khronos.opengles.GL10;

import android.util.FloatMath;
import android.util.Log;

import com.halftspgames.rocketromeo.framework.impl.GLGraphics;
import com.halftspgames.rocketromeo.framework.math.Vector2;

public class SpriteBatcher {
	final float[] verticesBuffer;
	int bufferIndex;
	final Vertices vertices;
	int numSprites;
	
	
	public SpriteBatcher(GLGraphics glGraphics, int maxSprites) {
		this.verticesBuffer = new float[maxSprites * 4 * 4];
		this.vertices = new Vertices(glGraphics, maxSprites * 4, maxSprites * 6, false, true);
		
		this.bufferIndex = 0;
		this.numSprites = 0;
		
		short[] indices = new short[maxSprites * 6];
		int len = indices.length;
		short j = 0;
		for(int i = 0; i < len; i += 6, j += 4) {
			indices[i + 0] = (short)(j + 0);
			indices[i + 1] = (short)(j + 1);
			indices[i + 2] = (short)(j + 2);
			indices[i + 3] = (short)(j + 2);
			indices[i + 4] = (short)(j + 3);
			indices[i + 5] = (short)(j + 0);	
		}
		vertices.setIndices(indices, 0, indices.length);
		
	}
	
	
	
	public void beginBatch(Texture texture) {
		texture.bind();
		numSprites = 0;
		bufferIndex = 0;
		//9+vertices.unbind();
	}

    public void beginBatch()  {
        numSprites = 0;                                 // Empty Sprite Counter
        bufferIndex = 0;                                // Reset Buffer Index (Empty)
    }

	public void endBatch(String stage) {
        //Log.d("End Batch:", "Binding the verticesBuffer: Stage "+stage);
		vertices.setVertices(verticesBuffer, 0, bufferIndex);
        //Log.d("End Batch:", "Binding the verticesBuffer: ("+verticesBuffer+" and bufferIndex"+bufferIndex);
		vertices.bind();
		vertices.draw(GL10.GL_TRIANGLES, 0, numSprites * 6);
		vertices.unbind();
		
	}
	
	
	public void drawSprite(float x, float y, float width, float height, TextureRegion region) {
		float halfWidth = width / 2;
		float halfHeight = height / 2;
		float x1 = x - halfWidth;
		float y1 = y - halfHeight;
		float x2 = x + halfWidth;
		float y2 = y + halfHeight;

		//Log.d("Sprite Batcher Class","Buffer Index value:"+bufferIndex+" Size of "+verticesBuffer.length);
		verticesBuffer[bufferIndex++] = x1;
		verticesBuffer[bufferIndex++] = y1;
		verticesBuffer[bufferIndex++] = region.u1;
		verticesBuffer[bufferIndex++] = region.v2;
		
		verticesBuffer[bufferIndex++] = x2;
		verticesBuffer[bufferIndex++] = y1;
		verticesBuffer[bufferIndex++] = region.u2;
		verticesBuffer[bufferIndex++] = region.v2;
		
		verticesBuffer[bufferIndex++] = x2;
		verticesBuffer[bufferIndex++] = y2;
		verticesBuffer[bufferIndex++] = region.u2;
		verticesBuffer[bufferIndex++] = region.v1;
		
		verticesBuffer[bufferIndex++] = x1;
		verticesBuffer[bufferIndex++] = y2;
		verticesBuffer[bufferIndex++] = region.u1;
		verticesBuffer[bufferIndex++] = region.v1;

        //Log.d("Inside DrawSprite:","BufferIndex:"+bufferIndex+"x1=>"+x1+" ,y1=>"+y1+",x2=>"+x2+",y2=>"+y2+" and region u1 =>"+ region.u1+" ,u2=>"+region.u2+" ,v1=>"+region.v1+" , v2=>"+region.v2);
		numSprites++;
	}
	
	
	public void drawSprite(float x, float y, float width, float height, float radianAngle,TextureRegion region) {
		
		float halfWidth = width / 2;
		float halfHeight = height / 2;

		float cos = FloatMath.cos(radianAngle);
		float sin = FloatMath.sin(radianAngle);
		
		float x1 = -halfWidth * cos - (-halfHeight) * sin;
		float y1 = -halfWidth * sin + (-halfHeight) * cos;
		float x2 = halfWidth * cos - (-halfHeight) * sin;
		float y2 = halfWidth * sin + (-halfHeight) * cos;
		float x3 = halfWidth * cos - halfHeight * sin;
		float y3 = halfWidth * sin + halfHeight * cos;
		float x4 = -halfWidth * cos - halfHeight * sin;
		float y4 = -halfWidth * sin + halfHeight * cos;
		
		
		x1 += x;
		y1 += y;
		x2 += x;
		y2 += y;
		x3 += x;
		y3 += y;
		x4 += x;
		y4 += y;
		
		
		verticesBuffer[bufferIndex++] = x1;
		verticesBuffer[bufferIndex++] = y1;
		verticesBuffer[bufferIndex++] = region.u1;
		verticesBuffer[bufferIndex++] = region.v2;
		
		verticesBuffer[bufferIndex++] = x2;
		verticesBuffer[bufferIndex++] = y2;
		verticesBuffer[bufferIndex++] = region.u2;
		verticesBuffer[bufferIndex++] = region.v2;
		
		verticesBuffer[bufferIndex++] = x3;
		verticesBuffer[bufferIndex++] = y3;
		verticesBuffer[bufferIndex++] = region.u2;
		verticesBuffer[bufferIndex++] = region.v1;
		
		verticesBuffer[bufferIndex++] = x4;
		verticesBuffer[bufferIndex++] = y4;
		verticesBuffer[bufferIndex++] = region.u1;
		verticesBuffer[bufferIndex++] = region.v1;
		
		numSprites++;
		
		
	}
	
	
	
	
	
}
