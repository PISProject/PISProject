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

import android.util.Log;
import android.util.SparseArray;

import com.ub.pis.clientsupport.Client.OnServerListener;
import com.ub.pis.game.World;

import java.util.ArrayList;

public class GameService {


	/*
	 * Modos de envío
	 */
	private static final int JUEGO_ACABADO = 1;
	private static final int STREAMING = 0;
	private static final int ATAQUE = 2;


	/*
	 * Modelos 3D
	 */
	public static final String KING = "0";
	public static final String VAMPIRO = "1";


	private OnServerListener listener;

	public GameService(OnServerListener listener) {
		this.listener = listener;
	}

	public void parseTrama(String trama) {
		String[] splitted = trama.split("[|]");
		int function = Integer.parseInt(splitted[0]);
		switch (function) {
		case JUEGO_ACABADO:
            if(listener!=null) listener.gameOver();
			break;
		case STREAMING:
			parseGame(splitted[1]);
			break;

		default:
			break;
		}
	}


	private void parseGame(String seq) {
		int myid = Client.getInstance(false).getMyId();
		Player p;
		SparseArray<Player> players = new SparseArray<Player>();
        SparseArray<Integer> actors = World.getActorsFlags();
        ArrayList<String> lists = readLists(seq);


        /**
         *
         * TRAMA STREAMING
         *
         *
         *
         *
         */
        ArrayList<String[]> elemsStreaming = readSequence(lists.get(0));

        Log.i("trama streaming",lists.get(0));
        String[] playerAttribut;

        for (int i = 0; i < elemsStreaming.size(); i++) {//Splitted: <id>,<modelo>,<vida>,<posX>,<posY>*<id>,<modelo>,<vida>,<posX>,<posY>*<id>,<posX>,<posY>*...
            playerAttribut = elemsStreaming.get(i);

            p = new Player();
            p.id = Integer.parseInt(playerAttribut[0]);
            p.nombre = playerAttribut[1];
            p.modelo = Integer.parseInt(playerAttribut[2]);
            p.vida = Integer.parseInt(playerAttribut[3]);
            p.pos = new float[2];
            p.pos[0] = Float.parseFloat(playerAttribut[4]);
            p.pos[1] = Float.parseFloat(playerAttribut[5]);

            /*Añadimos el player en la lista*/
            players.put(p.id, p);


            //VIGILAR, SI NO EXISTE MI PROPIO PERSONAJE, SE TIENE QUE CREAR


            if(p.id == myid) listener.updateMyPlayer(p);
            else{ //Si el player no es el mío...
	            if(actors.get(p.id) != null) { //Si es diferente de null es que el player existe y puedo 'updatearlo'
		            listener.updateOtherPlayer(p);
	            }else { //Si deveulve null es que ese player no está en la lista y hay que crear uno.
	            	listener.createActor(p);
	            }
            }
        }

        //Buscar que actor se ha eliminado y enviar con el listener la id del player eliminado
    	int id;
    	for (int i = 0; i < actors.size(); i++) {
			id = actors.keyAt(i);
			p = players.get(id);

			if(p == null) {
				if(id == myid) listener.removeMyPlayer();
				else listener.removeActor(id);
			}
    	}




        /*
         *
         * TRAMA ATAQUES
         *
         *
         *
         */

        if(lists.size()<2) return;
        //Log.i("trama de ataque", lists.get(1));
        ArrayList<String[]> elemsAtaques = readSequence(lists.get(1));
        String[] attack;
        int uid, attackid;
        for (int i = 0; i < elemsAtaques.size(); i++) {
        	attack = elemsAtaques.get(i);
            
        	attackid = Integer.parseInt(attack[0]);
        	uid = Integer.parseInt(attack[1]);
			listener.attack(uid, attackid);
		}
    }



	private ArrayList<String[]> readSequence(String seq) {
		ArrayList<String[]> out = new ArrayList<String[]>();
		String[] splitted = seq.split("[*]");
		String[] elem;
		for (int i = 0; i < splitted.length; i++) {
			elem = splitted[i].split("[,]");
			out.add(elem);
		}
		return out;
	}

	private ArrayList<String> readLists(String seq) {
		String[] splitted = seq.split("[/]");
		ArrayList<String> out = new ArrayList<String>();
		for (String elem : splitted) {
			out.add(elem);
		}
		return out;
	}

}
