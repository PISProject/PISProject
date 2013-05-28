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
package com.ub.pis.renderer.models;

import com.ub.pis.renderer.animation.Action;
import com.ub.pis.renderer.linearalgebra.Matrix4fGroup;
import com.ub.pis.renderer.opengl.ShaderProgram;
import com.ub.pis.renderer.opengl.SkinnedMesh;
import com.ub.pis.renderer.opengl.Texture;
import com.ub.pis.renderer.renderer.Constants;
import com.ub.pis.renderer.scene.Camera;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class AnimatedModel extends Model {
	
	protected SkinnedMesh mesh;
	private HashMap<String, Action> actions;
	private Action currentAction;
	private Stack<Action> currentActions;
	private Matrix4fGroup basicPose;
	private Matrix4fGroup currentPose;
	private int bonesHandle;

    public AnimatedModel(ModelNode parent, SkinnedMesh mesh, Texture texture, ShaderProgram program, Map<String, Action> actions) {
        super(parent, mesh, texture, program);
        this.mesh = mesh;
        this.actions = (HashMap<String, Action>) actions;
        currentActions = new Stack<Action>();
        currentAction = new Action("null",null);
        basicPose = new Matrix4fGroup(Constants.MAX_BONES);
        basicPose.setIdentity();
    }
	
	
	public AnimatedModel(SkinnedMesh mesh, Texture texture, ShaderProgram program, Map<String, Action> actions) {
		super(mesh, texture, program);
		this.mesh = mesh;
		this.actions = (HashMap<String, Action>) actions;
		currentActions = new Stack<Action>();
		currentAction = new Action("null",null);
		basicPose = new Matrix4fGroup(Constants.MAX_BONES);
		basicPose.setIdentity();
	}
	
	public Map<String, Action> getActions() {
		return actions;
	}
	
	public Set<String> getActionsNames() {
		return actions.keySet();
	}
	
	public void setActions(Map<String,Action> actions) {
		this.actions = (HashMap<String, Action>) actions;
	}
	
	@Override
	protected void getShaderLocations() {
		super.getShaderLocations();
		bonesHandle = program.getUniformLocation("bones");
		mesh.setBonesIndicesLocation(program.getAttribLocation("bonesIndices"));
		mesh.setWeightLocation(program.getAttribLocation("weights"));
	}

	
	@Override
	public void update(Camera camera) {
		super.update(camera);
		if (!currentActions.empty()) {
			currentAction = currentActions.lastElement();
			if (currentAction.isPlay()) {
				currentAction.step();
				currentPose = currentAction.getCurrentFrame().getBones();
			} else {
				currentActions.pop();
			}
		} else {
			currentPose = basicPose;
		}
	}
	
	@Override
	public void bind() {
		super.bind();
		program.setUniformValue(bonesHandle, currentPose);
	}
	
	public Action getCurrentAction() {
		return currentAction;
	}
	
	private Action getActionByName(String name) {
		Action ac = actions.get(name);
		if (ac == null) throw new RuntimeException("No action called "+name);
		currentActions.push(ac);
		return ac;
	}
	
	public void play(String name) {
		currentAction = getActionByName(name);
		currentAction.play();
	}
	
	public void play(String name, boolean continously) {
		currentAction = getActionByName(name);
		currentAction.play(continously);
	}
	
	public void stop() {
		currentAction.stop();
		currentActions.pop();
	}


}
