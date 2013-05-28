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
package com.ub.pis.renderer.text;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import com.ub.pis.renderer.opengl.Texture;
import com.ub.pis.renderer.primitives.Sprite;

import static android.graphics.Color.WHITE;

public class Text extends Sprite{
	
	private final static int TEXT_SIZE = 16;
	private static int bitmapWidth = 128;
	private static int bitmapHeight = 32;

	public Text(String str) {
		super(genTexture(str));
	}
	
	public void setText(String str) {
		Texture tex = genTexture(str);
		//tex.initialize();
		this.setTexture(tex);
	}
	
	private static Texture genTexture(String str) {
		Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		bitmap.eraseColor(0);
		Paint fillPaint = new Paint();
		fillPaint.setColor(WHITE);
		fillPaint.setAntiAlias(true);
		fillPaint.setTextSize(TEXT_SIZE);
		fillPaint.setStyle(Style.FILL);
		canvas.drawText(str, 20, TEXT_SIZE, fillPaint);
		
		return new Texture(bitmap);
	}
}
