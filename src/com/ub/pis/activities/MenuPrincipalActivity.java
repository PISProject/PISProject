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

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ub.pis.R;
import com.ub.pis.clientsupport.Client;
import com.ub.pis.dialogs.LoadingDialog;
import com.ub.pis.game.UserData;

/**
 * Desde aquí se podrá escoger lo que quiere hacer el usuario(jugar, mirar sus puntuaciones, etc.)
 * 
 * @author zenbook
 *
 */

public class MenuPrincipalActivity extends BaseActivity {
	private Button options_button;
	private Button playNow_button;
	private Button quit_button;
	private Button highscores_button;
	private Button shop_button;
	
	private boolean isInQueue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setResult(RESULT_CANCELED);
		initActivity(); 
	}
	
	public void initActivity() {
		setContentView(R.layout.activity_menu_principal);
		highscores_button = (Button)findViewById(R.id.highscores_button);
		options_button = (Button)findViewById(R.id.options_button);
		playNow_button = (Button)findViewById(R.id.playNow_button);
		shop_button = (Button)findViewById(R.id.shop_button);
		quit_button = (Button)findViewById(R.id.quit_button);
		
		isInQueue = false;
		
		quit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialogQuit();
			}
		});
		shop_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					iniciarActivity(ShopActivity.class);
			}
		});
		options_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getApplicationContext(),SettingsActivity.class), 1);
			}
		});
		playNow_button.setOnClickListener(new OnClickListener() {
			@Override 
			public void onClick(View v) {
				iniciarActivity(ChooseStageActivity.class);
				/*if(isInQueue) return;
				isInQueue = true;
				final LoadingDialog dialog = new LoadingDialog();
		        dialog.show(getSupportFragmentManager(), "fragment_loading_dialog");
				new Thread(new Runnable() {
					@Override
					public void run() {
						Client c = Client.getInstance(false);
						if(c!=null) {
							ArrayList<UserData> actors = c.joinQueue();
							isInQueue = false;
							if(actors == null) {
								dialog.dismiss();
								return;
							}
							Intent i = new Intent(getApplicationContext(), LoadingGameActivity.class);
							i.putExtra("actors", (Serializable)actors);
							startActivity(i);
							
							//dialog.dismiss();
							finish();
						}
					}
				}).start();*/
			}
		});
		highscores_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iniciarActivity(HighScoreActivity.class);
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	showDialogQuit();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			initActivity();
			setResult(RESULT_OK);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void logout() {
		final Client c = Client.getInstance(false);
		if(c!=null){ 
			//PONER UN LO PUTO THREAD
			if(c.isConnected()) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						c.close();
					}
				}).start();
			}
		}
		finish();
	}
}
