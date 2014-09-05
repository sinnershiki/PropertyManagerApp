package com.example.sugano.myapplication;

/**
 * Created by Sugano on 2014/09/05.
 */
public class CustomData {
    private int id;
    private String person;
    private String property;
    private String fromDate;
    private String periodDate;
    private int isLending;
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setPerson(String person){
        this.person = person;
    }
    public String getPerson(){
        return person;
    }
    public void setProperty(String property){
        this.property = property;
    }
    public String getProperty(){
        return property;
    }
    public void setFromDate(String fromDate){
        this.fromDate = fromDate;
    }
    public String getFromDate(){
        return fromDate;
    }
    public void setPeriodDate(String periodDate){
        this.periodDate = periodDate;
    }
    public String getPeriodDate(){
        return periodDate;
    }
    public void setIsLending(int isLending){
        this.isLending = isLending;
    }
    public int getIsLending(){
        return isLending;
    }
}
