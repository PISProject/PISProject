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


import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.ub.pis.R;
import com.ub.pis.activities.models.Escenari;
import com.ub.pis.game.GameObjectFactory;
import com.ub.pis.game.UserData;
import com.ub.pis.game.World;

/**
 * Clase donde transcurre el renderizado del juego.
 * 
 * @author zenbook
 *
 */

public class GameView extends GLSurfaceView{

	private final Renderer renderer;
	
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        renderer = new com.ub.pis.renderer.renderer.Renderer(World.getScene());
        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);
        setRenderer(renderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public static void loadModels(Context c, Escenari escenari) {
    	World.create(c);
    	GameObjectFactory gof = new GameObjectFactory(c);
        try {
        	World.add(gof.loadTerrain(escenari.refJson, escenari.refText));	
        	gof.loadActor(new UserData("", 0, R.raw.king, R.drawable.kingtex));
        	gof.loadActor(new UserData("", 0, R.raw.vampire3, R.drawable.vampiretex));
			gof.loadActor(new UserData("",0, R.raw.malote2, R.drawable.malotetex));
            gof.loadActor(new UserData("", 0, R.raw.caballero4, R.drawable.knighttexture));
            gof.loadActor(new UserData("", 0, R.raw.mosquito, R.drawable.mosquitotex));

		} catch (FileNotFoundException e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}
