package com.techacsent.route_recon.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.FilteredFriendAdapter;
import com.techacsent.route_recon.adapter.FriendsItemAdapter;
import com.techacsent.route_recon.model.FollowUnfollowResponse;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.FollowersViewModel;

import java.util.Objects;

public class FollowersFragment extends Fragment /*implements OnRecyclerClickListener<FollowersResponse.ListBean>*/ {
    private RecyclerView rvFollowersList;
    private SearchView searchView;
    //private ProgressBar progressBar;
    private FriendsItemAdapter mAdapter;
    private FollowersViewModel followersViewModel;
    private FilteredFriendAdapter mFilterAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private static final String TAG = FollowersFragment.class.getSimpleName();

    public FollowersFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_followers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        followersViewModel = ViewModelProviders.of(this).get(FollowersViewModel.class);
        initializeView(view);
        loadFollowers();
        initlistener();
    }

    private void initializeView(View view) {
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        rvFollowersList = view.findViewById(R.id.rv_followers_list);
        searchView = view.findViewById(R.id.search_user);
        //progressBar = view.findViewById(R.id.progress_bar);
        rvFollowersList.setItemAnimator(new DefaultItemAnimator());
        rvFollowersList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        rvFollowersList.invalidate();
        rvFollowersList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initlistener() {
        searchView.setOnClickListener(v -> searchView.onActionViewExpanded());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //loadUserSearch(query);
                mFilterAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //loadUserSearch(newText);
                try {
                    mFilterAdapter.getFilter().filter(newText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

    }

    private void loadFollowers() {
        //progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        followersViewModel.getFollowerList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                "yes", 20, 0).observe(this, followersResponse -> {
            if (followersResponse != null) {
                if (followersResponse.getList() != null) {
                    //initializeAdapter(followersResponse);
                    setFilterAdapter(followersResponse);
                    //progressBar.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {
                    //progressBar.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            } else {
                //progressBar.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    private void setFilterAdapter(FollowersResponse followersResponse) {
        mFilterAdapter = new FilteredFriendAdapter(0, getActivity(), followersResponse.getList(), (item, flag) -> {
            followUnfollow(item);
        });

        rvFollowersList.setAdapter(mFilterAdapter);

    }

    private void initializeAdapter(FollowersResponse followersResponse) {
        mAdapter = new FriendsItemAdapter(getActivity(), 0);
        mAdapter.setItems(followersResponse.getList());
        //mAdapter.setListener(this);
        rvFollowersList.setAdapter(mAdapter);
    }

    private void followUnfollow(FollowersResponse.ListBean item) {
        String actionString = "";
        switch (item.getUser().getAmfollower()) {
            case "no":
                actionString = "yes";
                break;
            case "yes":
                actionString = "no";
                break;
            case "pending":
                actionString = "no";
                break;
        }
        ApiService apiService = new ApiCaller();
        apiService.followUnfollowUser(Integer.parseInt(item.getUser().getId()),
                PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), actionString, new ResponseCallback<FollowUnfollowResponse>() {
                    @Override
                    public void onSuccess(FollowUnfollowResponse data) {
                        if (data.getAmfollower().equals("no")) {
                            item.getUser().setAmfollower("no");
                        } else if (data.getAmfollower().equals("Pending")) {
                            item.getUser().setAmfollower("pending");
                        }
                        //mAdapter.notifyDataSetChanged();
                        mFilterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

   /* @Override
    public void onItemClicked(FollowersResponse.ListBean item) {
        followUnfollow(item);
    }*/
}
