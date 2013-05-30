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

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.ub.pis.R;
import com.ub.pis.activities.models.PlayerStats;
import com.ub.pis.views.MyTableLayout;

import java.util.ArrayList;

/**
 * Actividad que aparece al finalizar el juego.
 * 
 * @author zenbook
 *
 */

public class GameOverActivity extends BaseActivity {

	private Button quit_button;
	private MyTableLayout table;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game_over);
		
		quit_button = (Button)findViewById(R.id.quit_button);
		table = (MyTableLayout) findViewById(R.id.tableLayout2);
		
		ArrayList<PlayerStats> array = (ArrayList<PlayerStats>) getIntent().getExtras().getSerializable("stats");
		boolean victory = getIntent().getExtras().getBoolean("victory");


		String[] headers = {
				"Name",
				"Deaths",
				"Kills"
		};

        //Log.i("array", array.toString());
        PlayerStats[] players = new PlayerStats[array.size()];
        for (int i = 0; i < array.size(); i++) {
            PlayerStats p = array.get(i);
            players[i] = p;
        }

		table.setTable(headers, players);
		
		quit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//iniciarActivity(MenuPrincipalActivity.class);
				finish();
			}
		});
	}
	
	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}*/
}
