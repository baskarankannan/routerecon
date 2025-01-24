package com.techacsent.route_recon.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
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
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.UserSearchAdapter;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.FollowUnfollowResponse;
import com.techacsent.route_recon.model.UserSearchResponse;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.service.RouteReconIntentService;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class UserSearchFragment extends Fragment implements OnRecyclerClickListener<UserSearchResponse.ListBean> {

    private SearchView searchView;
    private RecyclerView rvUserList;
    private ProgressBar progressBar;
    private UserSearchAdapter mAdapter;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public UserSearchFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.setTitle(getResources().getString(R.string.user_search_text));
        initializeView(view);
        initializeListener();
    }

    private void initializeView(View view) {
        searchView = view.findViewById(R.id.search_user);
        rvUserList = view.findViewById(R.id.rv_user_list);
        progressBar = view.findViewById(R.id.progress_bar);
        rvUserList.invalidate();
        rvUserList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUserList.setItemAnimator(new DefaultItemAnimator());
        rvUserList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
    }

    private void initializeListener() {
        searchView.setOnClickListener(v -> searchView.onActionViewExpanded());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadUserSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadUserSearch(newText);
                return false;
            }
        });
    }

    private void initializeAdapter(UserSearchResponse userSearchResponse) {
        mAdapter = new UserSearchAdapter(getActivity());
        mAdapter.setItems(userSearchResponse.getList());
        mAdapter.setListener(this);
        rvUserList.setAdapter(mAdapter);

    }

    private void loadUserSearch(String username) {
        Intent intent = new Intent(getActivity(), RouteReconIntentService.class);
        intent.setAction(RouteReconIntentService.ACTION_LOAD_USER_SEARCH);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_USER_NAME_SEARCH, username);
        intent.putExtras(bundle);
        intent.putExtra(RouteReconIntentService.ACTION_GET_USER_BY_NAME, new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                progressBar.setVisibility(View.VISIBLE);
                UserSearchResponse userSearchResponse = resultData.getParcelable(Constant.KEY_USER_LIST_BY_NAME);
                if (userSearchResponse != null) {
                    if (mAdapter == null) {
                        initializeAdapter(userSearchResponse);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        mAdapter.clear();
                        mAdapter.setItems(userSearchResponse.getList());
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        Objects.requireNonNull(getActivity()).startService(intent);
    }

    private void followUnfollow(UserSearchResponse.ListBean userBean) {
        String actionText = "";
        switch (userBean.getUser().getAmfollower()) {
            case "no":
                actionText = "yes";
                break;
            case "yes":
                actionText = "no";
                break;
            case "pending":
                actionText = "no";
                break;
        }
        ApiService apiService = new ApiCaller();
        apiService.followUnfollowUser(Integer.parseInt(userBean.getUser().getId()), PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                actionText, new ResponseCallback<FollowUnfollowResponse>() {
                    @Override
                    public void onSuccess(FollowUnfollowResponse data) {
                        userBean.getUser().setAmfollower(data.getAmfollower().toLowerCase());
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable th) {
                        th.printStackTrace();
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateList(UserSearchResponse.ListBean listBean) {
        if (listBean.getUser().isContent()) {
            mAdapter.remove(listBean);
        } else {
            mAdapter.notifyDataSetChanged();
        }
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClicked(UserSearchResponse.ListBean item) {
        if (item.getUser().isContent()) {
            UserDetailsFragment fragment = new UserDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("parcel", item);
            fragment.setArguments(bundle);
            fragment.show(getChildFragmentManager(), null);
        } else {
            followUnfollow(item);
        }

    }
}
