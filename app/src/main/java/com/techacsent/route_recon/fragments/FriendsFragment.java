package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {
    private Button btnFollowers;
    private Button btnPending;
    private Button btnFollowing;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private Fragment fragment;

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.setTitle(getResources().getString(R.string.friends));
        initializeView(view);
        initializeListener();
    }

    private void initializeView(View view) {
        btnFollowers = view.findViewById(R.id.btn_followers);
        btnPending = view.findViewById(R.id.btn_pending);
        btnFollowing = view.findViewById(R.id.btn_following);
        btnPending.setText("Pending ("+PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT)+")");
        fragment = new FollowersFragment();
        loadFragment(fragment);
    }

    private void initializeListener() {
        btnFollowers.setOnClickListener(v -> {
            btnFollowers.setBackgroundResource(R.drawable.next_trip_colored_bg);
            btnPending.setBackgroundResource(R.drawable.pending_unselected);
            btnFollowing.setBackgroundResource(R.drawable.last_trip_bg);
            btnFollowers.setTextColor(getResources().getColor(R.color.white));
            btnPending.setTextColor(getResources().getColor(R.color.orange));
            btnFollowing.setTextColor(getResources().getColor(R.color.orange));
            fragment = new FollowersFragment();
            loadFragment(fragment);
        });

        btnPending.setOnClickListener(v -> {
            //PreferenceManager.updateValue(Constant.KEY_FRIEND_REQ_BADGE_COUNT, 0);
            PreferenceManager.updateValue(Constant.KEY_FRIEND_REQ_BADGE_COUNT,0);
            btnPending.setText(getResources().getString(R.string.pending));
            btnPending.setBackgroundResource(R.drawable.pending_selected);
            btnFollowing.setBackgroundResource(R.drawable.last_trip_bg);
            btnFollowers.setBackgroundResource(R.drawable.next_trip_bg);
            btnPending.setTextColor(getResources().getColor(R.color.white));
            btnFollowing.setTextColor(getResources().getColor(R.color.orange));
            btnFollowers.setTextColor(getResources().getColor(R.color.orange));
            fragment = new PendingFragment();
            loadFragment(fragment);
        });

        btnFollowing.setOnClickListener(v -> {
            btnFollowing.setBackgroundResource(R.drawable.last_trip_colored_bg);
            btnFollowers.setBackgroundResource(R.drawable.next_trip_bg);
            btnPending.setBackgroundResource(R.drawable.pending_unselected);
            btnFollowing.setTextColor(getResources().getColor(R.color.white));
            btnFollowers.setTextColor(getResources().getColor(R.color.orange));
            btnPending.setTextColor(getResources().getColor(R.color.orange));
            fragment = new FollowingFragment();
            loadFragment(fragment);
        });
    }

    private void loadFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName()).commit();
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
