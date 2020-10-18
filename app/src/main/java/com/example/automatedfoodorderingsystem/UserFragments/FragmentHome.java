package com.example.automatedfoodorderingsystem.UserFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.automatedfoodorderingsystem.Model.GridImages;
import com.example.automatedfoodorderingsystem.R;
import com.example.automatedfoodorderingsystem.UserAdapter.GridViewAdapter;
import com.example.automatedfoodorderingsystem.UserAdapter.OfferViewAdapter;
import com.example.automatedfoodorderingsystem.UserAdapter.ViewPagerAdapter;

import java.util.ArrayList;


public class FragmentHome extends Fragment {
    ViewPager viewPager;
    RecyclerView recyclerView;
    RecyclerView offerRecyclerView;
    ViewPagerAdapter viewPagerAdapter;
    OfferViewAdapter offerViewAdapter;
    GridViewAdapter gridViewAdapter;
    ArrayList<GridImages> gridImagesArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.user_viewPager);
        recyclerView = view.findViewById(R.id.user_home_recView);
        offerRecyclerView = view.findViewById(R.id.user_offers_recView);
        offerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewPagerAdapter = new ViewPagerAdapter(getContext());
        offerViewAdapter = new OfferViewAdapter(getContext());
        offerRecyclerView.setAdapter(offerViewAdapter);
        gridImagesArrayList = new ArrayList<>();

        gridViewAdapter = new GridViewAdapter(getContext(), gridImagesArrayList);
        GridImages obj1 = new GridImages(R.drawable.image1);
        gridImagesArrayList.add(obj1);
        GridImages obj2 = new GridImages(R.drawable.ic_profile);
        gridImagesArrayList.add(obj2);
        GridImages obj3 = new GridImages(R.drawable.image2);
        gridImagesArrayList.add(obj3);
        GridImages obj4 = new GridImages(R.drawable.ic_profile);
        gridImagesArrayList.add(obj4);
        GridImages obj5 = new GridImages(R.drawable.ic_launcher_background);
        gridImagesArrayList.add(obj5);
        GridImages obj6 = new GridImages(R.drawable.image3);
        gridImagesArrayList.add(obj6);
        GridImages obj7 = new GridImages(R.drawable.ic_launcher_background);
        gridImagesArrayList.add(obj7);
        GridImages obj8 = new GridImages(R.drawable.ic_profile);
        gridImagesArrayList.add(obj8);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(gridViewAdapter);
        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }
}