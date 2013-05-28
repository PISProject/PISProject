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

import com.ub.pis.renderer.opengl.Mesh;
import com.ub.pis.renderer.opengl.Texture;
import com.ub.pis.renderer.utils.VertexStruct;

public class Quad extends Primitive {
		
	private static float[] textures = { 0, 1,
										0, 0,
										1, 0,
										
										0, 1,
										1, 0,
										1, 1};

	public Quad(float width, float height, int color) {
		super(
				new Mesh(
						new VertexStruct(genVertices(width,height), new float[6*3], textures)), 
				color);
	}
	
	public Quad(float width, float height, Texture texture) {
		super(
				new Mesh(
						new VertexStruct(genVertices(width,height), new float[6*3], textures)),
				texture);
	}
	
	protected static float[] genVertices(float w, float h) {
		float[] vertices = {-w, 0, -h,
							-w, 0, h,
							w, 0, h,
			   
							-w, 0, -h,
							w, 0, h,
							w, 0, -h};
		return vertices;
	}

}
