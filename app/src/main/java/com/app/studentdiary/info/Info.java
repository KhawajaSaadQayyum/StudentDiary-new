package com.app.studentdiary.info;

public interface Info {
    String TAG = "sad";
    String NODE_USERS = "Users";
    String NODE_ATTENDANCE = "Attendances";
    String NODE_ACTIVITY = "Activities";
    String NODE_MARKS = "Marks";
    String NODE_CHATS = "Chats";

    String PARENT = "Parent";
    String ADMIN = "Princ";
    String TEACHER = "Teacher";

    String VER_PENDING = "pend";
    String VER_REJECTED = "rej";
    String VER_APPROVED = "appr";

    int RV_TYPE_TEACHER_REGS = 1;
    int RV_TYPE_ATTENDANCE = 2;
    int RV_TYPE_STUDENT_ATTENDANCE = 3;
    int RV_TYPE_STUDENT_MARKS = 4;
    int RV_TYPE_ACTIVITY = 5;
    int RV_TYPE_PARENT_MARKS = 6;
    int RV_TYPE_PARENT_ACTIVITY = 7;
    int RV_TYPE_CHATS = 8;
}
