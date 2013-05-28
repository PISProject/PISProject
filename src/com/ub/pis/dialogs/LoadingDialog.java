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

package com.ub.pis.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ub.pis.R;
import com.ub.pis.clientsupport.Client;

public class LoadingDialog extends DialogFragment {

	
	private Button quit_button;
	
	public LoadingDialog() {
		super.setStyle(STYLE_NO_TITLE, 0);
		setCancelable(false);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_dialog_loading, container);
		
		quit_button = (Button) v.findViewById(R.id.quit_button);
		quit_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				quit_button.setClickable(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						Client c = Client.getInstance(false);
						if(c != null) c.stopWaiting();
					}
				}).start();
			}
		});
		
		return v;
	}
}
