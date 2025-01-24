package com.techacsent.route_recon.fragments;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.SelectFollwerGroupAdapter;
import com.techacsent.route_recon.custom_view.CustomSeekbar;
import com.techacsent.route_recon.event_bus_object.StartServiceObj;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.GroupListResponse;
import com.techacsent.route_recon.model.LocationShareResponse;
import com.techacsent.route_recon.model.ShareLocationModel;
import com.techacsent.route_recon.model.TripFollowerResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.ShareLiveLocationViewModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareLiveLocationFragment extends Fragment {
    private boolean isEdit;
    private CustomSeekbar seekbar;
    private TextView tvTimeInterval;
    private RecyclerView rvList;
    private Button ibtnShareLocation;
    private SelectFollwerGroupAdapter selectMemberAdapter;
    private List<Object> listBeans;
    private ShareLiveLocationViewModel shareLiveLocationViewModel;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private ShareLocationModel shareLocationModel;
    private int totalTimeInSecond = 1800;

    private int hourInSec = 0;
    private int minInSec = 0;
    private ShimmerFrameLayout shimmerFrameLayout;

    public ShareLiveLocationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEdit = getArguments().getBoolean("is_edit");
            Timber.d("%s", isEdit);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share_live_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //fragmentActivityCommunication.hideBottomNav(true);
        shareLiveLocationViewModel = ViewModelProviders.of(this).get(ShareLiveLocationViewModel.class);
        if (shareLocationModel == null) {
            shareLocationModel = new ShareLocationModel();
        }
        listBeans = new ArrayList<>();
        initializeView(view);
        loadUser();
        initializeListener();
    }

    private void initializeView(View view) {
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        seekbar = view.findViewById(R.id.seek_time_interval);
        tvTimeInterval = view.findViewById(R.id.tv_time_interval);
        rvList = view.findViewById(R.id.rv_follower);
        ibtnShareLocation = view.findViewById(R.id.ibtn_share_location);

        rvList.invalidate();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
    }

    private void initializeListener() {
        ibtnShareLocation.setOnClickListener(v -> {
            if (!isEdit) {
                if (((getSelectedGroup() != null && getSelectedGroup().size() > 0) || (getSelectedUser() != null && getSelectedUser().size() > 0)) &&
                        totalTimeInSecond > 0) {
                    shareLiveLocation();
                } else if (totalTimeInSecond == 0) {
                    Toast.makeText(getActivity(), Constant.MSG_SELECT_DURATION, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), Constant.MSG_SELECT_AT_LEAST_ONE_USER, Toast.LENGTH_SHORT).show();
                }
                /*if (getSelectedUser().size() < 1 || getSelectedGroup().size() < 1) {
                    Toast.makeText(getActivity(), Constant.MSG_SELECT_AT_LEAST_ONE_USER, Toast.LENGTH_SHORT).show();
                } else if (totalTimeInSecond == 0) {
                    Toast.makeText(getActivity(), Constant.MSG_SELECT_DURATION, Toast.LENGTH_SHORT).show();
                } else {
                    shareLiveLocation();
                }*/
            } else {
                /*if ((getSelectedUser() != null && getSelectedUser().size() < 1) || (getSelectedGroup() != null && getSelectedGroup().size() < 1)) {
                    Toast.makeText(getActivity(), Constant.MSG_SELECT_AT_LEAST_ONE_USER, Toast.LENGTH_SHORT).show();
                } else if (totalTimeInSecond == 0) {
                    Toast.makeText(getActivity(), Constant.MSG_SELECT_DURATION, Toast.LENGTH_SHORT).show();
                } else {
                    editLiveLocation();
                }*/
                if (((getSelectedGroup() != null && getSelectedGroup().size() > 0) || (getSelectedUser() != null && getSelectedUser().size() > 0)) &&
                        totalTimeInSecond > 0) {
                    editLiveLocation();
                } else if (totalTimeInSecond == 0) {
                    Toast.makeText(getActivity(), Constant.MSG_SELECT_DURATION, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), Constant.MSG_SELECT_AT_LEAST_ONE_USER, Toast.LENGTH_SHORT).show();
                }
            }

        });

        tvTimeInterval.setText(getString(R.string.default_expire_time));

        seekbar.setEventListener((textToShow, hours, minutes) -> {
            tvTimeInterval.setText(textToShow);
            if (hours > 0) {
                hourInSec = hours * 3600;
            }
            if (hours == 0) {
                hourInSec = 0;
            }
            if (minutes > 0) {
                minInSec = minutes * 60;
            }
            if (minutes == 0) {
                minInSec = 0;
            }
            totalTimeInSecond = hourInSec + minInSec;
            Timber.d(String.valueOf(totalTimeInSecond));
        });

    }

    private List getSelectedUser() {
        List<Integer> userList = new ArrayList<>();
        for (Object bean : listBeans) {
            if (bean instanceof TripFollowerResponse.ListBean) {
                if (((TripFollowerResponse.ListBean) bean).getUser().isSelected()) {
                    userList.add(Integer.parseInt(((TripFollowerResponse.ListBean) bean).getUser().getId()));
                }
            }
        }
        return userList;
    }

    private List getSelectedGroup() {
        List<Integer> groupList = new ArrayList<>();
        for (Object bean : listBeans) {
            if (bean instanceof GroupListResponse.ListBean) {
                if (((GroupListResponse.ListBean) bean).isSelected()) {
                    groupList.add(Integer.parseInt(((GroupListResponse.ListBean) bean).getId()));
                }
            }
        }
        return groupList;
    }

    private void shareLiveLocation(){
        fragmentActivityCommunication.showProgressDialog(true);
        shareLocationModel.setIspublic("no");
        shareLocationModel.setDuration(totalTimeInSecond);
        if (getSelectedUser() != null && getSelectedUser().size() > 0) {
            shareLocationModel.setUsers(getSelectedUser());
        }
        if (getSelectedGroup() != null && getSelectedGroup().size() > 0) {
            shareLocationModel.setGroups(getSelectedGroup());
        }
        shareLocationModel.setStatus("Location Shared");
        shareLocationModel.setAll("no");

        shareLiveLocationViewModel.callShareLiveLocation(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                shareLocationModel).observe(this, new Observer<LocationShareResponse>() {
            @Override
            public void onChanged(@Nullable LocationShareResponse locationShareResponse) {
                if(locationShareResponse!=null){
                    Timber.d("Created--> " + locationShareResponse.getId() + "");
                    PreferenceManager.updateValue(Constant.KEY_IS_LOCATION_SHARING, true);
                    PreferenceManager.updateValue(Constant.KEY_START_TIME, System.currentTimeMillis());
                    PreferenceManager.updateValue(Constant.KEY_DURATION, totalTimeInSecond);
                    PreferenceManager.updateValue(Constant.KEY_END_TIME, (System.currentTimeMillis() + totalTimeInSecond * 1000));
                    PreferenceManager.updateValue(Constant.KEY_LOCATION_SHARE_ID, locationShareResponse.getId());
                    EventBus.getDefault().post(new StartServiceObj(true, locationShareResponse.getId(), totalTimeInSecond));
                    //setBackData(true, data.getId(), totalTimeInSecond);
                    fragmentActivityCommunication.showProgressDialog(false);
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                    getActivity().finish();
                }else {
                    fragmentActivityCommunication.showProgressDialog(false);
                }
            }
        });
    }

    private void setBackData(boolean isEdit, int id, int duration) {
        Intent intent = new Intent();
        intent.putExtra("is_shared", isEdit);
        intent.putExtra("shared_id", id);
        intent.putExtra("duration", duration);
        Objects.requireNonNull(getActivity()).setResult(RESULT_OK, intent);
    }

    private void editLiveLocation() {
        fragmentActivityCommunication.showProgressDialog(true);
        shareLocationModel.setIspublic("yes");
        shareLocationModel.setGroups(getSelectedGroup());
        shareLocationModel.setUsers(getSelectedUser());
        shareLocationModel.setStatus("1");
        shareLocationModel.setAll("no");
        shareLocationModel.setDuration(totalTimeInSecond);
        //Toast.makeText(getActivity(), PreferenceManager.getInt(Constant.KEY_LOCATION_SHARE_ID)+"",Toast.LENGTH_SHORT).show();
        Timber.d("Edited--> " + PreferenceManager.getInt(Constant.KEY_LOCATION_SHARE_ID) + "");
        ApiService apiService = new ApiCaller();
        apiService.editSharedLocation(PreferenceManager.getInt(Constant.KEY_LOCATION_SHARE_ID),
                PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                shareLocationModel, new ResponseCallback<LocationShareResponse>() {
                    @Override
                    public void onSuccess(LocationShareResponse data) {
                        PreferenceManager.updateValue(Constant.KEY_IS_LOCATION_SHARING, true);
                        PreferenceManager.updateValue(Constant.KEY_START_TIME, System.currentTimeMillis());
                        PreferenceManager.updateValue(Constant.KEY_DURATION, totalTimeInSecond);
                        PreferenceManager.updateValue(Constant.KEY_END_TIME, (System.currentTimeMillis() + totalTimeInSecond * 1000));
                        PreferenceManager.updateValue(Constant.KEY_LOCATION_SHARE_ID, data.getId());
                        EventBus.getDefault().post(new StartServiceObj(true, data.getId(), totalTimeInSecond));
                        //setBackData(true,data.getId(), totalTimeInSecond);
                        fragmentActivityCommunication.showProgressDialog(false);
                        Timber.d("Newly Created--> " + data.getId() + "");
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                        getActivity().finish();
                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                        fragmentActivityCommunication.showProgressDialog(false);
                    }
                });

    }

    private void loadUser() {
        shimmerFrameLayout.startShimmer();
        shareLiveLocationViewModel.callTripFollower(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "yes", 0, 50, 0).observe(this, followersResponse -> {
            if (followersResponse != null) {
                AsyncTask.execute(() -> listBeans.addAll(followersResponse.getList()));
                loadGroupList();
            } else {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onStop() {
        super.onStop();
        fragmentActivityCommunication.hideBottomNav(false);
    }

    private void loadGroupList() {
        shareLiveLocationViewModel.callGroupList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                50, 0).observe(this, groupListResponse -> {
            if (groupListResponse != null) {
                AsyncTask.execute(() -> {
                    if (groupListResponse.getList() != null) {
                        listBeans.addAll(groupListResponse.getList());
                    }
                });
                Collections.shuffle(listBeans);
                selectMemberAdapter = new SelectFollwerGroupAdapter(listBeans);
                rvList.setAdapter(selectMemberAdapter);

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            } else {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
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
    public void onResume() {
        super.onResume();
        fragmentActivityCommunication.hideBottomNav(true);
    }
}
