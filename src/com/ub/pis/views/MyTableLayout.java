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

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.ub.pis.R;

/**
 * Clase de suport que ajuda a posar una llista d'objectes en una taula.
 * La clase interesada ha d'implementar la interfaç de ITable.
 * 
 * @author zenbook
 *
 */

public class MyTableLayout extends TableLayout {

	public MyTableLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setTable(String[] header, ITable[] tables) {
		super.removeAllViews(); //Borramos todo lo que haya.
		super.setStretchAllColumns(true);
		LayoutParams params = new LayoutParams();
		params.setMargins(0,15,0,15);
		ArrayList<View> views;
		TableRow tr = new TableRow(getContext());
		tr.setLayoutParams(params);
		tr.setGravity(Gravity.CENTER);
		CustomTextView tvh;
		for (String h : header) {
			tvh = new CustomTextView(getContext());
			tvh.setGravity(Gravity.CENTER);
			tvh.setTextColor(getResources().getColor(R.color.text_principal));
			tvh.setText(h);
			tr.addView(tvh);
		}
		super.addView(tr);
		for (ITable t : tables) {
			views = t.getViews(getContext());
			tr = new TableRow(getContext());
			tr.setLayoutParams(params);
			tr.setGravity(Gravity.CENTER);
			for (View v : views) {
				tr.addView(v);
			}
			super.addView(tr);
		}
		invalidate();
	}
	
	public interface ITable {
		/**
		 * Aquest mètode hauria de retornar per orde com vol l'usuari els views que es vegin en la taula.
		 * 
		 * @param c
		 * @return
		 */
		public ArrayList<View> getViews(Context c);
	}
}
