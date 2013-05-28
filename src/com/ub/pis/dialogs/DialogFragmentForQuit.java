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
import com.ub.pis.activities.MenuPrincipalActivity;

/**
 * Dialog que sale cuando se quiere preguntar con seguridad si desea salir de la actividad o no.
 * 
 * @author zenbook
 *
 */

public class DialogFragmentForQuit extends DialogFragment{

	private Button btn_si, btn_no;
	
	public DialogFragmentForQuit() {
		super.setStyle(STYLE_NO_TITLE, 0);
		//super.setStyle(STYLE_NO_FRAME, 0);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_quit, container);
		
		btn_si = (Button) view.findViewById(R.id.si_button);
		btn_no = (Button) view.findViewById(R.id.no_button);
		
		btn_si.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(getActivity() instanceof MenuPrincipalActivity) ((MenuPrincipalActivity) getActivity()).logout();
				else getActivity().finish();
			}
		});
		btn_no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		return view;
	}

}
