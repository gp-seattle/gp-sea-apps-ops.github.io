
import java.io.*;
import java.util.*;

public class EventMap {
   
   //TODO: -main
   //         -events
   //            -include only three weeks worth of events
   //            -index the events into the proper boxes based on day that the 
   //             code is ran
   //         -data
   //            -make an calander title for the front end to use
   //            -list number of events at each index
   //      -implement getDayOfWeek
   //      -implement method to send data off to front end
   
   
   
   

   //post: pulls data from the .csv file and puts it into a hashmap as well as
   //      putting other important info into the hashmap
   public static void main(String[] args) {
   
      //map we can send to front end
      //21 keys: 0-20 and "data"
      Map<String, Object> info = new HashMap<>();
      
      //map to hold all the events in an easily manipulated manner
      try {
         Map<String, Map<String, String>> events = new HashMap<>();
         File f = new File("../../../../../gp-sea-apps-ops.github.io/data/calendar_data.csv");
         Scanner in = new Scanner(f);
         in.nextLine();
         int i = 0;
         while(in.hasNext()) {
            Map<String, String> h = new HashMap<>();
            String[] temp = in.nextLine().split(",");
         
            h.put("date", temp[0]);
            h.put("startTime", temp[1]);
            h.put("endTime", temp[2]);
            h.put("name", temp[3]);
            h.put("location", temp[4]);
            h.put("group_gp", temp[5]);
         
            events.put("Event" + i, h);
            
         
            i++;
         } 
         
         System.out.println(getEventCount("20190505", events));
         /* 
         for (String s : events.keySet()) {
            System.out.println(s + " = " + events.get(s));
         
         }
         */
      } catch(FileNotFoundException e) {
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
      //post: returns an int from 1-7 with 1 being Sunday and 7 being Saturday
   public static int getDayOfWeek(int date) {
      return -1;
   }
   
}