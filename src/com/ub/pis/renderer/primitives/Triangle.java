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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.opengl.GLES20;

import com.ub.pis.renderer.models.Model;
import com.ub.pis.renderer.opengl.Mesh;
import com.ub.pis.renderer.opengl.ShaderProgram;
import com.ub.pis.renderer.opengl.Texture;
import com.ub.pis.renderer.utils.VertexStruct;

class MyVertexStruct extends VertexStruct {	
	public MyVertexStruct() {
		this.numVertices = 3;
		float[] f =     {0.0f,  0.0f, 10.0f,
						-10.0f, 0.0f, -10.0f,
						 10.0f, 0.0f,-10.0f};
		this.vertices = f;
		this.normals = new float[3*3];
		float[] t = {1.0f, 1.0f, 
					 1.0f, 0.0f,
					 -1.0f,-1.0f};
		this.textures = t;
	}
	
	public MyVertexStruct(float[] vertices) {
		this.vertices = vertices;
		this.normals = new float[3*3];
		this.textures = new float[2*3];
	}
}


class MyTexture extends Texture {

	public MyTexture() {
		super(genTexture());
		// TODO Auto-generated constructor stub
	}
	
	private static Bitmap genTexture() {
		Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);
		
		Paint fillPaint = new Paint();
		//fillPaint.setColor(Color.argb(255, 255, 0, 255));
		fillPaint.setColor(Color.WHITE);
		//fillPaint.setARGB(0xff, 0xff, 0xff, 0xff);
		fillPaint.setAntiAlias(true);
		fillPaint.setTextSize(32);
		canvas.drawText("Hello World", 16, 112, fillPaint);
		
		Paint stkPaint = new Paint();
		stkPaint.setStyle(Style.STROKE);
		stkPaint.setTextSize(32);
		stkPaint.setAntiAlias(true);
		stkPaint.setStrokeWidth(1);
		stkPaint.setColor(Color.BLACK);
		canvas.drawText("Hello World", 16, 112, stkPaint);

		// Draw the text
//		Paint textPaint = new Paint();
//		textPaint.setTextSize(32);
//		textPaint.setAntiAlias(true);
//		textPaint.setColor(Color.WHITE);
//		//textPaint.setARGB(0xff, 0xff, 0, 0xff);
//		// draw the text centered
//		canvas.drawText("Hello World", 16,112, textPaint);
		
		return bitmap;
	}
	
}


public class Triangle extends Model {
	private static MyVertexStruct verts= new MyVertexStruct();
	
	//private static Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
	
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
				"vec3 lightPos = vec3(0,10,0);"+
				"float distance = length(lightPos - vPosition);"+
				"vec3 lightVector = normalize(lightPos - vPosition);"+

	    		"float diffuse = max(dot(normalize(vNormal), lightVector), 0.1);"+
	 
	 			"diffuse = diffuse * 1.0/1.0;"+

				"gl_FragColor = texture2D(texture, vTextureCoords);"+
			"}";
	
	private final static ShaderProgram primitiveProgram = new ShaderProgram(vshader, fshader);


	public Triangle() {
		super(new Mesh(verts),new MyTexture(), primitiveProgram);
	}
	
	@Override
	public void bind() {
		GLES20.glEnable(GLES20.GL_BLEND);
		//GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		super.bind();
		
	}
	
	@Override 
	public void unbind() {
		GLES20.glDisable(GLES20.GL_BLEND);
		super.unbind();
	}

}
