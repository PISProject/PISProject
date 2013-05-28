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

package com.ub.pis.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ub.pis.R;
import com.ub.pis.activities.GameActivity;
import com.ub.pis.activities.GameOverActivity;
import com.ub.pis.game.World;

/**
 * Fragment que aparece al pausar la partida iniciada.
 * 
 * @author zenbook
 *
 */

public class PauseFragment extends Fragment{
		
		private Button quitGame;
	
		@Override
  		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				View vista = inflater.inflate(R.layout.fragment_pause, container, false);
				
				quitGame = (Button) vista.findViewById(R.id.quit_button);
				quitGame.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
								GameActivity game = (GameActivity) getActivity();
								game.iniciarActivity(GameOverActivity.class);
								game.finish();
								World.destroy();
						}
				});
				return vista;
		}
}
