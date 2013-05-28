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

package com.ub.pis.renderer.utils;

import com.ub.pis.renderer.renderer.Constants;


public class VertexStruct {
	public int numVertices;
	public float[] vertices;
	public float[] normals;
	public float[] textures;
	
	public float[] weights;
	public float[] boneIndices;
	
	public float[] packed;
	
	public boolean isPacked = false;
	public boolean isSkinned = false;
	
	public VertexStruct() {
		
	}
	
	public VertexStruct(float[] vertices, float[] normals, float[] textures) {
		this.vertices = vertices;
		this.normals = normals;
		this.textures = textures;
		this.numVertices = vertices.length / Constants.VERTEX_COORDINATES;
	}
	
	
	/*
	 * TODO: Falta optimizaci�n Y en vez de esto podrias usar buffer subdata
	 * Mejor cargarlo todo con listas
	 */
	public float[] getPackedVertex() {
		if (isPacked) {
			return packed;
		}
		if (isSkinned) {
			return getSkinnedPackedVertex();
		}
		return getNotSkinnedPackedVertex();
	}
	
	private float[] getNotSkinnedPackedVertex() {
		packed = new float[vertices.length+normals.length+textures.length];
		int i = 0;
		int vertIndex = 0;
		int norIndex = 0;
		int texIndex = 0;
		while (i < packed.length) {
			for (int j = vertIndex; j < vertIndex + Constants.VERTEX_COORDINATES; j++) {
				packed[i] = vertices[j];
				i++;
			}
			vertIndex += Constants.VERTEX_COORDINATES;
			
			for (int j = norIndex; j < norIndex + Constants.NORMAL_COORDINATES; j++) {
				packed[i] = normals[j];
				i++;
			}
			norIndex += Constants.NORMAL_COORDINATES;
			
			for (int j = texIndex; j < texIndex + Constants.TEXTURE_COORDINATES; j++) {
				packed[i] = textures[j];
				i++;
			}
			texIndex += Constants.TEXTURE_COORDINATES;
			
		}
		return packed;
	}
	
	private float[] getSkinnedPackedVertex() {
		packed = new float[vertices.length+normals.length+textures.length+weights.length+boneIndices.length];
		int i = 0;
		int vertIndex = 0;
		int norIndex = 0;
		int texIndex = 0;
		int weiIndex = 0;
		int indIndex = 0;
		while (i < packed.length) {
			for (int j = vertIndex; j < vertIndex + Constants.VERTEX_COORDINATES; j++) {
				packed[i] = vertices[j];
				i++;
			}
			vertIndex += Constants.VERTEX_COORDINATES;
			
			for (int j = norIndex; j < norIndex + Constants.NORMAL_COORDINATES; j++) {
				packed[i] = normals[j];
				i++;
			}
			norIndex += Constants.NORMAL_COORDINATES;
			
			for (int j = texIndex; j < texIndex + Constants.TEXTURE_COORDINATES; j++) {
				packed[i] = textures[j];
				i++;
			}
			texIndex += Constants.TEXTURE_COORDINATES;
			
			for (int j = weiIndex; j < weiIndex + Constants.NUM_BONES; j++) {
				packed[i] = weights[j];
				i++;
			}
			weiIndex += Constants.NUM_BONES;
			
			for (int j = indIndex; j < indIndex + Constants.NUM_BONES; j++) {
				packed[i] = boneIndices[j];
				i++;
			}
			indIndex += Constants.NUM_BONES;
			
		}
		return packed;
	}

	

	
}
