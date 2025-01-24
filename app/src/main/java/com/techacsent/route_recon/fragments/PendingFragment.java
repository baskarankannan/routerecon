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
import com.techacsent.route_recon.adapter.PendingFilteredAdapter;
import com.techacsent.route_recon.adapter.PendingUserAdapter;
import com.techacsent.route_recon.interfaces.OnFriendSearchListener;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.model.PendingAcceptResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.PendingViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingFragment extends Fragment implements OnRecyclerItemClickListener<FollowersResponse.ListBean> {
    private SearchView searchView;
    private RecyclerView rvPendingList;
    //private ProgressBar progressBar;
    private PendingUserAdapter mAdapter;
    private PendingFilteredAdapter mFilterAdapter;
    private PendingViewModel followersViewModel;
    private List<FollowersResponse.ListBean> listBeanList;
    private ShimmerFrameLayout shimmerFrameLayout;

    public PendingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        followersViewModel = ViewModelProviders.of(this).get(PendingViewModel.class);
        listBeanList= new ArrayList<>();
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        searchView = view.findViewById(R.id.search_user);
        rvPendingList = view.findViewById(R.id.rv_pending_list);
        //progressBar = view.findViewById(R.id.progress_bar);
        rvPendingList.invalidate();
        rvPendingList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPendingList.setItemAnimator(new DefaultItemAnimator());
        rvPendingList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        loadPendingRequestList();
        initlistener();
    }

    private void loadPendingRequestList() {
        shimmerFrameLayout.startShimmer();
        //progressBar.setVisibility(View.VISIBLE);
        followersViewModel.getPendingUser(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "pending", 20, 0).observe(this, new Observer<FollowersResponse>() {
            @Override
            public void onChanged(@Nullable FollowersResponse followersResponse) {
                if (followersResponse!=null && followersResponse.getList() != null) {
                    setFilterAdapter(followersResponse);
                    //progressBar.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    //progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initlistener(){
        searchView.setOnClickListener(v -> searchView.onActionViewExpanded());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mFilterAdapter.getFilter().filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    mFilterAdapter.getFilter().filter(newText);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
            }
        });

    }

    private void setFilterAdapter(FollowersResponse followersResponse){
        listBeanList = followersResponse.getList();
        mFilterAdapter = new PendingFilteredAdapter(getActivity(), listBeanList, new OnFriendSearchListener() {
            @Override
            public void onClick(FollowersResponse.ListBean item, int flag) {
                switch (flag){
                    case 0:
                        acceptPendingRequest(item,"yes");
                        break;

                    case 1:
                        acceptPendingRequest(item,"no");
                        break;

                }
            }
        });
        rvPendingList.setAdapter(mFilterAdapter);

    }

    private void initializeAdapter(FollowersResponse followersResponse) {
        mAdapter = new PendingUserAdapter(getActivity());
        mAdapter.setItems(followersResponse.getList());
        mAdapter.setListener(this);
        rvPendingList.setAdapter(mAdapter);
    }

    private void acceptPendingRequest(FollowersResponse.ListBean item,String action) {
        followersViewModel.getPendingActionResponse(Integer.parseInt(item.getUser().getId()), PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), action).observe(this, new Observer<PendingAcceptResponse>() {
            @Override
            public void onChanged(@Nullable PendingAcceptResponse pendingAcceptResponse) {
                if(pendingAcceptResponse!=null){
                    listBeanList.remove(item);
                    mFilterAdapter.notifyDataSetChanged();
                }

            }
        });
                /*.observe(this, pendingAcceptResponse -> mAdapter.remove(item));*/
    }


    @Override
    public void onItemClicked(FollowersResponse.ListBean item, int itemID) {
        switch (itemID){
            case 0:
                acceptPendingRequest(item,"yes");
                break;

            case 1:
                acceptPendingRequest(item,"no");
                break;
        }

    }
}
