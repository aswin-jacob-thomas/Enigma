package com.example.aswin.hope;

import java.sql.Time;

/**
 * Created by aswin on 10/11/16.
 */

public class Security {

    //private variables
    String time;
    String accx;
    String accy;
    String accz;
    // Empty constructor
    public Security(){

    }
    // constructor
    public Security(String time,String accx,String accy,String accz){
        this.time=time;
        this.accx = accx;
        this.accy = accy;
        this.accz=accz;
    }


    // getting ID
    public String getTime(){
        return this.time;
    }

    // setting id
    public void setTime(String time){
        this.time = time;
    }

    // getting name
    public String getAccx(){
        return this.accx;
    }
    public String getAccy(){
        return this.accy;
    }
    public String getAccz(){
        return this.accz;
    }

    public void setAccx(String accx){
        this.accx=accx;
    }

    public void setAccy(String accy){
        this.accy=accy;
    }

    public void setAccz(String accz){
        this.accx=accz;
    }

}