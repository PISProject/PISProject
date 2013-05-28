package com.ub.pis.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.ub.pis.R;

/**
 * Actividad donde el usuario esperará a que haya alguien conectado a una partida.
 * 
 * @author zenbook
 *
 */

public class WaitingActivity extends BaseActivity {

	private TextView waiting_text;
	private View bola1;
	private View bola2;
	private View bola3;
	
	private Boolean start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_waiting);
		waiting_text = (TextView) findViewById(R.id.waiting_text);
		bola1 = findViewById(R.id.bola1);
		bola2 = findViewById(R.id.bola2);
		bola3 = findViewById(R.id.bola3);
		waiting_text.setTextColor(Color.YELLOW);
		start = true;
        //World.create((GLSurfaceView)findViewById(R.id.game_view));
        new Thread(new Runnable() {
			@Override
			public void run() {
				//GameView.loadModels(getApplicationContext());
				synchronized (start) {
					if(start) {
						iniciarActivity(GameActivity.class);
						finish();
					}
				}
			}
		}).start();
	}
	
	@Override
	protected void onDestroy() {
		start = false;
		super.onDestroy();
	}
}
