package com.app.studentdiary.activities;

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
import com.app.studentdiary.models.Super;
import com.app.studentdiary.models.UserModel;
import com.app.studentdiary.utils.DialogUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminRegistrationApproval extends AppCompatActivity {
    RecyclerView rvTeacherRegs;
    List<Super> superList;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;

    TextView tvData;

    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration_approval);
        tvData = findViewById(R.id.tv_data);

        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);

        initRv();
        initData();

    }

    private void initData() {
        loadingDialog.show();
        FirebaseDatabase.getInstance().getReference().child(Info.NODE_USERS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        superList.clear();
                        loadingDialog.dismiss();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            UserModel userModel = child.getValue(UserModel.class);
                            if (userModel != null && userModel.getType().equals(Info.TEACHER))
                                superList.add(userModel);
                        }
                        if (superList.isEmpty())
                            tvData.setVisibility(View.VISIBLE);
                        else
                            tvData.setVisibility(View.GONE);

                        typeRecyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void initRv() {
        rvTeacherRegs = findViewById(R.id.rv_regs);
        superList = new ArrayList<>();
        typeRecyclerViewAdapter
                = new TypeRecyclerViewAdapter(this, superList, Info.RV_TYPE_TEACHER_REGS);
        rvTeacherRegs.setAdapter(typeRecyclerViewAdapter);
    }
}