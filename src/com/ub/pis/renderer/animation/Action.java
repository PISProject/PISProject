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



public class Action{
	
	private String name;
	private FrameList frames;
	private Frame currentFrame;
	private int currentIndexFrame;
	private int lastIndexFrame;
	private int firstIndexFrame;
	
	private boolean continuosly;
	private boolean play;
	
	
	public Action(String name, FrameList frames) {
		this.name = name;
		this.frames = frames;
		play = false;
		continuosly = false;
	}
	
	/**
	 * Obtains the name of this action
	 */
	public String getName() {
		return name;
	}
	
	public FrameList getFrameList() {
		return frames;
	}
	
	public void setDuration(int numFrames) {
		
	}
	
	/**
	 * Starts and plays once this action
	 */
	public void play() {
		play = true;
		currentFrame = frames.getFirstFrame();
		lastIndexFrame = frames.getLastFrame().getNumFrame();
		firstIndexFrame = frames.getFirstFrame().getNumFrame();
		currentIndexFrame = currentFrame.getNumFrame();
	}

	/**
	 * Starts and plays continously this action
	 * @param if continously is false it behaves like the play method
	 */
	public void play(boolean continously) {
		play();
		continuosly = continously;
	}

	/**
	 * Stops this action
	 */
	public void stop() {
		play = false;
	}

	public boolean isPlay() {
		return play;
	}
	
	/**
	 * Step in one frame this action
	 */
	public void step() {
		currentFrame = frames.getFrame(currentIndexFrame);
		currentIndexFrame = (currentIndexFrame == lastIndexFrame) ? firstIndexFrame : currentIndexFrame+1;
		if (currentIndexFrame == firstIndexFrame && !continuosly) stop();
	}
	
	/**
	 * @return The current frame for this action
	 */
	public Frame getCurrentFrame() {
		return currentFrame;
	}

}
