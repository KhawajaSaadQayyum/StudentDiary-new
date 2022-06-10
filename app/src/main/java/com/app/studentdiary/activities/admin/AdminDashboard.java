package com.app.studentdiary.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;
import com.app.studentdiary.activities.auth.AuthLoginActivity;
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
        startActivity(new Intent(this, AdminSchoolTimings.class));
    }

    public void notices(View view) {
        startActivity(new Intent(this, AdminNotices.class));
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, AuthLoginActivity.class));
        finish();
    }

    public void feeStatus(View view) {
        startActivity(new Intent(this, FeeStatusActivity.class));

    }
}