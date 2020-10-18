package com.example.automatedfoodorderingsystem.UserFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.automatedfoodorderingsystem.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentProfile extends Fragment {
    CircleImageView profileImage;
    TextView editProfile;
    View viewLine;
    TextView orderHistory;
    TextView shareApp;
    TextView reportBug;
    TextView rateApp;
    Button logoutBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = view.findViewById(R.id.user_profile_image);
        editProfile = view.findViewById(R.id.user_profile_edit);
        viewLine = view.findViewById(R.id.single_line);
        orderHistory = view.findViewById(R.id.user_profile_order);
        shareApp = view.findViewById(R.id.user_share);
        reportBug = view.findViewById(R.id.user_report);
        rateApp = view.findViewById(R.id.user_rate_app);
        logoutBtn = view.findViewById(R.id.user_logout_btn);
        return view;
    }
}