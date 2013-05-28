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
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.ub.pis.R;

public class LifeProgress {

	private ProgressBar progressBar;
	
	public LifeProgress(Context c) {
		LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.progress_life, null);
		progressBar = (ProgressBar) v;
	}
	
	public Bitmap getScreenViewBitmap(int progress) {
		
		progressBar.setProgress(progress);
		
		progressBar.setDrawingCacheEnabled(true);
	 
		progressBar.layout(0, 0, 64, 16); 
	 
		progressBar.buildDrawingCache(true);
	    Bitmap b = Bitmap.createBitmap(progressBar.getDrawingCache(true));
	    progressBar.setDrawingCacheEnabled(false); //limpieza de memoria caché
	    return b;
	}
}
