package com.app.studentdiary.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.studentdiary.R;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.models.UserModel;
import com.app.studentdiary.utils.DialogUtils;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * Email = studentd618@gmail.com
 * Password = finalyearproject
 */

public class LoginActivity extends AppCompatActivity implements Info {

    public static Activity context;
    EditText etEmail;
    EditText etPassword;
    String strEtEmail;
    String strEtPassword;
    boolean isPassVisible = false;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_pass);

        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            loadingDialog.show();
            parseUserData();
        }

    }

    private void parseUserData() {
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingDialog.dismiss();
                        Utils.userModel = snapshot.getValue(UserModel.class);
                        initUser(Utils.userModel);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void initUser(UserModel userModel) {
        Log.i(TAG, "initUser: ");
        if (userModel != null) {
            Log.i(TAG, "initUser: " + userModel.getType());
            Utils.userModel = userModel;
            switch (Utils.userModel.getType()) {
                case PARENT:
                    finish();
                    startActivity(new Intent(LoginActivity.this, ParentDashboard.class));
                    break;
                case TEACHER:
                    switch (Utils.userModel.getVerStatus()) {
                        case VER_APPROVED:
                            finish();
                            startActivity(new Intent(LoginActivity.this, TeacherDashboard.class));
                            break;
                        case VER_REJECTED:
                            Toast.makeText(LoginActivity.this, "Your verification is rejected", Toast.LENGTH_SHORT).show();
                            break;
                        case VER_PENDING:
                            Toast.makeText(LoginActivity.this, "Your verification is still pending", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
                case ADMIN:
                    startActivity(new Intent(LoginActivity.this, AdminDashboard.class));
                    finish();
                    break;
                default:
                    Toast.makeText(LoginActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    public void signUp(View view) {
        startActivity(new Intent(this, Registration.class));
    }

    public void showPassword(View view) {
        if (!isPassVisible) {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isPassVisible = true;
        } else {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isPassVisible = false;
        }

    }


    private void castStrings() {
        strEtEmail = etEmail.getText().toString().trim();
        strEtPassword = etPassword.getText().toString();
    }

    private boolean isEverythingValid() {
        if (!Utils.validEt(etEmail, strEtEmail))
            return false;
        return Utils.validEt(etPassword, strEtPassword);
    }

    public void Login(View view) {
        castStrings();
        if (!isEverythingValid())
            return;
        loadingDialog.show();
        initSignIn();
    }

    private void initSignIn() {
        loadingDialog.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(strEtEmail, strEtPassword)
                .addOnCompleteListener(task -> {
                    Log.i(TAG, "initSignIn: RESPONDED");
                    loadingDialog.dismiss();
                    if (task.isSuccessful()) {
                        initUserData();
                    } else {
                        Log.i(TAG, "initSignIn: " + task.getException().getMessage());
                    }
                });
    }

    private void initUserData() {
        loadingDialog.show();
        Log.i(TAG, "initUserData: CHECK USER DATA");
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingDialog.dismiss();
                        Utils.userModel = snapshot.getValue(UserModel.class);
                        initUser(Utils.userModel);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}