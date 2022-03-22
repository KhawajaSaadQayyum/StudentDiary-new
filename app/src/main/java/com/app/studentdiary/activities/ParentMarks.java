package com.app.studentdiary.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
import com.app.studentdiary.adapters.TypeRecyclerViewAdapter;
import com.app.studentdiary.info.Info;
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

public class ParentMarks extends AppCompatActivity implements Info {
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
        setContentView(R.layout.activity_parent_marks);
        initRv();
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        initMarks();

    }

    private void initMarks() {
        FirebaseDatabase.getInstance().getReference().child(NODE_MARKS)
                .child(Utils.userModel.getClassroom())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren())
                            for (DataSnapshot grandChild : child.getChildren()) {
                                Marks marks = grandChild.getValue(Marks.class);
                                if (marks != null && marks.getStudentId().equals(Utils.userModel.getId()))
                                    superList.add(marks);
                            }
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
                = new TypeRecyclerViewAdapter(this, superList, Info.RV_TYPE_PARENT_MARKS);
        rvAttendance.setAdapter(typeRecyclerViewAdapter);
    }
}