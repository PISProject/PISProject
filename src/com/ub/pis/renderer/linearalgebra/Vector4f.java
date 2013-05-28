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

public class Vector4f extends Vectorf {

	public Vector4f() {
		super(4);
	}
	
	public Vector4f(float w, float x, float y, float z) {
		super(4);
		setValues(w, x, y, z);
	}
	
	public void setValues(float w, float x, float y, float z) {
		values[0] = w;
		values[1] = x;
		values[2] = y;
		values[3] = z;
	}
	
	public float getW() {
		return values[0];
	}
	
	public float getX() {
		return values[1];
	}
	
	public float getY() {
		return values[2];
	}
	
	public float getZ() {
		return values[3];
	}
	
	public void setX(float x) {
		values[1] = x;
	}
	
	public void setY(float y) {
		values[2] = y;
	}
	
	public void setZ(float z) {
		values[3] = z;
	}
	
	public void setW(float w) {
		values[0] = w;
	}
}
