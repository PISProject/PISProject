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

package com.ub.pis.activities;

import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import com.ub.pis.dialogs.DialogFragmentForQuit;
import com.ub.pis.music.broadcasts.ScreenReceiver;
import com.ub.pis.music.services.MusicService;
import com.ub.pis.music.services.MusicService.ServiceBinder;
import com.ub.pis.sharedpreferences.MainPreferences;

/**
 * Activity base que implementen totes les activities.
 * 
 * @author zenbook
 *
 */

public class BaseActivity extends FragmentActivity {
	
	private BroadcastReceiver mReceiver;
	protected MusicService mServ;
	protected ServiceConnection mScon = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MainPreferences pref = new MainPreferences(getApplicationContext());
			boolean music = pref.getBoolean(MainPreferences.MUSIC, true);
			ServiceBinder sb = (ServiceBinder) service;
			mServ = sb.getService();
			if(music) {
				if(ScreenReceiver.wasScreenOn) mServ.resumeMusic();
				else mServ.pauseMusic();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//write here code comun in activites
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		Intent i = new Intent(this, MusicService.class);
		bindService(i, mScon, Context.BIND_AUTO_CREATE);
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		unbindService(mScon);
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	
	
	public void iniciarActivity(Class<?> classe) {
	 	Intent i = new Intent(this, classe);
        startActivity(i);
	}
	
	public void showDialogQuit() { 
        DialogFragmentForQuit editNameDialog = new DialogFragmentForQuit();
        editNameDialog.show(getSupportFragmentManager(), "fragment_quit");
    }
}
