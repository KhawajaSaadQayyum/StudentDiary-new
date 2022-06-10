package com.app.studentdiary.models;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.app.studentdiary.R;

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

    public MessagePojo() {
    }

    @BindingAdapter("android:titleLogic")
    public static void tl(TextView textView, String title) {
        if (title != null && !title.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(title);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("android:activityLogic")
    public static void al(TextView textView, String title) {
        if (title != null && !title.isEmpty()) {
            textView.setText("Context - ");
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("android:colorAuthor")
    public static void ca(TextView textView, MessagePojo messagePojo) {
        if (messagePojo.getAuthor().equals(messagePojo.getParentId())) {
            textView.setText(messagePojo.getParentName());
            textView.setTextColor(textView.getContext().getColor(R.color.yellow));
        } else {
            textView.setText(messagePojo.getTeacherName());
            textView.setTextColor(textView.getContext().getColor(R.color.red));
        }
    }

    public String getAuthor() {
        return author;
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
