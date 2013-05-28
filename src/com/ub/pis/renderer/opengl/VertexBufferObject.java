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

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import com.ub.pis.renderer.renderer.Constants;
import com.ub.pis.renderer.utils.BufferUtils;

import android.opengl.GLES20;


/**
 * TODO: Una vez cargados los datos creo que puedes cargartelos los almacenados eh!
 * @author Demenus
 *
 */
class VertexBufferObject {
	
	private IntBuffer id;
	private int length;
	private int type;
	
	public VertexBufferObject(float[] array) {
		FloatBuffer fb = BufferUtils.getFloatBuffer(array);
		id = IntBuffer.allocate(1);
		length = array.length;
		GLES20.glGenBuffers(1, id);
		type = GLES20.GL_ARRAY_BUFFER;
		GLES20.glBindBuffer(type, id.get(0));
		GLES20.glBufferData(type, array.length*Constants.FLOAT_SIZE, fb, GLES20.GL_STATIC_DRAW);
		unbind();
	}
	
	public VertexBufferObject(short[] array) {
		ShortBuffer sb = BufferUtils.getShortBuffer(array);
		type = GLES20.GL_ELEMENT_ARRAY_BUFFER;
		id = IntBuffer.allocate(1);
		length = array.length;
		GLES20.glGenBuffers(1, id);
		GLES20.glBindBuffer(type, id.get(0));
		GLES20.glBufferData(type, array.length*Constants.SHORT_SIZE, sb, GLES20.GL_STATIC_DRAW);
		unbind();
	}
	
	public int getLength() {
		return length;
	}
	
	public void bind() {
		GLES20.glBindBuffer(type, id.get(0));
	}
	
	public void unbind() {
		GLES20.glBindBuffer(type, 0);
	}
	

}