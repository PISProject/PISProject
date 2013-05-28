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
import com.ub.pis.renderer.linearalgebra.Matrix4f;
import com.ub.pis.renderer.linearalgebra.Matrix4fGroup;
import com.ub.pis.renderer.linearalgebra.Vector3f;
import com.ub.pis.renderer.linearalgebra.Vector4f;


public class ShaderProgram {
	private String vs;
	private String fs;
	
	private int vertexShader;
	private int fragmentShader;
	private int program;
	
	private boolean initialized;
	
	public ShaderProgram(String vertexShaderSource, String fragmenShaderSource) {
		this.vs = vertexShaderSource;
		this.fs = fragmenShaderSource;
		initialized = false;
	}
	
	public void initialize() {
		if (initialized) return;
		vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vs);
		fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fs);
		
	    program = GLES20.glCreateProgram();
	    GLES20.glAttachShader(program, vertexShader);
	    GLES20.glAttachShader(program, fragmentShader);
	    linkProgram();
	    
	    vs = null;
	    fs = null;
	    initialized = true;
	}
	
	public void useProgram() {
		GLES20.glUseProgram(program);
	}
	
	
	public void unbindProgram() {
		GLES20.glUseProgram(0);
	}
	
	public int getAttribLocation(String name) {
		return GLES20.glGetAttribLocation(program, name);
	}
	
	public int getUniformLocation(String name) {
		return GLES20.glGetUniformLocation(program, name);
	}
	
	public void setUniformValue(int location, float[] m) {
		GLES20.glUniformMatrix4fv(location, 1, false, m, 0);
	}
	
	public void setUniformValue(int location, Matrix4f m) {
		GLES20.glUniformMatrix4fv(location, 1, false, m.getValues(), 0);
	}
	
	public void setUniformValue(int location, Matrix4fGroup g) {
		GLES20.glUniformMatrix4fv(location, g.getCount(), false, g.getValues(), 0);
		//GLES20.glUniform4fv(location, g.getCount(), g.getValues(), 0);
	}
	
	public void setUniformValue(int location, float f) {
		GLES20.glUniform1f(location, f);
	}
	
	public void setUniformValue(int location, Vector3f v) {
		GLES20.glUniform3fv(location, 1, v.getValues(), 0);
	}
	
	public void setUniformValue(int location, Vector4f v) {
		GLES20.glUniform4fv(location, 1, v.getValues(), 0);
	}
	
	private void linkProgram() {
		GLES20.glLinkProgram(program);
		final int[] linkStatus = new int[1];
	    GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
	    if (linkStatus[0] == 0)
	    {
	        GLES20.glDeleteProgram(program);
	        throw new RuntimeException("Error creating program.");
	    }
	}
	
	private int loadShader(int type, String source) {
	    int shader = GLES20.glCreateShader(type);

	    GLES20.glShaderSource(shader, source);
	    GLES20.glCompileShader(shader);

	    final int[] compileStatus = new int[1];
	    GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
	 
	    // If the compilation failed, delete the shader.
	    if (compileStatus[0] == 0)
	    {
	    	String error = GLES20.glGetShaderInfoLog(shader);
	    	int error2 = GLES20.glGetError();
	        GLES20.glDeleteShader(shader);
	        if (type == GLES20.GL_VERTEX_SHADER) {
	        	throw new RuntimeException("Error creating vertex shader. "+error+" GLERROR:"+error2);
	        } else {
	        	throw new RuntimeException("Error creating fragment shader. "+error+" GLERROR:"+error2);
	        }
	    }
	    return shader;
	}

}
