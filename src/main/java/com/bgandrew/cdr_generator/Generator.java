package com.bgandrew.cdr_generator;


import com.bgandrew.cdr_generator.model.CITY;
import com.bgandrew.cdr_generator.model.BTS;
import com.bgandrew.cdr_generator.model.BTSSet;
import com.bgandrew.cdr_generator.model.Constants;
import com.bgandrew.cdr_generator.model.Customer;
import com.bgandrew.cdr_generator.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/** Generator class.
 *
 */
public class Generator {
   
    private final static String[] CSV_HEADER = {
    	"timestamp", "caller MSISDN", "caller IMEI", "caller IMSI", "caller LAC", "caller cellID",
    	"caller latitude", "caller longitude", "recipient MSISDN",
    	"recipient IMEI", "recipient IMSI", "recepient LAC", "recepient cellID", "recipient latitude",
    	"recipient longitude", "type", "duration", "length" 
    };
    
    private final static String DEVICES_LIST = "devices.json";
    private final static String OUTPUT_FILE = "data.csv";
    private final static String OPENCELLID_FILE = "cell_towers.csv";
    
    
    private static Map<CITY,List<BTS>> works; // = Utils.generateLocationMap(numberOfDevices/10);
    private static Map<CITY,List<BTS>> others; // = Utils.generateLocationMap(numberOfDevices);
    private static Map<CITY,List<BTS>> homes; // = Utils.generateLocationMap(numberOfDevices/2);
    
