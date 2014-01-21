package org.neo4j.batchimport.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateToTimeUnits {
	
	public static long getTimeMillis(String date) throws ParseException
	{
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date d = sdf.parse(date);	    
	    	//long timestamp = d.getTime();	    
	    Calendar c = Calendar.getInstance();
	    c.setTime(d);
	    long timeMillis = c.getTimeInMillis();

	    return timeMillis;
	}
	
	
	public static String getDate(long timemillis) throws ParseException 
	{
		String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(timemillis));
		return date;
	}
	
	
	
	public static long getTimeStamp(String date) throws ParseException
	{
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date d = sdf.parse(date);	    
	    long timeStamp = d.getTime();	    
	    return timeStamp;
	}
	
	
	
	/**
	 * Get Translation TEST
	 * @param args
	 */
	public static void main(String [] args){
		
		 String date = "27/12/2013";
		 try {
			long timeMillis = DateToTimeUnits.getTimeMillis(date);
			long timeStamp = DateToTimeUnits.getTimeStamp(date);
			System.out.println( "DATE: " + date + " to timeMillis :" +  timeMillis + " to timeStamp :" +  timeStamp);
			System.out.println( timeMillis + "\n" +  timeStamp);
			System.out.println( "Back to DATE again: " + DateToTimeUnits.getDate(timeMillis));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	

}
