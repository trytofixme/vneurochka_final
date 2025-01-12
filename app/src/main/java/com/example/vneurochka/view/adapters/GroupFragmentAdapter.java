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
import com.example.vneurochka.view.ui.ChatActivity;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupFragmentAdapter extends RecyclerView.Adapter<GroupFragmentAdapter.GroupHolder> {
    private static List<Group> groupList;
    private static Context context;

    public GroupFragmentAdapter(List<Group> groupList, Context context) {
        GroupFragmentAdapter.groupList = groupList;
        GroupFragmentAdapter.context = context;
    }

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_view, parent, false);

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

    public static List<Group> getGroupList() {return groupList; }

    public void setItems(List<Group> groupList) {
        GroupFragmentAdapter.groupList = groupList;
    }

    class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView groupName;
        private final CircleImageView groupImage;

        public GroupHolder(View itemView)
        {
            super(itemView);
            this.groupName = itemView.findViewById(R.id.group_name);
            this.groupImage = itemView.findViewById(R.id.iv_group_image_base);
            itemView.setOnClickListener(this);
        }

        public CircleImageView getGroupImage() {
            return this.groupImage;
        }

        public TextView getGroupName() {
            return this.groupName;
        }

        @Override
        public void onClick(View view) {
            List<Group> groupList = GroupFragmentAdapter.getGroupList();
            for (Group group: groupList) {
                String selectedGroupName = this.groupName.getText().toString();
                if (Objects.equals(group.getName(), selectedGroupName)) {
                    changeUserToChatActivity(group.getId());
                }
            }
        }

        private void changeUserToChatActivity(String selectedGroupId) {
            Intent intent = new Intent(GroupFragmentAdapter.context, ChatActivity.class);
            intent.putExtra("currentGroupId", selectedGroupId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }
}
