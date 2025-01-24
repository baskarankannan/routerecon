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
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.FilteredGroupAdapter;
import com.techacsent.route_recon.adapter.GroupListAdapter;
import com.techacsent.route_recon.adapter.SelectMemberAdapter;
import com.techacsent.route_recon.event_bus_object.EditGroupObject;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnGroupClickListener;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.DeleteGroupresponse;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.model.GroupListResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.CreateGroupViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupFragment extends Fragment implements OnRecyclerItemClickListener<GroupListResponse.ListBean> {
    //private EditText etGroupName;
    //private RecyclerView rvFollowerList;
    private SearchView searchView;
    private RecyclerView rvGroupList;
    private Button btnCreateGroup;
    private ArrayList<FollowersResponse.ListBean> listBeans;
    private SelectMemberAdapter selectMemberAdapter;
    private GroupListAdapter mGroupAdapter;
    private CreateGroupViewModel createGroupViewModel;
    //private ProgressBar progressBar;
    private FilteredGroupAdapter mAdapter;
    private FragmentActivityCommunication fragmentActivityCommunication;
    List<GroupListResponse.ListBean> groupList;
    private ShimmerFrameLayout shimmerFrameLayout;

    public CreateGroupFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.setTitle(getResources().getString(R.string.create_manage_group_text));
        createGroupViewModel = ViewModelProviders.of(this).get(CreateGroupViewModel.class);
        groupList = new ArrayList<>();
        initializeView(view);
        loadFollower();
        loadGroupList();
        initializeListener();
    }

    private void initializeView(View view) {
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        searchView = view.findViewById(R.id.search_user);
        //progressBar = view.findViewById(R.id.progress_bar);
        rvGroupList = view.findViewById(R.id.rv_group_list);
        btnCreateGroup = view.findViewById(R.id.btn_create_group);

        rvGroupList.invalidate();
        rvGroupList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvGroupList.setHasFixedSize(false);
        rvGroupList.setItemAnimator(new DefaultItemAnimator());
        rvGroupList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
    }

    private void initializeListener() {
        btnCreateGroup.setOnClickListener(v -> {
            Fragment fragment = new CreateGroupWithFollowerFragment();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName()).commit();

        });

        searchView.setOnClickListener(v -> searchView.onActionViewExpanded());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                //selectMemberAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //loadUserSearch(newText);
                //selectMemberAdapter.getFilter().filter(newText);
                try {
                    mAdapter.getFilter().filter(newText);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }
        });
    }

    private void loadFollower() {
        //fragmentActivityCommunication.showProgressDialog(true);
        createGroupViewModel.callUserList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                "yes", 50, 0).observe(this, followersResponse -> {
            if (followersResponse != null) {
                listBeans = (ArrayList<FollowersResponse.ListBean>) followersResponse.getList();
            }
        });
    }

    private void loadGroupList() {
        shimmerFrameLayout.startShimmer();
        createGroupViewModel.callGroupList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                50, 0).observe(this, groupListBean -> {
            if (groupListBean != null) {
                //initGroupAdapter(groupListBean);
                groupList = groupListBean.getList();
                mAdapter = new FilteredGroupAdapter(groupListBean.getList(), new OnGroupClickListener() {
                    @Override
                    public void onClick(GroupListResponse.ListBean listBean, int flag) {
                        switch (flag) {
                            case 1:
                                AlertDeleteGroupFragment alertDeleteGroupFragment = new AlertDeleteGroupFragment();
                                Bundle bundle1 = new Bundle();
                                bundle1.putParcelable("parcel", listBean);
                                alertDeleteGroupFragment.setArguments(bundle1);
                                alertDeleteGroupFragment.show(getChildFragmentManager(), null);
                                break;

                            case 2:
                                Fragment fragment = new EditGroupFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(Constant.KEY_GROUP_NAME, listBean.getName());
                                bundle.putString(Constant.KEY_GROUP_ID, listBean.getId());
                                bundle.putParcelableArrayList("list", listBeans);
                                fragment.setArguments(bundle);
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                                        .addToBackStack(fragment.getClass().getSimpleName()).commit();
                                break;
                        }
                    }
                });
                rvGroupList.setAdapter(mAdapter);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            } else {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }


    private void initGroupAdapter(GroupListResponse groupListResponse) {
        try {
            mGroupAdapter = new GroupListAdapter(Objects.requireNonNull(getActivity()).getApplicationContext());
            mGroupAdapter.setItems(groupListResponse.getList());
            mGroupAdapter.setListener(this);
            rvGroupList.setAdapter(mGroupAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateRow(EditGroupObject editGroupObject) {
        GroupListResponse.ListBean group = mGroupAdapter.getItems().get(editGroupObject.getPosition());
        group.setName(editGroupObject.getGroupName());
        mGroupAdapter.notifyItemChanged(editGroupObject.getPosition());
    }

    private void deleteGroup(GroupListResponse.ListBean item) {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.deleteGroup(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                item.getId(), new ResponseCallback<DeleteGroupresponse>() {
                    @Override
                    public void onSuccess(DeleteGroupresponse data) {
                        try {
                            //mGroupAdapter.remove(item);
                            groupList.remove(item);
                            mAdapter.notifyDataSetChanged();
                            fragmentActivityCommunication.showProgressDialog(false);
                        } catch (IndexOutOfBoundsException e) {
                            e.printStackTrace();
                            fragmentActivityCommunication.showProgressDialog(false);
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                        }
                    }

                    @Override
                    public void onError(Throwable th) {
                        th.printStackTrace();
                        fragmentActivityCommunication.showProgressDialog(false);
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private JSONArray getSelectedUser() {
        JSONArray jsonArray = new JSONArray();
        if (listBeans != null) {
            for (FollowersResponse.ListBean listBean : listBeans) {
                if (listBean.getUser().isSelected()) {
                    jsonArray.put(Integer.parseInt(listBean.getUser().getId()));
                }
            }
        }

        return jsonArray;
    }

    private void createGroup() {
        fragmentActivityCommunication.showProgressDialog(true);
        createGroupViewModel.callCreateGroup(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                "", getSelectedUser()).observe(this, createGroupResponse -> {
            if (createGroupResponse != null) {
                if (createGroupResponse.getSuccess().getMessage().equals("Create group successfully")) {
                    fragmentActivityCommunication.showProgressDialog(false);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                } else {
                    fragmentActivityCommunication.showProgressDialog(false);

                }
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deleteuserGroup(GroupListResponse.ListBean item) {
        deleteGroup(item);
    }


    @Override
    public void onItemClicked(GroupListResponse.ListBean item, int itemID) {
        switch (itemID) {
            case 0:
                //deleteGroup(item);
                AlertDeleteGroupFragment alertDeleteGroupFragment = new AlertDeleteGroupFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("parcel", item);
                alertDeleteGroupFragment.setArguments(bundle1);
                alertDeleteGroupFragment.show(getChildFragmentManager(), null);
                break;

            case 1:
                Fragment fragment = new EditGroupFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.KEY_GROUP_NAME, item.getName());
                bundle.putString(Constant.KEY_GROUP_ID, item.getId());
                bundle.putParcelableArrayList("list", listBeans);
                fragment.setArguments(bundle);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                        .addToBackStack(fragment.getClass().getSimpleName()).commit();
                break;
        }

    }
}