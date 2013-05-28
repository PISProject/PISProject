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

package com.ub.pis.music;

import android.content.Context;
import android.media.MediaPlayer;

import com.ub.pis.sharedpreferences.MainPreferences;

/**
 * Sempre que s'hagi de reproduir un so, s'ha de fer mitjançant aquesta clase, controla si s'ha d'activar el so o no.
 * 
 * @author zenbook
 *
 */

public class SoundPlayer {
	
	private MainPreferences pref;
	private MediaPlayer mp;
	
	public SoundPlayer(Context c, int ref) {
		super();
		mp = MediaPlayer.create(c, ref);
		pref = new MainPreferences(c);
	}
	
	public void start() {
		// TODO Auto-generated method stub
		boolean sound =pref.getBoolean(MainPreferences.SOUND, true);
		int volum = pref.getInt(MainPreferences.VOLUMEN_SOUND, 100);
		setVolumen(volum);
		if(sound) mp.start();
	}
	
	public void seekTo(int mil) {
		mp.seekTo(mil);
	}
	
	public void stop() {
		mp.stop();
	}
	
	public void pause() {
		mp.pause();
	}
	
	public boolean isPlaying() {
		return mp.isPlaying();
	}
	
	public void setVolumen(int volum){
		float log=(float)(Math.log(100-volum)/Math.log(100));
		mp.setVolume(1-log,1-log);
	}
	
}
