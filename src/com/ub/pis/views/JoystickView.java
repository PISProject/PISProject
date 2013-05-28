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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Joystick customitzat per al tipus de aplicació que volem fer.
 * 
 * @author zenbook
 *
 */

public class JoystickView extends View {

	private Paint baseJoystick;
	private Paint palancaJoystick;

	private int baseRadius;
	private int palancaRadius;

	private int padding;

	private Rect rectLimit;
	private Rect rectSupEsquerra;
	private Rect rectSupMig;
	private Rect rectSupDreta;
	private Rect rectCentreEsquerra;
	private Rect rectCentreDreta;
	private Rect rectInfEsquerra;
	private Rect rectInfMig;
	private Rect rectInfDreta;
	
	private boolean isMove;
	private boolean isMoveAnt;
	
	private float x;
	private float y;
	private float centerX;
	private float centerY;
	public static double[] baseVector; // Vector base que parteix desde la dreta
									// v(0, -baseRadius) tenint el origen de
									// coordenades al centre de la
									// circumferencia.

	private OnJoystickChangeListener listener;

	private boolean principi;

	public interface OnJoystickChangeListener {
		/**
		 * Se pasa por parámetro el angulo en enterode 0 a 360.
		 * 
		 * @param angulo
		 */
		public void onValueChanged(int angulo);
		/**
		 * Se pasa por parámetro una x i una y con valor del -1 al 1
		 * 
		 * @param x
		 * @param y
		 */
		//public void onValueChanged(float x, float y);
		/**
		 * Se llamará a este evento cuando el joystick empiece a moverse
		 * 
		 */
		public void onJoystickDown();
		/**
		 * Se llamará a este evento cuando se haya dejado de mover el joystick
		 * 
		 */
		public void onJoystickUp();
	}

	public JoystickView(Context context) {
		super(context);
		initJoystick();
	}

