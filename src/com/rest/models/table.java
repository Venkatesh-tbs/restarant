package com.rest.models;
public class table{
    // Three possible states for a table:
    // free    = table is available for booking
    // booked  = customer has reserved the table but not yet arrived
    // occupy  = customer has arrived and is currently seated
    public enum Availability{
        free, booked, occupy
    }

    public   int id;
    public   int capcity;
    public   Availability status;

     public table(int idd , int c) {
        id = idd;
        capcity = c;
        status = Availability.free;
    }

    public int getId(){
        return  id;
    }
    public  int  getcap(){
        return capcity;
    }
    public Availability getstatus(){
        return status;
    }

     // Book: marks a free table as Booked (customer reserved it)
     public void Book(){
        status = Availability.booked;
     }

     // Occupy: marks a booked table as Occupied (customer has arrived)
     public void occupy(){
        status = Availability.occupy;
     }

     // Release: marks an occupied/booked table back as Free (customer left)
     public void cancel(){
        status = Availability.free;
     }

     // Setter for capacity
     public void setcap(int c) {
        this.capcity = c;
     }

     // Setter for availability status
     public void setstatus(Availability s) {
        this.status = s;
     }

     @Override
     public String toString() {
        // Show a human-friendly label for each status
        String statusLabel;
        switch (status) {
            case free:    statusLabel = "Available";  break;
            case booked:  statusLabel = "Booked";     break;
            case occupy:  statusLabel = "Occupied";   break;
            default:      statusLabel = status.name();
        }
        return "Table ID: " + id + " | Capacity: " + capcity + " seats | Status: " + statusLabel;
     }
}