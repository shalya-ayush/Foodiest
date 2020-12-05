package com.example.automatedfoodorderingsystem.UserAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automatedfoodorderingsystem.Model.FoodDetails;
import com.example.automatedfoodorderingsystem.Model.UserDatabase;
import com.example.automatedfoodorderingsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuViewAdapter extends RecyclerView.Adapter<MenuViewAdapter.ViewHolder> {
    Context mContext;
    ArrayList<FoodDetails> menuList;
    FirebaseUser mUser;

    public MenuViewAdapter(Context mContext, ArrayList<FoodDetails> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
        mUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_menu_recylcler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        putUserDetails(reference);

        final FoodDetails menuItems = menuList.get(position);
        Picasso.get().load(menuItems.getImageURL()).into(holder.dishImage);
        String price = "Rs." + menuItems.getPrice();
        holder.dishPrice.setText(price);
        holder.dishName.setText(menuItems.getDescription());
        Animation fromRight = AnimationUtils.loadAnimation(mContext, R.anim.enter_from_right);
        holder.selectedDishBtn.setAnimation(fromRight);


        //// Method to check if dish is selected or not
        isSelected(menuItems.getRandomUID(), holder.selectedDishBtn, holder.menuCard, holder.dishName);

        holder.menuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("UsersDatabase").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserDatabase user = snapshot.getValue(UserDatabase.class);
                        if (user != null && !user.getRestaurantId().equals("default")) {
                            String chefId = user.getRestaurantId();
                            /// To check if user has selected the dish or not /////
                            if (holder.menuCard.getTag().equals("select")) {
                                HashMap<String, String> map = new HashMap<>();
                                map.put("dishName", menuItems.getDescription());
                                map.put("dishQty", "1");
                                map.put("dishPrice", menuItems.getPrice());

                                ///// if user selects the dish then dishId and its details would be added into the OrderDetails database inside "Orders" child
                                reference.child("OrderDetails").child(chefId).child(mUser.getUid()).child("Orders").child(menuItems.getRandomUID()).setValue(map);
                                reference.child("OrderDetails").child(chefId).child(mUser.getUid()).child("billAmount").setValue(0);
                            } else {

                                reference.child("OrderDetails").child(chefId).
                                        child(mUser.getUid()).child("Orders").child(menuItems.getRandomUID()).removeValue();
                                reference.child("OrderDetails").child(chefId).child(mUser.getUid()).child("billAmount").removeValue();

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }

    ///// Method to put user details into the OrderDetails database ///////

    private void putUserDetails(final DatabaseReference reference) {

        reference.child("UsersDatabase").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatabase user = snapshot.getValue(UserDatabase.class);
                if (user != null) {
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


    private void isSelected(final String dishId, final ImageButton selectedDishBtn, final CardView menuCard, final Button dishName) {
        final Animation fromRight = AnimationUtils.loadAnimation(mContext, R.anim.enter_from_right);
        fromRight.setDuration(500);
        FirebaseDatabase.getInstance().getReference().child("UsersDatabase").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDatabase user = snapshot.getValue(UserDatabase.class);
                String chefId = user.getRestaurantId();
                FirebaseDatabase.getInstance().getReference().child("OrderDetails").child(chefId).child(mUser.getUid()).child("Orders").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(dishId).exists()) {
                            dishName.setVisibility(View.GONE);
                            selectedDishBtn.setAnimation(fromRight);

                            selectedDishBtn.setVisibility(View.VISIBLE);
                            menuCard.setTag("selected");
                        } else {
                            dishName.setVisibility(View.VISIBLE);
                            selectedDishBtn.setVisibility(View.GONE);
                            menuCard.setTag("select");
                        }
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


    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView menuCard;
        ImageView dishImage, QtyIncrease, QtyDecrease;
        TextView dishPrice, dishQty;
        Button dishName;
        ImageButton selectedDishBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            menuCard = itemView.findViewById(R.id.menu_card);
            dishImage = itemView.findViewById(R.id.dish_image);
            dishQty = itemView.findViewById(R.id.dish_qty);
            QtyIncrease = itemView.findViewById(R.id.qty_increment);
            QtyDecrease = itemView.findViewById(R.id.qty_decrement);
            dishPrice = itemView.findViewById(R.id.dish_price);
            dishName = itemView.findViewById(R.id.dish_name_btn);
            selectedDishBtn = itemView.findViewById(R.id.selected_dish_btn);
        }
    }
}
