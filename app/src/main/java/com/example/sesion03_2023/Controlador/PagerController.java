package com.example.sesion03_2023.Controlador;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerController extends FragmentPagerAdapter {
    int numoftabs;

    public PagerController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        this.numoftabs = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PrincipalPage();
            case 1:
                return new Playlist();
            case 2:
                return new Channels();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}
