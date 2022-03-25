package com.app.studentdiary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
    }

    public void attendance(View view) {
        startActivity(new Intent(this, TeacherAttendance.class));
    }

    public void marks(View view) {
        startActivity(new Intent(this, TeacherMarksActivity.class));
    }

    public void activities(View view) {
        startActivity(new Intent(this, TeacherActivityNotice.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthLoginActivity.class));
        finish();
    }
}