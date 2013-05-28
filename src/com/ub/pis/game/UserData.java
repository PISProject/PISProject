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

import java.io.Serializable;

public class UserData implements Serializable{
	private static final long serialVersionUID = 6869504253637683693L;
	public String nombre;
	public int id;
	/*Aún por implementar*/
	public int refTextura;
	public int refJson;
	
	public UserData(String nombre, int id, int refJson, int refTextura) {
		this.id = id;
		this.nombre = nombre;
		this.refJson = refJson;
		this.refTextura = refTextura;
	}
}
