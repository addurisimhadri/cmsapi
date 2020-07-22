package com.sim.wicmsapi.utility;
import java.util.Calendar;
public class TransId {
	public static int cms_serialno = 1;
	static String serv_offset="";//This is used when multi server environment
	
	    public static synchronized long getCpTransId(){
                if (cms_serialno > 99999){
                    cms_serialno = 1;
                }else{
                    cms_serialno++;
                }
                //System.out.println(serialno);
                return getPDUTimeStamp(cms_serialno);
        }
	    
	    public static long  getPDUTimeStamp(int sno)  {
		   String temps="";
		   String msgid="";
		   java.util.Calendar gc=new java.util.GregorianCalendar();
		   temps=Integer.toString(gc.get(Calendar.YEAR)% 100);
		   msgid=msgid+temps;
		   temps=Integer.toString(gc.get(Calendar.MONTH)+1);
		   if(temps.length()<2) temps="0"+temps;
		   msgid=msgid+temps;
		   temps=Integer.toString(gc.get(Calendar.DATE));
		   if(temps.length()<2) temps="0"+temps;
		   msgid=msgid+temps;
		   temps=Integer.toString(gc.get(Calendar.HOUR_OF_DAY));
		   if(temps.length()<2) temps="0"+temps;
		   msgid=msgid+temps;
		   temps=Integer.toString(gc.get(Calendar.MINUTE));
		   if(temps.length()<2) temps="0"+temps;
		   msgid=msgid+temps;
		   temps=Integer.toString(gc.get(Calendar.SECOND));
		   if(temps.length()<2) temps="0"+temps;
		   msgid=msgid+temps+serv_offset;
		   msgid+=convertSno(sno);
  		   return Long.parseLong(msgid);
     }
    
    public static String convertSno(int sno) {
		String snotemp="";
	    if(sno<10) snotemp="0000"+sno;
	    else if(sno>9 && sno<100) snotemp="000"+sno;
	    else if(sno>99 && sno<1000) snotemp="00"+sno;
	    else if(sno>999 && sno<10000) snotemp="0"+sno;
	    else snotemp=sno+"";
	    return snotemp;
	}
	
}