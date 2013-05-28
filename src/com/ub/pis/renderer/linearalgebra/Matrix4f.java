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

import android.opengl.Matrix;

/*
 * TODO: Podr�as cambiar los m�todos de multiplicaci�n por est�ticos
 */

public class Matrix4f {
	private float[] values;
	
	private final float[] rotx = new float[16];
	private final float[] roty = new float[16];
	private final float[] rotz = new float[16];
	
	public Matrix4f() {
		values = new float[16];
	}
	
	public Matrix4f(float[] values) {
		this.setValues(values);
	}
	
	public void setValues(float[] values) {
		this.values = values;
	}
	
	public float[] getValues() {
		return values;
	}
	
	/**
	 * Sets the matrix to identity
	 */
	public void setIdentity() {
		Matrix.setIdentityM(values, 0);
	}
	
	/**
	 * Inverts this matrix
	 */
	public void invert() {
		Matrix.invertM(values, 0, values, 0);
	}
	
	/**
	 * Gets the inverted matrix
	 * @return
	 */
	public Matrix4f inverted() {
		Matrix4f m = new Matrix4f();
		Matrix.invertM(m.values, 0, values, 0);
		return m;
	}
	
	/**
	 * Multiplies, in place, m * this
	 * @param m
	 */
	public void multiplyLeft(Matrix4f m) {
		Matrix.multiplyMM(values, 0, m.getValues(), 0, values, 0);
	}
	
	/**
	 * Multipies, in place, this * m
	 * @param m
	 */
	public void multiplyRight(Matrix4f m) {
		Matrix.multiplyMM(values, 0, values, 0, m.getValues(), 0);
	}
	
	/**
	 * Multiplies m * this and saves the result in result
	 * @param m
	 * @param result
	 */
	public void multiplyLeft(Matrix4f m, Matrix4f result) {
		Matrix.multiplyMM(result.getValues(), 0, m.getValues(), 0, values, 0);
	}
	
	/**
	 * Multiplies this * m and saves the result in result
	 * @param m
	 * @param result
	 */
	public void multiplyRight(Matrix4f m, Matrix4f result) {
		Matrix.multiplyMM(result.getValues(), 0, values, 0, m.getValues(), 0);
	}
	
	
	/**
	 * Translate to point (x,y,z)
	 * @param x
	 * @param y
	 * @param z
	 */
	public void translate(float x, float y, float z) {
		Matrix.translateM(values, 0, x, y, z);
	}
	
	/**
	 * Translate to point V
	 * @param v
	 */
	public void translate(Vector3f v) {
		float[] f = v.getValues();
		Matrix.translateM(values, 0, f[0], f[1], f[2]);
	}
	
	/**
	 * Set the matrix as a translation matrix based in v
	 * @param v
	 */
	public void setTranslate(Vector3f v) {
		float[] f = v.getValues();
		Matrix.setIdentityM(values, 0);
		Matrix.translateM(values, 0, f[0], f[1], f[2]);
	}
	
	/**
	 * Set the matrix as a translation matrix placed in (x,y,z)
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setTranslate(float x, float y, float z) {
		Matrix.setIdentityM(values, 0);
		Matrix.translateM(values, 0, x, y, z);
	}

	/**
	 * Rotate the matrix in rx, ry, rz degrees in that order
	 * @param rx
	 * @param ry
	 * @param rz
	 */
	public void rotate(float rx, float ry, float rz) {
		Matrix.rotateM(values, 0, rx, 1, 0, 0);
		Matrix.rotateM(values, 0, ry, 0, 1, 0);
		Matrix.rotateM(values, 0, rz, 0, 0, 1);	
	}
	
	/**
	 * Rotate a degrees around the axis (x,y,z)
	 * @param a
	 * @param x
	 * @param y
	 * @param z
	 */
	public void rotate(float a, float x, float y, float z) {
		Matrix.rotateM(values, 0, a, x, y, z);
	}
	
	/**
	 * Set the rotation matrix defined by the rotation rx, ry, rz in that order
	 * @param rx
	 * @param ry
	 * @param rz
	 */
	public void setRotate(float rx, float ry, float rz) {
		Matrix.setRotateM(rotx, 0, rx, 1, 0 , 0);
		Matrix.setRotateM(roty, 0, ry, 0, 1, 0);
		Matrix.setRotateM(rotz, 0, rz, 0, 0, 1);
		Matrix.multiplyMM(values, 0, roty, 0, rotx, 0);
		Matrix.multiplyMM(values, 0, rotz, 0, values, 0);
	}
	
	/**
	 * Set the rotation matrix defined by the rotation in a degrees around 
	 * the (x,y,z) axis
	 * @param a
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setRotate(float a, float x, float y, float z) {
		Matrix.setRotateM(values, 0, a, x, y, z);
	}

    public Vector3f getTranslation() {
        return new Vector3f(values[3],values[7],values[11]);
    }

    public Vector3f getScalation() {
        return new Vector3f(values[12],values[13],values[14]);
    }



}
