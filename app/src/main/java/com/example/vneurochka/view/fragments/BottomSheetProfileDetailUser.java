package com.example.vneurochka.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vneurochka.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

public class BottomSheetProfileDetailUser extends BottomSheetDialogFragment {
    String displayName;
    String imageURL;
    Context context;

    ImageView iv_profile_bottom_sheet_profile_image;
    TextView tv_profile__bottom_sheet_fragment_username;

    public BottomSheetProfileDetailUser(String displayName, String imageURL, Context context) {
        this.displayName = displayName;
        this.imageURL = imageURL;
        this.context = context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_sheet_show_profile, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        init(view);
        setDetails(displayName, imageURL);
        return view;
    }

    private void setDetails(String username, String imageURL) {
        tv_profile__bottom_sheet_fragment_username.setText(username);

        if (imageURL.equals("default")) {
            iv_profile_bottom_sheet_profile_image.setImageResource(R.drawable.ic_home_black_24dp);
        } else {
            //Glide.with(context).load(imageURL).into(iv_profile_bottom_sheet_profile_image);
        }

    }

    private void init(View view) {
        iv_profile_bottom_sheet_profile_image = view.findViewById(R.id.iv_profile_bottom_sheet);
        tv_profile__bottom_sheet_fragment_username = view.findViewById(R.id.tv_profile__bottom_sheet_fragment_username);
    }

}
