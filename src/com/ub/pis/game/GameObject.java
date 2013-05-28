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

package com.ub.pis.game;

import android.opengl.GLSurfaceView;
import com.ub.pis.renderer.models.Model;

import java.util.TimerTask;

abstract class GameObjectRunnable extends TimerTask {
    protected float x,y,a;
    GameObjectRunnable(float x, float y) {
        this.x = x;
        this.y = y;
    }

    GameObjectRunnable(float a) {
        this.a = a;
    }
}

public class GameObject{
	
	protected Model model;
	protected float x;
	protected float y;
	protected float a;
    private GLSurfaceView glView;
	
	
	public GameObject(Model model) {
		this.model = model;
	}
	
	public Model getModel() {
		return model;
	}

    public void setView(GLSurfaceView glView) {
        this.glView = glView;
    }
	
	public void translate(float x, float y) {

		model.setTranslation(x, y, 0);
		this.x = x;
		this.y = y;
	}
	
	public void rotate(float a) {

        model.setRotZ(a);
		this.a = a;
	}
}
