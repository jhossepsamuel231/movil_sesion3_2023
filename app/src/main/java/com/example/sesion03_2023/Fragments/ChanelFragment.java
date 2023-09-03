package com.example.sesion03_2023.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.sesion03_2023.Controlador.PagerController;
import com.example.sesion03_2023.R;
import com.example.sesion03_2023.User;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChanelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChanelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChanelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChanelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChanelFragment newInstance(String param1, String param2) {
        ChanelFragment fragment = new ChanelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TabLayout tabLayout;
        ViewPager viewPager;
        TabItem tab1, tab2, tab3;

        PagerController pagerAdapter;

        View rootView = inflater.inflate(R.layout.fragment_chanel, container, false);

       tabLayout = rootView.findViewById(R.id.tablayout);
        viewPager = rootView.findViewById(R.id.viewpager);

        tab1 = rootView.findViewById(R.id.tabprinpape);
        tab2 = rootView.findViewById(R.id.tabplaylist);
        tab3 = rootView.findViewById(R.id.tabchanels);

        pagerAdapter = new PagerController(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    pagerAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition() == 1){
                    pagerAdapter.notifyDataSetChanged();
                }
                if(tab.getPosition() == 2){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        User user = (User) getActivity().getIntent().getSerializableExtra("user");

        // Actualizar elementos de la interfaz con los datos del usuario
        TextView nameTextView = rootView.findViewById(R.id.textl);
        nameTextView.setText(user.getFirstName() + " " + user.getLastName());

        ImageView profileImageView = rootView.findViewById(R.id.imagetl);
        profileImageView.setImageResource(user.getProfileImageRes());

        return rootView;
    }

}