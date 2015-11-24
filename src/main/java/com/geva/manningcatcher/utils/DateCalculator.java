/**
 * 
 */
package com.geva.manningcatcher.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Geva
 *
 */
public class DateCalculator {

	public static Date getCurrentSpainDate() {
		Calendar calendar = Calendar.getInstance();
	      calendar.setTime(new Date());
	      calendar.add(Calendar.HOUR, Constants.HOUR_INCREMENT); //6 horas mas que en espania
	    
	     return calendar.getTime();
	}
	
	public static Date getCurrentServerDate() {
	     return new Date();
	}
}
