/*
08/12/2018
Author AngieSR

NOTE: This class was based on these tutorials: https://www.simplifiedcoding.net/firebase-realtime-database-crud/
*/

package com.example.angel.timein;

public class Shift {
    private String shiftId;
    private String date;
    private String timeStart;
    private String timeEnd;

    public Shift(){

    }

    public Shift(String shiftId, String date, String timeStart, String timeEnd) {
        this.shiftId = shiftId;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
