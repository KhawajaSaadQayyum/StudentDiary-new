package com.app.studentdiary.activities.admin;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
import com.app.studentdiary.adapters.TypeRecyclerViewAdapter;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.info.RvType;
import com.app.studentdiary.models.FeesPojo;
import com.app.studentdiary.models.Super;
import com.app.studentdiary.models.UserModel;
import com.app.studentdiary.utils.DialogUtils;
import com.app.studentdiary.utils.MonthPicker;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeeStatusActivity extends AppCompatActivity implements Info {

    String strEtDate;
    String strSpnClass;

    EditText et_date;
    Spinner spnClass;

    RecyclerView rvAttendance;
    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    Dialog loadingDialog;

    Button btnAddFees;

    List<Super> superList;
    List<UserModel> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_status);

        spnClass = findViewById(R.id.spn_class);
        et_date = findViewById(R.id.et_date);
        btnAddFees = findViewById(R.id.btn_add);
        btnAddFees.setVisibility(View.GONE);

        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        initRv();
        initClassSpinner();
    }

    private void initClassSpinner() {
        allUsers = new ArrayList<>();
        List<String> classes = new ArrayList<>();
        Utils.getReference().child(NODE_USERS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        classes.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            UserModel userModel = child.getValue(UserModel.class);
                            if (userModel == null)
                                continue;
                            Log.i(TAG, "onDataChange: " + userModel.getType());

                            allUsers.add(userModel);

                            if (userModel.getType().equals(TEACHER) && userModel.getVerStatus().equals(VER_APPROVED)) {
                                classes.add(userModel.getClassroom());
                                Log.i(TAG, "onDataChange: ADDING CLASSES ");
                            }
                        }
                        initSpinnerData(classes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void initSpinnerData(List<String> stringList) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spnClass.setAdapter(spinnerArrayAdapter);
        spnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (et_date.getText().toString().isEmpty())
                    Toast.makeText(FeeStatusActivity.this, "Please select a date first", Toast.LENGTH_SHORT).show();
                else
                    initData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initData() {
        String date = et_date.getText().toString();
        if (date.isEmpty()) {
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_FEES)
                .child(date)
                .child(spnClass.getSelectedItem().toString())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        superList.clear();
                        for (DataSnapshot child : snapshot.getChildren()) {
                            FeesPojo activityPojo = child.getValue(FeesPojo.class);
                            superList.add(activityPojo);
                        }
                        if (superList.isEmpty())
                            btnAddFees.setVisibility(View.VISIBLE);
                        else
                            btnAddFees.setVisibility(View.GONE);

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
                = new TypeRecyclerViewAdapter(this, superList, RvType.RV_TYPE_FEES);
        rvAttendance.setAdapter(typeRecyclerViewAdapter);
    }

    public void back(View view) {
        finish();
    }

    private void castStrings() {
        strEtDate = et_date.getText().toString();
        strSpnClass = spnClass.getSelectedItem().toString();
    }

    public void monthPicker(View view) {
        MonthPicker pd = new MonthPicker();
        pd.setListener((datePicker, i, i1, i2) -> {
            et_date.setText(i1 + "-" + i);
            initData();
        });
        pd.show(getSupportFragmentManager(), "");
    }

    public void addFees(View view) {
        castStrings();
        if (!isEverythingValid())
            return;
        List<UserModel> targetClassUsers = new ArrayList<>();
        for (UserModel targetUser : allUsers)
            if (targetUser.getClassroom().equals(strSpnClass))
                targetClassUsers.add(targetUser);
        for (UserModel classUser : targetClassUsers) {
            FeesPojo feesPojo = new FeesPojo(classUser.getFirstName(),
                    classUser.getId(),
                    strEtDate,
                    strSpnClass,
                    "false");

            Utils.getReference().child(NODE_FEES)
                    .child(strEtDate)
                    .child(strSpnClass)
                    .child(classUser.getId())
                    .setValue(feesPojo);
        }
        Log.i(TAG, "addFees: Fees should be added");

    }

    private boolean isEverythingValid() {
        if (!Utils.validEt(et_date, strEtDate))
            return false;

        if (strSpnClass.isEmpty()) {
            Toast.makeText(this, "Class not selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

