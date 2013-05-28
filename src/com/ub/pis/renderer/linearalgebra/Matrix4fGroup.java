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

public class Matrix4fGroup {
	private float[] values;
	private int count;
	
	public Matrix4fGroup(Matrix4f[] matrices) {
		count = matrices.length;
		values = new float[count*16];
		int i = 0;
		for (Matrix4f m : matrices) {
			float[] v = m.getValues();
			for (int j = 0; j < 16; j++) {
				values[i] = v[j];
				i++;
			}
		}
	}
	
	public Matrix4fGroup(int n) {
		count = n;
		values = new float[n*16];
	}
	
	public Matrix4fGroup(float[] values) {
		this.setValues(values);
		count = values.length/16;
	}
	
	public float[] getValues() {
		return values;
	}
	
	public void setValues(float[] values) {
		this.values = values;
		this.count = values.length/16;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setIdentity() {
		for (int i = 0; i < count; i++) {
			values[16*i] = 1;
			values[16*i+4+1] = 1;
			values[16*i+8+2] = 1;
			values[16*i+12+3] = 1;
		}
	}
	
	public static Matrix4fGroup linearInterp(float t, Matrix4fGroup m1, Matrix4fGroup m2) {
		float[] v1 = m1.getValues();
		float[] v2 = m2.getValues();
		Matrix4fGroup m = new Matrix4fGroup(m1.count);
		float[] v = m.getValues();
		for (int i = 0; i < v.length; i++) {
			v[i] = t*v1[i] + (1-t)*v2[i];
		}
		for (int i = 0; i < m1.count; i++) {
			v[16*i+12+3] = 1;
		}
		return m;
	}

}
