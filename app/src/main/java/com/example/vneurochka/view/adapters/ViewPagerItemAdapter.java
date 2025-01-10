package com.example.vneurochka.view.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerItemAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<>();
    private final List<String> listTitles = new ArrayList<>();

    public ViewPagerItemAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    public void addFragment(Fragment fragment, String title) {
        listFragment.add(fragment);
        listTitles.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitles.get(position);
    }
}


