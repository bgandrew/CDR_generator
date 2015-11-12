package com.bgandrew.cdr_generator.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* List of supported cities with locations

**/
public enum CITY {
    
    LA (33.938234, -118.106140,  // Los Angeles
           Arrays.asList("213", "310", "323",  // TODO this should go to BTS
                         "424", "626", "818",
                                "661","747")), 
    SD (32.799612, -117.140673,  // San Diego
                  Arrays.asList("619","858")), 
    SJ (37.317485, -121.893148,  // San Jose
                        Arrays.asList("408")); 
        
    public final double latitude;
    public final double longitude;
    public final List<String> ndcs; 
    public final BTS defaultBTS;
    public static final double CITY_RADIUS_SQUARE = 0.04; // about 22 kilometers
    
    
 
    
    
    CITY(double latitude, double longitude, List<String> ndcs) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ndcs = Collections.unmodifiableList(ndcs);
        this.defaultBTS = new BTS(latitude, longitude, 0,0,0,0);
    }
    
    public boolean contains(BTS bts) {
        return (Math.pow(bts.latitude - latitude, 2) + Math.pow(bts.longitude - longitude, 2) < CITY_RADIUS_SQUARE);   
    }
        
       
}