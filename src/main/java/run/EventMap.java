package run;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;  
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class EventMap {  
   
   
   //Map to send out to front end
   public HashMap<String, Object> data;
   private Map<String, Object> info;
   private Date first;
   private Date last;
   


   //pre: 
   //post: creates an EventMap object
   public EventMap() throws FileNotFoundException {
   
      data = new HashMap<>();
      info = new HashMap<>();
      int[] firstLast = getFirstAndLastDay();
      
      //sets the first and last day
      first = makeDate(firstLast[0]);
      last = makeDate(firstLast[1]);
      
      data.put("info", info);
      fillInfo();
      
      
         /********************************************\
         *                                            *
         *                                            *
         *     EXTRA INFO IS PUT INTO DATA HERE       *
         *                                            *
         *                                            *
         \********************************************/
      
      data.put("title", getTitle());
   }
   
   //pre:  takes an int date. date must be in form yyyymmdd
   //post: returns an int from 0-6 with 0 being Sunday and 6 being Saturday
   public static int getDayOfWeek(int date) {
      return makeDate(date).getDay();
   }
   
   public void fillInfo() throws FileNotFoundException {
      //sets up file input
      File f = new File("../../../../../gp-sea-apps-ops.github.io/data/calendar_data.csv");
      Scanner in = new Scanner(f);
      //skips format line
      in.nextLine();
        
      //creates "data" for all additional information
      Map<String, Object> data = new HashMap<>();
         
      for (int i = 0; i < 21; i++) {
         //creates info indices
         info.put(String.valueOf(i), new HashMap<String, Map<String, String>>());
      }
      
      while(in.hasNext()) {
            //creates a HashMap for an individual event
         Map<String, String> event = new HashMap<>();
         String[] temp = in.nextLine().split(",");
            
         //sorts out the events out of range of the acceptable timeframe
         long milliesBetween = makeDate(temp[0]).getTime() - first.getTime();
         long daysBetween =  TimeUnit.DAYS.convert(milliesBetween, TimeUnit.MILLISECONDS);
         
         if (daysBetween <= 20 && daysBetween >= 0) {
            event.put("date", temp[0]);
            event.put("startTime", temp[1]);
            event.put("endTime", temp[2]);
            event.put("name", temp[3]);
            event.put("location", temp[4]);
            event.put("group_gp", temp[5]);
               
            //puts the event into the proper index in info
            Map<String, Map<String, String>> events = (Map<String, Map<String, String>>)info.get(String.valueOf(daysBetween));
            events.put(String.valueOf(events.size()), event);                 
         }
            
      }
            
   }
      
   //post: returns an array of size 2 with the first element being the first 
   //      Sunday on or before the current date and the last being the Saturday
   //      3 weeks after the Sunday. Both are in yyyymmdd format
   public int[] getFirstAndLastDay() {
      int[] firstLast = new int[2];
      Calendar c = new GregorianCalendar();
      
      //YEAR = 1, MONTH = 2, WEEK_OF_YEAR = 3, DATE = 5, DAY_OF_WEEK = 7
      
      //gets Sunday directly before date
      c.add(5, - c.get(7) + 1);
      firstLast[0] = c.get(1) * 10000 + (c.get(2) + 1) * 100 + c.get(5);
      
      //gets last Saturday to display
      c.add(5, 20);
      firstLast[1] = c.get(1) * 10000 + (c.get(2) + 1) * 100 + c.get(5);
      
      return firstLast;
   }
   
   //pre:  takes an int date in the yyyymmdd format
   //post: returns a Date object
   public Date makeDate(int date) {
      int day = date % 100;
      int month = (date / 100) % 100;
      int year = (date / 10000);
      
      return new Date(year - 1900, month - 1, day);
   }
   

   public Date makeDate(String date) {
      return makeDate(Integer.valueOf(date));
   }
   
   //pre:  
   //post: returns a String of the apporpriate title for the calendar

   public String getTitle() {
      String result = (first.getMonth() + 1) + "/" + first.getDate() +
                      " - " + (last.getMonth() + 1) + "/" + last.getDate();
      return result;
   }
}