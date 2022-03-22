package com.app.studentdiary.models;

public class Marks extends Super {
    String id;
    String subject;
    String percentage;
    String studentName;
    String studentId;
    String classRoom;

    public Marks() {
    }

    public Marks(String id, String subject, String percentage, String studentId, String studentName, String classRoom) {
        this.id = id;
        this.subject = subject;
        this.percentage = percentage;
        this.studentName = studentName;
        this.studentId = studentId;
        this.classRoom = classRoom;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
