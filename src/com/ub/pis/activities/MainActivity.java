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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ub.pis.R;
import com.ub.pis.clientsupport.Client;

/**
 * Actividad principal donde aparece el sistema de logeo.
 * 
 * @author zenbook
 *
 */

public class MainActivity extends BaseActivity {
	private TextView textRegister;
	private Button login_button;
	private TextView textForgot;
	
	private EditText mEditTextUser;
	private EditText mEditTextPass;
	
	private ProgressDialog pDialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActivity();
	}
	
	
	private void initActivity() {
		
		setContentView(R.layout.activity_main);
		
		textForgot = (TextView)findViewById(R.id.forgot_text);
		textRegister = (TextView)findViewById(R.id.register_text);
		login_button = (Button)findViewById(R.id.login_buton);
		mEditTextUser = (EditText) findViewById(R.id.user_entrace);
		mEditTextPass = (EditText) findViewById(R.id.password_edittext);
		
		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);
		pDialog.setMessage("Login");
		
		textRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iniciarActivity(RegisterActivity.class);
			}
		});
		textForgot.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				iniciarActivity(ForgotPasswordActivity.class);
			}
		});
		login_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pDialog.show();
				
				final Toast t =Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
				new Thread(new Runnable() {
					@Override
					public void run() {
						//Utilizar un sistema de parseo
						Client c = Client.getInstance(true);
						String user, pass;
						if(c.isConnected()) {
							user = mEditTextUser.getText().toString();
							pass =  mEditTextPass.getText().toString();
                            if(user.equals("") || pass.equals("")){
                                t.setText(R.string.revise_campos);
                                t.show();
                                pDialog.dismiss();
                                return;
                            }
							int state = c.login(user,pass);
							if(state>0) {
								startActivityForResult(new Intent(getApplicationContext(), MenuPrincipalActivity.class), 1);
							}else if(state == -1) {
								t.setText(R.string.incorrect_login);
								t.show();
							}else if(state == -2) {
								t.setText(R.string.user_playing);
								t.show();
							}else if(state == -3) {
								t.setText(R.string.server_error);
								t.show();
							}
						} else{
							t.setText(R.string.server_error);
							t.show();
						}
						pDialog.dismiss();
					}
				}).start();
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	    	showDialogQuit();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	  
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK)initActivity();//si s'ha canviat l'idioma es refresca
		super.onActivityResult(requestCode, resultCode, data);
	}
	
    
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	menu.clear();
    	getMenuInflater().inflate(R.menu.main, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	        case R.id.action_settings:
	        	startActivityForResult(new Intent(getApplicationContext(), SettingsActivity.class), 1); //es cridarà el mètode onActivityResult
	            return true;
	        case R.id.action_developers:
	            iniciarActivity(DevelopersActivity.class);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
    	}
    }
}
