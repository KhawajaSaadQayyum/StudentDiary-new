package com.app.studentdiary.activities.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;
import com.app.studentdiary.activities.auth.AuthLoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ParentDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
    }

    public void studentInfo(View view) {
        startActivity(new Intent(this, ParentStdInfo.class));
    }

    public void classDetails(View view) {
        startActivity(new Intent(this, ParentActivities.class));
    }

    public void schoolUpdates(View view) {
        startActivity(new Intent(this, SchoolUpdates.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthLoginActivity.class));
        finish();
    }

    public void back(View view) {
        finish();
    }
}