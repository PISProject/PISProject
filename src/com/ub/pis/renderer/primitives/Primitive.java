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

package com.ub.pis.renderer.primitives;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.ub.pis.renderer.models.Model;
import com.ub.pis.renderer.opengl.Mesh;
import com.ub.pis.renderer.opengl.ShaderProgram;
import com.ub.pis.renderer.opengl.Texture;

public abstract class Primitive extends Model{

	private final static String vshader = 
			"uniform mat4 mvpMatrix;"+
			"uniform mat4 mvMatrix;"+

			"attribute vec3 position;"+
			"attribute vec3 normal;"+
			"attribute vec2 textureCoords;"+

			"varying vec3 vPosition;"+
			"varying vec3 vNormal;"+
			"varying vec2 vTextureCoords;"+

			"void main() {"+
				"vPosition = vec3(mvMatrix * vec4(position,1));"+

    			"vNormal = vec3(mvMatrix * vec4(normal, 0.0));"+

				"vTextureCoords = textureCoords;"+
	
				"gl_Position = mvpMatrix * vec4(position,1);"+
			"}";
	
	private final static String fshader = 
			"precision mediump float;"+

			"uniform sampler2D texture;"+

			"varying vec2 vTextureCoords;"+
			"varying vec3 vPosition;"+
			"varying vec3 vNormal;"+

			"void main() {"+
				"gl_FragColor = texture2D(texture, vTextureCoords);"+
			"}";
	
	protected final static ShaderProgram primitiveProgram = new ShaderProgram(vshader, fshader);
	
	
	public Primitive(Mesh mesh, int color) {
		super(mesh, genTexture(color), primitiveProgram);
	}
	
	public Primitive(Mesh mesh, Texture texture) {
		super(mesh, texture, primitiveProgram);
	}
	
	
	private static Texture genTexture(int color) {
		Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);
		canvas.drawColor(color);
		return new Texture(bitmap);
	}

}
