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

import org.jetbrains.annotations.NotNull;

public class TermsPrivacyFragment extends Fragment {
    private View view;
    boolean isPrivacyPolicy;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public TermsPrivacyFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_terms_privacy, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isPrivacyPolicy = getArguments().getBoolean("is_privacy_policy");
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.hideBottomNav(true);
        initializeView();
    }

    private void initializeView() {
        WebView webView = view.findViewById(R.id.web_view);
        if (isPrivacyPolicy) {
            //webView.loadUrl("file:///android_asset/privacy.html");
            webView.loadUrl(getResources().getString(R.string.url_privacy_policy));
            fragmentActivityCommunication.setTitle("Privacy Policy");
        } else {
            //webView.loadUrl("file:///android_asset/terms.html");
            webView.loadUrl(getResources().getString(R.string.url_terms_and_condition));
            fragmentActivityCommunication.setTitle("Terms & Condition");
        }

        webView.requestFocus();
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

}
