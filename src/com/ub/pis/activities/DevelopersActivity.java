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

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;

import com.ub.pis.R;
import com.ub.pis.dialogs.DialogUB;
import com.ub.pis.views.DeveloperView;

public class DevelopersActivity extends BaseActivity {
	
	private DeveloperView[] devs;
	private Timer timer;
	private int cont;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Display display = getWindowManager().getDefaultDisplay(); 
		int width = display.getWidth();
		int height = display.getHeight();
		
		setContentView(R.layout.activity_developers);
		
		DeveloperView dev1 = (DeveloperView) findViewById(R.id.first_developer);
		DeveloperView dev2 = (DeveloperView) findViewById(R.id.second_developer);
		DeveloperView dev3 = (DeveloperView) findViewById(R.id.three_developer);
		DeveloperView dev4 = (DeveloperView) findViewById(R.id.four_developer);
		
		devs = new DeveloperView[] {dev1, dev2, dev3, dev4};
		
		for (int i = 0;i<devs.length;i++) {
			devs[i].getLayoutParams().width = width/2;
			devs[i].getLayoutParams().height = height/2;
		}
		
		cont=0;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				cont = 0;
			}
		}, 0, 1000);
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		cont ++;
	    if (keyCode == KeyEvent.KEYCODE_MENU && cont == 3) {
	    	DialogUB dialog = new DialogUB();
	        dialog.show(getSupportFragmentManager(), "fragment_ub");
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		timer.cancel();
		super.onDestroy();
	}
}
