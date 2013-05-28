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

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ReadFile {
	
	public static String read(InputStream stream) throws IOException {
		Scanner sc = new Scanner(stream);
		String txt = "";
		while (sc.hasNextLine()) {
			txt += sc.nextLine()+"\n";
		}
		stream.reset();
		return txt;
	}

}