	public JoystickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initJoystick();
	}

	public JoystickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initJoystick();
	}

	private void initJoystick() {
		setFocusable(true);
		
		baseJoystick = new Paint(Paint.ANTI_ALIAS_FLAG);
		int con = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
		baseJoystick.setStrokeWidth(con);
		baseJoystick.setStyle(Paint.Style.STROKE);
		baseJoystick.setAlpha(0);

		palancaJoystick = new Paint(Paint.ANTI_ALIAS_FLAG);
		palancaJoystick.setColor(Color.YELLOW);
		con = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
		palancaJoystick.setStrokeWidth(con);
		palancaJoystick.setStyle(Paint.Style.STROKE);
		palancaJoystick.setAlpha(128);
		
		baseVector = new double[2];

		principi = true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		if (principi) { //Només entrará el primer cop que el joystick sigui inicialitzat
			baseRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
			palancaRadius = (int) (baseRadius / 1.75);

			int maxWidth = getWidth() - baseRadius - palancaRadius;
			int maxHeight = getHeight();
			if (baseRadius + palancaRadius > maxWidth || baseRadius + palancaRadius > maxHeight) {
				baseRadius = maxWidth<maxHeight  ?  maxWidth : maxHeight;
				palancaRadius = (int) (baseRadius / 1.75);
			}

			padding = palancaRadius;

			rectLimit = new Rect(baseRadius + padding, baseRadius + padding,
					getWidth() - baseRadius - padding, getHeight() - baseRadius
							- padding);

			rectSupEsquerra = new Rect(0, 0, rectLimit.left, rectLimit.top);
			rectSupMig = new Rect(rectLimit.left, 0, rectLimit.right, rectLimit.top);
			rectSupDreta = new Rect(rectLimit.right, 0, getWidth(),	rectLimit.top);
			rectCentreEsquerra = new Rect(0, rectLimit.left, rectLimit.left, rectLimit.bottom);
			rectCentreDreta = new Rect(rectLimit.right, rectLimit.top, getWidth(), rectLimit.bottom);
			rectInfEsquerra = new Rect(0, rectLimit.bottom, rectLimit.left, getHeight());
			rectInfMig = new Rect(rectLimit.left, rectLimit.bottom, rectLimit.right, getHeight());
			rectInfDreta = new Rect(rectLimit.right, rectLimit.bottom, getWidth(), getHeight());

			x = baseRadius;// x de la cantonada esquerra inferior
			y = getHeight() - baseRadius;// y de la cantonada esquerra inferior

			baseVector[0] = 0;
			baseVector[1] = -baseRadius;
			
			int[] colores = new int[] {Color.parseColor("#F7FE2E"), Color.parseColor("#5E610B")};
			//baseJoystick.setShader(new LinearGradient(centerX-baseRadius, centerY-baseRadius, centerX+baseRadius, centerY+baseRadius, colores,null, Shader.TileMode.CLAMP));
			baseJoystick.setShader(new RadialGradient(getWidth()/2, getHeight()/2, getWidth()/2, colores, null, Shader.TileMode.CLAMP));

			principi = false;
		}
		
		canvas.drawCircle(centerX, centerY, baseRadius, baseJoystick);
		canvas.drawCircle(x, y, palancaRadius, palancaJoystick);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		final int posX = (int) event.getX();
		final int posY = (int) event.getY();
		
		final int action = event.getAction();
		switch (action & MotionEvent.ACTION_MASK) {
		
			case MotionEvent.ACTION_MOVE:
				procesarMovimientJoystick(posX, posY);
				return true;
			case MotionEvent.ACTION_DOWN:
				if(rectLimit.contains(posX, posY) ) {
					centerX = posX;
					centerY = posY;
				}else{
					if(rectSupEsquerra.contains(posX, posY)){
						centerX = rectLimit.left;
						centerY = rectLimit.top;
					}else if(rectSupMig.contains(posX, posY)) {
						centerX = posX;
						centerY = rectLimit.top;
					}else if(rectSupDreta.contains(posX, posY)) {
						centerX = rectLimit.right;
						centerY = rectLimit.top;
					}else if(rectCentreEsquerra.contains(posX, posY)) {
						centerX = rectLimit.left;
						centerY = posY;
					}else if(rectCentreDreta.contains(posX, posY)) {
						centerX = rectLimit.right;
						centerY = posY;
					}else if(rectInfEsquerra.contains(posX, posY)) {
						centerX = rectLimit.left;
						centerY = rectLimit.bottom;
					}else if(rectInfMig.contains(posX, posY)) {
						centerX = posX;
						centerY = rectLimit.bottom;
					}else if(rectInfDreta.contains(posX, posY)) {
						centerX = rectLimit.right;
						centerY = rectLimit.bottom;
					}
				}
				baseJoystick.setAlpha(128);
				procesarMovimientJoystick(posX, posY);
				return true;
			case MotionEvent.ACTION_UP:
				baseJoystick.setAlpha(0);
				x = baseRadius;// x de la cantonada esquerra inferior
				y = getHeight()-baseRadius;//y de la cantonada esquerra inferior
				invalidate(); //això crida a l'onDraw per a refrescar la pantalla
				isMoveAnt = isMove;
				isMove = false;
				if(listener != null && isMove != isMoveAnt) {
					listener.onJoystickUp();
				}
				return true;
			default:
				break;
		}
		return false;
	}

	/**
	 * Función que controla el movimiento del joystick
	 * 
	 * @param posX
	 * @param posY
	 */
	private void procesarMovimientJoystick(float posX, float posY) {
		double abs = Math.sqrt((posX - centerX) * (posX - centerX) + (posY - centerY) * (posY - centerY));
		isMoveAnt = isMove;
		if (abs > baseRadius-palancaRadius) {
			isMove = true;
			if(listener != null  && isMove != isMoveAnt) { //Si hay un cambio de estado...
				listener.onJoystickDown();
			}
			x = (int) ((posX - centerX) * baseRadius / abs + centerX);
			y = (int) ((posY - centerY) * baseRadius / abs + centerY);
			
			//float xRel = (x-centerX)/baseRadius;
			//float yRel = (y-centerY)/baseRadius;
			
			double[] w = { (x - centerX), (y - centerY) };
			double angulo = 0;
			for (int i = 0; i < w.length; i++) {
				angulo += baseVector[i] * w[i];
			}
			angulo /= baseRadius; // aquest és el modul del vector base(r,0)
			angulo /= Math.sqrt(w[0] * w[0] + w[1] * w[1]);
			angulo = Math.acos(angulo);
			angulo = Math.toDegrees(angulo);
			if (w[0] > 0) angulo = 360 - angulo;
			if (listener != null) {
				int a = (int) angulo;
				a = a-90;
				listener.onValueChanged(a);
				//listener.onValueChanged(xRel, yRel);
			}
		} else {
			isMove = false;
			if(listener != null && isMove != isMoveAnt) {
				listener.onJoystickUp();
			}
			x = centerX;
			y = centerY;
		}
		
		invalidate();
	}
	/**
	 * Setter listener que te permite manejar funcionalidades que el joystick te proporciona.
	 * 
	 * @param listener
	 * @param millis
	 */
	public void setOnJoystickListener(OnJoystickChangeListener listener) {
		this.listener = listener;
	}
	
}
