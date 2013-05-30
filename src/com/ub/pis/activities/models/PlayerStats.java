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

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.ub.pis.views.CustomTextView;
import com.ub.pis.views.MyTableLayout.ITable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que implementa ITable i presenta en una taula les puntuacions que s'han tret en una partida.
 * 
 * @author zenbook
 *
 */

public class PlayerStats implements ITable, Serializable{
	private String name;
	private int deaths;
	private int kills;
	
	public PlayerStats(String name, int deaths, int kills) {
		this.name = name;
		this.deaths = deaths;
		this.kills = kills;
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
		t.setText(deaths+"");
		views.add(t);
		t = new CustomTextView(c);
		t.setGravity(Gravity.CENTER);
		t.setText(kills+"");
		views.add(t);
		return views;
	}

    @Override
    public String toString() {
        return name+","+kills+","+deaths;
    }
}
