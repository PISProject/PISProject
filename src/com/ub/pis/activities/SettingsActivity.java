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

package com.ub.pis.activities;

import java.util.HashMap;
import java.util.Locale;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.ub.pis.R;
import com.ub.pis.sharedpreferences.MainPreferences;

/**
 * Actividad donde se controla el idioma del juego y más adelante la música y sonido.
 * 
 * @author zenbook
 *
 */

public class SettingsActivity extends BaseActivity {
	private static Locale LOCALE;
	private Button[] idiomes;
	private Button musicBtn;
	private Button soundBtn;
	private HashMap<Integer, String> strIdiomes;
	
	private MainPreferences pref;
	private boolean music, sound;
	private SeekBar volumenBarra;
	private SeekBar sonidoBarra;
	private Button developersBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LOCALE = Locale.getDefault();
		setResult(RESULT_CANCELED);
		initActivity();
	}
	
	
	public void initActivity() {
		
		setContentView(R.layout.activity_settings);
		
		musicBtn = (Button) findViewById(R.id.music_button);
		soundBtn = (Button) findViewById(R.id.sound_button);
		developersBtn = (Button) findViewById(R.id.developersButton);
		pref = new MainPreferences(getApplicationContext());
		music = pref.getBoolean(MainPreferences.MUSIC, true);
		musicBtn.setText(music?"On":"Off");
		sound = pref.getBoolean(MainPreferences.SOUND, true);
		soundBtn.setText(sound?"On":"Off");
		volumenBarra = (SeekBar) findViewById(R.id.volumenMusicaBar);
		sonidoBarra = (SeekBar) findViewById(R.id.volumenSonidoBar);
		 
		Button cat_button = (Button)findViewById(R.id.cat_buto);
		Button esp_button = (Button)findViewById(R.id.esp_buto);
		Button eng_button = (Button)findViewById(R.id.eng_buto);
		idiomes = new Button[]{cat_button, esp_button, eng_button};
		strIdiomes = new HashMap<Integer, String>();
		strIdiomes.put(R.id.cat_buto, "ca");
		strIdiomes.put(R.id.esp_buto, "es");
		strIdiomes.put(R.id.eng_buto, "en");
		
		for (Button b: idiomes) {
			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					changeLanguage(strIdiomes.get(v.getId()));
			    	initActivity();
				}
			});
		}
		volumenBarra.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			
				if (fromUser){
					mServ.setVolumen(progress);
					seekBar.setProgress(progress);
				}
			}
		});
		sonidoBarra.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			
				if (fromUser){
					pref.putInt(MainPreferences.VOLUMEN_SOUND, progress);
					seekBar.setProgress(progress);
				}
			}
		});
		sonidoBarra.setProgress(pref.getInt(MainPreferences.VOLUMEN_SOUND, 100));//sonido y musica por defecto al valor inicial MainPreferences.Volumen_music guardado en las preferencias por si se cierra la app
		volumenBarra.setProgress(pref.getInt(MainPreferences.VOLUMEN_MUSIC, 100));
		musicBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				musicBtn.setText(music?"Off":"On");
				music = !music; 
				if(music) mServ.resumeMusic();
				else mServ.pauseMusic();
				pref.putBoolean(MainPreferences.MUSIC, music);
			}
		});
		
		soundBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				soundBtn.setText(sound?"Off":"On");
				sound = !sound; 
				pref.putBoolean(MainPreferences.SOUND, sound);
			}
		});
		developersBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				iniciarActivity(DevelopersActivity.class);
			}
		});
	}
	
	public void changeLanguage(String language){
		LOCALE = new Locale(language);
	    Locale.setDefault(LOCALE);
	    Configuration config = new Configuration();
	    config.locale = LOCALE; 
	    getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
	    setResult(RESULT_OK);
	}

}
