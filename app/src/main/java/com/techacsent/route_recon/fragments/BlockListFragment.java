package com.techacsent.route_recon.fragments;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.BlockAdapter;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.model.BlockResponse;
import com.techacsent.route_recon.model.BlockedUserResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.BlockedUserViewModel;

import java.util.List;
import java.util.Objects;

public class BlockListFragment extends Fragment implements OnRecyclerClickListener<BlockedUserResponse.ListBean> {
    private RecyclerView rvBlockedList;
    private List<BlockedUserResponse.ListBean> listBeanList;
    private BlockAdapter mAdapter;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private BlockedUserViewModel blockedUserViewModel;
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView tvNoUser;

    public BlockListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_block_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        blockedUserViewModel = ViewModelProviders.of(this).get(BlockedUserViewModel.class);
        initializeView(view);
        fragmentActivityCommunication.setTitle(getResources().getString(R.string.block_list_text));
        loadBlockList();
    }

    private void initializeView(View view) {
        rvBlockedList = view.findViewById(R.id.rv_blocked_list);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_block_layout);
        rvBlockedList.invalidate();
        rvBlockedList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvBlockedList.setHasFixedSize(false);
        rvBlockedList.setItemAnimator(new DefaultItemAnimator());
        rvBlockedList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        tvNoUser = view.findViewById(R.id.tv_no_user);
    }

    private void loadBlockList() {


        shimmerFrameLayout.startShimmer();
        blockedUserViewModel.getBlockedList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                "yes", 20, 0).observe(this, blockedUserResponse -> {
            if (blockedUserResponse != null && blockedUserResponse.getList() != null && blockedUserResponse.getList().size() > 0) {
                listBeanList = blockedUserResponse.getList();
                if (listBeanList != null && listBeanList.size() > 0) {
                    if (mAdapter == null) {
                        initializeAdapter();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                } else {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }else {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                tvNoUser.setVisibility(View.VISIBLE);
            }

        });
    }

    private void initializeAdapter() {
        mAdapter = new BlockAdapter(getActivity());
        mAdapter.setItems(listBeanList);
        mAdapter.setListener(this);
        rvBlockedList.setAdapter(mAdapter);
    }

    private void unblockUser(BlockedUserResponse.ListBean item, int position) {
        ApiService apiService = new ApiCaller();
        apiService.unblockUser(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "no", item.getUser().getId(), position, new ResponseCallback<BlockResponse>() {
            @Override
            public void onSuccess(BlockResponse data) {
                mAdapter.remove(item);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
    public void onItemClicked(BlockedUserResponse.ListBean item) {
        unblockUser(item, item.getUser().getPosition());

    }
}
