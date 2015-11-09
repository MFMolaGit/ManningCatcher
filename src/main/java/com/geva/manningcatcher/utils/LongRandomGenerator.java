package com.geva.manningcatcher.utils;

import java.util.List;
import java.util.Random;

public class LongRandomGenerator {

	public static Long generateId(List<Long> oldIds) {
		final long LOWER_RANGE = 0; //assign lower range value
		final long UPPER_RANGE = 1000000; //assign upper range value
		Long randomValue;
		boolean exists = false;
		
		do {
			Random random = new Random();
			
			randomValue = LOWER_RANGE + 
	                (long)(random.nextDouble()*(UPPER_RANGE - LOWER_RANGE));
			
			if(oldIds.contains(randomValue)) {
				exists = true;
			}
			
		} while(!exists);
		
		return randomValue;
	}
	
}
