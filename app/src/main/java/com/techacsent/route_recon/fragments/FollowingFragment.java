package com.techacsent.route_recon.fragments;


import androidx.lifecycle.Observer;
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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.FilteredFriendAdapter;
import com.techacsent.route_recon.adapter.FriendsItemAdapter;
import com.techacsent.route_recon.interfaces.OnFriendSearchListener;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.FollowUnfollowResponse;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.FollowingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFragment extends Fragment implements OnRecyclerClickListener<FollowersResponse.ListBean> {
    private SearchView searchView;
    private RecyclerView rvFollowingList;
    //private ProgressBar progressBar;
    private FriendsItemAdapter mAdapter;
    private FollowingViewModel followingViewModel;
    private FilteredFriendAdapter mFilterAdapter;
    private List<FollowersResponse.ListBean> listBeanList;
    private ShimmerFrameLayout shimmerFrameLayout;

    public FollowingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_following, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listBeanList= new ArrayList<>();
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        searchView = view.findViewById(R.id.search_user);
        rvFollowingList = view.findViewById(R.id.rv_following_list);
        rvFollowingList.setItemAnimator(new DefaultItemAnimator());
        rvFollowingList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        rvFollowingList.invalidate();
        rvFollowingList.setLayoutManager(new LinearLayoutManager(getActivity()));
        //progressBar = view.findViewById(R.id.progress_bar);
        followingViewModel = ViewModelProviders.of(this).get(FollowingViewModel.class);
        loadFollowingList();
        initlistener();
    }

    private void initlistener(){
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
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            }
        });

    }


    private void loadFollowingList() {
        //progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
        followingViewModel.getFollowinfList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "yes", 20, 0).observe(this, new Observer<FollowersResponse>() {
            @Override
            public void onChanged(@Nullable FollowersResponse followersResponse) {
                if (followersResponse!=null && followersResponse.getList() != null) {
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

            }
        });
    }

    private void initializeAdapter(FollowersResponse followersResponse) {
        mAdapter = new FriendsItemAdapter(getActivity(), 2);
        mAdapter.setItems(followersResponse.getList());
        mAdapter.setListener(this);
        rvFollowingList.setAdapter(mAdapter);
    }

    private void setFilterAdapter(FollowersResponse followersResponse){
        listBeanList = followersResponse.getList();
        mFilterAdapter = new FilteredFriendAdapter(0,getActivity(), listBeanList, new OnFriendSearchListener() {
            @Override
            public void onClick(FollowersResponse.ListBean item, int flag) {
                unfollowUser(item);
                //followUnfollow(item);
            }
        });

        rvFollowingList.setAdapter(mFilterAdapter);

    }


    private void unfollowUser(FollowersResponse.ListBean item) {
        followingViewModel.getUnfollow(Integer.parseInt(item.getUser().getId()), PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "no").observe(this, new Observer<FollowUnfollowResponse>() {
            @Override
            public void onChanged(@Nullable FollowUnfollowResponse followUnfollowResponse) {
                if(followUnfollowResponse!=null){
                    listBeanList.remove(item);
                    mFilterAdapter.notifyDataSetChanged();
                }


            }
        });
    }


    @Override
    public void onItemClicked(FollowersResponse.ListBean item) {
        unfollowUser(item);
    }
}
