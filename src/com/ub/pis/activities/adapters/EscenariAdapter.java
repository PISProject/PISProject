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

package com.ub.pis.activities.adapters;

import com.ub.pis.R;
import com.ub.pis.activities.models.Escenari;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Adapter que tindrá la llista de escenaris.
 * 
 * @author zenbook
 *
 */

public class EscenariAdapter extends ArrayAdapter<Escenari>{
	Context context; 
    int layoutResourceId;    
    Escenari[] data;
    
    public EscenariAdapter(Context context, int layoutResourceId, Escenari[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(layoutResourceId, null);
		}
        
		Escenari e = data[position];
		if(e!=null) {
			TextView nom = (TextView) v.findViewById(R.id.txtTitle);
			ImageView icona = (ImageView) v.findViewById(R.id.imgIcon);
			ImageView pany = (ImageView) v.findViewById(R.id.candau);
			
			nom.setText("Escenari de "+e.numPlayers+" "+(e.numPlayers>1?"jugadors":"jugador"));
			icona.setImageDrawable(context.getResources().getDrawable(R.drawable.nieve));
			if(e.bloqueado) pany.setImageDrawable(context.getResources().getDrawable(R.drawable.candau));
			else pany.setImageDrawable(null);
			
		}
		
        return v;
    }
}


