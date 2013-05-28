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

import android.os.Bundle;
import android.view.Window;

import com.ub.pis.R;
import com.ub.pis.activities.models.StageStats;
import com.ub.pis.views.MyTableLayout;

/**
 * Actividad para visualizar las máximas puntuaciones realizadas.
 * 
 * @author zenbook
 *
 */

public class HighScoreActivity extends BaseActivity {


		private MyTableLayout table;
		private String[] stages_names;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_high_score);
			
			table = (MyTableLayout) findViewById(R.id.tableLayout1);
			stages_names = getResources().getStringArray(R.array.llista_escenaris);
			StageStats[] stages = {
					new StageStats(stages_names[0], 134567878, 342345),
					new StageStats(stages_names[1], 1244, 342),
					new StageStats(stages_names[2], 897, 134),
					new StageStats(stages_names[3], 342, 87)
			};
			String[] headers = {
					getString(R.string.highscores_escenaris),
					getString(R.string.highscores_puntuacio),
					getString(R.string.highscores_monedes)
			};
			table.setTable(headers, stages);
			
		}


	}