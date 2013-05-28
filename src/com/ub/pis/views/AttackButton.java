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
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import com.ub.pis.R;

import java.util.Timer;
import java.util.TimerTask;

public class AttackButton extends View {

    private Paint contorno;

    private ClipDrawable clip;
    private Drawable image;

    private int centerX;
    private int centerY;
    private int radiusButton;

    private boolean principi;

    private Timer timer;

    private int cooldown;
    private boolean isCooldown;

    private OnClickAttackButtonListener listener;

    public static interface OnClickAttackButtonListener {
        /**
         * Cuando el botón sea apretado dentro de una circumferencia, se llamará a este método.
         *
         */
        public void onClick();
    }

    public AttackButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AttackButton, 0, 0);
        cooldown = ta.getInteger(R.styleable.AttackButton_cooldown, 1000);
        int refDw = ta.getResourceId(R.styleable.AttackButton_abilityImage, 0);
        if(refDw!=0) {
            image = context.getResources().getDrawable(refDw);
        }else{
            image = null;
        }


        contorno = new Paint(Paint.ANTI_ALIAS_FLAG);
        contorno.setColor(Color.YELLOW);
        int con = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
        contorno.setStrokeWidth(con);
        contorno.setStyle(Paint.Style.STROKE);

        clip = (ClipDrawable) getResources().getDrawable(R.anim.clip_cooldown);
        clip.setLevel(10000);

        principi = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        if(principi) {
            centerX = getWidth()/2;
            centerY = getHeight()/2;

            radiusButton = (int) ((getWidth()-contorno.getStrokeWidth())/2);

            int t = (int) Math.sqrt((radiusButton*radiusButton)/2);
            if(image!=null) {
                image.setBounds((getWidth()/2)-t, (getHeight()/2)-t, (getWidth()/2)+t, (getHeight()/2)+t);
            }

            clip.setBounds(centerX-radiusButton,centerY-radiusButton,centerX+radiusButton,centerY+radiusButton);

            principi = false;
        }
        clip.draw(canvas);
        canvas.drawCircle(centerX, centerY, radiusButton, contorno);
        if(image!=null) image.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int posX = (int) event.getX();
        final int posY = (int) event.getY();
        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                double abs = Math.sqrt((posX-centerX)*(posX-centerX)+(posY-centerY)*(posY-centerY));
				/*Si se apretado dentro del radio de la circumferencia podremos avisar al listener*/
                if(abs<=radiusButton && listener != null && !isCooldown) {
                    timer = new Timer();
                    timer.schedule(new CoolDownTask(), 0, 16);
                    listener.onClick();
                }
                return true;
        }
        return true;
    }

    /**
     * Método para setear el listener a escuchar cuando se apreta este botón.
     *
     * @param listener
     */

    public void setOnClickListener(OnClickAttackButtonListener listener) {
        this.listener = listener;
    }

    /**
     * Método donde se puede setear la imagen del boton y está será ajustada a la medida adecuada.
     *
     * @param ref
     */

    public void setImage(int ref) {
        image = getResources().getDrawable(ref);
        principi = true;
        invalidate();
    }

    /**
     * Método para setear el cooldown de una habilidad.
     *
     * @param cooldown
     */
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    private class CoolDownTask extends TimerTask {
        private float cont = 0;
        private float factor;
        public CoolDownTask() {
            cont = 0;
            factor = 160000f/ cooldown;
            isCooldown = true;
        }
        @Override
        public void run() {
            cont += factor; //160000 = 10000(maximum of level) * 16(time to refresh)
            if(cont > 10000) {
                timer.cancel();
                isCooldown = false;
            }
            if(clip.setLevel((int)cont)) clip.invalidateSelf();
            postInvalidate();
        }
    }
}
