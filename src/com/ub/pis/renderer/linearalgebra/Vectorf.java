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

public class Vectorf {
	protected int dim;
	protected float[] values;
	
	public Vectorf(int dim) {
		this.dim = dim;
		values = new float[dim];
	}
	
	public Vectorf(float[] values) {
		this.values = values;
		this.dim = values.length;
	}
	
	public int getDimension() {
		return dim;
	}
	
	public float[] getValues() {
		return values;
	}
	
	public void setValues(float[] values) {
		this.values = values;
	}
	
	public Vectorf linearInterpolation(float t, Vectorf v1, Vectorf v2) {
		return null;
	}
	
	public Vectorf add(Vectorf v1, Vectorf v2) {
		int n = v1.getValues().length;
		float[] r = new float[n];
		float[] f1 = v1.getValues();
		float[] f2 = v2.getValues();
		for (int i = 0; i < n; i++) {
			r[i] = f1[i] + f2[i];
		}
		return new Vectorf(r);
	}
	
	public void scale(float t) {
		for (int i = 0; i < values.length; i++) {
			values[i] = t*values[i];
		}
	}
	
	public String toString() {
		String s = "[";
		for (int i = 0; i < values.length; i++) {
			s += values[i]+", ";
		}
		s += "]";
		return s;
	}
	
	/*public Vector add(Vector v) throws DimensionException {
		if (this.dim != v.getDimension()) {
			throw new DimensionException();
		}
		Vector r = new Vector(this.dim);
		
		for (int i = 0; i < m_v.length; i++) {
			
		}
	}*/

}
