package com.app.studentdiary.activities.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
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
import java.util.Collections;
import java.util.List;

public class AdminNotices extends AppCompatActivity implements Info {

    EditText etTitle;
    EditText etDesc;
    String strEtTitle;
    String strEtDesc;
    TextView tvNoData;

    RecyclerView rvAttendance;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    Dialog loadingDialog;

    List<Super> superList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notices);
        etTitle = findViewById(R.id.et_title);
        etDesc = findViewById(R.id.et_desc);
        tvNoData = findViewById(R.id.textView_no);
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        initRv();
        initData();


    }

    private void initData() {
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_SCHOOL_NOTICES)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        superList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            ActivityPojo activityPojo = child.getValue(ActivityPojo.class);
                            superList.add(activityPojo);
                        }
                        Collections.reverse(superList);
                        if (superList.isEmpty())
                            tvNoData.setVisibility(View.VISIBLE);
                        else
                            tvNoData.setVisibility(View.GONE);

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
                = new TypeRecyclerViewAdapter(this, superList, RvType.RV_TYPE_SCHOOL_NOTICES);
        rvAttendance.setAdapter(typeRecyclerViewAdapter);
    }

    public void back(View view) {
        finish();
    }

    public void submit(View view) {
        Log.i(TAG, "submit: BUTTON SUBMITTED");
        castStrings();
        if (!Utils.validEt(etDesc, strEtDesc))
            return;
        if (!Utils.validEt(etTitle, strEtTitle))
            return;
        Log.i(TAG, "submit: INIT FIREBASE");

        String id = superList.size() + "";
        FirebaseDatabase.getInstance().getReference().child(NODE_SCHOOL_NOTICES)
                .child(id)
                .setValue(new ActivityPojo(id, Utils.userModel.getClassroom(), strEtTitle, strEtDesc))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        etDesc.setText("");
                        etTitle.setText("");
                        Toast.makeText(AdminNotices.this, "Notice Submitted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminNotices.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void castStrings() {
        strEtDesc = etDesc.getText().toString();
        strEtTitle = etTitle.getText().toString();
    }
}