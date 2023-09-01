package com.example.sesion03_2023;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        // Recuperar los datos del usuario
        User user = (User) getActivity().getIntent().getSerializableExtra("user");

        // Actualizar elementos de la interfaz con los datos del usuario
        TextView nameTextView = rootView.findViewById(R.id.nameTextView);
        nameTextView.setText(user.getFirstName() + " " + user.getLastName());

        TextView sexTextView = rootView.findViewById(R.id.sexTextView);
        sexTextView.setText(user.getGender());

        TextView phoneTextView = rootView.findViewById(R.id.phoneTextView);
        phoneTextView.setText(user.getPhone());

        TextView statusTextView = rootView.findViewById(R.id.statusTextView);
        statusTextView.setText(user.getStatus() ? "Active" : "Inactive");

        ImageView profileImageView = rootView.findViewById(R.id.profileImage);
        profileImageView.setImageResource(user.getProfileImageRes());

        return rootView;
    }

}