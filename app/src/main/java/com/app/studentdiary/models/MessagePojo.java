package com.app.studentdiary.models;

public class MessagePojo extends Super {
    String MessageId;
    String activityId;
    String activityTitle;
    String messageText;
    String parentId;
    String parentName;
    String teacherId;
    String teacherName;
    String author;

    public MessagePojo(String messageId, String activityId, String activityTitle, String messageText,
                       String parentId, String parentName, String teacherId, String teacherName, String author) {
        this.activityId = activityId;
        this.activityTitle = activityTitle;
        MessageId = messageId;
        this.messageText = messageText;
        this.parentId = parentId;
        this.parentName = parentName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public MessagePojo() {
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
