package com.bgandrew.cdr_generator.model;

import java.util.Objects;

/**
 *
 *  Represents set of locations (home, work, somewhere else) where a customer can appear.
 */
public class LocationSet {
    
    public final CITY city;
    public final Location home;
    public final Location  work;
    public final Location other;
    
    public LocationSet(CITY city, Location home, Location work, Location other) {
        this.city = city;
        this.home = home;
        this.work = work;
        this.other = other;
    }

    @Override
    public String toString() {
        return "LocationSet{" + "city=" + city + ", home=" + home + ", work=" + work + ", other=" + other + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.city);
        hash = 31 * hash + Objects.hashCode(this.home);
        hash = 31 * hash + Objects.hashCode(this.work);
        hash = 31 * hash + Objects.hashCode(this.other);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LocationSet arg = (LocationSet) obj;
        
        return city.equals(arg.city) && home.equals(arg.home) 
            && work.equals(arg.work)&&other.equals(arg.other);
    }
    
    
    
    
}
