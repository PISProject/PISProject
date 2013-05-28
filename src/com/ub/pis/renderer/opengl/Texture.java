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

import java.nio.IntBuffer;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Texture {
	private int textureHandle;
	
	private Bitmap bitmap;
	
	private boolean initialized;
	
	public Texture(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	public void initialize() {
		if (initialized) return;
		IntBuffer buf = IntBuffer.allocate(1);
		
		GLES20.glGenTextures(1, buf);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, buf.get(0));
        
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        
        //GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);//GLES20.GL_NEAREST);
        
        bitmap.recycle();
        
        if (buf.get(0) == 0) {
        	throw new RuntimeException("Error loading texture.");
        } 
        
        textureHandle = buf.get(0);
        initialized = true;
	}
	
	public void bind() {
	    GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle);
	}
	
	public void unbind() {
		//GLES20.glActiveTexture(0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}

}
