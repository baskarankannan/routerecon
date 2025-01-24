package com.techacsent.route_recon.fragments;

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

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.TripDetailsActivity;
import com.techacsent.route_recon.adapter.TripAdapter;
import com.techacsent.route_recon.event_bus_object.RemoveListObject;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

public class LastTripFragment extends Fragment implements OnRecyclerItemClickListener<BasicTripDescription> {
    private RecyclerView rvLastTrip;
    private TripAdapter mAdapter;
    private TextView tvEmpty;

    public LastTripFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_last_trip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvLastTrip = view.findViewById(R.id.rv_last_trip_list);
        tvEmpty = view.findViewById(R.id.tv_no_item);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvLastTrip.setLayoutManager(layoutManager);
        rvLastTrip.setItemAnimator(new DefaultItemAnimator());
        rvLastTrip.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        loadLastTrip();
    }

    private void loadLastTrip() {
        mAdapter = new TripAdapter(getActivity());
        mAdapter.setItems(AppDatabase.getAppDatabase(getActivity()).daoTripBasic().getLastTrip(System.currentTimeMillis()));
        mAdapter.setListener(this);
        rvLastTrip.setAdapter(mAdapter);
        if(mAdapter.getItemCount()<1){
            tvEmpty.setVisibility(View.VISIBLE);
        }else {
            tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.clear();
            mAdapter.setItems(AppDatabase.getAppDatabase(getActivity()).daoTripBasic().getLastTrip(System.currentTimeMillis()));
            if(mAdapter.getItemCount()<1){
                tvEmpty.setVisibility(View.VISIBLE);
            }else {
                tvEmpty.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onItemClicked(BasicTripDescription item, int itemID) {
        switch (itemID) {
            case 0:
                Intent intent = new Intent(getActivity(), TripDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("parcel", item);
                bundle.putBoolean("is_previous",true);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent,121);
                break;

            case 1:
                DeleteTripFragment deleteTripFragment = new DeleteTripFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("parcel", item);
                deleteTripFragment.setArguments(bundle1);
                deleteTripFragment.show(getChildFragmentManager(), null);
                break;
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
    public void updateList(BasicTripDescription item) {
        mAdapter.remove(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dismissLoading(Boolean bool) {
        if (mAdapter != null) {
            mAdapter.clear();
        }
        loadLastTrip();
        EventBus.getDefault().post(1);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dismissLoading(RemoveListObject removeListObject) {
        if (mAdapter != null) {
            mAdapter.clear();
        }
    }
}
