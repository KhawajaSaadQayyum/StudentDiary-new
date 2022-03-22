package com.app.studentdiary.activities;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
import com.app.studentdiary.adapters.TypeRecyclerViewAdapter;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.models.Attendance;
import com.app.studentdiary.models.Super;
import com.app.studentdiary.utils.DialogUtils;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParentAttendance extends AppCompatActivity implements Info {
    RecyclerView rvAttendance;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    Dialog loadingDialog;
    List<Super> superList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_attendance);
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        initRv();
        initData();
    }

    private void initData() {
        FirebaseDatabase.getInstance().getReference().child(NODE_ATTENDANCE)
                .child(Utils.userModel.getClassroom())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()) {
                            for (DataSnapshot grandChild : child.getChildren()) {
                                Attendance attendance = grandChild.getValue(Attendance.class);
                                if (attendance != null)
                                    if (attendance.getStudentId().equals(Utils.getCurrentUserId()))
                                        superList.add(attendance);
                            }
                        }
                        typeRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initRv() {
        rvAttendance = findViewById(R.id.rv_attend);
        superList = new ArrayList<>();
        typeRecyclerViewAdapter
                = new TypeRecyclerViewAdapter(this, superList, Info.RV_TYPE_STUDENT_ATTENDANCE);
        rvAttendance.setAdapter(typeRecyclerViewAdapter);
    }
}
