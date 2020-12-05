package com.example.automatedfoodorderingsystem.UserFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.automatedfoodorderingsystem.Model.OrderDetails;
import com.example.automatedfoodorderingsystem.Model.UserDatabase;
import com.example.automatedfoodorderingsystem.R;
import com.example.automatedfoodorderingsystem.userPart.ScannerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentPay extends Fragment {
    CircleImageView payerImage;
    TextView payerName, payerMobile, payableAmount, restaurantName;


    // If user has not scan the QR code then no payment details will be shown to him
    LinearLayout linearLayout;
    CardView cardView;
    Button scanNowBtn;

    //// Firebase hooks ////
    FirebaseUser mUser;
    DatabaseReference reference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        payerImage = view.findViewById(R.id.payer_image);
        payerName = view.findViewById(R.id.payer_name);
        payerMobile = view.findViewById(R.id.payer_mobile);
        payableAmount = view.findViewById(R.id.payable_amount);
        restaurantName = view.findViewById(R.id.restaurant_name);


        ///// Card View hooks ///////
        linearLayout = view.findViewById(R.id.linearLayout);
        cardView = view.findViewById(R.id.cardView);
        scanNowBtn = view.findViewById(R.id.scanNowBtn);


        ////// firebase hooks ////////
        reference = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        //// Method to fetch order details from the OrdersDatabase //////
        showDetails();


        scanNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ScannerActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }


    //// Method to fetch order details from the OrdersDatabase //////
    private void showDetails() {


        ////// to get Restaurant id from the user's database /////////
        reference.child("UsersDatabase").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatabase user = snapshot.getValue(UserDatabase.class);
                if (user != null && !user.getRestaurantId().equals("default")) {
                    linearLayout.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                    String restaurantId = user.getRestaurantId();

                    ////// to get details from the Order's Database ////////
                    reference.child("OrderDetails").child(restaurantId).child(mUser.getUid()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            OrderDetails order = snapshot.getValue(OrderDetails.class);
                            if (order != null) {
                                payerName.setText(order.getUserName());
                                payerMobile.setText(order.getUserPhone().substring(3));
                                Picasso.get().load(order.getUserImg()).into(payerImage);
                                payableAmount.setText(String.valueOf(order.getBillAmount()));
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                ////// if user has not scan the QR code //////
                else {
                    linearLayout.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}