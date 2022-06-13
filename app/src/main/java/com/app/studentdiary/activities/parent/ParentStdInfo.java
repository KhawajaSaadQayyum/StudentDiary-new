package com.app.studentdiary.activities.parent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;

public class ParentStdInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_class_info);
    }

    public void back(View view) {
        finish();
    }

    public void attendance(View view) {
        startActivity(new Intent(this, ParentAttendance.class));
    }

    public void marks(View view) {
        startActivity(new Intent(this, ParentMarks.class));

    }

    public void feeStatus(View view) {
//        TODO: Fees Status working here
        startActivity(new Intent(this, FeesActivity.class));
    }
}