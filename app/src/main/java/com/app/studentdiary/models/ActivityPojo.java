package com.app.studentdiary.models;

public class ActivityPojo extends Super {
    String id;
    String classroom;
    String title;
    String desc;

    public ActivityPojo() {
    }

    public ActivityPojo(String id, String classroom, String title, String desc) {
        this.id = id;
        this.classroom = classroom;
        this.title = title;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
