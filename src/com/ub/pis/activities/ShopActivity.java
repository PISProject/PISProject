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

package com.ub.pis.activities;

import android.os.Bundle;
import android.view.Window;

import com.ub.pis.R;
import com.ub.pis.activities.models.ObjectShop;
import com.ub.pis.views.MyTableLayout;

/**
 * Desde aquí el usuario podrá escoger que desea comprar.
 * 
 * @author zenbook
 *
 */

public class ShopActivity extends BaseActivity {

	private MyTableLayout table;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_shop);
		table = (MyTableLayout) findViewById(R.id.tableLayout1);
		
		ObjectShop[] objs = {
				new ObjectShop(R.drawable.pocion, getString(R.string.shop_pocion_descripcion), 20),
				new ObjectShop(R.drawable.pocion2, getString(R.string.shop_pocion2_descripcion), 420),
				new ObjectShop(R.drawable.pocion3, "Algo que poner", 0),
				new ObjectShop(R.drawable.pocion4, "Algo que poner", 0),
				new ObjectShop(R.drawable.pocion5, "Algo que poner", 0),
				new ObjectShop(R.drawable.pocion6, "Algo que poner", 0),
		};
		
		String[] headers = {
				getString(R.string.shop_nombre_objeto),
				getString(R.string.shop_descripcion),
				getString(R.string.shop_precio)
		};
		
		table.setTable(headers, objs);
	}

	

}
