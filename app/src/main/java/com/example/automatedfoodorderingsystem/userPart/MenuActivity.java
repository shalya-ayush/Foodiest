package com.example.automatedfoodorderingsystem.userPart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automatedfoodorderingsystem.Model.FoodDetails;
import com.example.automatedfoodorderingsystem.Model.UserDatabase;
import com.example.automatedfoodorderingsystem.R;
import com.example.automatedfoodorderingsystem.UserAdapter.MenuViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuActivity extends AppCompatActivity {
    ImageView backBtn;
    TextView counter;
    FrameLayout frameLayout;
    RecyclerView menuRecyclerView;
    MenuViewAdapter menuViewAdapter;
    ArrayList<FoodDetails> menuList;
    FirebaseUser mUser;
    DatabaseReference userReference, reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ///// hooks //////
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        putUserDetails(reference);
        frameLayout = findViewById(R.id.frame_container);
        backBtn = findViewById(R.id.back);
        counter = findViewById(R.id.counter);
        menuRecyclerView = findViewById(R.id.menu_recycler_view);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, UserDashboardActivity.class));
                finish();

            }
        });
        final int cart = Integer.parseInt(counter.getText().toString());
        counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, SelectedDishActivity.class));


//                    frameLayout.setVisibility(View.VISIBLE);
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentPay()).commit();


            }
        });

        ////// Firebase hooks //////

        userReference = FirebaseDatabase.getInstance().getReference().child("UsersDatabase").child(mUser.getUid());

        menuList = new ArrayList<>();
        menuViewAdapter = new MenuViewAdapter(getApplicationContext(), menuList);
        menuRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false));
        menuRecyclerView.setAdapter(menuViewAdapter);
        showMenu();


    }

    private void putUserDetails(final DatabaseReference reference) {

        reference.child("UsersDatabase").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatabase user = snapshot.getValue(UserDatabase.class);
                if (user != null && !user.getRestaurantId().equals("default")) {
                    String chefId = user.getRestaurantId();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("userId", mUser.getUid());
                    hashMap.put("userImg", user.getImageUrl());
                    hashMap.put("userName", user.getName());
                    hashMap.put("userPhone", user.getPhoneNo());
                    hashMap.put("restaurantId", chefId);
                    reference.child("OrderDetails").child(chefId).child(mUser.getUid()).setValue(hashMap);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void showMenu() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Good things take time..");
        pd.show();
        FirebaseDatabase.getInstance().getReference().child("UsersDatabase").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatabase user = snapshot.getValue(UserDatabase.class);
                String restaurantId = user.getRestaurantId();
                FirebaseDatabase.getInstance().getReference().child("FoodDetails").child(restaurantId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pd.dismiss();
                        menuList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            FoodDetails menu = dataSnapshot.getValue(FoodDetails.class);
                            menuList.add(menu);


                        }
                        menuViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                /// To increase the value of counter in Cart
                FirebaseDatabase.getInstance().getReference().child("OrderDetails").child(restaurantId).child(mUser.getUid()).child("Orders").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        counter.setText("" + snapshot.getChildrenCount());
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