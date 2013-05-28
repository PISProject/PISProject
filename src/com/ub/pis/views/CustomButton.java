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
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.Button;

import com.ub.pis.R;
import com.ub.pis.music.SoundPlayer;

public class CustomButton extends Button{

	private SoundPlayer sound;
	
	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public CustomButton(Context context) {
		super(context);
		init();
	}

	public CustomButton(Context context, AttributeSet attrs, int style) {
		super(context, attrs, style);
		init();
	}
	
	public void init() {
		Typeface face = Typeface.createFromAsset(getContext().getAssets(), ConstantsView.FUENTE);
		setTypeface(face);
		
		int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 23, getResources().getDisplayMetrics());
		setPadding(dp, 0, dp, 0);
		
		sound = new SoundPlayer(getContext(), R.raw.soundbutton);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				if(sound.isPlaying()) sound.seekTo(0);
				sound.start();
				break;
		}
		return super.onTouchEvent(event);
	}
}
