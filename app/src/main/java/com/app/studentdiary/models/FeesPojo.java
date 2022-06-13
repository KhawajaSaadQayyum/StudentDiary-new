package com.app.studentdiary.models;

public class FeesPojo extends Super {
    String studentName;
    String studentID;
    String date;
    String studentClass;
    String status;

    public FeesPojo() {
    }

    public FeesPojo(String studentName, String studentID, String date, String studentClass, String status) {
        this.studentName = studentName;
        this.studentID = studentID;
        this.date = date;
        this.studentClass = studentClass;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
}
