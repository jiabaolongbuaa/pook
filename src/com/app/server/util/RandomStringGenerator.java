package com.app.server.util;

import java.util.Random;

public class RandomStringGenerator {
	public static String get(int length){
		String result ="";
		if(length==0){
			length=6;
		}
		Random random = new Random();
		for (int i=0;i<length;i++){
			int tmp = random.nextInt(10);
			result+=tmp;
		}
		
		return result;
	}
}
