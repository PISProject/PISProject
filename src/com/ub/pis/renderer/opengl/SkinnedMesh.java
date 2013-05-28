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

public class SkinnedMesh extends Mesh{
	private static int positionInit = 0;

	private static int normalInit = Constants.FLOAT_SIZE * Constants.VERTEX_COORDINATES;
	
	private static int textureInit = Constants.FLOAT_SIZE * (Constants.VERTEX_COORDINATES + Constants.NORMAL_COORDINATES);
	
	protected int weigthHandle;
	private static int weightInit = Constants.FLOAT_SIZE * (Constants.VERTEX_COORDINATES + Constants.NORMAL_COORDINATES + Constants.TEXTURE_COORDINATES);
	
	protected int boneIndicesHandle;
	private static int boneIndicesInit = Constants.FLOAT_SIZE * (Constants.VERTEX_COORDINATES + Constants.NORMAL_COORDINATES + Constants.TEXTURE_COORDINATES + Constants.NUM_BONES);
	
	private static final int Stride = Constants.FLOAT_SIZE * (Constants.VERTEX_COORDINATES + 
			Constants.NORMAL_COORDINATES + 
			Constants.TEXTURE_COORDINATES +
			Constants.NUM_BONES +
			Constants.NUM_BONES);
	
	public SkinnedMesh(VertexStruct vStruct) {
		super(vStruct);
	}
	
	public void setWeightLocation(int location) {
		this.weigthHandle = location;
	}
	
	public void setBonesIndicesLocation(int location) {
		this.boneIndicesHandle = location;
	}
	
	@Override
	public void bind() {
		this.vbo.bind();
		
		GLES20.glEnableVertexAttribArray(positionHandle);
		GLES20.glVertexAttribPointer(positionHandle, Constants.VERTEX_COORDINATES, GLES20.GL_FLOAT, false, Stride, positionInit);
		
		GLES20.glEnableVertexAttribArray(normalHandle);
		GLES20.glVertexAttribPointer(normalHandle, Constants.NORMAL_COORDINATES, GLES20.GL_FLOAT, false, Stride, normalInit);
		
		GLES20.glEnableVertexAttribArray(textureHandle);
		GLES20.glVertexAttribPointer(textureHandle, Constants.TEXTURE_COORDINATES, GLES20.GL_FLOAT, false, Stride, textureInit);
		
		GLES20.glEnableVertexAttribArray(weigthHandle);
		GLES20.glVertexAttribPointer(weigthHandle, Constants.NUM_BONES, GLES20.GL_FLOAT, false, Stride, weightInit);
		
		GLES20.glEnableVertexAttribArray(boneIndicesHandle);
		GLES20.glVertexAttribPointer(boneIndicesHandle, Constants.NUM_BONES, GLES20.GL_FLOAT, false, Stride, boneIndicesInit);
	}
	
	public void unbind() {
		GLES20.glDisableVertexAttribArray(positionHandle);
		GLES20.glDisableVertexAttribArray(normalHandle);
		GLES20.glDisableVertexAttribArray(textureHandle);
		GLES20.glDisableVertexAttribArray(weigthHandle);
		GLES20.glDisableVertexAttribArray(boneIndicesHandle);
		this.vbo.unbind();
	}

}

