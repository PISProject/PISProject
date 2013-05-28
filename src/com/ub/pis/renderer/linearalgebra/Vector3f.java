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

public class Vector3f extends Vectorf {

	public Vector3f() {
		super(3);
	}
	
	public Vector3f(float x, float y, float z) {
		super(3);
		setValues(x, y, z);
	}
	
	public void setValues(float x, float y, float z) {
		values[0] = x;
		values[1] = y;
		values[2] = z;
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
	
	public void setX(float x) {
		values[0] = x;
	}
	
	public void setY(float y) {
		values[1] = y;
	}
	
	public void setZ(float z) {
		values[2] = z;
	}

}
