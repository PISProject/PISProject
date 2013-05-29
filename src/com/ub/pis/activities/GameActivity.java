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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import com.ub.pis.R;
import com.ub.pis.clientsupport.Client;
import com.ub.pis.clientsupport.Client.OnServerListener;
import com.ub.pis.clientsupport.Player;
import com.ub.pis.fragments.PauseFragment;
import com.ub.pis.game.Actor;
import com.ub.pis.game.UserData;
import com.ub.pis.game.World;
import com.ub.pis.views.AttackButton;
import com.ub.pis.views.AttackButton.OnClickAttackButtonListener;
import com.ub.pis.views.GameView;
import com.ub.pis.views.JoystickView;
import com.ub.pis.views.JoystickView.OnJoystickChangeListener;
import com.ub.pis.views.MyLifeProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends BaseActivity implements OnServerListener{

    private JoystickView joystick;
	private AttackButton attckButton;
	//private AttackButton[] spells;
	private Button pauseButton;
	//private TextView stage;
	private MyLifeProgressBar lifeProgress;
	private GameView gameView;
	
	private Timer timer;
	
	private Client c;
	
	private int angle;
	
	private int cont = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_activity);
		
		joystick = (JoystickView) findViewById(R.id.joystickView);
		attckButton = (AttackButton) findViewById(R.id.buttonatck);
		pauseButton = (Button) findViewById(R.id.pausebutton);
		gameView = (GameView) findViewById(R.id.game_view);
		lifeProgress = (MyLifeProgressBar) findViewById(R.id.life_progress);
        //spells = new AttackButton[3];
        /*Les habilitats van de dreta a esquerra respecte com es veuen en pantalla*/
        /*
		int[] refs = {R.id.ability1, R.id.ability2, R.id.ability3};
		for (int i = 0; i < spells.length; i++) {
			spells[i] = (AttackButton) findViewById(refs[i]);
		}*/
        //stage = (TextView) findViewById(R.id.stageView);

		c = Client.getInstance(false);
		
		/*ArrayList<UserData> actors = (ArrayList<UserData>) getIntent().getExtras().getSerializable("actors");
		for (int i = 0; i < actors.size(); i++) {
			if(c.getMyId() == actors.get(i).id) World.addPlayer(actors.get(i), gameView);
			else World.add(actors.get(i), gameView);
		}*/

        /*Damos inicio al thread que leerá del Server todos los datos relacionados con el mapa*/
        c.startThreadGame(this);
		
		lifeProgress.setProgress(100);

		attckButton.setImage(R.drawable.espada2);
		
		/*Carregar aquÃ­ les imatges de les tres habilitats que tÃ© un player*/
		/*Cada player tÃ© habilitats diferents*/
        /*
		spells[0].setImage(R.drawable.ojo);
		spells[1].setImage(R.drawable.fletxa_circular);
		spells[2].setImage(R.drawable.mort);*/
		
		attckButton.setOnClickListener(new OnClickAttackButtonListener() {
			@Override
			public void onClick() {
				//World.getPlayer().play("Attack");
				new Thread(new Runnable() {
					@Override
					public void run() {
						c.attack();
					}
				}).start();
				//Toast.makeText(getApplicationContext(), "button pressed", Toast.LENGTH_SHORT).show();
			}
		});
        /*
		for (int i = 0; i < spells.length; i++) {
			spells[i].setOnClickListener(new OnClickAttackButtonListener() {
				@Override
				public void onClick() {
					//Toast.makeText(getApplicationContext(), "spell pressed", Toast.LENGTH_SHORT).show();
				}
			});
		}*/
		
		joystick.setOnJoystickListener(new OnJoystickChangeListener() {
			@Override
			public void onValueChanged(int ang) {
				angle = ang;
				//stage.setText(ang+"");
			}

			@Override
			public void onJoystickDown() {
				timer = new Timer();
				timer.schedule(new MoveTask(), 0, 100);
			}

			@Override
			public void onJoystickUp() {
				timer.cancel();
			}
		});
		
		pauseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pause();
			}
		});
		
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        pause();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	public void pause() {
		FragmentManager fm = getSupportFragmentManager();
        Fragment editor = fm.findFragmentByTag("editor");
        FragmentTransaction ft = fm.beginTransaction();
        if (editor == null) {
        	ft.add(R.id.contenedorfragment, new PauseFragment(),"editor");
        }else{
        	ft.remove(editor);
        }
        ft.commit();
	}
	
	/*TimerTask encarregat de donar l'ordre de moure's al servidor*/
	
	private class MoveTask extends TimerTask {
		@Override
		public void run() {
			c.moveTo(angle);
		}
	}

	/*Funcions del server*/
	@Override
	public void updateMyPlayer(final Player p){
		lifeProgress.setProgress(p.vida);
        Integer b = World.getActorsFlags().get(p.id);
        if(b==null) {
            int[] model = Player.getModel(p.modelo);
            World.addPlayer(new UserData(p.nombre, p.id,model[0],model[1]),gameView);
        }else{
            gameView.queueEvent(new Runnable() {
                @Override
                public void run() {
                    Actor a = World.getPlayer();
                    a.moveTo(p.pos[0], p.pos[1]);
                    a.setLife(p.vida);
                }
            });
        }
	}

	@Override
	public void updateOtherPlayer(final Player p) {
		gameView.queueEvent(new Runnable() {
			@Override
			public void run() {
				Actor a = World.getActors().get(p.id);
				if(a!=null) {
					a.moveTo(p.pos[0], p.pos[1]);
					a.setLife(p.vida);
				}
			}
		});
	}

	@Override
	public void createActor(final Player p) {
		Log.i("Actor creado con id: ", p.id+"");
        int[] model = Player.getModel(p.modelo);
        World.add(new UserData(p.nombre, p.id, model[0], model[1]), gameView);
	}
	
	@Override
	public void removeActor(final int id) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), "Player "+id+" se ha desconectado", Toast.LENGTH_SHORT).show();
			}
		});
		gameView.queueEvent(new Runnable() {
			@Override
			public void run() {
				World.remove(id);
			}
		});
	}


	@Override
	public void removeMyPlayer() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), "Has muerto", Toast.LENGTH_SHORT).show();
                lifeProgress.setProgress(0);
			}
		});
		
		gameView.queueEvent(new Runnable() {
			@Override
			public void run() {
				World.removeMyPlayer();
			}
		});
	}

	@Override
	public void attack(final int uid, final int attackid) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Actor a = World.getActors().get(uid);
				if(a != null) a.play("Attack");
			}
		});

	}
}