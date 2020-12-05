package com.example.automatedfoodorderingsystem.userPart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automatedfoodorderingsystem.Model.OrderDetails;
import com.example.automatedfoodorderingsystem.Model.UserDatabase;
import com.example.automatedfoodorderingsystem.R;
import com.example.automatedfoodorderingsystem.UserAdapter.OrderItemsAdapter;
import com.example.automatedfoodorderingsystem.UserFragments.FragmentPay;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectedDishActivity extends AppCompatActivity {
    RecyclerView orderItemsRecView;
    OrderItemsAdapter orderItemsAdapter;
    ArrayList<OrderDetails> orderList;
    ImageView backBtn;
    TextView counterBtn, billAmount;
    FrameLayout frameLayout;
    Button payNowBtn;
    /// Firebase hooks ////
    FirebaseUser mUser;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_dish);
        frameLayout = findViewById(R.id.paymentContainer);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        backBtn = findViewById(R.id.backBtn);
        counterBtn = findViewById(R.id.counterBtn);
        billAmount = findViewById(R.id.bill_amount);
        payNowBtn = findViewById(R.id.payNowBtn);
        payNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frameLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.paymentContainer, new FragmentPay()).commit();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference();
        orderItemsRecView = findViewById(R.id.orderItemsRecView);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectedDishActivity.this, MenuActivity.class));
                finish();

            }
        });
        orderList = new ArrayList<>();
        orderItemsAdapter = new OrderItemsAdapter(getApplicationContext(), orderList);
        orderItemsRecView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        orderItemsRecView.setAdapter(orderItemsAdapter);
//        counterText();


        showOrderDetails();


    }



    private void showOrderDetails() {
        reference.child("UsersDatabase").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatabase user = snapshot.getValue(UserDatabase.class);
                final String chefId = user.getRestaurantId();


                reference.child("OrderDetails").child(chefId).child(mUser.getUid()).child("Orders").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int amount = 0;
                        orderList.clear();
                        counterBtn.setText(String.valueOf(snapshot.getChildrenCount()));
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            OrderDetails order = dataSnapshot.getValue(OrderDetails.class);

                            orderList.add(order);
                            if (order != null) {
                                String price = order.getDishPrice();
                                amount += Integer.parseInt(price);
                            }
                        }

                        billAmount.setText(String.valueOf(amount));
                        reference.child("OrderDetails").child(chefId).child(mUser.getUid()).child("billAmount").setValue(amount);
                        orderItemsAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}