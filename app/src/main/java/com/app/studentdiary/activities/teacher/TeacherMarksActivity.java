package com.app.studentdiary.activities.teacher;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
import com.app.studentdiary.adapters.TypeRecyclerViewAdapter;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.info.RvType;
import com.app.studentdiary.models.Marks;
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

public class TeacherMarksActivity extends AppCompatActivity implements Info {
    Spinner spnDate;
    List<UserModel> students;
    List<Super> superList;
    List<Marks> studentAttendances;
    Button btnAdd;

    RecyclerView rvAttendance;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_marks);

        initRv();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        initListStudents();
        spnDate = findViewById(R.id.et_date);
        btnAdd = findViewById(R.id.btn_add);
        addMarks(null);
        spnDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnDate.getSelectedItem().toString().equals("Select Subject"))
                    return;
                initMarksStudents(spnDate.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initListStudents() {
        students = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        students.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            UserModel userModel = child.getValue(UserModel.class);
                            if (userModel != null && userModel.getType().equals(PARENT)
                                    & userModel.getClassroom().equals(Utils.userModel.getClassroom()))
                                students.add(userModel);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initMarksStudents(String subject) {
        studentAttendances = new ArrayList<>();
        loadingDialog.show();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_MARKS)
                .child(Utils.userModel.getClassroom())
                .child(subject)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingDialog.dismiss();
                        superList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            Marks attendance = child.getValue(Marks.class);
                            superList.add(attendance);
                        }
                        if (superList.isEmpty()) {
                            btnAdd.setVisibility(View.VISIBLE);
                            btnAdd.setText("Click to add results");
                        } else
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
                = new TypeRecyclerViewAdapter(this, superList, RvType.RV_TYPE_STUDENT_MARKS);
        rvAttendance.setAdapter(typeRecyclerViewAdapter);
    }

    public void addMarks(View view) {
        if (spnDate.getSelectedItem().toString().equals("Select Subject")) {
            btnAdd.setText("Please select a subject");
            return;
        }

        for (UserModel userModel : students) {
            String id = userModel.getId();
            String classroom = userModel.getClassroom();
            String studentId = userModel.getId();
            String studentName = userModel.getFirstName();
            Marks attendance = new Marks(id,
                    spnDate.getSelectedItem().toString(),
                    "-",
                    studentId + "",
                    studentName + "",
                    classroom + "");
            FirebaseDatabase.getInstance().getReference().child(NODE_MARKS)
                    .child(Utils.userModel.getClassroom())
                    .child(spnDate.getSelectedItem().toString())
                    .child(id)
                    .setValue(attendance);
        }

    }

    public void back(View view) {
        finish();
    }

    public void submit(View view) {

    }
}
