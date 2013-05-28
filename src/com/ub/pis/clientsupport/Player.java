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

package com.ub.pis.clientsupport;

public class Player {
	public int id;
	public int modelo;
	public int vida;
	public float[] pos;
	
	public String toString() {
		return "Player{id = "+id+",modelo = "+modelo+", vida = "+vida+", pos = ["+pos[0]+","+pos[1]+"]}";
	}
}
