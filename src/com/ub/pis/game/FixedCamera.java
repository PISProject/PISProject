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


import com.ub.pis.renderer.linearalgebra.Vector3f;
import com.ub.pis.renderer.scene.Camera;

public class FixedCamera extends Camera{

	@Override
	public void initialize(int width, int height) {
		float ratio = (float)width/height;
		setProjectionMatrix(45.0f, ratio, 1, 100.0f);
		setViewMatrix(0,20,20,0,0,0,0,0,1);
		setViewProjectionMatrix();
	}

	@Override
	public void update() {
		Actor actor = World.getPlayer();
		if(actor==null) return;
		Vector3f v = actor.getModel().getPosition();
		
		setViewMatrix(v.getX(),15+v.getY(),40,  //Posici�n de la c�mara
				v.getX(),v.getY(),v.getZ(),  //Punto al que mira la c�mara
				0,0,1);  //Vector normal de la c�mara
		
		setViewProjectionMatrix();
	}
	
	

}

