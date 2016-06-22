package com.app.server.util;

import java.util.Calendar;
import java.util.Date;

public class BirthdayCalculator {
	
	public static int calculate(Date birth){
		if(birth==null){
			return 0;
		}
		Calendar birthday =  Calendar.getInstance(); 
		birthday.setTime(birth);
        Calendar now = Calendar.getInstance();  
        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);  
        int month = now.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);  
        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);  
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。  
        if(day<0){  
            month -= 1;  
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。  
            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);  
        }  
        if(month<0){  
            month = (month+12)%12;  
            year--;  
        }  
       return year;
		
	}

}
