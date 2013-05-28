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
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ub.pis.R;
import com.ub.pis.clientsupport.Client;
import com.ub.pis.fragments.InfoFragment;
import com.ub.pis.utils.Parser;

/**
 * Actividad donde el usuario se podrá registrar a partir de una serie de campos a rellenar.
 * 
 * @author zenbook
 *
 */

public class RegisterActivity extends BaseActivity {

	private Button quit_button;
	private Button ok_button;
	private EditText contrasenya;
	private EditText mateixaContrasenya;
	private TextView textErrorMateixaContrasenya;
	private EditText mail;
	private TextView textMailError;
	private EditText usuari;
	private TextView textUserError;
	private TextView textErrorContrasenya;
	private Button info_button;
	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		contrasenya = (EditText)findViewById(R.id.contrasenya);
		mateixaContrasenya = (EditText)findViewById(R.id.mateixa_contrasenya);
		textErrorMateixaContrasenya = (TextView)findViewById(R.id.textContrasenyaMateixa);
		textErrorContrasenya = (TextView)findViewById(R.id.textContrasenya);
		mail = (EditText)findViewById(R.id.mail_input);
		usuari = (EditText)findViewById(R.id.nom_usuari_input);
		textMailError = (TextView)findViewById(R.id.errorMail);
		textUserError = (TextView)findViewById(R.id.errorUser);
		ok_button = (Button)findViewById(R.id.buto_registrarse);
		info_button = (Button)findViewById(R.id.infoButton);
		quit_button = (Button)findViewById(R.id.quit_button);
		
		pDialog = new ProgressDialog(this);
		pDialog.setMessage(getString(R.string.wait_register));
		pDialog.setCancelable(false);
		
		info_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialogInfo();
			}
		});
		
		quit_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialogQuit();
			}
		});
		ok_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//comprovar que no existeixi l'usuari
				//comprovar q la contrasenya sigui prou llarga,segura...
				//comprovacio via mail q existeix el mail
				boolean contrasenyaBenEscrita = false,
						mailCorrecte = false,
						usuariCorrecte = false,
						contrasenyaNoBuida = false,
						contrasenyaMateixaNoBuida = false,
						contrasenyesIguals = false;
				textMailError.setText("");
				textUserError.setText("");
				textErrorMateixaContrasenya.setText("");
				textErrorContrasenya.setText("");
				final String pass = contrasenya.getText().toString(),
						samePass = mateixaContrasenya.getText().toString(),
						mailText = mail.getText().toString(),
						userText = usuari.getText().toString();
						
						
				if (pass.equals("")){
					textErrorContrasenya.setText(R.string.ompleContrasenya);
				}else contrasenyaNoBuida = true;
				if(samePass.equals("")){
					textErrorMateixaContrasenya.setText(R.string.ompleContrasenya);
				}else contrasenyaMateixaNoBuida = true;
				
				if(Parser.isAValidName(pass, Parser.PATTERNUSER) && pass.length()>=5){
					contrasenyaBenEscrita = true;
				}else {
					textErrorContrasenya.setText(R.string.noPass);//formato contraseña incorrecto
					textErrorMateixaContrasenya.setText(R.string.noPass);
				}
				if (!pass.equals(samePass) && contrasenyaBenEscrita){
					textErrorMateixaContrasenya.setText(R.string.contrasenyesDiferents);
				}else contrasenyesIguals = true;
				
				if(mailText.equals("")){
					textMailError.setText(R.string.ompleMail);
				}else if(!Parser.isValidMail(mailText)){
						textMailError.setText(R.string.noMail);
				}else mailCorrecte = true;
				
				if(userText.equals("")){
					textUserError.setText(R.string.ompleUsuari);
				} else if(!Parser.isAValidName(userText, Parser.PATTERNUSER)||Character.isLowerCase(userText.charAt(0))||
						userText.length()<5){
					textUserError.setText(R.string.noUser);
				} else usuariCorrecte = true;
				if(contrasenyesIguals && mailCorrecte && usuariCorrecte && contrasenyaNoBuida && contrasenyaMateixaNoBuida){
					//Una vegada la informació es correcte, s'ho pasem al servidor, per a que ens digui si 
					// es posible registrar-se o no.
					//Enviar aquí al servidor la informació correcte
					final Toast t =Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
					pDialog.show();
					new Thread(new Runnable() {
						@Override
						public void run() {
							Client c = Client.getInstance(true);
							int state = c.registrarse(userText, pass, mailText);
							//Veure els flags amb els que el servidor ens respon i actuar sobre ells.
							switch (state) {
								case 0:
									t.setText(R.string.registered);
									t.show();
									finish();
									break;
								case 1:
									t.setText("Revisa los campos");
									t.show();
									break;
								case 2:
									t.setText("Ha hagut un problema amb el servidor");
									t.show();
									break;
								case -1:
									t.setText("No s'ha pogut conectar amb el servidor");
									t.show();
							}
							pDialog.dismiss();
						}
					}).start();
				}
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
	public void showDialogInfo() { //dafuq???
        InfoFragment infoDialog = new InfoFragment();
        infoDialog.show(getSupportFragmentManager(), "fragment_info");
    }
	
 
	

}
