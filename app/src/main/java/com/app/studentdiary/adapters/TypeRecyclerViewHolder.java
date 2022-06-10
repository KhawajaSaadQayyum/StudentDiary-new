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
import com.app.studentdiary.databinding.ItemChatBinding;
import com.app.studentdiary.databinding.RvTeacherRegsBinding;


public class TypeRecyclerViewHolder extends RecyclerView.ViewHolder {

    RvTeacherRegsBinding rvTeacherRegsBinding;
    ItemChatBinding itemChatBinding;

    Button btnReject;
    Button btnApprove;
    TextView tvTeacherName;
    TextView tvVerStatus;
    TextView tvClassroom;
    EditText tvMarks;

    TextView tvStudentName;
    TextView tvActivity;
    TextView tvActivityTitle;
    TextView tvName;
    TextView tvMessage;
    CheckBox cbPresent;

    TextView tvTitle;
    TextView tvDesc;
    CardView cvDel;
    CardView cvChat;
    CardView cvClick;
    Button btnDel;
    Button btnChat;

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

        tvActivityTitle = itemView.findViewById(R.id.tv_activity_title);
        tvActivity = itemView.findViewById(R.id.tv_activity);
        tvName = itemView.findViewById(R.id.tv_name);
        tvMessage = itemView.findViewById(R.id.tv_message);

        tvTitle = itemView.findViewById(R.id.tv_title);
        tvDesc = itemView.findViewById(R.id.tv_desc);
        cvDel = itemView.findViewById(R.id.cv_delete);
        cvClick = itemView.findViewById(R.id.card);
        btnDel = itemView.findViewById(R.id.btn_del);
        cvChat = itemView.findViewById(R.id.cv_chat);
        btnChat = itemView.findViewById(R.id.btn_chat);
    }

    public TypeRecyclerViewHolder(@NonNull RvTeacherRegsBinding rvTeacherRegsBinding) {
        super(rvTeacherRegsBinding.getRoot());
        this.rvTeacherRegsBinding = rvTeacherRegsBinding;
    }

    public TypeRecyclerViewHolder(@NonNull ItemChatBinding itemChatBinding) {
        super(itemChatBinding.getRoot());
        this.itemChatBinding = itemChatBinding;
    }

}


