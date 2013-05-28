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

package com.ub.pis.music.broadcasts;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.ub.pis.music.services.MusicService;
import com.ub.pis.music.services.MusicService.ServiceBinder;

/**
 * Broadcasts que comprueba si la pantalla está bloqueada o no.
 * Pendiente de darle un uso todavía.
 * 
 * @author zenbook
 *
 */

public class ScreenReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    private MusicService mServ;
    private Context c;
    
	private ServiceConnection mScon= new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			ServiceBinder sb = (ServiceBinder) service;
			mServ = sb.getService();
			if(!wasScreenOn) mServ.pauseMusic();
			c.unbindService(mScon);
		}
	};
    @Override
    public void onReceive(Context context, Intent intent) {
    	this.c = context;
    	if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
        	wasScreenOn = false;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
    		wasScreenOn = true;
	    }
    	Intent i = new Intent(context, MusicService.class);
    	c.bindService(i, mScon, Context.BIND_AUTO_CREATE);
        
	}
}
