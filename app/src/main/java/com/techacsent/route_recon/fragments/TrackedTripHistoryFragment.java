package com.techacsent.route_recon.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.TripDetailsActivity;
import com.techacsent.route_recon.adapter.TrackRecordHistoryAdapter;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.interfaces.NavigationOptionTrackHistoryToolbar;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.TrackHistoryListViewModel;

import java.util.Objects;

public class TrackedTripHistoryFragment extends Fragment implements OnRecyclerItemClickListener<BasicTripDescription> {

    private TrackHistoryListViewModel trackHistoryListViewModel;


    private RecyclerView rvTrackHistory;
    private TrackRecordHistoryAdapter mAdapter;
    private TextView tvEmpty;

    LinearLayoutManager layoutManager;

    private NavigationOptionTrackHistoryToolbar navigationOptionTrackHistoryToolbar;



    public TrackedTripHistoryFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        trackHistoryListViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TrackHistoryListViewModel.class);

        return inflater.inflate(R.layout.fragment_tracked_trip_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTrackHistory = view.findViewById(R.id.rv_next_trip_list);

        Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.setTitle("Track Records");

        TextView tvTitle = view.findViewById(R.id.tv_title);
        ImageView ivAddUpdate = view.findViewById(R.id.iv_add_update);
        ImageView ivRefresh = view.findViewById(R.id.iv_refresh);
        ImageView imageViewBack = view.findViewById(R.id.iv_back);
        TextView tvDone = view.findViewById(R.id.tv_done);
        tvEmpty= view.findViewById(R.id.tv_no_item);

        tvDone.setVisibility(View.GONE);
        ivRefresh.setVisibility(View.GONE);
        ivAddUpdate.setVisibility(View.GONE);
        imageViewBack.setVisibility(View.VISIBLE);
        tvTitle.setText(Constant.TRACKED_TRIPS);

        navigationOptionTrackHistoryToolbar.onToolbar("Track History");

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("back","clicked");

              //  getActivity().onBackPressed();
                getFragmentManager().popBackStack();


            }
        });

        trackHistoryListViewModel.getTrackTripHistory(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), 0,0).observe(getActivity(), data -> {



            if(data.getList().isEmpty()) {


                tvEmpty.setVisibility(View.VISIBLE);
                rvTrackHistory.setVisibility(View.GONE);

            }else{

                mAdapter = new TrackRecordHistoryAdapter(getContext(), data);
                layoutManager = new LinearLayoutManager(getContext());

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTrackHistory.getContext(),
                        layoutManager.getOrientation());

                rvTrackHistory.addItemDecoration(dividerItemDecoration);
                rvTrackHistory.setLayoutManager(layoutManager);
                rvTrackHistory.setAdapter(mAdapter);

                tvEmpty.setVisibility(View.GONE);
                rvTrackHistory.setVisibility(View.VISIBLE);



            }


        });


    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onItemClicked(BasicTripDescription item, int itemID) {
        switch (itemID) {
            case 0:

                Intent intent = new Intent(getActivity(), TripDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("parcel", item);
                bundle.putBoolean("is_previous", false);
                intent.putExtras(bundle);
                getActivity().startActivityForResult(intent,121);

              /*  Intent intent = new Intent(getActivity(), TestActivity.class);
                startActivity(intent);*/



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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationOptionInterface) {
            navigationOptionTrackHistoryToolbar = (NavigationOptionTrackHistoryToolbar) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement NavigationOptionInterface interface");
        }
    }




}
