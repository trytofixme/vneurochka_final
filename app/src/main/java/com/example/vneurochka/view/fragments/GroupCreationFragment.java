package com.example.vneurochka.view.fragments;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.vneurochka.R;
import com.google.android.gms.drive.events.CompletionListener;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class GroupCreationFragment extends DialogFragment {
    private Button btn1;
    private EditText groupName;
    private EditText groupDesk;
    public static String TAG = "123";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_group_creation, new ConstraintLayout(getActivity()), false);
        initComponents(view);
        initListeners();
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        builder.setTitle(TAG);
        return builder;
    }

    private void initListeners() {
        btn1.setOnClickListener(view -> {
            String groupNameText = groupName.getText().toString();
            String groupDeskText = groupDesk.getText().toString();
            //Сделать валидацию про количество участников группы
            if (groupNameText.isEmpty()) {
//                Toast.makeText(view, getText(R.string.group_name_empty), Toast.LENGTH_SHORT).show();
//                groupName.requestFocus();
            } else {

                long timestamp = System.currentTimeMillis();

                DatabaseReference groupsDatabase = FirebaseDatabase.getInstance().getReference("Groups");
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", groupNameText);
                hashMap.put("description", groupDeskText);
                hashMap.put("imageURL", "default");
                hashMap.put("creationDate", timestamp);
                //hashMap.put("userIds", null);
                AtomicReference<Boolean> isGroupAdded = new AtomicReference<>(Boolean.FALSE);
                groupsDatabase.push().setValue(hashMap);
                getDialog().dismiss();
            }
        });
    }

    private void initComponents(View view) {
        btn1 = view.findViewById(R.id.btn_new_group);
        groupName = view.findViewById(R.id.group_name);
        groupDesk = view.findViewById(R.id.group_desc);
    }
}