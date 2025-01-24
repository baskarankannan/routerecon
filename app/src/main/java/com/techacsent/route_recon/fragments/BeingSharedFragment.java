package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.ArchivedTripActivity;
import com.techacsent.route_recon.activity.MyTripDescriptionActivity;
import com.techacsent.route_recon.adapter.BeingSharedAdapter;
import com.techacsent.route_recon.adapter.PendingTripsAdapter;
import com.techacsent.route_recon.event_bus_object.LoadPendingTripobject;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.BeingSharedTripResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.UserTripSharingResponseModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static java.util.Objects.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeingSharedFragment extends Fragment implements OnRecyclerClickListener<BeingSharedTripResponse.ListBean>,
        OnRecyclerItemClickListener<BeingSharedTripResponse.ListBean> {
    private RecyclerView rvAttendingTrip;
    private RecyclerView rvPendingTrip;
    private PendingTripsAdapter mPendingTripsAdapter;
    private BeingSharedAdapter mSharedAdapter;
    private UserTripSharingResponseModel userTripSharingResponseModel;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private ShimmerFrameLayout shimmerAttending;
    private ShimmerFrameLayout shimmerPending;

    public BeingSharedFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_being_shared, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userTripSharingResponseModel = new UserTripSharingResponseModel();
        initializeView(view);
        loadAttendingTrip();
        loadPendingTrip(false);
    }

    private void initializeView(View view) {
        shimmerAttending = view.findViewById(R.id.shimmer_view_attending);
        shimmerPending = view.findViewById(R.id.shimmer_view);

        rvAttendingTrip = view.findViewById(R.id.rv_attending_trips);
        rvPendingTrip = view.findViewById(R.id.rv_pending_trips);

        rvAttendingTrip.invalidate();
        rvAttendingTrip.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttendingTrip.setItemAnimator(new DefaultItemAnimator());
        rvAttendingTrip.addItemDecoration(new DividerItemDecoration(requireNonNull(getActivity()), VERTICAL));

        rvPendingTrip.invalidate();
        rvPendingTrip.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPendingTrip.setItemAnimator(new DefaultItemAnimator());
        rvPendingTrip.addItemDecoration(new DividerItemDecoration(requireNonNull(getActivity()), VERTICAL));
    }

    private void loadAttendingTrip() {
        shimmerAttending.startShimmer();
        ApiService apiService = new ApiCaller();
        apiService.getSharedTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                1, 20, 0, new ResponseCallback<BeingSharedTripResponse>() {
                    @Override
                    public void onSuccess(BeingSharedTripResponse data) {
                        if (data != null && data.getList() != null) {
                            try {
                                initAttendingTripAdapter(data);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                                shimmerAttending.stopShimmer();
                                shimmerAttending.setVisibility(View.GONE);
                            }
                        }
                        shimmerAttending.stopShimmer();
                        shimmerAttending.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable th) {
                        shimmerAttending.stopShimmer();
                        shimmerAttending.setVisibility(View.GONE);
                    }
                });

    }

    private void loadPendingTrip(boolean isReload) {
        shimmerPending.startShimmer();
        ApiService apiService = new ApiCaller();
        apiService.getSharedTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                0, 20, 0, new ResponseCallback<BeingSharedTripResponse>() {
                    @Override
                    public void onSuccess(BeingSharedTripResponse data) {
                        if (data != null && data.getList() != null) {
                            try {
                                initPendingTripAdapter(data);
                                if(!isReload){
                                    shimmerPending.stopShimmer();
                                    shimmerPending.setVisibility(View.GONE);
                                }


                            } catch (NullPointerException e) {
                                if(!isReload){
                                    shimmerPending.stopShimmer();
                                    shimmerPending.setVisibility(View.GONE);
                                }
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable th) {
                        shimmerPending.stopShimmer();
                        shimmerPending.setVisibility(View.GONE);
                    }
                });

    }

    private void initPendingTripAdapter(BeingSharedTripResponse data) {
        mPendingTripsAdapter = new PendingTripsAdapter(getActivity(), true);
        mPendingTripsAdapter.setItems(data.getList());
        mPendingTripsAdapter.setListener(this);
        rvPendingTrip.setAdapter(mPendingTripsAdapter);
    }

    private void initAttendingTripAdapter(BeingSharedTripResponse data) {
        mSharedAdapter = new BeingSharedAdapter(getActivity(), false);
        mSharedAdapter.setItems(data.getList());
        mSharedAdapter.setListener(this);
        rvAttendingTrip.setAdapter(mSharedAdapter);
    }

    private void loadTripDetails(String tripId, String tripSharingId) {
        Intent intent = new Intent(getActivity(), ArchivedTripActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("trip_id", tripId);
        bundle.putString("shared_trip_id", tripSharingId);
        bundle.putBoolean("is_editable", false);
        bundle.putBoolean("is_archieved", true);
        bundle.putBoolean("is_removable", false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void tripSharingAction(int tripSharingId, int status, BeingSharedTripResponse.ListBean listBean, int flag) {
        fragmentActivityCommunication.showProgressDialog(true);
        userTripSharingResponseModel.setTripSharingId(tripSharingId);
        userTripSharingResponseModel.setStatus(String.valueOf(status));
        ApiService apiService = new ApiCaller();
        apiService.callUserActionForTripAttending(PreferenceManager.getString(
                Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), userTripSharingResponseModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                try {
                    mPendingTripsAdapter.remove(listBean);
                    if (flag == 2) {
                        if (mSharedAdapter != null) {
                            mSharedAdapter.add(listBean);
                        } else {
                            addAdapter(listBean);
                        }
                    }
                    fragmentActivityCommunication.showProgressDialog(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    fragmentActivityCommunication.showProgressDialog(false);
                }
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                fragmentActivityCommunication.showProgressDialog(false);
            }
        });
    }

    private void addAdapter(BeingSharedTripResponse.ListBean listBean) {
        mSharedAdapter = new BeingSharedAdapter(getActivity(), false);
        mSharedAdapter.add(listBean);
        mSharedAdapter.setListener(this);
        rvAttendingTrip.setAdapter(mSharedAdapter);
    }

    @Override
    public void onItemClicked(BeingSharedTripResponse.ListBean item) {
        Intent intent = new Intent(getActivity(), MyTripDescriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("trip_id", item.getTrip().getId());
        bundle.putString("shared_trip_id", item.getTrip().getTripSharingId());
        bundle.putBoolean("is_removable", false);
        intent.putExtras(bundle);
        startActivityForResult(intent, 112);
    }

    @Override
    public void onItemClicked(BeingSharedTripResponse.ListBean item, int itemID) {
        switch (itemID) {
            case 0:
                loadTripDetails(item.getTrip().getId(), item.getTrip().getTripSharingId());
                break;

            case 1:
                tripSharingAction(Integer.parseInt(item.getTrip().getTripSharingId()), 2, item, 1);
                break;

            case 2:
                tripSharingAction(Integer.parseInt(item.getTrip().getTripSharingId()), 1, item, 2);
                break;
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
    public void onDetach() {
        super.onDetach();
        fragmentActivityCommunication = null;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 112 && resultCode == RESULT_OK) {
            if (data != null) {
                Fragment fragment = new BeingSharedFragment();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.shared_trip_content, fragment, fragment.getClass().getSimpleName()).commit();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoadPendingTrip(LoadPendingTripobject loadPendingTripobject){
        if(mPendingTripsAdapter!=null){
            mPendingTripsAdapter.clear();
        }
        loadPendingTrip(false);
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
}
