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

package com.ub.pis.renderer.linearalgebra;

public class Quaternion {
	private float[] values;

	public Quaternion(float x, float y, float z, float w) {
		values = new float[4];
		values[0] = x;
		values[1] = y;
		values[2] = z;
		values[3] = w;
	}
	
	public Quaternion() {
		values = new float[4];
	}
	
	public void setX(float x) {
		values[0] = x;
	}
	
	public void setY(float y) {
		values[1] = y;
	}
	
	public void setZ(float z) {
		values[2] = z;
	}
	
	public void setW(float w) {
		values[3] = w;
	}
	
	public float getX() {
		return values[0];
	}
	
	public float getY() {
		return values[1];
	}
	
	public float getZ() {
		return values[2];
	}
	
	public float getW() {
		return values[3];
	}
	
	/**
	 * Gets the Quaternion related to matrix
	 * @param matrix
	 * @return
	 */
	public static Quaternion matrix4fToQuaternion(Matrix4f matrix) {
		Quaternion q = new Quaternion();
		float[] values = q.values;
		float[] m = matrix.getValues();
		float x2 = values[0] + values[0]; 
		float y2 = values[1] + values[1]; 
		float z2 = values[2] + values[2]; 
		{ 
			float xx2 = values[0] * x2; 
			float yy2 = values[1] * y2; 
			float zz2 = values[2] * z2; 
			m[0*4+0] = 1.0f - yy2 - zz2; 
			m[1*4+1] = 1.0f - xx2 - zz2; 
			m[2*4+2] = 1.0f - xx2 - yy2; 
		 } 
		return q;
	}
	
	/**
	 * Applies spherical interpolation between q1 and q2
	 * @param t
	 * @param q1
	 * @param q2
	 * @return
	 */
	public static Quaternion sphericalInterpolation(float t, Quaternion q1, Quaternion q2) {
		return null;
	}
	
	/**
	 * Conjugate this quaternion
	 */
	public void conjugate() {
	}
	
	/**
	 * Get the conjugated quaternion 
	 * @return
	 */
	public Quaternion conjugated() {
		return null;
	}
	
	/**
	 * Multiplies q1 and q2
	 * @param q1
	 * @param q2
	 * @return
	 */
	public static Quaternion multiply(Quaternion q1, Quaternion q2) {
		return null;
	}
}
