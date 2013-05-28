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

import com.ub.pis.renderer.linearalgebra.Vector3f;

public interface IPositionable {
	public void setRotX(float a);
	public void setRotY(float a);
	public void setRotZ(float a);
	public void setRotation(float rotx, float roty, float rotz);
	public void setTranslation(float x, float y, float z);
	public Vector3f getPosition();
	public Vector3f getRotation();
}

