package com.app.studentdiary.activities.parent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.models.School;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class ParentSchoolTimings extends AppCompatActivity implements Info {

    MaterialDayPicker mdpDayPicker;

    EditText etTimingFrom;
    EditText etTimingTo;
    EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_shool_timings);

        initViews();
        initCurrentSaloon();

        mdpDayPicker.setClickable(false);
    }

    private void initCurrentSaloon() {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_SCHOOL)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        School school = snapshot.getValue(School.class);
                        try {
                            if (school == null)
                                return;

                            List<MaterialDayPicker.Weekday> weekdays = new ArrayList<>();
                            String workingDays = school.getSchoolOnDays();
                            for (String workingDay : workingDays.split(", "))
                                weekdays.add(MaterialDayPicker.Weekday.valueOf(workingDay));
                            mdpDayPicker.setSelectedDays(weekdays);
                            etTimingTo.setText(school.getTimingTo());
                            etTimingFrom.setText(school.getTimingFrom());
                            etNote.setText(school.getNote());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initViews() {
        mdpDayPicker = findViewById(R.id.mdp_days);
        etTimingFrom = findViewById(R.id.et_time_from);
        etTimingTo = findViewById(R.id.et_timing_to);
        etNote = findViewById(R.id.et_note);
    }

    public void back(View view) {
        finish();
    }


}
