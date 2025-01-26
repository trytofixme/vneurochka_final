package com.example.vneurochka.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vneurochka.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class GroupCreationFragment extends Fragment {
    private Button btn;
    private Button btn1;
    // Инициализровать остальные поля объектами


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_creation, container, false);
        initComponents(view);
        initListeners();

        return view;
    }

    private void initListeners() {
        btn.setOnClickListener(view -> {
            // Как открыть диалоговый фрагмент из фрагмента по нажатию на кнопку?
        });

        btn1.setOnClickListener(view -> {
            // 1. Добавить группу в базу данных
            // Считать данные с введенных полей
            // Провалидировать данные (непустое имя группы, > 0 участников)

            DatabaseReference groupsDatabase = FirebaseDatabase.getInstance().getReference("Groups");
            HashMap<String, String> hashMap = new HashMap<>();
            // hashMap.put("id", userId); для каждого поля из таблицы Groups
            // Firebase add new object
//            groupsDatabase.addValueEventListener(task -> {
//
//            });
            // 2. Закрыть окно создания группы
        });
    }

    private void initComponents(View view) {
        btn = view.findViewById(R.id.btn_add_user);
        // Привязать остальные объекты к представлениям
    }
}