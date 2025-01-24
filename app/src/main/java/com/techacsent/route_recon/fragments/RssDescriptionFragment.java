package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;

/**
 * A simple {@link Fragment} subclass.
 */
public class RssDescriptionFragment extends Fragment {
    private String description;
    private String title;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public RssDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            description = getArguments().getString("description");
            title = getArguments().getString("title");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rss_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = view.findViewById(R.id.web_view);
        webView.loadData(description,"text/html", "UTF-8");
        fragmentActivityCommunication.setTitle(title);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            fragmentActivityCommunication = (FragmentActivityCommunication) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentActivityCommunication = null;

    }

}
