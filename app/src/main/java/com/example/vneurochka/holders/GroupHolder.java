package com.example.vneurochka.holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupHolder extends RecyclerView.ViewHolder
{
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