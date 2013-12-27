package org.neo4j.batchimport.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataToTimeMillis {
	
	public static long getTimeMillis(String date) throws ParseException
	{
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date d = sdf.parse(date);
	    Calendar c = Calendar.getInstance();
	    c.setTime(d);
	    long timeStamp = c.getTimeInMillis();
	    return timeStamp;
	}
	
	public static String getDate(long timemillis) throws ParseException 
	{
		String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(timemillis));
		return date;
	}
	
	
	
	/**
	 * Get Translation TEST
	 * @param args
	 */
	public static void main(String [] args){
		
		 String date = "27/12/2013";
		 try {
			long timeMillis = DataToTimeMillis.getTimeMillis(date);
			System.out.println( "DATE: " + date + " to timeMillis :" +  timeMillis);
			System.out.println( "Back to DATE again: " + DataToTimeMillis.getDate(timeMillis));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	

}
