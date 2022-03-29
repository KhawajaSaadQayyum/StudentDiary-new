package com.app.studentdiary.singletons;

import com.app.studentdiary.models.MessagePojo;

public class CommentSingleton {
    public static MessagePojo instance;

    private CommentSingleton() {
    }

    public static MessagePojo getInstance() {
        return instance;
    }

    public static void setInstance(MessagePojo instance) {
        CommentSingleton.instance = instance;
    }
}
