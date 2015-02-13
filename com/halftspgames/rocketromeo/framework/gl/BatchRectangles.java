package com.halftspgames.rocketromeo.framework.gl;

import com.halftspgames.rocketromeo.framework.impl.GLGraphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mareenator on 20/1/15.
 */
public class BatchRectangles {

    final GL10 gl;
    final GLGraphics glGraphics;
    final int vertexSize;
    final FloatBuffer vertices;
    final ShortBuffer indices;


    public BatchRectangles(GLGraphics glGraphics,int numRectangles) {

        this.glGraphics = glGraphics;
        this.gl = glGraphics.getGL();
        this.vertexSize = 2;

        ByteBuffer buffer = ByteBuffer.allocateDirect( numRectangles * 4 * vertexSize * 4  );
        buffer.order(ByteOrder.nativeOrder());
        this.vertices = buffer.asFloatBuffer();

        buffer = ByteBuffer.allocateDirect(numRectangles * 6 * 2);
        buffer.order(ByteOrder.nativeOrder());
        indices = buffer.asShortBuffer();
    }


    public void setVerticesAndIndices(float[] vertices, int offset, int length) {
        this.vertices.clear();
        this.vertices.put(vertices);
        this.vertices.flip();

        //Setting the indices as well
        int len = length/8;
        short[] indices = new short[ len * 6];
        short j = 0;
        for(int i = 0; i < len; i += 6, j += 4) {
            indices[i] = (short)(j + 0);
            indices[i + 1] = (short)(j + 1);
            indices[i + 2] = (short)(j + 2);
            indices[i + 3] = (short)(j + 2);
            indices[i + 4] = (short)(j + 3);
            indices[i + 5] = (short)(j + 0);
        }
        this.indices.clear();
        this.indices.put(indices, 0 , len * 6 );
        this.indices.flip();
    }

    public void draw(int primitiveType, int offset, int numVertices) {

        if(indices != null) {
            indices.position(offset);
            gl.glDrawElements(primitiveType, numVertices, GL10.GL_UNSIGNED_SHORT, indices);
        } else {
            gl.glDrawArrays(primitiveType, offset, numVertices);
        }
    }

    public void bind(float r, float g, float b, float a) {

        gl.glColor4f(r,g,b,a);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT,0, vertices);

    }

    public void unbind() {
    }
}
