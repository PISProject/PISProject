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

public class ThreadGame extends Thread{
	private Protocol protocol;
    private boolean isAlive;
    
    public ThreadGame (Protocol protocol){
        this.protocol = protocol;
    }
    
    @Override
    public void run() {
    	boolean correct;
        isAlive = true;
    	do{
            correct = protocol.readMap();
        } while(correct || isAlive);
    }

    public void closeThread() {
        isAlive = false;
    }
}