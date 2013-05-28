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

package com.ub.pis.renderer.scene;

import com.ub.pis.renderer.models.Model;

import java.util.ArrayList;
import java.util.Collections;

public class Scene {
    private ArrayList<Model> models;
	private Camera camera;
	private boolean initialized = false;
	
	public Scene(Camera camera) {
		this.camera = camera;
        models = new ArrayList<Model>();
	}
	
	public void add(Model m) {
		models.add(m);
		if (initialized) {
			m.initialize();
		}
        Collections.sort(models);
    }
	
	public void remove(Model m) {
		models.remove(m);
	}
	
	public void initialize(int width, int height) {
		this.initialized = true;
		for (Model m : models){
			m.initialize();
		}
		camera.initialize(width, height);
	}
	
	public void draw() {
		camera.update();
		for (Model m : models) {
			m.update(camera);
			m.bind();
			m.draw();
			m.unbind();
		}
		
	}

}
