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

import android.util.SparseArray;

import com.ub.pis.R;

import java.io.Serializable;

/**
 * Clase Escenari, per poder representar el Listview de objectes tipus Escenari.
 * 
 * @author zenbook
 *
 */

public class Escenari implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -647299364464604505L;

	/*
	 * Retorna la referencia del json i de la textura del escenari amb id indicada per parametre
	 */
	private static int[] getEscenari(int id) {
		SparseArray<int[]> mapes = new SparseArray<int[]>();
		
		mapes.put(1, new int[]{R.raw.ground,R.drawable.grasstex});
		mapes.put(2, new int[]{R.raw.ground2,R.drawable.ground2tex});
        mapes.put(3, new int[]{R.raw.muntanya,R.drawable.muntanyatex});
		mapes.put(4, new int[]{R.raw.muntanya,R.drawable.muntanyatex});

		return mapes.get(id);
	}
	
	public int id;
    public int numPlayers;
	public boolean bloqueado;
	public int refJson;
	public int refText;
    
    public Escenari(int id, int numPlayers, boolean bloqueado) {
        this.id = id;
        this.numPlayers = numPlayers;
        this.bloqueado = bloqueado;
        int[] e = getEscenari(id);
        this.refJson = e[0];
        this.refText = e[1];
    }
}