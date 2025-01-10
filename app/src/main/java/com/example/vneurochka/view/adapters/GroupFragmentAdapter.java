package com.example.vneurochka.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.example.vneurochka.holders.GroupHolder;
import com.example.vneurochka.model.Group;

import java.util.List;

public class GroupFragmentAdapter extends RecyclerView.Adapter<GroupHolder> {
    private final List<Group> groupList;

    public GroupFragmentAdapter(List<Group> groupList) {
        this.groupList = groupList;
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_view_cpy, parent, false);

        return new GroupHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        Group currentGroup = groupList.get(position);
        holder.getGroupName().setText(currentGroup.getName());
        if (!currentGroup.getImageUrl().equals("default"))
        {
            // ДОБАВИТЬ ЗАГРУЗКУ ИЗОБРАЖЕНИЕ В ПРЕДСТАВЛЕНИЕ ПО ССЫЛКЕ
        }
        else
        {
            holder.getGroupImage().setImageResource(R.drawable.sample_img);
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }
}
