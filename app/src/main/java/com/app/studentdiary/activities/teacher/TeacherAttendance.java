package com.app.studentdiary.activities.teacher;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
import com.app.studentdiary.adapters.TypeRecyclerViewAdapter;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.info.RvType;
import com.app.studentdiary.models.Attendance;
import com.app.studentdiary.models.Super;
import com.app.studentdiary.models.UserModel;
import com.app.studentdiary.utils.DialogUtils;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherAttendance extends AppCompatActivity implements Info {
    EditText etDate;
    DatePickerDialog datePickerDialog;
    List<Attendance> studentAttendances;
    List<UserModel> students;

    List<Super> superList;

    RecyclerView rvAttendance;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    Dialog loadingDialog;

    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);

        btnAdd = findViewById(R.id.btn_add);
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);

        initListStudents();
        initRv();

        etDate = findViewById(R.id.et_date);
        datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
            String dob = i2 + " - " + i1 + " - " + i;
            etDate.setText(dob);
            initAttendanceStudents(dob);
        });
    }

    private void initAttendanceStudents(String date) {
        studentAttendances = new ArrayList<>();
        loadingDialog.show();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_ATTENDANCE)
                .child(Utils.userModel.getClassroom())
                .child(date)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingDialog.dismiss();
                        superList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Attendance attendance = child.getValue(Attendance.class);
                            if (attendance != null && attendance.getDate().equals(etDate.getText().toString()))
                                superList.add(attendance);

                        }
                        if (superList.isEmpty())
                            btnAdd.setVisibility(View.VISIBLE);
                        else
                            btnAdd.setVisibility(View.GONE);
                        typeRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initRv() {
        rvAttendance = findViewById(R.id.rv_regs);
        superList = new ArrayList<>();
        typeRecyclerViewAdapter
                = new TypeRecyclerViewAdapter(this, superList, RvType.RV_TYPE_ATTENDANCE);
        rvAttendance.setAdapter(typeRecyclerViewAdapter);
    }

    private void initListStudents() {
        students = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            UserModel userModel = child.getValue(UserModel.class);
                            if (userModel != null
                                    && userModel.getType().equals(PARENT)
                                    && userModel.getClassroom().equals(Utils.userModel.getClassroom()))
                                students.add(userModel);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void datePicker(View view) {
        datePickerDialog.show();
    }

    public void addAttendance(View view) {
        String date = etDate.getText().toString();
        if (date.isEmpty()) {
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
            return;
        }
        for (UserModel userModel : students) {
            String id = userModel.getId();
            String classroom = userModel.getClassroom();
            String studentId = userModel.getId();
            String studentName = userModel.getFirstName();
            String isPresent = "true";
            Attendance attendance = new Attendance(id,
                    date,
                    classroom,
                    studentId,
                    studentName,
                    isPresent);

            FirebaseDatabase.getInstance().getReference().child(NODE_ATTENDANCE)
                    .child(Utils.userModel.getClassroom())
                    .child(date)
                    .child(id)
                    .setValue(attendance)
                    .addOnCompleteListener(task -> initAttendanceStudents(date));
        }

    }

    public void back(View view) {
        finish();
    }
}