package com.example.vneurochka.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.example.vneurochka.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragmentAdapter extends RecyclerView.Adapter<UserFragmentAdapter.UserHolder> {

    private ArrayList<User> usersList;

    public UserFragmentAdapter(ArrayList<User> usersArrayList) {
        this.usersList = usersArrayList;
    }

    @NonNull
    @Override
    public UserFragmentAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_item_view, parent, false);

        return new UserFragmentAdapter.UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFragmentAdapter.UserHolder holder, int position) {
        User currentUser = usersList.get(position);
        holder.getUserNameTextView().setText(currentUser.getDisplayName());
        if (!currentUser.getImageUrl().equals("default"))
        {
            // ДОБАВИТЬ ЗАГРУЗКУ ИЗОБРАЖЕНИЕ В ПРЕДСТАВЛЕНИЕ ПО ССЫЛКЕ
        }
        else
        {
            holder.getUserImageView().setImageResource(R.drawable.sample_img);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public void setItems(ArrayList<User> userList) {
        this.usersList = userList;
    }

    public static class UserHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final CircleImageView userImage;

        public UserHolder(View itemView)
        {
            super(itemView);
            this.userName = itemView.findViewById(R.id.user_name);
            this.userImage = itemView.findViewById(R.id.profile_image);
        }

        public CircleImageView getUserImageView() {
            return this.userImage;
        }

        public TextView getUserNameTextView() {
            return this.userName;
        }
    }
}
