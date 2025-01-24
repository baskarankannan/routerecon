package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.custom_view.ZoomImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDisplayFragment extends Fragment {
    private View view;
    private ZoomImageView ivImage;
    private ProgressBar progressBar;
    private String imageUrl;

    public ImageDisplayFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            imageUrl = getArguments().getString("image_url");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image_display, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    private void initializeView(){
        progressBar = view.findViewById(R.id.progress_bar);
        ivImage = view.findViewById(R.id.iv_image);
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.landmark_place_holer)
                .into(ivImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

    }

}
