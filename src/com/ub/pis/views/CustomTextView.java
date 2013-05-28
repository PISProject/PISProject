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

package com.ub.pis.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Clase para introducir un tipo de letra predeterminado.
 * 
 * @author zenbook
 *
 */

public class CustomTextView extends TextView{

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		Typeface face = Typeface.createFromAsset(context.getAssets(), ConstantsView.FUENTE);
		setTypeface(face);
	}
	public CustomTextView(Context context) {
		super(context);
		Typeface face = Typeface.createFromAsset(context.getAssets(), ConstantsView.FUENTE);
		setTypeface(face);
	}

}
