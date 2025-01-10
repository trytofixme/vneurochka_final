package com.example.vneurochka.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vneurochka.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void setHolder(String name, String imageUrl)
    {
        final TextView groupName = itemView.findViewById(R.id.group_name);
        final CircleImageView groupImage = itemView.findViewById(R.id.iv_group_image_base);

        groupName.setText(name);

        if (!imageUrl.equals("default"))
        {
            // ДОБАВИТЬ ЗАГРУЗКУ ИЗОБРАЖЕНИЕ В ПРЕДСТАВЛЕНИЕ ПО ССЫЛКЕ
        }
        else
        {
            groupImage.setImageResource(R.drawable.sample_img);
        }

//        if (groupsDatabase != null & groupListener != null)
//        {
//            groupsDatabase.removeEventListener(groupListener);
//        }
//
//        groupsDatabase = FirebaseDatabase.getInstance().getReference().child("Groups").child(groupId);
//        groupListener = new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
//            {
//                try
//                {
//                    final String name = dataSnapshot.child("name").getValue().toString();
//                    final String image = dataSnapshot.child("imageUrl").getValue().toString();
//                    groupName.setText(name);
//
//                    if(!image.equals("default"))
//                    {
//                        // ДОБАВИТЬ ЗАГРУЗКУ ИЗОБРАЖЕНИЕ В ПРЕДСТАВЛЕНИЕ ПО ССЫЛКЕ
//                    }
//                    else
//                    {
//                        groupImage.setImageResource(R.drawable.sample_img);
//                    }
//                }
//                catch(Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError)
//            {
//                throw new RuntimeException(databaseError.getMessage());
//            }
//        };
//        groupsDatabase.addValueEventListener(groupListener);
    }
}