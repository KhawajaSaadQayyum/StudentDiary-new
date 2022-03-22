package com.app.studentdiary.adapters;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;


public class TypeRecyclerViewHolder extends RecyclerView.ViewHolder {

    Button btnReject;
    Button btnApprove;
    TextView tvTeacherName;
    TextView tvVerStatus;
    TextView tvClassroom;
    EditText tvMarks;

    TextView tvStudentName;
    CheckBox cbPresent;

    TextView tvTitle;
    TextView tvDesc;
    CardView cvDel;
    Button btnDel;

    public TypeRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        btnApprove = itemView.findViewById(R.id.btn_approve);
        btnReject = itemView.findViewById(R.id.btn_reject);
        tvTeacherName = itemView.findViewById(R.id.tv_teacher_name);
        tvClassroom = itemView.findViewById(R.id.tv_class_room);
        tvVerStatus = itemView.findViewById(R.id.tv_regs);

        tvStudentName = itemView.findViewById(R.id.tv_student);
        cbPresent = itemView.findViewById(R.id.cb_present);
        tvMarks = itemView.findViewById(R.id.et_marks);

        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDesc = itemView.findViewById(R.id.tv_desc);
        cvDel = itemView.findViewById(R.id.cv_delete);
        btnDel = itemView.findViewById(R.id.btn_del);
    }

}


