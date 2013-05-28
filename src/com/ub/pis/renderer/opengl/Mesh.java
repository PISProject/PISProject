/**************************************************************************************************
 * Copyright (c) 2013.                                                                            *
 * Machango Fight, the Massive Multiplayer Online.                                                *
 * Android Application                                                                            *
 *                                                                                                *
 * Curso 2012-2013                                                                                *
 *                                                                                                *
 * Este software ha sido desarrollado integramente para la asignatura 'Projecte                   *
 * Integrat de Software' en la Universidad de Barcelona por los estudiantes                       *
 * Pablo Martínez Martínez, Albert Folch, Xavi Moreno y Aaron Negrín.                             *
 **************************************************************************************************/

package com.ub.pis.renderer.opengl;

import android.opengl.GLES20;
import com.ub.pis.renderer.renderer.Constants;
import com.ub.pis.renderer.utils.VertexStruct;

public class Mesh {
	protected int numVertices;
	
	protected int positionHandle;
	private static int positionInit = 0;
	
	protected int normalHandle;
	private static int normalInit = Constants.FLOAT_SIZE * Constants.VERTEX_COORDINATES;
	
	protected int textureHandle;
	private static int textureInit = Constants.FLOAT_SIZE * (Constants.VERTEX_COORDINATES + Constants.NORMAL_COORDINATES);
	
	private static final int Stride = Constants.FLOAT_SIZE * (Constants.VERTEX_COORDINATES + Constants.NORMAL_COORDINATES + Constants.TEXTURE_COORDINATES);
	
	protected VertexBufferObject vbo;
	protected VertexStruct vStruct;
	
	private boolean initialized;

    private int drawMode;
	
	public Mesh(VertexStruct vStruct) {
		this.vStruct = vStruct;
        drawMode = GLES20.GL_TRIANGLES;
	}
	
	public void initialize() {
		if (initialized) return;
		this.vbo = new VertexBufferObject(vStruct.getPackedVertex());
		numVertices = vStruct.numVertices;
		vStruct = null;
		initialized = true;
	}
	
	public void setPositionLocation(int location) {
		this.positionHandle = location;
	}
	
	public void setNormalLocation(int location) {
		this.normalHandle = location;
	}
	
	public void setTextureLocation(int location) {
		this.textureHandle = location;
	}

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
    }
	
	public void bind() {
		this.vbo.bind();
		
		GLES20.glEnableVertexAttribArray(positionHandle);
		GLES20.glVertexAttribPointer(positionHandle, Constants.VERTEX_COORDINATES, GLES20.GL_FLOAT, false, Stride, positionInit);
		
		GLES20.glEnableVertexAttribArray(normalHandle);
		GLES20.glVertexAttribPointer(normalHandle, Constants.NORMAL_COORDINATES, GLES20.GL_FLOAT, false, Stride, normalInit);
		
		GLES20.glEnableVertexAttribArray(textureHandle);
		GLES20.glVertexAttribPointer(textureHandle, Constants.TEXTURE_COORDINATES, GLES20.GL_FLOAT, false, Stride, textureInit);
	}
	
	
	public void unbind() {
		GLES20.glDisableVertexAttribArray(positionHandle);
		GLES20.glDisableVertexAttribArray(normalHandle);
		GLES20.glDisableVertexAttribArray(textureHandle);
		this.vbo.unbind();
	}

	public void draw() {
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, numVertices);
	}

}
