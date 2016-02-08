package com.zemoso.rideapplication;

import java.sql.Timestamp;

/**
 * Created by zemoso on 11/1/16.
 */
public class Display {


    private int id;
    private String username;
    private String mobilenumber;
    private String startingplace;
    private String ridedistance;
    private String timetaken;
    private String waitingtime;
    private String destination;
    private String cabtype;
    private Timestamp boookingTime ;

    public Timestamp getBoookingTime() {
        return boookingTime;
    }

    public void setBoookingTime(Timestamp boookingTime) {
        this.boookingTime = boookingTime;
    }

    public Display(){}

    public Display(String username,String mobilenumber,String startingplace,
                   String ridedistance,String cabtype,String timetaken,String waitingtime,String destination)
    {
        super();
        this.username = username;
        this.mobilenumber = mobilenumber;
        this.startingplace = startingplace;
        this.ridedistance = ridedistance;
        this.timetaken = timetaken;
        this.waitingtime = waitingtime;
        this.destination = destination;
        this.cabtype = cabtype;

    }
    @Override
    public String toString(){
        return "Display[id= "+id+",username= "+username+",mobilenumber="+mobilenumber+",startingplace="+startingplace+",cabtype="+cabtype+",ridedistance="+ridedistance+",timetaken="+timetaken+",waitingtime="+waitingtime+"]";
    }

    public String getUsername()
    {
        return username;
    }

    public String getMobilenumber()
    {
        return mobilenumber;
    }

    public String getStartingplace()
    {
        return startingplace;
    }

    public String getRidedistance()
    {
        return ridedistance;
    }

    public String getWaitingtime()
    {
        return waitingtime;
    }

    public String getTimetaken()
    {
        return timetaken;
    }

    public String getCabtype()
    {
        return cabtype;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setMobilenumber(String mobilenumber)
    {
        this.mobilenumber = mobilenumber;
    }

    public void setStartingplace(String startingplace)
    {
        this.startingplace = startingplace;
    }

    public void setRidedistance(String ridedistance)
    {
        this.ridedistance = ridedistance;
    }

    public void setWaitingtime(String waitingtime)
    {
        this.waitingtime = waitingtime;
    }

    public void setTimetaken(String timetaken)
    {
        this.timetaken = timetaken;
    }

    public void setCabtype(String cabtype)
    {
        this.cabtype = cabtype;
    }

    public int getId() {
        return id;
    }
}
