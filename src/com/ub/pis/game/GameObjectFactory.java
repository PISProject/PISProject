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
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import android.content.Context;
import android.util.SparseArray;

import com.ub.pis.R;
import com.ub.pis.renderer.animation.Action;
import com.ub.pis.renderer.loader.ModelLoader;
import com.ub.pis.renderer.models.AnimatedModel;
import com.ub.pis.renderer.opengl.SkinnedMesh;

public class GameObjectFactory {
	
	private Context context;
	
	private static SparseArray<GameObject> gameObjects = new SparseArray<GameObject>();
	
	public GameObjectFactory(Context context) {
		this.context = context;
	}
	
	public Terrain loadTerrain(int modelId, int textureId) throws JSONException, IOException {
		InputStream modelStream = context.getResources().openRawResource(modelId);
		InputStream textureStream = context.getResources().openRawResource(textureId);
		InputStream vsStream = context.getResources().openRawResource(R.raw.terrainvs);
		InputStream fsStream = context.getResources().openRawResource(R.raw.terrainfs);
		
		ModelLoader loader = new ModelLoader(modelStream, textureStream, vsStream, fsStream);
		return new Terrain(loader.loadModel());
	}
	
	public Actor loadActor(UserData actor) throws JSONException, IOException {
		AnimatedModel m;
		Actor a = (Actor) gameObjects.get(actor.refJson);
		if (a != null) {
			m = (AnimatedModel) gameObjects.get(actor.refJson).getModel();
			return new Actor(new AnimatedModel((SkinnedMesh)m.getMesh(), m.getTexture(), m.getShaderProgram(), copyActions(m)), actor, context);//m.getActions()));	
		} else {
			InputStream modelStream = context.getResources().openRawResource(actor.refJson);
			InputStream textureStream = context.getResources().openRawResource(actor.refTextura);
			InputStream vsStream = context.getResources().openRawResource(R.raw.charactervs);
			InputStream fsStream = context.getResources().openRawResource(R.raw.characterfs);
			
			ModelLoader loader = new ModelLoader(modelStream, textureStream, vsStream, fsStream);
			a = new Actor(loader.loadAnimatedModel(), actor, context);
			gameObjects.put(actor.refJson, a);
			return a;
			//ModelLoader loader = new ModelLoader(context, modelId, textureId, R.raw.charactervs, R.raw.characterfs);
		}
		
	}
	
	private Map<String, Action> copyActions(AnimatedModel m) {
		HashMap<String, Action> copiedActions = new HashMap<String, Action>();
		Set<String> keys = m.getActionsNames();
		for(Iterator<String> it = keys.iterator();it.hasNext(); ) {
			Action a = m.getActions().get(it.next());
			copiedActions.put(a.getName(), new Action(a.getName(), a.getFrameList()));
		}
		return copiedActions;
	}
	
	public static void destroy() {
		gameObjects = new SparseArray<GameObject>();
	}
}


