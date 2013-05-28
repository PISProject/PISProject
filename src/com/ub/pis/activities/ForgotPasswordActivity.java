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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ub.pis.R;
import com.ub.pis.utils.Parser;

/**
 * En caso que el usuario no recuerde la contrasenya saltará a esta actividad.
 * 
 * @author zenbook
 *
 */

public class ForgotPasswordActivity extends BaseActivity {

	private Button quit_button;
	private Button send_button;
	private EditText forgotPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_forgot_password);
		forgotPass = (EditText)findViewById(R.id.forgotPass);
		send_button = (Button)findViewById(R.id.buto_send);
		send_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(Parser.isValidMail(forgotPass.getText().toString())){
					finish();
				}
			}
		});
		quit_button = (Button)findViewById(R.id.quit_button);
		quit_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
 
	

}