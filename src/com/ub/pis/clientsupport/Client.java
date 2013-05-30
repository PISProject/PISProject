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

import com.ub.pis.activities.models.Escenari;
import com.ub.pis.activities.models.PlayerStats;
import com.ub.pis.game.UserData;

import java.util.ArrayList;

public class Client {
	
	private static Client CLIENT;
	
	private int myId;
    private ThreadGame t_game;
	
	/**
	 * Función estática que hace referencia a un objeto de tipo Client.
	 * 
	 * Pasar un:
	 * 		- true: En caso que no exista el objeto, la función te crea uno.
	 * 		- false: En caso que no exista el objeto, la función te devuelve el valor actual de Cliente.
	 * 
	 * @param createIfNotExist
	 * @return
	 */
	
    public static synchronized Client getInstance(boolean createIfNotExist) {
    	if (CLIENT == null  && createIfNotExist) CLIENT = new Client();
        return CLIENT;
    }
    
	private Protocol protocol;
    
    public Client() {
        protocol = new Protocol();
    }
    
    public int getMyId() {
    	return myId;
    }
    
    /**
     * En cas que s'hagi logat correctament retornarà un 0, en cas contrari retornarà un número major a 0, especificant l'error:
     * 		Si és 1: No existeix un usuari amb aquest nom d'usuari i contrasenya.
     * 		Si és 2: El nom i contrasenya és correcte, però no pot jugar degut a que aquest compte ja està en ús actualment.
     * 
     * @param user
     * @param pass
     * @return boolean
     */
    
    public int login(String user, String pass) {
    	int state = protocol.login(user, pass);
    	if(state <= 0) close();
    	else myId = state;
    	return state;
    }
    
    /**
     * Retornarà 0 en cas d'exit i un número major a 0 especificant l'error:
     * 		Si és 1: Especifica que l'error està en que aquest usuari ja està registrat.
     * 		Si és 2: Especifica que ja existeix un usuari amb el correu especificat (Implementació més endavant.)
     * 		Si és 3: ...
     * 
     * @param user
     * @param pass
     * @param correo
     * @return estado
     */
    
    public int registrarse(String user, String pass, String correo) {
    	/*
    	 * Tengo hecha una implementación en la cual cada vez que llama a registrarse,
    	 * al finalizar esta función, se cerrará la conexión por seguridad y ahorro de energía
    	 */
    	int state = protocol.registrarse(user, pass, correo);
    	close();
    	return state;
    }
    
    public Escenari[] getEscenaris() {
    	Escenari[] data;
    	ArrayList<String[]> lista = protocol.getEscenaris();
    	if(lista == null) return null;
    	data = new Escenari[lista.size()];
    	int id, numPlayer;
    	for (int i = 0; i < data.length; i++) {
    		id = Integer.parseInt(lista.get(i)[0]);
    		numPlayer = Integer.parseInt(lista.get(i)[1]);
    		
			data[i] = new Escenari(id, numPlayer, false);
		}
    	return data;
    }
    
    /**
     * Mètode que se queda esperando a que le responda el servidor.
     * Si devuelve 'true' es que puede empezar la partida.
     * Si devuelve 'false' no podrá empezar la partida.
     * 
     * @return boolean
     */
    
    public ArrayList<UserData> joinQueue(int id) {
        return protocol.joinQueue(id);
    }
    
    public void stopWaiting() {
        protocol.quitQueue();
    }
    
    public boolean readyToStart() {
        boolean res;
        res = protocol.readyToStart();
        return res;
    }
    
    public void startThreadGame(OnServerListener listener) {
    	/*Activo el Thread que estará leyendo constantemente del servidor*/
    	protocol.setListener(listener);
        t_game = new ThreadGame(protocol);
        t_game.start();
    }
    
    public void moveTo(int angulo){
        protocol.moveTo(angulo);
    }
    
    public void attack() {
    	protocol.attack();
    }
    
    public boolean isConnected() {
    	return protocol.isConnected();
    }
    
    public void close() {
        protocol.close();
        CLIENT = null;
    }

    public void closeGameThread() {
        t_game.closeThread();
    }

    

    public interface OnServerListener {
		public void updateMyPlayer(Player p);
		public void updateOtherPlayer(Player p);
		public void createActor(Player p);
		public void removeActor(int id);
		public void removeMyPlayer();
		public void attack(int uid, int attackid);
        public void gameOver(boolean victory, ArrayList<PlayerStats> stats);
    }
    
    
    
}