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

import java.util.TreeMap;

/**
 * TODO: La forma de interpolar no me convence mucho, revisar
 * @author Aarón Negrín
 *
 */

public class FrameList {
	
	private TreeMap<Integer,Frame> frames;
	//private Frame previous;
	//private Frame next;
	
	public FrameList() {
		this.frames = new TreeMap<Integer, Frame>();
	}
	
	/**
	 * Adds a new keyframe to the list
	 * @param frame
	 */
	public void add(Frame frame) {
		frames.put(frame.getNumFrame(), frame);
	}
	
	/**
	 * Gets the frameNumber frame in the sequence, if it doesn't exists in the list 
	 * it will be interpolated
	 * @param frameNumber
	 * @return The frameNumber-th of this sequence
	 */
	
	public Frame getFrame(int frameNumber) {
		Frame previous = getPreviousFrame(frameNumber);
		Frame next = getNextFrame(frameNumber);
		//return next;
		return Frame.interpFrames(frameNumber, previous, next);
	}
	
	
	/**
	 * Returns the first frame in the list
	 * @return 
	 */
	public Frame getFirstFrame() {
		return frames.firstEntry().getValue();
	}
	
	/**
	 * Returns the last frame in the list
	 * @return
	 */
	public Frame getLastFrame() {
		return frames.lastEntry().getValue();
	}
	
	private Frame getNextFrame(int frameNumber) {
		Integer k = frames.higherKey(frameNumber);
		if (k == null) {
			return frames.lastEntry().getValue();
		}
		return frames.get(k);
	}
	
	private Frame getPreviousFrame(int frameNumber) {
		Integer k = frames.lowerKey(frameNumber);
		if (k == null) {
			return frames.firstEntry().getValue();
		}
		return frames.get(k);
	}

}
