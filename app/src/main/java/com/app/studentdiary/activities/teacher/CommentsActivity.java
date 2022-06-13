package com.app.studentdiary.activities.teacher;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.app.studentdiary.R;
import com.app.studentdiary.adapters.TypeRecyclerViewAdapter;
import com.app.studentdiary.info.Info;
import com.app.studentdiary.info.RvType;
import com.app.studentdiary.models.MessagePojo;
import com.app.studentdiary.models.Super;
import com.app.studentdiary.utils.DialogUtils;
import com.app.studentdiary.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity implements Info {

    RecyclerView recyclerView;
    List<Super> superList;

    TypeRecyclerViewAdapter typeRecyclerViewAdapter;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

//        Initializing recyclerview and dialog boxes
        recyclerView = findViewById(R.id.rv_comments);
        loadingDialog = new Dialog(this);
        DialogUtils.initLoadingDialog(loadingDialog);
        initData();
        initRv();
    }

    private void initRv() {
        recyclerView = findViewById(R.id.rv_comments);
        superList = new ArrayList<>();
        typeRecyclerViewAdapter
                = new TypeRecyclerViewAdapter(this, superList, RvType.RV_TYPE_COMMENTS);
        recyclerView.setAdapter(typeRecyclerViewAdapter);
    }

    private void initData() {
//        Fetching data from firebase
        DatabaseReference reference = Utils.getReference().child(NODE_CHATS).child(Utils.userModel.getClassroom());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren())
                    for (DataSnapshot grandChild : child.getChildren()) {
                        MessagePojo message = grandChild.getValue(MessagePojo.class);
                        superList.add(message);
                        break;
                    }
                Log.i(TAG, "onDataChange: " + superList);
                typeRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void back(View view) {
        finish();
    }
}