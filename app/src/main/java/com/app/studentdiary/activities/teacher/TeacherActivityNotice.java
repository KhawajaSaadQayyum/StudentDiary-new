package com.app.studentdiary.activities.teacher;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
import com.app.studentdiary.activities.admin.AdminNotices;
import com.app.studentdiary.adapters.TypeRecyclerViewAdapter;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.info.RvType;
import com.app.studentdiary.models.ActivityPojo;
import com.app.studentdiary.models.Super;
import com.app.studentdiary.utils.DialogUtils;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeacherActivityNotice extends AppCompatActivity implements Info {

    EditText etTitle;
    EditText etDesc;
    String strEtTitle;
    String strEtDesc;

    RecyclerView rvAttendance;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    Dialog loadingDialog;

    List<Super> superList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_notice);
        etTitle = findViewById(R.id.et_title);
        etDesc = findViewById(R.id.et_desc);
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        initRv();
        initData();

    }

    private void initData() {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_ACTIVITY)
                .child(Utils.userModel.getClassroom())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        superList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            ActivityPojo activityPojo = child.getValue(ActivityPojo.class);
                            superList.add(activityPojo);
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
                = new TypeRecyclerViewAdapter(this, superList, RvType.RV_TYPE_ACTIVITY);
        rvAttendance.setAdapter(typeRecyclerViewAdapter);
    }

    public void back(View view) {
        finish();
    }

    public void submit(View view) {
        castStrings();
        if (!Utils.validEt(etDesc, strEtDesc))
            return;
        if (!Utils.validEt(etTitle, strEtTitle))
            return;
        String id = superList.size() + "";
        FirebaseDatabase.getInstance().getReference().child(NODE_ACTIVITY)
                .child(Utils.userModel.getClassroom())
                .child(id)
                .setValue(new ActivityPojo(id, Utils.userModel.getClassroom(), strEtTitle, strEtDesc))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        etDesc.setText("");
                        etTitle.setText("");
                        Toast.makeText(TeacherActivityNotice.this, "Notice Submitted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TeacherActivityNotice.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void castStrings() {
        strEtDesc = etDesc.getText().toString();
        strEtTitle = etTitle.getText().toString();
    }
}