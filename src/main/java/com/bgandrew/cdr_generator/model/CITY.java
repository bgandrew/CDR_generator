package com.bgandrew.cdr_generator.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* List of supported cities with locations

**/
public enum CITY {
    
    LA (new Location(33.938234, -118.106140),  // Los Angeles
           Arrays.asList("213", "310", "323",
                         "424", "626", "818",
                                "661","747")), 
    SD (new Location(32.799612, -117.140673),  // San Diego
                  Arrays.asList("619","858")), 
    SJ (new Location(37.317485, -121.893148),  // San Jose
                        Arrays.asList("408")); 
        
    public final Location location;
    public final List<String> ndcs; 
        
    CITY(Location location, List<String> ndcs) {
        this.location = location;
        this.ndcs = Collections.unmodifiableList(ndcs);
    }
        
       
}