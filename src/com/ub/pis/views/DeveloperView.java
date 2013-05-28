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
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.ub.pis.R;
import com.ub.pis.music.SoundPlayer;


public class DeveloperView extends View {

	private Drawable image;
	private String name;
	
	private CustomTextView text;
	private LinearLayout linear;
	
	private boolean principi;
	private boolean isDown;
	
	private SoundPlayer first_sound, second_sound;
	
	public DeveloperView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		linear = new LinearLayout(getContext());
		text = new CustomTextView(getContext());
		
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Developer, 0, 0);
	    name = ta.getString(R.styleable.Developer_name);
	    int ref = ta.getResourceId(R.styleable.Developer_image, 0);
    	image =  context.getResources().getDrawable(ref);
    	ref = ta.getResourceId(R.styleable.Developer_first_sound, 0);
    	first_sound = new SoundPlayer(getContext(), ref);
    	ref = ta.getResourceId(R.styleable.Developer_second_sound, 0);
    	second_sound =new SoundPlayer(getContext(), ref);
	    
    	
	    isDown = false;
	    principi = true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.save();
		//canvas.drawColor(0, PorterDuff.Mode.CLEAR);
		
		if(principi) {
			int tam;
			
			text.setText(name);
			text.setTextColor(getResources().getColor(R.color.text_principal));
			linear.addView(text);
			linear.measure(getWidth(), getHeight());
			linear.setGravity(Gravity.CENTER);
			linear.layout(0, 0, getWidth(), getHeight());
			
			if(getWidth()>getHeight())tam = getHeight();
			else tam = getWidth();
			image.setBounds((getWidth()/2)-tam/2, 0, (getWidth()/2)+tam/2, getHeight());
			
			principi = false;
		}
		if(isDown) linear.draw(canvas);
		else image.draw(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				isDown = true;
				if(first_sound.isPlaying()) {
					first_sound.seekTo(0);
				}
				first_sound.start();
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				if(first_sound.isPlaying()) {
					first_sound.pause();
				}
				if(second_sound.isPlaying()) {
					second_sound.seekTo(0);
				}
				second_sound.start();
				break;
			case MotionEvent.ACTION_UP:
				isDown = false;
				break;
		}
		invalidate();
		return true;
	}
}
