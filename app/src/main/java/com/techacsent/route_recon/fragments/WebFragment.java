package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.techacsent.route_recon.R;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends Fragment {
    private View view;
    boolean isPrivacyPolicy;

    public WebFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isPrivacyPolicy = getArguments().getBoolean("is_privacy_policy");
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_web, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    private void initializeView() {
        WebView webView = view.findViewById(R.id.web_view);
        /*if(isPrivacyPolicy){
            webView.loadUrl("file:///android_asset/privacy.html");
        }
        else {
            webView.loadUrl("file:///android_asset/terms.html");
        }*/

        if (isPrivacyPolicy) {
            //webView.loadUrl("file:///android_asset/privacy.html");
            webView.loadUrl(getResources().getString(R.string.url_privacy_policy));
            //fragmentActivityCommunication.setTitle("Privacy Policy");
        } else {
            //webView.loadUrl("file:///android_asset/terms.html");
            webView.loadUrl(getResources().getString(R.string.url_terms_and_condition));
            //fragmentActivityCommunication.setTitle("Terms & Condition");
        }

        webView.requestFocus();
    }


}