    private static final Map<CITY, List<BTS>> globalBTSMap = new HashMap<>();
            
            
    private static void fillBTSMaps(int numberOfDevices) {
        // read file with BTS
        // TODO use Stream API propely here
        // TODO find how to get substream
        try (Stream<String> lines = Files.lines(Paths.get(OPENCELLID_FILE))) {

            for (CITY city : CITY.values()) {
                globalBTSMap.put(city, new ArrayList<>());
            }

            for (String line : (Iterable<String>) lines::iterator) {
                if (!line.startsWith("radio")) {
                    String[] values = line.split(",");
                    int mcc = Integer.parseInt(values[1]);
                    int mns = Integer.parseInt(values[2]);
                    long lac = Long.parseLong(values[3]);
                    int cellId = Integer.parseInt(values[4]);
                    double latitude = Double.parseDouble(values[7]);
                    double longitude = Double.parseDouble(values[6]);
                    

                  //  System.out.println("cellID: " + cellId + " , lat: " + latitude + " long: " + longitude);
                    BTS bts = new BTS(latitude, longitude, cellId, lac, mcc, mns);

                    for (CITY city : CITY.values()) {
                        if (city.contains(bts)) {
                            globalBTSMap.get(city).add(bts);
                            break;
                        }
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }

        works = generateBTSMap(numberOfDevices / 10);
        others = generateBTSMap(numberOfDevices);
        homes = generateBTSMap(numberOfDevices / 2);

    }

    private static Map<CITY, List<BTS>> generateBTSMap(int numberOfBTSs) {

        int numberOfBTSperCity = numberOfBTSs / CITY.values().length;
        if (numberOfBTSperCity < 1) {
            numberOfBTSperCity = 1;
        }

        Map<CITY, List<BTS>> map = new HashMap<>();

        for (CITY city : CITY.values()) {
            ArrayList<BTS> list = new ArrayList<>();
            map.put(city, list);
            for (int i = 0; i <= numberOfBTSperCity; i++) {
                list.add(randomBTSInCity(city));
            }

        }

        return map;
    }

    private static BTS randomBTSInCity(CITY city) {
        return Utils.pickRandomElement(globalBTSMap.get(city));
    }

    private static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }
     
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        long starttime = System.nanoTime();
        
        int numberOfCalls = (int)1e7;
        int numberOfDevices = (int)1e3;
        boolean doExport = false;
        boolean doImport = false;
        if (args.length > 0) {
            try {
                ArrayList<String> options = new ArrayList<>(Arrays.asList(args));
                if (options.indexOf("-import") != -1) {
                    doImport = true;
                }
                if (options.indexOf("-export") != -1) {
                    doExport = true;
                }
                if (options.indexOf("-d") != -1) {
                    numberOfDevices = Integer.parseInt(options.get(options.indexOf("-d") + 1));
                }
                if (options.indexOf("-c") != -1) {
                    numberOfCalls = Integer.parseInt(options.get(options.indexOf("-c") + 1));
                }
            
            } catch (Throwable t) {
                System.out.println("Error parsing command line arguments!");
                System.exit(1);
            } 
        }
        
        ArrayList<Customer> customers = new ArrayList<>();
        
        Type listOfCustomers = new TypeToken<List<Customer>>(){}.getType();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        LocalDateTime start_time = LocalDateTime.now().minusMonths(3);
        
        
        if (doImport) {
            try {
                customers = gson.fromJson(readFile(DEVICES_LIST), listOfCustomers);
            } catch (IOException | JsonSyntaxException e)  {
                System.out.println("Error reading devices.json : " + e );
            }
        } else {
            
            
            // read file with BTS
            // TODO use Stream API propely here
            // TODO find how to get substream
            try( Stream<String> lines = Files.lines(Paths.get(OPENCELLID_FILE))) {
                
                for( String line : (Iterable<String>) lines::iterator ) { 
                    if (!line.startsWith("radio")&&!line.startsWith("sep")) { // TODO how to properly omit first two lines???
                        String[] values = line.split(",");
                        double latitude = Double.parseDouble(values[7]);
                        double longitude = Double.parseDouble(values[6]);
                        int cellId = Integer.parseInt(values[4]);
                    }
                } 
            } catch (IOException ex) {
                Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
            }

            // add every intersting BTS to map <CITY, BTS>
            
            fillBTSMaps(numberOfDevices);
            
            
            for (int i = 0; i < numberOfDevices; i++) {
                // pick random set of home, work and other from one city
                CITY city = Utils.randomCity();
                BTSSet locationSet = new BTSSet(city,
                        Utils.pickRandomElement(homes.get(city)),
                        Utils.pickRandomElement(works.get(city)),
                        Utils.pickRandomElement(others.get(city)));
                
                customers.add(Customer.generateCustomer(start_time, locationSet));
            }
        }
        
        
        // sorting based on activity value. 
        customers.sort(null);
        
        

        try (PrintWriter writer = new PrintWriter(new FileWriter(OUTPUT_FILE, true))) {
            if (Files.size(Paths.get(OUTPUT_FILE)) == 0) {
        	StringBuilder sb = new StringBuilder(CSV_HEADER[0]);
                for(int i = 1; i < CSV_HEADER.length; i++){
                    sb.append(Constants.DELIMITER).append(CSV_HEADER[i]);
                }
        	writer.println(sb);
            }
   
        	

            Customer customer1;
            Customer customer2;
            
            for (int j = 0; j < numberOfCalls; j++) {
                
                // since customers are sorted based on activity of each customer
                // we'll pick customers with more activity more frequently 
                customer1 = Utils.pickElementWithTriangularDestribution(customers);
                customer2 = Utils.pickElementWithTriangularDestribution(customers);
                
                if (customer1.equals(customer2)) {
                    continue; // don't want to make customers to call themselfs
                } 
                
                Customer.CallType type = Utils.generateCallType();
                int duration;
                int length;
                if (type == Customer.CallType.call) {
                    length = 0;
                    duration = Utils.generateDuration();
                } else {
                    duration = 0;
                    length = Utils.generateLength();
                }
                writer.println(customer1.call(customer2, type, duration, length));
                
            }
        
            
        } catch (FileNotFoundException fnfe) {
            System.out.println("Could not create the file");
        } catch (UnsupportedEncodingException uee) {
            System.out.println("Unsupported encoding exception");
        } catch (IOException ioe) {
            System.out.println("Error writing to file");    
        }
        
        if (doExport) {
            try (PrintWriter writer = new PrintWriter(DEVICES_LIST, "UTF-8")) {
                writer.println(gson.toJson(customers, listOfCustomers));
            } catch (FileNotFoundException fnfe) {
                System.out.println("Could not create the file");
            } catch (UnsupportedEncodingException uee) {
                System.out.println("Unsupported encoding exception");
            }
        }
        
        System.out.println("Done in " + (System.nanoTime() - starttime)/1e6 + " ms");

        
    }
    
}
