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
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ub.pis.R;

/**
 * Fragment que aparece al pulsar boton informacion.
 * 
 * @author zenbook
 *
 */

public class InfoFragment extends DialogFragment{
		public InfoFragment() {
			super.setStyle(STYLE_NO_TITLE, 0);//pk no surti lapartat del titol al dialog
//			getDialog().getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);//? funciona?
			//super.setStyle(STYLE_NO_FRAME, 0);
		}
		@Override
  		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				View vista = inflater.inflate(R.layout.fragment_info, container, false);
				
				
				return vista;
		}
		
}
