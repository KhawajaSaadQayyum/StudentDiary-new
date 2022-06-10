package com.app.studentdiary.models;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.app.studentdiary.R;
import com.app.studentdiary.info.Info;

public class UserModel extends Super {
    String id;
    String firstName;
    String lastName;
    String classroom;
    String verStatus;
    String email;
    String phoneNumber;
    String type;

    public UserModel() {
    }

    public UserModel(String id, String firstName, String lastName, String classroom,
                     String email, String phoneNumber, String type, String verStatus) {
        this.id = id;
        this.verStatus = verStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.classroom = classroom;
    }

    @BindingAdapter("android:setTextColor")
    public static void textColor(TextView textView, String verStatus) {
        if (verStatus.equals(Info.VER_APPROVED)) {
            textView.setTextColor(textView.getContext().getColor(R.color.green));
            textView.setText(Info.VER_APPROVED_DISP);
        }
        if (verStatus.equals(Info.VER_PENDING)) {
            textView.setTextColor(textView.getContext().getColor(R.color.yellow_orig));
            textView.setText(Info.VER_PENDING_DISP);
        }
        if (verStatus.equals(Info.VER_REJECTED)) {
            textView.setTextColor(textView.getContext().getColor(R.color.red));
            textView.setText(Info.VER_REJECTED_DISP);
        }
    }

    @BindingAdapter("android:setButtonApprove")
    public static void btnAppr(Button textView, String verStatus) {
        if (verStatus.equals(Info.VER_APPROVED))
            textView.setVisibility(View.GONE);

        if (verStatus.equals(Info.VER_PENDING))
            textView.setVisibility(View.VISIBLE);

        if (verStatus.equals(Info.VER_REJECTED))
            textView.setVisibility(View.GONE);
    }

    @BindingAdapter("android:setButtonReject")
    public static void btnReject(Button textView, String verStatus) {
        if (verStatus.equals(Info.VER_APPROVED))
            textView.setVisibility(View.GONE);

        if (verStatus.equals(Info.VER_PENDING))
            textView.setVisibility(View.VISIBLE);

        if (verStatus.equals(Info.VER_REJECTED))
            textView.setVisibility(View.GONE);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getVerStatus() {
        return verStatus;
    }

    public void setVerStatus(String verStatus) {
        this.verStatus = verStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
