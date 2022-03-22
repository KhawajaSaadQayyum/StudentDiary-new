package com.app.studentdiary.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.models.UserModel;
import com.app.studentdiary.utils.DialogUtils;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class Registration extends AppCompatActivity implements Info {

    public static Activity context;

    public static UserModel userModel;
    public static String strEtPassword;
    boolean isPassVisible = false;

    EditText etEmail;
    EditText etPhone;
    EditText etPassword;
    EditText etConfirmPassword;
    EditText etFirstName;
    EditText etLastName;
    EditText etClassroom;

    String strEtFirstName;
    String strEtClassroom;
    String strEtLastName;
    String strEtEmail;
    String strEtPhone;
    String strEtConfirmPassword;

    CountryCodePicker cpp;

    RadioButton rbParent;
    RadioButton rbTeacher;

    Dialog dgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        context = this;
        initViews();
        dgLoading = new Dialog(this);
        DialogUtils.initLoadingDialog(dgLoading);
    }

    public void showPassword(View view) {
        if (!isPassVisible) {
            etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isPassVisible = true;
        } else {
            etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isPassVisible = false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void castStrings() {
        strEtFirstName = etFirstName.getText().toString();
        strEtLastName = etLastName.getText().toString();
        strEtEmail = etEmail.getText().toString();
        strEtPhone = etPhone.getText().toString();
        strEtPassword = etPassword.getText().toString();
        strEtConfirmPassword = etConfirmPassword.getText().toString();
        strEtClassroom = etClassroom.getText().toString();
    }

    private void initViews() {
        etEmail = findViewById(R.id.et_user_name);
        etPhone = findViewById(R.id.et_phone);
        etClassroom = findViewById(R.id.et_class_room);
        etPassword = findViewById(R.id.et_pass);
        etConfirmPassword = findViewById(R.id.et_confirm_pass);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        cpp = findViewById(R.id.ccp);
        rbParent = findViewById(R.id.rb_salon_manager);
        rbTeacher = findViewById(R.id.rb_customer);
        rbParent.setChecked(true);
    }

    public void back(View view) {
        finish();
    }

    public void SignUpWithPhone(View view) {
        castStrings();
        if (!Utils.validEt(etFirstName, strEtFirstName))
            return;

        if (!Utils.validEt(etLastName, strEtLastName))
            return;

        if (!Utils.validEt(etEmail, strEtEmail))
            return;

        if (!Utils.validEt(etPhone, strEtPhone))
            return;

        if (!strEtPassword.equals(strEtConfirmPassword))
            return;

        if (!Utils.validEt(etClassroom, strEtClassroom))
            return;

        String type = PARENT;
        String verStatus = VER_APPROVED;
        if (rbTeacher.isChecked()) {
            type = TEACHER;
            verStatus = VER_PENDING;
        }
        if (rbParent.isChecked()) {
            type = PARENT;
            verStatus = VER_APPROVED;
        }

        String id = "sad";
        userModel = new UserModel(id, strEtFirstName, strEtLastName, strEtClassroom,
                strEtEmail.trim(), "+" + cpp.getSelectedCountryCode() + strEtPhone, type, verStatus);

        dgLoading.show();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(strEtEmail, strEtConfirmPassword)
                .addOnCompleteListener(task -> {
                    dgLoading.dismiss();
                    if (task.isSuccessful()) {
                        userModel.setId(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
                        initData(userModel);
                    } else
                        Toast.makeText(Registration.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                });
    }

    private void initData(UserModel userModel) {
        dgLoading.show();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(userModel)
                .addOnCompleteListener(task -> {
                    dgLoading.dismiss();
                    if (task.isSuccessful()) {
                        if (userModel.getType().equals(TEACHER)) {
                            Toast.makeText(this, "Registration pending", Toast.LENGTH_SHORT).show();
                            finish();
                        } else if (userModel.getType().equals(PARENT)) {
                            startActivity(new Intent(Registration.this, ParentDashboard.class));
                            LoginActivity.context.finish();
                            finish();
                        }
                    } else
                        Toast.makeText(Registration.this, "ERROR OCCURRED", Toast.LENGTH_SHORT).show();

                });
    }


}