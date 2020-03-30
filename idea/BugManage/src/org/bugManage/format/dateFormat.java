package org.bugManage.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class dateFormat {
	public String dateToString (Date date){
		String Str="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Str=sdf.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Str;
	}
	
	public Date stringToDate (String Str){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			date=sdf.parse(Str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return date;
	}
	
	public Date dateToDate (Date date){
		try {
			date=stringToDate(dateToString(date));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return date;
	}
}
