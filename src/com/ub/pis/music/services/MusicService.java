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

package com.ub.pis.music.services;


import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.ub.pis.R;
import com.ub.pis.sharedpreferences.MainPreferences;

/**
 * Servei per escoltar la música en segon pla.
 * Pendent a realitzar.
 * 
 * @author zenbook
 *
 */

public class MusicService extends Service{

	private final IBinder mBinder = new ServiceBinder();
	private MainPreferences pref;
    private MediaPlayer mPlayer;
    private int length = 0;

    public MusicService() { }

    public class ServiceBinder extends Binder {
    	 public MusicService getService(){
    		 return MusicService.this;
    	 }
   }
    
    @Override
    public IBinder onBind(Intent arg0){return mBinder;}

    @Override
    public void onCreate (){
	  super.onCreate();
	  
       mPlayer = MediaPlayer.create(this, R.raw.game_music);
       pref = new MainPreferences(getApplicationContext());
       
       if(mPlayer!= null)
        {
    	   	mPlayer.setScreenOnWhilePlaying(false);
        	mPlayer.setLooping(true);
        	int volum = pref.getInt(MainPreferences.VOLUMEN_MUSIC, 100);
        	setVolumen(volum);
        }


        mPlayer.setOnErrorListener(new OnErrorListener() {
        	public boolean onError(MediaPlayer mp, int what, int extra){
				onError(mPlayer, what, extra);
				return true;
			}
    	 });
	}

	public void pauseMusic()
	{
		if(mPlayer.isPlaying())
		{
			mPlayer.pause();
			length=mPlayer.getCurrentPosition();

		}
	}

	public void resumeMusic()
	{
		if(mPlayer.isPlaying()==false)
		{
			mPlayer.seekTo(length);
			mPlayer.start();
		}
	}

	public void stopMusic()
	{
		mPlayer.stop();
		mPlayer.release();
		mPlayer = null;
	}
	
	public void setVolumen(int volum){
		float log=(float)(Math.log(100-volum)/Math.log(100));
		mPlayer.setVolume(1-log,1-log);
		pref.putInt(MainPreferences.VOLUMEN_MUSIC, volum);
	}

	@Override
	public void onDestroy ()
	{
		super.onDestroy();
		
		if(mPlayer != null)
		{
		try{
		 mPlayer.stop();
		 mPlayer.release();
			}finally {
				mPlayer = null;
			}
		}
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {

		Toast.makeText(this, "music player failed", Toast.LENGTH_SHORT).show();
		if(mPlayer != null)
		{
			try{
				mPlayer.stop();
				mPlayer.release();
			}finally {
				mPlayer = null;
			}
		}
		return false;
	}
}