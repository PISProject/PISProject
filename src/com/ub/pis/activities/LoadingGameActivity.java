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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.ub.pis.R;
import com.ub.pis.activities.models.Escenari;
import com.ub.pis.clientsupport.Client;
import com.ub.pis.game.UserData;
import com.ub.pis.views.GameView;

/**
 * Actividad donde el usuario esperará a que haya alguien conectado a una partida.
 * 
 * @author zenbook
 *
 */

public class LoadingGameActivity extends BaseActivity {

	private View bola1;
	private View bola2;
	private View bola3;
	
	private int bolaVisible;
	private Timer timer;
	
	private ArrayList<UserData> actors;
	private Escenari escenari;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_waiting);
		bola1 = findViewById(R.id.bola1);
		bola2 = findViewById(R.id.bola2);
		bola3 = findViewById(R.id.bola3);
		
		bolaVisible = 3;

		actors = (ArrayList<UserData>) getIntent().getExtras().getSerializable("actors");
		escenari = (Escenari) getIntent().getExtras().getSerializable("escenari");
		 
		new Thread(new Runnable() {
			@Override
			public void run() {
				Client c = Client.getInstance(false);
				if(c!=null) {
					GameView.loadModels(getApplicationContext(), escenari);
					if(c.readyToStart()) {
						Intent i = new Intent(getApplicationContext(), GameActivity.class);
						i.putExtra("actors", (Serializable)actors);
						startActivity(i);
					}
				}
				finish();
			}
		}).start();
		
		timer = new Timer();
		timer.schedule(new MyTimerTask(), 0,500);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}
	
	private class MyTimerTask extends TimerTask {
		@Override
		public void run() {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					bolaVisible = (bolaVisible)%3 + 1;
					bola1.setVisibility(bolaVisible == 1?View.VISIBLE:View.INVISIBLE);
					bola2.setVisibility(bolaVisible == 2?View.VISIBLE:View.INVISIBLE);
					bola3.setVisibility(bolaVisible == 3?View.VISIBLE:View.INVISIBLE);
				}
			});
		}
		
	}
}
