package com.app.studentdiary.singletons;

import com.app.studentdiary.models.ActivityPojo;

public class ActivitySingleton {
    public static ActivityPojo activity;

    private ActivitySingleton() {
    }

    public static ActivityPojo getInstance() {
        return activity;
    }

    public static void setInstance(ActivityPojo activity) {
        ActivitySingleton.activity = activity;
    }
}
