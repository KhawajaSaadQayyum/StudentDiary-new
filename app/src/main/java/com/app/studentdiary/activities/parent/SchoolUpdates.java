package com.app.studentdiary.activities.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;

public class SchoolUpdates extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_updates);
    }

    public void back(View view) {
        finish();
    }

    public void schoolNotices(View view) {
        startActivity(new Intent(this, ParentSchoolNotices.class));
    }

    public void schoolTimings(View view) {
        startActivity(new Intent(this, ParentSchoolTimings.class));
    }
}