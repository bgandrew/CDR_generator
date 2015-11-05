package com.bgandrew.cdr_generator.model;

import java.util.Objects;

/**
 *
 *  Represents set of locations (home, work, somewhere else) where a customer can appear.
 */
public class BTSSet {
    
    public final CITY city;
    public final BTS home;
    public final BTS  work;
    public final BTS other;
    
    public BTSSet(CITY city, BTS home, BTS work, BTS other) {
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
        final BTSSet arg = (BTSSet) obj;
        
        return city.equals(arg.city) && home.equals(arg.home) 
            && work.equals(arg.work)&&other.equals(arg.other);
    }
    
    
    
    
}
