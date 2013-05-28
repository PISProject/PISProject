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

package com.ub.pis.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class MainPreferences {
    private static final String NAME = "MainPreferences";
    public static final String FIRST_TIME_APP = "firsttime";
    public static final String MUSIC = "music";
    public static final String SOUND = "sound";
    public static final String VOLUMEN_MUSIC = "volum_music";
    public static final String VOLUMEN_SOUND = "volum_sound";
    
    
    private SharedPreferences activityPreferences;
    private SharedPreferences.Editor editor;
    
    public MainPreferences(Context context) {
        activityPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    	editor = activityPreferences.edit();
    }
    
    public String getString(String key, String def) {
        return activityPreferences.getString(key, def);
    }
    
    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    
    public boolean getBoolean(String key, boolean def) {
        return activityPreferences.getBoolean(key, def);
    }
    
    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    
    public int getInt(String key, int def) {
        return activityPreferences.getInt(key, def);
    }
    
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }
    
    public void removeTag(String key) {
    	editor.remove(key);
    	editor.commit();
    }
}
