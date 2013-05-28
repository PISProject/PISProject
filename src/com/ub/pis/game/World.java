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

package com.ub.pis.game;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;

import android.content.Context;
import android.util.SparseArray;

import com.ub.pis.clientsupport.Client;
import com.ub.pis.renderer.scene.Scene;
import com.ub.pis.views.GameView;

public class World {
	
	private static Scene scene;
	
	private static Terrain terrain;
	private static ConcurrentHashMap<Integer,Actor> actors;
	private static SparseArray<Integer> actorsFlag;
	private static Actor player;
	private static GameObjectFactory gof;
	
	public static void create(Context c) {
		scene = new Scene(new FixedCamera());
		actors = new ConcurrentHashMap<Integer,Actor>();
		actorsFlag = new SparseArray<Integer>();
		gof = new GameObjectFactory(c);
	}
	
	public static void destroy() {
		GameObjectFactory.destroy();
	}
	
	public static Scene getScene() {
		return scene;
	}

	
	public static void add(final UserData user, final GameView gameView) {
		actorsFlag.put(user.id, 0);
		new Thread(new Runnable() {
			@Override
			public void run() {
				gameView.queueEvent(new Runnable() {
						@Override
						public void run() {
							try {
								Actor actor = gof.loadActor(user);
								scene.add(actor.getModel());
								scene.add(actor.getTextModel());
								scene.add(actor.getProgressBar());
								actors.put(actor.getId(), actor);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
				});
			}
		}).start();
	}

	public static void add(Terrain terrain) {
        World.terrain = terrain;
        scene.add(terrain.getModel());
	}
	
	public static void remove(int id) {
		actorsFlag.remove(id);
		Actor a = actors.get(id);
		scene.remove(a.getTextModel());
		scene.remove(a.getProgressBar());
		scene.remove(a.getModel());
	}
	
	public static void removeMyPlayer() {
		int id = Client.getInstance(false).getMyId();
		actorsFlag.remove(id);
		Actor a = actors.get(id);
		scene.remove(a.getTextModel());
		scene.remove(a.getProgressBar());
		scene.remove(a.getModel());
		player = null;
	}
	
	public static void addPlayer(final UserData user, GameView gameView) {
		actorsFlag.put(user.id, 0);
		gameView.queueEvent(new Runnable() {
			@Override
			public void run() {
				try {
					Actor actor = gof.loadActor(user);
					scene.add(actor.getModel());
					scene.add(actor.getTextModel());
					scene.add(actor.getProgressBar());
					actors.put(actor.getId(), actor);
					player = actor;
				} catch (JSONException e) {}
				catch (IOException e) {}
			}
		});
	}
	
	public static Actor getPlayer() {
		return player;
	}
	
	public static ConcurrentHashMap<Integer,Actor> getActors() {
		return actors;
	}
	
	public static SparseArray<Integer> getActorsFlags() {
		return actorsFlag;
	}
	
	public static Terrain getTerrain() {
		return terrain;
	}
}
