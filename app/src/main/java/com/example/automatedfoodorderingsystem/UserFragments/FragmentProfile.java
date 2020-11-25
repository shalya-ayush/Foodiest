package com.example.automatedfoodorderingsystem.UserFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.automatedfoodorderingsystem.Model.UserDatabase;
import com.example.automatedfoodorderingsystem.R;
import com.example.automatedfoodorderingsystem.userPart.EditProfileActivity;
import com.example.automatedfoodorderingsystem.userPart.UserLoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentProfile extends Fragment {
    TextView userName;
    TextView userMobile;
    CircleImageView profileImage;
    TextView editProfile;
    TextView orderHistory;
    TextView shareApp;
    TextView reportBug;
    TextView rateApp;
    Button logoutBtn;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser mUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //////////// Firebase hooks //////////////
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("UsersDatabase");
        mUser = mAuth.getCurrentUser();

        ////////////hooks ////////////////////
        userName = view.findViewById(R.id.userName);
        userMobile = view.findViewById(R.id.userMobile);
        profileImage = view.findViewById(R.id.user_profile_image);

        //////// Edit Profile Button ///////////
        editProfile = view.findViewById(R.id.user_profile_edit);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));

            }
        });
        orderHistory = view.findViewById(R.id.user_profile_order);
        shareApp = view.findViewById(R.id.user_share);
        reportBug = view.findViewById(R.id.user_report);
        rateApp = view.findViewById(R.id.user_rate_app);

        /////// Logout Button //////////
        logoutBtn = view.findViewById(R.id.user_logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setIcon(R.mipmap.circlelogo)
                        .setTitle("Foodiest")
                        .setMessage("Do you really want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logoutUser();
                            }
                        }).setNegativeButton("No", null)
                        .show();

            }
        });

//        test = view.findViewById(R.id.testing_btn);
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = Objects.requireNonNull(getActivity()).getPackageManager().getLaunchIntentForPackage("com.google.android.apps.nbu.paisa.user");
//                if (i != null) {
//                    startActivity(i);
//                } else {
//                    Toast.makeText(getContext(), "No such app is there", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        /////// Method to show user name and mobile number ///////

        showUserName();


        return view;
    }


    ///////Method to show user name and mobile number /////////
    private void showUserName() {
        reference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatabase user = snapshot.getValue(UserDatabase.class);
                if (user != null) {
                    userName.setText(user.getName());
                    userMobile.setText(user.getPhoneNo().substring(3));
                    Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.ic_account_circle_).into(profileImage);
                } else {
                    userName.setVisibility(View.GONE);
                    userMobile.setVisibility(View.GONE);
                    Picasso.get().load(R.drawable.logo).into(profileImage);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //////// Method to Logout User ////////////
    private void logoutUser() {
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();

    }


}