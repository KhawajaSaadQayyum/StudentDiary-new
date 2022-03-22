package com.app.studentdiary.models;

public class Attendance extends Super {
    String id;
    String date;
    String classroom;
    String studentId;
    String studentName;
    String isPresent;

    public Attendance() {
    }

    public Attendance(String id, String date, String classroom, String studentId, String studentName, String isPresent) {
        this.id = id;
        this.date = date;
        this.classroom = classroom;
        this.studentId = studentId;
        this.isPresent = isPresent;
        this.studentName = studentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }
}
