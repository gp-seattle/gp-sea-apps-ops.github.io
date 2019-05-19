package run;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.bindings.MapBindings;
import au.com.codeka.carrot.resource.FileResourceLocator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

<<<<<<< HEAD
import java.util.Scanner;

public class MainEngine {
   public static void main(String[] args) {
      CarrotEngine engine = new CarrotEngine(new Configuration.Builder()
             .setResourceLocator(new FileResourceLocator.Builder("src/main/webapp/templates"))
             .build());
   
      HashMap<String, Object> values = new HashMap<String, Object>();
      
      File f = new File("calendar_data.csv");
      Scanner in = new Scanner(f);
      Set<Event> Events = new TreeSet<>();
      in.nextLine();
      Events.add(new Event(in.nextLine()));
      
      /*
      values.put("name1", "Jim");
      values.put("name2", "Jen");
      values.put("day1", "8/18/2019");
      values.put("day2", "8/20/2019");
      */
   
      Bindings bindings = new MapBindings(values);
   
      try {
         String htmlOutput = engine.process("carrot_index.html", bindings);
         // Show status
         System.out.println("ENGINE STATUS " + htmlOutput);
         // Output html file
         BufferedWriter bw = new BufferedWriter(new FileWriter("index.html"));
         bw.write(htmlOutput);
         bw.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
=======
public class MainEngine {

    public static final String BASE_PATH = "src/main/webapp/templates";

    public static void main(String[] args) {
        // Create a new Carrot Engine to store
        CarrotEngine engine = new CarrotEngine(new Configuration.Builder()
                .setResourceLocator(new FileResourceLocator.Builder(BASE_PATH))
                .build());

        HashMap<String, Object> values = assignBindings();

        Bindings bindings = new MapBindings(values);

        outputHTML(engine, bindings, "index.html");
    }

    private static HashMap<String, Object> assignBindings() {
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("name1", "Jim");
        values.put("name2", "Jen");
        values.put("day1", "8/18/2019");
        values.put("day2", "8/20/2019");
        return values;
    }

    private static void outputHTML(CarrotEngine engine, Bindings bindings, String outputLocation) {
        try {
            String htmlOutput = engine.process("carrot_index.html", bindings);
            // Show status
            System.out.println("ENGINE STATUS " + htmlOutput);
            // Output html file
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputLocation));
            bw.write(htmlOutput);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
>>>>>>> 3b7a98af94045d809ac32ce4230f24ac4def3603
}
