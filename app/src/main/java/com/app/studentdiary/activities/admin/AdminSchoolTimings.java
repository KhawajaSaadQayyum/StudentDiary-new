package com.app.studentdiary.activities.admin;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.models.School;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class AdminSchoolTimings extends AppCompatActivity implements Info {

    MaterialDayPicker mdpDayPicker;

    EditText etTimingFrom;
    EditText etTimingTo;
    EditText etNote;

    String strEtTimingFrom;
    String strEtTimingTo;
    String strEtNote;
    String strWorkingDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_school_timings);

        initViews();
        initSchool();
    }

    private void initSchool() {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_SCHOOL)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        School school = snapshot.getValue(School.class);
                        if (school != null)
                            try {
                                setViewValues(school);
                            } catch (Exception e) {
                                Log.i(TAG, "onDataChange: EXCEPTION");
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setViewValues(School school) {
        List<MaterialDayPicker.Weekday> weekdays = new ArrayList<>();
        String workingDays = school.getSchoolOnDays();
        for (String workingDay : workingDays.split(", "))
            weekdays.add(MaterialDayPicker.Weekday.valueOf(workingDay));
        mdpDayPicker.setSelectedDays(weekdays);
        etTimingFrom.setText(school.getTimingFrom());
        etTimingTo.setText(school.getTimingTo());
        etNote.setText(school.getNote());

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


    public void showFromTimingsDialog(View view) {
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
            boolean isPM = selectedHour > 12;
            String timeString;
            if (isPM) {
                selectedHour -= 12;
                if (selectedMinute < 10)
                    timeString = selectedHour + ":0" + selectedMinute + " PM";
                else
                    timeString = selectedHour + ":" + selectedMinute + " PM";
            } else {
                if (selectedMinute < 10)
                    timeString = selectedHour + ":0" + selectedMinute + " AM";
                else
                    timeString = selectedHour + ":" + selectedMinute + " AM";
            }
            etTimingFrom.setText(timeString);
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void submit(View view) {
        castStrings();
        if (!isValidStrings())
            return;
        setSchoolDataToFirebase();
    }

    private void setSchoolDataToFirebase() {
        School school = new School();
        school.setNote(strEtNote);
        school.setSchoolOnDays(strWorkingDays);
        school.setTimingFrom(strEtTimingFrom);
        school.setTimingTo(strEtTimingTo);
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_SCHOOL)
                .setValue(school)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "School timings updated", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(this, "Unexpected please try again later", Toast.LENGTH_SHORT).show();
                });
    }

    private boolean isValidStrings() {
        if (!Utils.validEt(etTimingFrom, strEtTimingFrom))
            return false;

        if (strWorkingDays.isEmpty()) {
            Toast.makeText(this, "Select at least 1 working day", Toast.LENGTH_SHORT).show();
            return false;
        }

        return Utils.validEt(etTimingTo, strEtTimingTo);
    }


    private void castStrings() {
        strEtNote = etNote.getText().toString();
        strEtTimingFrom = etTimingFrom.getText().toString();
        strEtTimingTo = etTimingTo.getText().toString();
        StringBuilder workingDays;
        workingDays = new StringBuilder();
        for (MaterialDayPicker.Weekday weekday : mdpDayPicker.getSelectedDays())
            workingDays.append(weekday.toString()).append(", ");

        strWorkingDays = workingDays.toString();
        if (!strWorkingDays.isEmpty())
            strWorkingDays = strWorkingDays.substring(0, strWorkingDays.length() - 2);

    }

    public void showToTiming(View view) {
        etTimingTo.setOnClickListener(v -> {
            Calendar mCurrentTime = Calendar.getInstance();
            int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mCurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(this, (timePicker, selectedHour, selectedMinute) -> {
                boolean isPM = selectedHour > 12;
                String timeString;
                if (isPM) {
                    selectedHour -= 12;
                    if (selectedMinute < 10)
                        timeString = selectedHour + ":0" + selectedMinute + " PM";
                    else
                        timeString = selectedHour + ":" + selectedMinute + " PM";
                } else {
                    if (selectedMinute < 10)
                        timeString = selectedHour + ":0" + selectedMinute + " AM";
                    else
                        timeString = selectedHour + ":" + selectedMinute + " AM";
                }
                etTimingTo.setText(timeString);
            }, hour, minute, false);
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        });
    }
}