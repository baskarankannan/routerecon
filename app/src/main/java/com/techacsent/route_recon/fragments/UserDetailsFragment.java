package com.techacsent.route_recon.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.model.BlockResponse;
import com.techacsent.route_recon.model.FollowUnfollowResponse;
import com.techacsent.route_recon.model.UserSearchResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.UserDetailsViewModel;

import org.greenrobot.eventbus.EventBus;

public class UserDetailsFragment extends DialogFragment {
    private Button btnBlock;
    private Button btnFollowUnfollow;
    private UserSearchResponse.ListBean userBean;
    private UserDetailsViewModel userDetailsViewModel;
    private static final String TAG = UserDetailsFragment.class.getSimpleName();

    public UserDetailsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_use_details, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        initializeListener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userBean = getArguments().getParcelable("parcel");
        }
        userDetailsViewModel = ViewModelProviders.of(this).get(UserDetailsViewModel.class);
    }

    private void initializeView(View view) {
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        btnBlock = view.findViewById(R.id.btn_block);
        btnFollowUnfollow = view.findViewById(R.id.btn_follow_unfollow);
        tvName.setText(userBean.getUser().getName());
        tvEmail.setText(userBean.getUser().getEmail());
        switch (userBean.getUser().getAmfollower()) {
            case "no":
                btnFollowUnfollow.setText(R.string.follow);
                break;
            case "pending":
                btnFollowUnfollow.setText(R.string.pending);
                break;
            case "yes":
                btnFollowUnfollow.setText(R.string.following);
                break;
        }
    }

    private void initializeListener() {
        btnBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockUser(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                        userBean.getUser().getId());
                userBean.getUser().setContent(true);
                EventBus.getDefault().post(userBean);
                dismiss();
            }
        });
        btnFollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amFollower;
                if (userBean.getUser().getAmfollower().equals("no")) {
                    amFollower = "yes";
                } else {
                    amFollower = "no";
                }
                followUnfollow(Integer.valueOf(userBean.getUser().getId()), PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), amFollower);
            }
        });
    }

    private void blockUser(String token, String blockId) {
        userDetailsViewModel.getBlock(token, "yes", blockId).observe(this, new Observer<BlockResponse>() {
            @Override
            public void onChanged(@Nullable BlockResponse blockResponse) {
                Toast.makeText(getActivity(), blockResponse.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void followUnfollow(int path, String token, String amFollower) {
        userDetailsViewModel.getFollowUnfollow(path, token, amFollower).observe(this, new Observer<FollowUnfollowResponse>() {
            @Override
            public void onChanged(@Nullable FollowUnfollowResponse followUnfollowResponse) {
                if (followUnfollowResponse != null) {
                    Log.e(TAG, followUnfollowResponse.getAmfollower());
                    switch (followUnfollowResponse.getAmfollower()) {
                        case "Pending":
                            btnFollowUnfollow.setText(R.string.pending);
                            userBean.getUser().setAmfollower("pending");
                            userBean.getUser().setContent(false);
                            EventBus.getDefault().post(userBean);
                            break;
                        case "no":
                            btnFollowUnfollow.setText(R.string.follow);
                            userBean.getUser().setContent(false);
                            userBean.getUser().setAmfollower("no");
                            EventBus.getDefault().post(userBean);
                            break;
                        case "yes":
                            btnFollowUnfollow.setText(R.string.following);
                            userBean.getUser().setContent(false);
                            EventBus.getDefault().post(userBean);
                            break;
                    }
                }

            }
        });
    }
}
