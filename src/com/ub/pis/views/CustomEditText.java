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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class CustomEditText extends EditText{

	public CustomEditText(Context context) {
		super(context);
		initEditText(context);
	}
	
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initEditText(context);
	}
	
	public CustomEditText(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
		initEditText(context);
	}
	
	private void initEditText(Context context) {
		Typeface face = Typeface.createFromAsset(context.getAssets(), ConstantsView.FUENTE);
		setTypeface(face);
		setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
	}

}
