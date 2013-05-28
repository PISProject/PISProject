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
import android.widget.ImageView;

import com.ub.pis.views.CustomTextView;
import com.ub.pis.views.MyTableLayout.ITable;

/**
 * Clase que implementa ITable, per poder mostrar els objectes que es venen en la tenda.
 * 
 * @author zenbook
 *
 */

public class ObjectShop implements ITable{
	private int ref;
	private String desc;
	private int price;
	
	public ObjectShop(int ref, String desc, int price) {
		this.ref = ref;
		this.desc = desc;
		this.price = price;
	}

	@Override
	public ArrayList<View> getViews(Context c) {
		ArrayList<View> views = new ArrayList<View>();
		ImageView i =new ImageView(c);
		i.setImageDrawable(c.getResources().getDrawable(ref));
		views.add(i);
		CustomTextView t = new CustomTextView(c);
		t.setGravity(Gravity.CENTER);
		t.setText(desc);
		views.add(t);
		t = new CustomTextView(c);
		t.setGravity(Gravity.CENTER);
		t.setText(price+"");
		views.add(t);
		return views;
	}
	
	
}
