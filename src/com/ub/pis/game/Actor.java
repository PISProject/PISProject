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

import android.content.Context;
import android.graphics.Bitmap;

import com.ub.pis.renderer.models.AnimatedModel;
import com.ub.pis.renderer.opengl.Texture;
import com.ub.pis.renderer.primitives.Quad;
import com.ub.pis.renderer.primitives.Sprite;
import com.ub.pis.renderer.text.Text;
import com.ub.pis.views.LifeProgress;

public class Actor extends GameObject {

	protected AnimatedModel model;

	private int id;
	private String nombre;
	private int vida;
	private boolean isMoving;

	private Text name;
	private Sprite life;
	private LifeProgress lifeProgress;

	
	public Actor(AnimatedModel model, UserData actor, Context c) {
		super(model);
		this.model = model;
		this.id = actor.id;
		this.nombre = actor.nombre;
		setName(nombre);
		vida = 100;
		lifeProgress = new LifeProgress(c);
		
		Bitmap b = lifeProgress.getScreenViewBitmap(100);
		Texture t = new Texture(b);
		life = new Sprite(2,1,t);
	}
	
	public void play(String name) {
		this.model.play(name);
	}
	
	public void play(String name, boolean continuosly) {
		this.model.play(name, continuosly);
		isMoving = true;
	}
	
	public void stop() {
		this.model.stop();
		isMoving = false;
	}
	
	public int getId() {
		return id;
	}
	
	public void moveTo(float x, float y) {
		float xAnt = model.getPosition().getX();
		float yAnt = model.getPosition().getY();
		
		/*Operación de movimiento del machango*/
		if(Math.abs(x-xAnt)<0.01 && Math.abs(y-yAnt)<0.01) {
			if(isMoving) stop();
		}else{
			if(!isMoving) play("Walk", true);
			/*Operación de rotación del machango*/
			double[] w = {(x-xAnt),(y-yAnt)};
			double[] v = {0,-1};
			double angulo = 0;
			for (int i = 0; i < w.length; i++) {
				angulo += v[i] * w[i];
			}
			angulo/= Math.sqrt(w[0] * w[0] + w[1] * w[1]);
			angulo/= Math.sqrt(v[0] * v[0] + v[1] * v[1]);
			angulo = Math.acos(angulo);
			angulo = Math.toDegrees(angulo);
			if (w[0] < 0) angulo = 360 - angulo;
			rotate((float) angulo);
		}
		translate(x, y);
	}
	
	public void setName(String name) {
		this.name = new Text(name);
        this.name.setParent(model);
		this.name.setTranslation(x, y+4f, 0);
	}
	
	public void setLife(int life) {
		//if(life == vida) return;
		vida = life;
		Bitmap b = lifeProgress.getScreenViewBitmap(life);
		Texture t = new Texture(b);
		//t.initialize();
		//this.life = new Sprite(2f,1f,t);
		this.life.setTexture(t);
	}
	
	public Text getTextModel() {
		return this.name;
	}
	
	public Sprite getProgressBar() {
		return life;
	}
	
	@Override
	public void translate(float x, float y) {
		super.translate(x, y);
		this.name.setTranslation(x, y+4f, 0);
		this.life.setTranslation(x, y+5, 0);
	}


}
