package com.bgandrew.cdr_generator.model;

/**
 *
 * simple value class for BTS
 */
public class BTS {
   
    public final double longitude;
    public final double latitude;
    
    public final long cellID;
    
    public final long LAC;
    
    public final int MCC;
    
    public final int MNS;
    
    public BTS (double latitude, double longitude, long cellID, long LAC, int MCC, int MNS) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cellID = cellID;
        this.LAC = LAC;
        this.MCC = MCC;
        this.MNS = MNS;
    } 

    @Override
    public String toString() {
        return "BTS{" + "longitude=" + longitude + ", latitude=" + latitude + ", cellID=" + cellID + ", LAC=" + LAC + ", MCC=" + MCC + ", MNS=" + MNS + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 71 * hash + (int) (this.cellID ^ (this.cellID >>> 32));
        hash = 71 * hash + (int) (this.LAC ^ (this.LAC >>> 32));
        hash = 71 * hash + this.MCC;
        hash = 71 * hash + this.MNS;
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
        if (this.cellID != other.cellID) {
            return false;
        }
        if (this.LAC != other.LAC) {
            return false;
        }
        if (this.MCC != other.MCC) {
            return false;
        }
        
        return true;
    }

    
    
    
}
