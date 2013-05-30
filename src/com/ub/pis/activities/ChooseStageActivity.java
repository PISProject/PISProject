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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.ub.pis.R;
import com.ub.pis.activities.adapters.EscenariAdapter;
import com.ub.pis.activities.models.Escenari;
import com.ub.pis.clientsupport.Client;
import com.ub.pis.dialogs.LoadingDialog;
import com.ub.pis.game.UserData;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Activity donde se escoge el escenario al cual se quiere jugar.
 * 
 * @author zenbook
 *
 */

public class ChooseStageActivity extends BaseActivity {
	private ListView listView1;
	
	private Escenari[] data;
	
	private boolean isInQueue;
    private Context c;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_stage);
       
        listView1 = (ListView)findViewById(R.id.list);

        isInQueue = false;

        c = this;

        
        
        listView1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View arg1, final int position, long arg3) {
                if (data[position].bloqueado)
                    Toast.makeText(getApplicationContext(), "Escenario bloqueado", Toast.LENGTH_SHORT).show();
                else {
                    if (isInQueue) return;
                    isInQueue = true;
                    final LoadingDialog dialog = new LoadingDialog();
                    dialog.show(getSupportFragmentManager(), "fragment_loading_dialog");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Client c = Client.getInstance(false);
                            if (c != null) {
                                ArrayList<UserData> actors = c.joinQueue(data[position].id);
                                isInQueue = false;
                                if (actors == null) {
                                    dialog.dismiss();
                                    return;
                                }
                                Intent i = new Intent(getApplicationContext(), LoadingGameActivity.class);
                                i.putExtra("actors", (Serializable) actors);
                                i.putExtra("escenari", (Serializable) data[position]);
                                startActivity(i);
                                finish();
                            }
                        }
                    }).start();
	            		/*iniciarActivity(LoadingGameActivity.class);
	            		finish();*/
                }
            }
        });

        new Thread(new Runnable() {
			@Override
			public void run() {
				Client c = Client.getInstance(false);
				if(c==null) return;
				data = c.getEscenaris();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(data != null) {
							EscenariAdapter adapter = new EscenariAdapter(getApplicationContext(), R.layout.listview_item_row, data);
					        listView1.setAdapter(adapter);
						}else{
							Toast.makeText(getApplicationContext(), "Error con el servidor", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		}).start();
        
    }
}