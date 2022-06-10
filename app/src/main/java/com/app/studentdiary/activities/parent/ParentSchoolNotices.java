package com.app.studentdiary.activities.parent;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ParentSchoolNotices extends AppCompatActivity implements Info {

    RecyclerView rvAttendance;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    Dialog loadingDialog;
    TextView tvNoData;
    List<Super> superList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_school_notices);
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
                = new TypeRecyclerViewAdapter(this, superList, RvType.RV_TYPE_PARENT_SCHOOL_NOTICES);
        rvAttendance.setAdapter(typeRecyclerViewAdapter);
    }

    public void back(View view) {
        finish();
    }
}