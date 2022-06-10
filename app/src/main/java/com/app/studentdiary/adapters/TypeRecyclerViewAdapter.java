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
import com.app.studentdiary.databinding.ItemChatBinding;
import com.app.studentdiary.databinding.RvTeacherRegsBinding;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.info.RvType;
import com.app.studentdiary.models.ActivityPojo;
import com.app.studentdiary.models.Attendance;
import com.app.studentdiary.models.FeesPojo;
import com.app.studentdiary.models.Marks;
import com.app.studentdiary.models.MessagePojo;
import com.app.studentdiary.models.Super;
import com.app.studentdiary.models.UserModel;
import com.app.studentdiary.singletons.ActivitySingleton;
import com.app.studentdiary.singletons.CommentSingleton;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class TypeRecyclerViewAdapter extends RecyclerView.Adapter<TypeRecyclerViewHolder> implements Info {
    Context context;
    List<Super> listInstances;
    RvType type;

    public TypeRecyclerViewAdapter(Context context, List<Super> listInstances, RvType type) {
        this.context = context;
        this.listInstances = listInstances;
        this.type = type;
    }

    @NonNull
    @Override
    public TypeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = 0;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        /** Data binding used in following two cases  */
        if (type == RvType.RV_TYPE_TEACHER_REGS)
            return new TypeRecyclerViewHolder(RvTeacherRegsBinding.inflate(layoutInflater, parent, false));

        if (type == RvType.RV_TYPE_CHATS)
            return new TypeRecyclerViewHolder(ItemChatBinding.inflate(layoutInflater, parent, false));

        /**  inflating corresponding recyclerview layouts */
        if (type == RvType.RV_TYPE_COMMENTS)
            layout = R.layout.item_history;
        if (type == RvType.RV_TYPE_ATTENDANCE | type == RvType.RV_TYPE_STUDENT_ATTENDANCE | type == RvType.RV_TYPE_FEES)
            layout = R.layout.rv_attendance;

        if (type == RvType.RV_TYPE_STUDENT_MARKS | type == RvType.RV_TYPE_PARENT_MARKS)
            layout = R.layout.rv_marks;

        if (type == RvType.RV_TYPE_ACTIVITY
                | type == RvType.RV_TYPE_PARENT_ACTIVITY
                | type == RvType.RV_TYPE_SCHOOL_NOTICES
                | type == RvType.RV_TYPE_PARENT_SCHOOL_NOTICES)
            layout = R.layout.rv_activity;

        return new TypeRecyclerViewHolder(layoutInflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TypeRecyclerViewHolder holder, int position) {
        if (type == RvType.RV_TYPE_COMMENTS) {
            initComments(holder, position);
            return;
        }

        if (type == RvType.RV_TYPE_ACTIVITY
                | type == RvType.RV_TYPE_PARENT_ACTIVITY
                | type == RvType.RV_TYPE_PARENT_SCHOOL_NOTICES
                | type == RvType.RV_TYPE_SCHOOL_NOTICES) {
            initActivity(holder, position);
            return;
        }

        if (type == RvType.RV_TYPE_STUDENT_MARKS | type == RvType.RV_TYPE_PARENT_MARKS) {
            initMarks(holder, position);
            return;
        }
        if (type == RvType.RV_TYPE_ATTENDANCE | type == RvType.RV_TYPE_FEES) {
            initAttendance(holder, position);
            return;
        }
        if (type == RvType.RV_TYPE_STUDENT_ATTENDANCE) {
            initStudentAttendance(holder, position);
            return;
        }

        if (type == RvType.RV_TYPE_CHATS) {
            MessagePojo userModel = (MessagePojo) listInstances.get(position);
            holder.itemChatBinding.setMessage(userModel);
            holder.itemChatBinding.setAdapter(this);
            return;
        }

        UserModel userModel = (UserModel) listInstances.get(position);
        holder.rvTeacherRegsBinding.setUser(userModel);
        holder.rvTeacherRegsBinding.setAdapter(this);
    }

    private void initComments(TypeRecyclerViewHolder holder, int position) {
        MessagePojo messagePojo = (MessagePojo) listInstances.get(position);
        Log.i(TAG, "initChats: ");
        if (messagePojo.getActivityTitle() != null && !messagePojo.getActivityTitle().isEmpty()) {
            holder.tvActivityTitle.setVisibility(View.VISIBLE);
            holder.tvActivityTitle.setText(messagePojo.getActivityTitle());
            holder.tvActivity.setText("Activity - ");
            holder.tvActivity.setVisibility(View.VISIBLE);
        } else {
            holder.tvActivityTitle.setVisibility(View.GONE);
            holder.tvActivity.setVisibility(View.GONE);
        }

        holder.tvName.setText(messagePojo.getParentName());
        holder.tvMessage.setText(messagePojo.getMessageText());

        holder.cvClick.setOnClickListener(view -> {
            CommentSingleton.setInstance(messagePojo);
            context.startActivity(new Intent(context, ChatActivity.class));
        });

    }

    private void initActivity(TypeRecyclerViewHolder holder, int position) {
//      TODO: DOWN CASTING
        ActivityPojo activity = (ActivityPojo) listInstances.get(position);
        holder.tvTitle.setText(activity.getTitle());
        holder.tvDesc.setText(activity.getDesc());
        if (type == RvType.RV_TYPE_PARENT_ACTIVITY
                | type == RvType.RV_TYPE_PARENT_SCHOOL_NOTICES) {
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
        if (type.equals(RvType.RV_TYPE_SCHOOL_NOTICES)) {
            holder.cvDel.setOnClickListener(view ->
                    FirebaseDatabase.getInstance().getReference().child(NODE_SCHOOL_NOTICES)
                            .child(activity.getId())
                            .removeValue());
            holder.btnDel.setOnClickListener(view ->
                    FirebaseDatabase.getInstance().getReference().child(NODE_SCHOOL_NOTICES)
                            .child(activity.getId())
                            .removeValue());
            return;
        }

        if (type.equals(RvType.RV_TYPE_PARENT_SCHOOL_NOTICES))
            return;

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
        holder.tvStudentName.setText(marks.getStudentName());
        holder.tvMarks.setText(marks.getPercentage());
        holder.tvMarks.setFocusableInTouchMode(false);
        if (type == RvType.RV_TYPE_PARENT_MARKS) {
            holder.tvStudentName.setText(marks.getSubject());
            return;
        }
        holder.tvMarks.setFocusableInTouchMode(true);
        holder.tvMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (holder.tvMarks.getText().toString().length() >= 2) {
                    if (holder.tvMarks.getText().toString().length() >= 3) {
                        Toast.makeText(context, "Cannot enter more than 2 digits", Toast.LENGTH_SHORT).show();
                        holder.tvMarks.setText("");
                        holder.tvMarks.clearFocus();
                        return;
                    }
                    holder.tvMarks.clearFocus();
                    marks.setPercentage(holder.tvMarks.getText().toString());
                    FirebaseDatabase.getInstance().getReference()
                            .child(NODE_MARKS)
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
        if (type == RvType.RV_TYPE_FEES) {
//            TODO: Work should be done here
            FeesPojo feesPojo = (FeesPojo) listInstances.get(position);
            holder.tvStudentName.setText(feesPojo.getStudentName());
            holder.cbPresent.setChecked(Boolean.parseBoolean(feesPojo.getStatus()));
            holder.cbPresent.setOnClickListener(view -> {
                feesPojo.setStatus(String.valueOf(holder.cbPresent.isChecked()));
                FirebaseDatabase.getInstance().getReference()
                        .child(NODE_FEES)
                        .child(feesPojo.getDate())
                        .child(feesPojo.getStudentClass())
                        .child(attendance.getId())
                        .setValue(attendance);
            });

            return;
        }

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

    public void apprClick(View view, UserModel userModel) {
        userModel.setVerStatus(Info.VER_APPROVED);
        updateUser(userModel);
    }

    public void rejectClick(View view, UserModel userModel) {
        userModel.setVerStatus(Info.VER_REJECTED);
        updateUser(userModel);
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
