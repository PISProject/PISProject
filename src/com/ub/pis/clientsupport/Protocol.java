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

import com.ub.pis.R;
import com.ub.pis.clientsupport.Client.OnServerListener;
import com.ub.pis.game.UserData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Protocol {
	
    //////////////////
    //IP & PORT
    //////////////////
    public static final String IP = "81.38.161.107"; //37.14.151.198 81.38.161.107 192.168.43.6
    public static final int PORT = 5050;

    //////////////////
    // OUTPUT
    //////////////////
    public static final String DISCONNECT = "0";
    public static final String JOIN_QUEUE = "1";
    public static final String QUIT_QUEUE = "2";
    public static final String GAME_FOUND = "3";
    public static final String READY_TO_START = "4";
    public static final String MOVE_TO = "5";
    public static final String LOGIN = "6";
    public static final String REGISTER = "7";
    public static final String GET_ESCENARIS = "8";
    public static final String ATTACK = "9";

    //////////////////
    //INPUT
    //////////////////
    // Notificaciones de estado 
    public static final String NOTIFY_GAME_STARTING = "0x60";
    public static final String APPROVED = "0x04";
    public static final String DENIED = "0x05";    

    
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    
    private GameService gameService;
    
    
    public Protocol() { //En caso de android poner: public Protocol(Activity a);
        try {
            socket = new Socket(IP, PORT);
            
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            
        } catch (UnknownHostException ex) {
        	System.out.println("UnknownException");
            //Toast.makeText(a.getBaseContext(),<message>, Toast.LENGTH_SHORT);
        } catch (IOException ex) {
            //Toast.makeText(a.getBaseContext(),<message>, Toast.LENGTH_SHORT);
        	System.out.println("IOException");
        }
        
        gameService = null;
    }
    
    public void setListener(OnServerListener listener) {
    	gameService = new GameService(listener);
    }
 
    public ArrayList<String[]> getEscenaris() { // id, numplayers*id, numplayers*...
    	ArrayList<String[]> esc = new ArrayList<String[]>();
    	
    	 try{
    		//Posar el timeOut
    		 socket.setSoTimeout(6000);
    		 
     		//Enviar al server que quiero empezar
             out.writeUTF(GET_ESCENARIS);
             
             String trama = in.readUTF(); 
             /*Splitting trama*/
             String[] split = trama.split("[*]");
             String[] splitComes;
             for (int i = 0; i < split.length; i++) {
				splitComes = split[i].split("[,]");
				esc.add(splitComes);
			}
             
             //Tornar a timeOut a indefinit
             socket.setSoTimeout(0);
             
         }catch(IOException e){
         	return null;
         }
    	 
         return esc;
    }
    
    /**
     * Esta función envía el server que quiere empezar una partida.
     * Una vez se puede empezar la partida, éste crea una instancia de
     * Game, dónde debe cargar las instancias de los Players de la partida.
     * 
     * @return 
     */
    public ArrayList<UserData> joinQueue(int id) { 
    	ArrayList<UserData> actors = new ArrayList<UserData>();
        try{
    		//Enviar al server que quiero empezar
            out.writeUTF(JOIN_QUEUE + "|" + id); //este 1 significa la id de la partida
            
        	//Esperar con un read lo que me contestará el server
        	//Aquí tengo que recibir la inicialización de la partida(ahora solo recibo mi id)
            String trama = in.readUTF(); //p.e: 1,Kirtash*5,Xavi*94,Albert*123,Aaron 
            /*Splitting trama*/
            String[] split = trama.split("[*]");
            if(split.length == 1) {
            	if(split[0].equals("1")) return null;
            	else {
            		trama = in.readUTF();
            	}
            }
            String[] split2;
            int idPlayer;
            String nombre;
            for (int i = 0; i < split.length; i++) {
				split2 = split[i].split("[,]");
				idPlayer = Integer.parseInt(split2[0]);
				nombre = split2[1];
				actors.add(new UserData(nombre, idPlayer, R.raw.king, R.drawable.kingtex));
            }
        }catch(IOException e){
        	return null;
        }
        return actors;
    }

    /**
     * 
     * Esta es la única función que puede llamar el cliente mientras espera a conectarse.
     * Ésta envía al servidor que quiere dejar de esperar y lo que le devuelva el servidor
     * será leído desde la función 'joinQueue'
     */
    public void quitQueue() {
    	try {
			out.writeUTF(QUIT_QUEUE);
		} catch (IOException e) {
		}
        
        //Lo que me tenga que devolver, lo recibiré en la función 'joinQueue()', porque
        //es la función que está esperando.
    }
    
    
    /**
     * Esta función le comunica al server que ya está preparado para empear la partida.
     * Devolverá un 'true' si puede empezar la partida, en caso contrario, 'false'
     * @return 
     */
    public boolean readyToStart() {
        try{
        	//Enviar al server que ya está listo para empezar a jugar
        	out.writeUTF(READY_TO_START);
        	
        	//Esperar con un read
            int ready = Integer.parseInt(in.readUTF());
            //Entender lo que me ha enviado, si me ha dicho que puedo empezar, devuelvo true
            //sino un 'false'
            if(ready==1) return true;
            else return false;
        }catch(IOException a){
            return false;
        }
    }

    /**
     * Esta función leerá el estado del juego y lo introducirá en el objeto Game.
     * 
     */
    public boolean readMap() {
        try {
        	//Leer lo que me dice el server
            //TODO  here
			String trama  = in.readUTF();
			//Parsear lo que me diga y meterlo en el game object.
			if(gameService != null) gameService.parseTrama(trama);
			//System.out.println("LISTENER: "+listener);
			return true;
		} catch (IOException e) {
			//Se ha cerrado la conexión con el servidor por alguna razón.
			return false;
		}
        
    }

    /**
     * Función que le envía el server el ángulo al cual se moverá.
     * 
     * @param angulo 
     */
    public void moveTo(int angulo) {
        try {
        	//Preparar para enviar al server (no hace falta enviar la uid)
			out.writeUTF(MOVE_TO + "|" + angulo);
		} catch (IOException e) {
			
		}
    }
    
    public void attack() {
    	try {
        	//Preparar para enviar al server (no hace falta enviar la uid)
			out.writeUTF(ATTACK);
		} catch (IOException e) {
			
		}
    }
    
    public int login(String user, String pass) {
    	int state;
    	try {
    		//Indico un TimeOut de 6 segons per a que no es quedi penjat.
			socket.setSoTimeout(6000);
    		//Enviar al server, el usuario y contraseña
        	//La trama enviada serà un estil com això: LOGIN+"|"+user+","+pass
			out.writeUTF(LOGIN + "|" + user + "," + pass);
			
			//El servidor me responde si se ha logueado o no, en caso que me haya logueado correctamente me responde con mi id.
			state = Integer.parseInt(in.readUTF());
			
			//Poso de nou el TimeOut a indefinit
			socket.setSoTimeout(0);
		} catch (IOException e) {
			state = -3;
		}
		return state;
	}

	public int registrarse(String user, String pass, String correo) {
		int state;
		
		try{
			//Indico un TimeOut de 6 segons per a que no es quedi penjat.
			socket.setSoTimeout(6000);
			//Enviar al server, el usuario, la contraseña y el correo
	    	//La trama enviada serà un estil com això: REGISTRARSE+"|"+user+","+pass+","+correo
			out.writeUTF(REGISTER + "|" + user + "," + pass + "," + correo);
			//El servidor me responde indicando el estado de respuesta.
			state = Integer.parseInt(in.readUTF());
		}catch (IOException e) {
			state = -1;
		}
		return state;
	}
    
    public boolean isConnected() {
		if(socket!=null) return socket.isConnected();
		else return false;
	}

    /**
     * Función que se llama cuando se quiere cerrar una conexión de forma segura.
     * 
     */
    
    public void close() {
        try {
            //Enviar al server que cerraré el juego, para que haga los controles pertinentes.
        	out.writeUTF(DISCONNECT);
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
