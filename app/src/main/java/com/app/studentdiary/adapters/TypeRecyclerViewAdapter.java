package com.app.studentdiary.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
import com.app.studentdiary.activities.ChatActivity;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.models.ActivityPojo;
import com.app.studentdiary.models.Attendance;
import com.app.studentdiary.models.Marks;
import com.app.studentdiary.models.Message;
import com.app.studentdiary.models.Super;
import com.app.studentdiary.models.UserModel;
import com.app.studentdiary.singletons.ActivitySingleton;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class TypeRecyclerViewAdapter extends RecyclerView.Adapter<TypeRecyclerViewHolder> implements Info {
    Context context;
    List<Super> listInstances;
    int type;

    public TypeRecyclerViewAdapter(Context context, List<Super> listInstances, int type) {
        this.context = context;
        this.listInstances = listInstances;
        this.type = type;
    }

    @NonNull
    @Override
    public TypeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.rv_teacher_regs;

        if (type == RV_TYPE_CHATS)
            layout = R.layout.item_chat;
        if (type == RV_TYPE_ATTENDANCE | type == RV_TYPE_STUDENT_ATTENDANCE)
            layout = R.layout.rv_attendance;
        if (type == RV_TYPE_STUDENT_MARKS | type == RV_TYPE_PARENT_MARKS)
            layout = R.layout.rv_marks;
        if (type == RV_TYPE_ACTIVITY | type == RV_TYPE_PARENT_ACTIVITY)
            layout = R.layout.rv_activity;

        return new TypeRecyclerViewHolder(LayoutInflater.from(context)
                .inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TypeRecyclerViewHolder holder, int position) {
        if (type == RV_TYPE_CHATS) {
            initChats(holder, position);
            return;
        }
        if (type == RV_TYPE_ACTIVITY | type == RV_TYPE_PARENT_ACTIVITY) {
            initActivity(holder, position);
            return;
        }

        if (type == RV_TYPE_STUDENT_MARKS | type == RV_TYPE_PARENT_MARKS) {
            initMarks(holder, position);
            return;
        }
        if (type == RV_TYPE_ATTENDANCE) {
            initAttendance(holder, position);
            return;
        }
        if (type == RV_TYPE_STUDENT_ATTENDANCE) {
            initStudentAttendance(holder, position);
            return;
        }

        initRegs(holder, position);
    }

    private void initChats(TypeRecyclerViewHolder holder, int position) {
        Message message = (Message) listInstances.get(position);
        Log.i(TAG, "initChats: ");
        if (message.getActivityTitle() != null && !message.getActivityTitle().isEmpty()) {
            holder.tvActivityTitle.setVisibility(View.VISIBLE);
            holder.tvActivityTitle.setText(message.getActivityTitle());
            holder.tvActivity.setText("Context - ");
            holder.tvActivity.setVisibility(View.VISIBLE);
        } else {
            holder.tvActivityTitle.setVisibility(View.GONE);
            holder.tvActivity.setVisibility(View.GONE);
        }

        if (message.getAuthor().equals(message.getParentId()))
            holder.tvName.setText(message.getParentName());
        else
            holder.tvName.setText(message.getTeacherName());

        holder.tvMessage.setText(message.getMessageText());


    }

    private void initActivity(TypeRecyclerViewHolder holder, int position) {
//      TODO: DOWN CASTING
        ActivityPojo activity = (ActivityPojo) listInstances.get(position);
        holder.tvTitle.setText(activity.getTitle());
        holder.tvDesc.setText(activity.getDesc());
        if (type == RV_TYPE_PARENT_ACTIVITY) {
            holder.cvDel.setVisibility(View.GONE);
            holder.btnDel.setVisibility(View.GONE);
            holder.cvChat.setVisibility(View.VISIBLE);
            holder.btnChat.setVisibility(View.VISIBLE);
        } else {
            holder.cvDel.setVisibility(View.VISIBLE);
            holder.btnDel.setVisibility(View.VISIBLE);
            holder.cvChat.setVisibility(View.GONE);
            holder.btnChat.setVisibility(View.GONE);
        }

        holder.cvDel.setOnClickListener(view -> FirebaseDatabase.getInstance().getReference().child(NODE_ACTIVITY)
                .child(Utils.userModel.getClassroom())
                .child(activity.getId())
                .removeValue());
        holder.btnDel.setOnClickListener(view -> FirebaseDatabase.getInstance().getReference().child(NODE_ACTIVITY)
                .child(Utils.userModel.getClassroom())
                .child(activity.getId())
                .removeValue());

        holder.btnChat.setOnClickListener(view -> {
            ActivitySingleton.setInstance(activity);
            context.startActivity(new Intent(context, ChatActivity.class));
        });


    }

    private void initMarks(TypeRecyclerViewHolder holder, int position) {
//        TODO: DOWN CASTING
        Marks marks = (Marks) listInstances.get(position);
        holder.tvMarks.setText(marks.getPercentage());
        holder.tvMarks.setFocusableInTouchMode(false);
        if (type == RV_TYPE_PARENT_MARKS) {
            holder.tvStudentName.setText(marks.getSubject());
            return;
        }
        holder.tvMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (holder.tvMarks.getText().toString().length() >= 2) {
                    holder.tvMarks.clearFocus();
                    marks.setPercentage(holder.tvMarks.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child(NODE_MARKS)
                            .child(marks.getClassRoom())
                            .child(marks.getSubject())
                            .child(marks.getStudentId())
                            .setValue(marks);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initStudentAttendance(TypeRecyclerViewHolder holder, int position) {
        Attendance attendance = (Attendance) listInstances.get(position);
        holder.tvStudentName.setText(attendance.getDate());
        holder.cbPresent.setChecked(Boolean.parseBoolean(attendance.getIsPresent()));
        holder.cbPresent.setClickable(false);
    }

    private void initAttendance(TypeRecyclerViewHolder holder, int position) {
        Attendance attendance = (Attendance) listInstances.get(position);
        holder.tvStudentName.setText(attendance.getStudentName());
        holder.cbPresent.setChecked(Boolean.parseBoolean(attendance.getIsPresent()));
        holder.cbPresent.setOnClickListener(view -> {
            attendance.setIsPresent(String.valueOf(holder.cbPresent.isChecked()));
            FirebaseDatabase.getInstance().getReference()
                    .child(NODE_ATTENDANCE)
                    .child(attendance.getClassroom())
                    .child(attendance.getDate())
                    .child(attendance.getId())
                    .setValue(attendance);
        });


    }


    private void initRegs(TypeRecyclerViewHolder holder, int position) {
        UserModel userModel = (UserModel) listInstances.get(position);
        holder.tvClassroom.setText(userModel.getClassroom());
        String name = userModel.getFirstName() + userModel.getLastName();
        holder.tvTeacherName.setText(name);
        if (userModel.getVerStatus().equals(Info.VER_APPROVED)) {
            holder.btnApprove.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
            holder.tvVerStatus.setTextColor(context.getColor(R.color.green));
        }
        if (userModel.getVerStatus().equals(Info.VER_PENDING)) {
            holder.btnApprove.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.VISIBLE);
            holder.tvVerStatus.setTextColor(context.getColor(R.color.yellow_orig));
        }
        if (userModel.getVerStatus().equals(Info.VER_REJECTED)) {
            holder.btnApprove.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
            holder.tvVerStatus.setTextColor(context.getColor(R.color.red));
        }

        holder.btnReject.setOnClickListener(view -> {
            userModel.setVerStatus(Info.VER_REJECTED);
            updateUser(userModel);
        });
        holder.btnApprove.setOnClickListener(view -> {
            userModel.setVerStatus(Info.VER_APPROVED);
            updateUser(userModel);
        });

    }

    private void updateUser(UserModel userModel) {
        FirebaseDatabase.getInstance().getReference().child(NODE_USERS)
                .child(userModel.getId())
                .setValue(userModel).addOnCompleteListener(task -> {
            if (!task.isSuccessful())
                Toast.makeText(context, "Unexpected", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return listInstances.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
