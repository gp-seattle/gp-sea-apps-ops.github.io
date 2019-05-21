
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
   
   //TODO: -main
   //         -events
   //            -index the events into the proper boxes based on day that the 
   //             code is ran
   //         -data
   //            -make an calander title for the front end to use
   //               -create the getTitle method (see line 95)
   //      -further testing of getFirstAndLastDay method
   //      -implement method to send data off to front end
   //      -look into removing the getDayOfWeekMethod by using Calendars
   //      -further testing isBetween method for end and start of year
   //      
   
   
   
   

   //post: pulls data from the .csv file and puts it into a hashmap as well as
   //      putting other important info into the hashmap
   public static void main(String[] args) {
      
      //map we can send to front end
      //21 keys: 0-20 and "data"
      Map<String, Object> info = new HashMap<>();
      
      //map to hold all the events in an easily manipulated manner
      try {
      
                 //gets first and last date to display
         int[] firstLast = getFirstAndLastDay();
         Date first = makeDate(firstLast[0]);
         
         Map<String, Map<String, String>> events = new HashMap<>();
         
         //sets up file input
         File f = new File("../../../../../gp-sea-apps-ops.github.io/data/calendar_data.csv");
         Scanner in = new Scanner(f);
         //skips format line
         in.nextLine();
         
         //puts events into the hashmap with the key "Event" + i
         int i = 0;
         while(in.hasNext()) {
            //creates the inner hashmap
            Map<String, String> h = new HashMap<>();
            String[] temp = in.nextLine().split(",");
            
            //sorts out the events out of range of the acceptable timeframe
            long milliesBetween = makeDate(temp[0]).getTime() - first.getTime();
            long daysBetween =  TimeUnit.DAYS.convert(milliesBetween, TimeUnit.MILLISECONDS);
         
            if (daysBetween < 20 && daysBetween >= 0) {
               h.put("date", temp[0]);
               h.put("startTime", temp[1]);
               h.put("endTime", temp[2]);
               h.put("name", temp[3]);
               h.put("location", temp[4]);
               h.put("group_gp", temp[5]);
               //days between this event and the first Sunday
               h.put("daysBetween", String.valueOf(daysBetween));
            
               events.put("Event" + i, h);
            
               i++;
            }
         } 
         
         //creates "data" for all additional information
         Map<String, Object> data = new HashMap<>();
         
         //maps box index to number of events
         for (int j = 0; j < 21; j++) {
            int count = 0;
            for (Map<String, String> event : events.values()) {
               if (event.get("daysBetween").equals(String.valueOf(j))) {
                  count++;  
               }
            }
            data.put(String.valueOf(j), String.valueOf(count));
         }
         
         data.put("title", getTitle());
         
         info.put("data", data);
         
         
         
         
         /*
         //tests the getEventCount method
         System.out.println(getEventCount("20190505", events));
         
         //Prints the events out
         for (String s : events.keySet()) {
            System.out.println(s + " = " + events.get(s));
         
         }
         */
         
      } catch(FileNotFoundException e) {
         //filepath is wrong or calander data does not exist
         System.out.println("Error: Calander data not found");
      }
   }
   
   
   //pre:  takes an String date and a Map of String to Map of String to String.
   //      date must be in form yyyymmdd
   //post: returns the number of events 
   public static int getEventCount(String date, Map<String, Map<String, String>> events) {
      int count = 0;
      for(Map h : events.values()) {
         if (h.get("date").equals(date)) {
            count++;
         }
      }
      
      return count;
   }
   
   //pre:  takes an int date. date must be in form yyyymmdd
   //post: returns an int from 0-6 with 0 being Sunday and 6 being Saturday
   public static int getDayOfWeek(int date) {
      return makeDate(date).getDay();
   }
   
   //post: returns an array of size 2 with the first element being the first 
   //      Sunday on or before the current date and the last being the Saturday
   //      3 weeks after the Sunday. Both are in yyyymmdd format
   public static int[] getFirstAndLastDay() {
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
   
   //pre:  takes in an int date in the format yyyymmdd and an int array
   //      firstLast with the first element being the first date that date
   //      can be on and the second element being the last date that date
   //      can be on. Both elements must be in yyyymmdd format
   //post: returns true if date is on or between the dates in firstLast
   public static boolean isBetween(int[] firstLast, int date) {
      Date d = makeDate(date);
      Date first = makeDate(firstLast[0]);
      Date last = makeDate(firstLast[1]);
      
      return (d.compareTo(first) >= 0 && d.compareTo(last) <= 0);
   }
   
   //pre:  takes an int date in the yyyymmdd format
   //post: returns a Date object
   public static Date makeDate(int date) {
      int day = date % 100;
      int month = (date / 100) % 100;
      int year = (date / 10000);
      
      return new Date(year - 1900, month - 1, day);
   }
   
   public static Date makeDate(String date) {
      return makeDate(Integer.valueOf(date));
   }
   
   //pre:  
   //post: returns a String of the apporpriate title for the calendar
   public static String getTitle() {
      return null;
   }
}