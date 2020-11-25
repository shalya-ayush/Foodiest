package com.example.automatedfoodorderingsystem.UserAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.automatedfoodorderingsystem.R;

public class OfferViewAdapter extends RecyclerView.Adapter<OfferViewAdapter.ViewHolder> {
    private final Context mContext;
    //    ArrayList<GridImages> imagesArrayList;
    private Integer[] imagesList = {R.drawable.image1, R.drawable.image2, R.drawable.image3};

    public OfferViewAdapter(Context mContext) {
        this.mContext = mContext;
//        this.imagesArrayList = imagesArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_user_offers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        GridImages images = imagesArrayList.get(position);
        holder.offersImage.setImageResource(imagesList[position]);

    }

    @Override
    public int getItemCount() {
        return imagesList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView offersImage;
        public Button availOfferBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            offersImage = itemView.findViewById(R.id.offers_image);
            availOfferBtn = itemView.findViewById(R.id.avail_now_btn);
        }
    }
}
