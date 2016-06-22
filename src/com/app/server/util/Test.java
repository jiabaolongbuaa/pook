package com.app.server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.*;
import javapns.Push;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.exceptions.InvalidDeviceTokenFormatException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.transmission.PushQueue;

public class Test {
	public static void main(String[] args) throws KeystoreException, InvalidDeviceTokenFormatException{
		
		System.out.println(PatternValidation.isMobileNO("18910830717"));
		return ;
//		Calendar birthday = new GregorianCalendar(1988, 8, 28);//2010年10月12日，month从0开始  
//        Calendar now = Calendar.getInstance();  
//        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);  
//        int month = now.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);  
//        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);  
//        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。  
//        if(day<0){  
//            month -= 1;  
//            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。  
//            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);  
//        }  
//        if(month<0){  
//            month = (month+12)%12;  
//            year--;  
//        }  
//        System.out.println("年龄："+year+"年"+month+"月"+day+"天");  
		
		
/*		
		Date birthday = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			birthday =sdf.parse("1988-08-28");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(BirthdayCalculator.calculate(birthday));
*/		
		
		
		
		
//		 try {
//		        BasicConfigurator.configure();
//		 } catch (Exception e) {
//		 }
////		
//		String keystore = "D:\\littlebird.p12";
//		String password ="littlebird";
//		String token = "6bb7212a6622ab7957bc2351cc41f6d2783870030b29cf02f0e424eeb6541dfb";
//		
//		
//		/* Prepare a simple payload to push */ 
//        PushNotificationPayload payload = PushNotificationPayload.alert("Hello World!");
// 
//
//       /* Decide how many threads you want your queue to use */ 
//        int threads = 30;
// 
//
//       /* Create the queue */ 
//        PushQueue queue = Push.queue(keystore, password, false, threads);
// 
//
//       /* Start the queue (all threads and connections and initiated) */ 
//        queue.start();
// 
//
//       /* Add a notification for the queue to push */ 
//        queue.add(payload, token);
//        queue.start();
//        
//	}
	}
}
