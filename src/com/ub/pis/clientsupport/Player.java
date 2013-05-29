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

package com.ub.pis.clientsupport;

import android.util.SparseArray;
import com.ub.pis.R;

public class Player {

    public static int[] getModel(int id) {
        SparseArray<int[]> models = new SparseArray<int[]>();

        models.put(0, new int[]{R.raw.king,R.drawable.kingtex});
        models.put(1, new int[]{R.raw.vampire3,R.drawable.vampiretex});
        models.put(2, new int[]{R.raw.caballero4,R.drawable.knighttexture});
        models.put(3, new int[]{R.raw.malote2,R.drawable.malotetex});
        models.put(4, new int[]{R.raw.mosquito,R.drawable.mosquitotex});

        return models.get(id);
    }

	public int id;
    public String nombre;
	public int modelo;
	public int vida;
	public float[] pos;
	
	public String toString() {
		return "Player{id = "+id+",modelo = "+modelo+", vida = "+vida+", pos = ["+pos[0]+","+pos[1]+"]}";
	}
}
