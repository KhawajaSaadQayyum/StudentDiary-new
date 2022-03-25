package com.app.studentdiary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
    }

    public void teacherRegistrations(View view) {
        startActivity(new Intent(this, AdminRegistrationApproval.class));
    }

    public void schoolTimings(View view) {

    }

    public void notices(View view) {

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthLoginActivity.class));
        finish();
    }
}