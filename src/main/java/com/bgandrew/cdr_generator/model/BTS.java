package com.bgandrew.cdr_generator.model;

/**
 *
 * simple value class for BTS
 */
public class BTS {
   
    public final double longitude;
    public final double latitude;
    
    public final int cellID;
    
    public BTS (double latitude, double longitude, int cellID) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cellID = cellID;
    } 
    
    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
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
        final BTS other = (BTS) obj;
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        return true;
    }
    
    
}
