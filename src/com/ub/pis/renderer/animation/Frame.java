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

package com.ub.pis.renderer.animation;

import com.ub.pis.renderer.linearalgebra.Matrix4fGroup;

public class Frame {
	
	private int numFrame;
	private Matrix4fGroup bones;
	
	/**
	 * 
	 * @param num The frame number
	 * @param bones List of matrices related to this frame
	 */
	public Frame(int num, Matrix4fGroup bones) {
		this.numFrame = num;
		this.bones = bones;
	}
	
	/**
	 * 
	 * @return The frame number
	 */
	public int getNumFrame() {
		return numFrame;
	}
	
	/**
	 * 
	 * @return List of matrices related to this frame
	 */
	public Matrix4fGroup getBones() {
		return bones;
	}
	
	/**
	 * Applies interpolation between two frames f1 and f2. This frame will be the i-th frame and
	 * i should be a value between the frame number of f1 and f2, i.e., framenumber(f1) < i < framenumber(i2)
	 * @param i The frame number of the interpolated frame
	 * @param f1 The first keyframe, frame number of f1 < i
	 * @param f2 The second keyframe, frame number of f2 > i
	 * @return The i-th frame
	 */
	public static Frame interpFrames(int i, Frame f1, Frame f2) {
		int n1 = f1.getNumFrame();
		int n2 = f2.getNumFrame();
		float t = 1/(float)(n2-n1)*(i-n1);

		Matrix4fGroup g1 = f1.getBones();
		Matrix4fGroup g2 = f2.getBones();
		return new Frame(i, Matrix4fGroup.linearInterp(t, g1, g2));
	}
	
	
}
