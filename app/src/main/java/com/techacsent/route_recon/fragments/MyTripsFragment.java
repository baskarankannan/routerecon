package com.techacsent.route_recon.fragments;

import androidx.lifecycle.ViewModelProviders;
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
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.MyTripDescriptionActivity;
import com.techacsent.route_recon.adapter.MyTripAdapter;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.MyTripsResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.MyTripsViewModel;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class MyTripsFragment extends Fragment implements OnRecyclerClickListener<MyTripsResponse.ListBean> {
    private RecyclerView rvMyTripList;
    private TextView tvEmptyView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private MyTripsViewModel myTripsViewModel;

    public MyTripsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_trips, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        //getMyTrip();
        //if(myTripsViewModel.myTripsResponseMutableLiveData==null)
        getMyTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID));
    }

    private void initializeView(View view) {
        myTripsViewModel = ViewModelProviders.of(this).get(MyTripsViewModel.class);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        rvMyTripList = view.findViewById(R.id.rv_my_trips);
        tvEmptyView = view.findViewById(R.id.tv_no_trip);
        rvMyTripList.invalidate();
        rvMyTripList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMyTripList.setHasFixedSize(false);
        rvMyTripList.setItemAnimator(new DefaultItemAnimator());
        rvMyTripList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), VERTICAL));
    }

    private void getMyTrip(String token){
        myTripsViewModel.getMyTripFromServer(token, 20, 0).observe(this, data -> {
            if(data!=null){
                if (data.getList().size() > 0) {
                    loadData(data);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                } else {
                    tvEmptyView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }else {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });

    }


    private void loadData(MyTripsResponse myTripsResponse) {
        MyTripAdapter mAdapter = new MyTripAdapter(getActivity(), false);
        mAdapter.setItems(myTripsResponse.getList());
        mAdapter.setListener(this);
        rvMyTripList.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(MyTripsResponse.ListBean item) {
        Intent intent = new Intent(getActivity(), MyTripDescriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("trip_id", item.getTrip().getId());
        bundle.putString("shared_trip_id", item.getTrip().getTripSharingId());
        bundle.putParcelable("parcel", item);
        bundle.putBoolean("is_editable", true);
        bundle.putBoolean("is_archieved", false);
        bundle.putBoolean("is_removable", true);
        intent.putExtras(bundle);
        startActivityForResult(intent, 111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            if (data != null) {
                Fragment fragment = new MyTripsFragment();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.shared_trip_content, fragment, fragment.getClass().getSimpleName()).commit();
            }
        }
    }
}
