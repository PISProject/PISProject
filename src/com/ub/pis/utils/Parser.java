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

package com.ub.pis.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	
	private static final String  EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static final String PATTERNUSER2 = "qwertyuiopasdfghjklÃ±zxcvbnmÃ§"+
                                              "QWERTYUIOPASDFGHJKLÃ‘ZXCVBNMÃ‡"+
                                              "1234567890._";
	public static final String PATTERNUSER = "abcdefghijklmnopqrstuvwxyz"+
            								"ABCDEFGHIJKLMNOPQRSTUVWXYZ"+
            									"1234567890._";
	
	public static boolean isValidMail(final String hex) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	public static boolean isAValidName(String user, String pattern) {
		boolean trobat;
		int j;
		for (int i = 0; i < user.length(); i++) {
		    char c = user.charAt(i);
		    trobat = false;
		    j = 0;
		    while(j<pattern.length() && !trobat) {
		        char c1 = pattern.charAt(j);
		        if(c1==c) {
		            trobat = true;
		        }
		        j++;
		    }
		    if(!trobat) return false;
		}
		return true;
    	}
}
