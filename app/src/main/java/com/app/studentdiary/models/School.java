package com.app.studentdiary.models;

public class School {
    String schoolOnDays;
    String timingFrom;
    String timingTo;
    String Note;

    public School() {
    }

    public School(String schoolOnDays, String timingFrom, String timingTo, String note) {
        this.schoolOnDays = schoolOnDays;
        this.timingFrom = timingFrom;
        this.timingTo = timingTo;
        Note = note;
    }

    public String getSchoolOnDays() {
        return schoolOnDays;
    }

    public void setSchoolOnDays(String schoolOnDays) {
        this.schoolOnDays = schoolOnDays;
    }

    public String getTimingFrom() {
        return timingFrom;
    }

    public void setTimingFrom(String timingFrom) {
        this.timingFrom = timingFrom;
    }

    public String getTimingTo() {
        return timingTo;
    }

    public void setTimingTo(String timingTo) {
        this.timingTo = timingTo;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
