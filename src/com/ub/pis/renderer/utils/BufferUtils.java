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

package com.ub.pis.renderer.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Clase encargada de la generación de Buffers
 * @author Demenus
 *
 */
public class BufferUtils {
	
	/**
	 * Genera un FloatBuffer a partir de un array de floats
	 * @param lista de floats
	 * @return FloatBuffer resultante
	 */
	public static FloatBuffer getFloatBuffer(float[] list) {
		ByteBuffer bb = ByteBuffer.allocateDirect(list.length * Float.SIZE/8);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		
        fb.put(list);
        fb.position(0);
        return fb;
	}
	
	/**
	 * Genera un IntBuffer a partir de un array de integers
	 * @param lista de integers
	 * @return IntBuffer resultante
	 */
	public static IntBuffer getIntegerBuffer(int[] list) {
		ByteBuffer bb = ByteBuffer.allocateDirect(list.length * Integer.SIZE/8);
		bb.order(ByteOrder.nativeOrder());
		IntBuffer ib = bb.asIntBuffer();
		
		ib.put(list);
		ib.position(0);
		return ib;
	}
	
	/**
	 * Produce un ShortBuffer a partir de una lista de shorts
	 * @param lista de shorts
	 * @return ShortBuffer resultante
	 */
	public static ShortBuffer getShortBuffer(short[] list) {
		ByteBuffer bb = ByteBuffer.allocateDirect(list.length * Short.SIZE/8);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer sb = bb.asShortBuffer();
		
		sb.put(list);
		sb.position(0);
		return sb;
	}
	
}