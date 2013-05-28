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

package com.ub.pis.renderer.scene;

import com.ub.pis.renderer.linearalgebra.Matrix4f;
import android.opengl.Matrix;

public abstract class Camera {

	private final Matrix4f projectionMatrix;
	private final Matrix4f viewMatrix;
	private final Matrix4f viewProjectionMatrix;
	
	
	
	public Camera() {
		projectionMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
		viewProjectionMatrix = new Matrix4f();
	}
	
	public abstract void initialize(int width, int height);
	
	public abstract void update();
	
	/*
	 * Do not touch! It works! T__T I'm happy now
	 */
	public void setProjectionMatrix(float fov, float aspect, float znear, float zfar) {
		float xymax = (float) (znear * Math.tan(fov * Math.PI/360.0f));
		float ymin = -xymax;
		float xmin = -xymax;
		
		float width = xymax - xmin;
		float height = xymax - ymin;
		
		float depth = zfar - znear;
		float q = -(zfar + znear) / depth;
		float qn = -2 * (zfar * znear) / depth;
		
		float w = 2 * znear / width;
		w = w / aspect;
		float h = 2 * znear / height;
		
		float[] f = projectionMatrix.getValues();
		
		f[0] = w;
		f[5] = h;
		f[10] = q;
		f[11] = -1;
		f[14] = qn;
		
	}
	
	public void setProjectionMatrix(float left, float right, float bottom, float top, float near, float far) {
		Matrix.frustumM(projectionMatrix.getValues(), 0, left, right, bottom, top, near, far);
	}
	
	public void setViewMatrix( float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
		Matrix.setLookAtM(viewMatrix.getValues(), 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
	}
	
	public void setViewProjectionMatrix() {
		projectionMatrix.multiplyRight(viewMatrix, viewProjectionMatrix);
	}
	
	public void rotate(float angle, float x, float y, float z) {
		viewProjectionMatrix.rotate(angle, x, y, z);
	}
	
	public void translate(float x, float y, float z) {
		viewProjectionMatrix.translate(x, y, z);
	}
	
	public Matrix4f getViewProjectionMatrix() {
		return viewProjectionMatrix;
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
}
