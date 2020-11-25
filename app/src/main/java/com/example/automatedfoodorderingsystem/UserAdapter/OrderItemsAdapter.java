package com.example.automatedfoodorderingsystem.UserAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automatedfoodorderingsystem.Model.OrderDetails;
import com.example.automatedfoodorderingsystem.R;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {
    Context mContext;
    ArrayList<OrderDetails> orderList;
    int count = 1;

    public OrderItemsAdapter(Context mContext, ArrayList<OrderDetails> orderList) {
        this.mContext = mContext;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_user_order_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetails orderDetails = orderList.get(position);

        holder.dishName.setText(orderDetails.getDishName());
        holder.dishQty.setText(orderDetails.getDishQty());
        holder.dishPrice.setText(orderDetails.getDishPrice());
        holder.sno.setText(String.valueOf(count++));


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView sno, dishName, dishQty, dishPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishName = itemView.findViewById(R.id.dishName);
            dishQty = itemView.findViewById(R.id.dishQty);
            dishPrice = itemView.findViewById(R.id.dishPrice);
            sno = itemView.findViewById(R.id.Sno);


        }
    }
}
