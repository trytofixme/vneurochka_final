package com.example.vneurochka.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.example.vneurochka.model.Group;
import com.example.vneurochka.view.ui.MessageActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupFragmentAdapter extends RecyclerView.Adapter<GroupFragmentAdapter.GroupHolder> {
    private List<Group> groupList;
    private final Context context;

    public GroupFragmentAdapter(List<Group> groupList, Context context) {
        this.groupList = groupList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_item_view, parent, false);

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

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MessageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("currentGroupId", currentGroup.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public void setItems(List<Group> groupList) {
        this.groupList = groupList;
    }

    public static class GroupHolder extends RecyclerView.ViewHolder {
        private final TextView groupName;
        private final CircleImageView groupImage;

        public GroupHolder(View itemView)
        {
            super(itemView);
            this.groupName = itemView.findViewById(R.id.group_name);
            this.groupImage = itemView.findViewById(R.id.iv_group_image_base);
        }

        public CircleImageView getGroupImage() {
            return this.groupImage;
        }

        public TextView getGroupName() {
            return this.groupName;
        }
    }
}
