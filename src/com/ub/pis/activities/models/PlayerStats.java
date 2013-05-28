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

package com.ub.pis.activities.models;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.ub.pis.views.CustomTextView;
import com.ub.pis.views.MyTableLayout.ITable;

/**
 * Clase que implementa ITable i presenta en una taula les puntuacions que s'han tret en una partida.
 * 
 * @author zenbook
 *
 */

public class PlayerStats implements ITable{
	private String name;
	private int points;
	private int coins;
	
	public PlayerStats(String name, int points, int coins) {
		this.name = name;
		this.points = points;
		this.coins = coins;
	}

	@Override
	public ArrayList<View> getViews(Context c) {
		ArrayList<View> views = new ArrayList<View>();
		CustomTextView t = new CustomTextView(c);
		t.setGravity(Gravity.CENTER);
		t.setText(name);
		views.add(t);
		t = new CustomTextView(c);
		t.setGravity(Gravity.CENTER);
		t.setText(points+"");
		views.add(t);
		t = new CustomTextView(c);
		t.setGravity(Gravity.CENTER);
		t.setText(coins+"");
		views.add(t);
		return views;
	}
	
	
}
