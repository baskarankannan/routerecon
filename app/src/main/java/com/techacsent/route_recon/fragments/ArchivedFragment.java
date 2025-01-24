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
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.ArchivedTripActivity;
import com.techacsent.route_recon.adapter.BeingSharedAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.BeingSharedTripResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;
import static java.util.Objects.requireNonNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArchivedFragment extends Fragment implements OnRecyclerClickListener<BeingSharedTripResponse.ListBean> {

    private RecyclerView rvArchivedList;
    private TextView tvNoItem;
    private ShimmerFrameLayout shimmerFrameLayout;
    private FragmentActivityCommunication fragmentActivityCommunication;


    public ArchivedFragment() {

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_archived, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentActivityCommunication.setTitle(getResources().getString(R.string.text_archived_route));
        rvArchivedList = view.findViewById(R.id.rv_archived_list);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_attending);
        tvNoItem = view.findViewById(R.id.tv_no_item);
        rvArchivedList.invalidate();
        rvArchivedList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvArchivedList.setItemAnimator(new DefaultItemAnimator());
        rvArchivedList.addItemDecoration(new DividerItemDecoration(requireNonNull(getActivity()), VERTICAL));
        loadArchived();

    }


    private void loadArchived() {
        shimmerFrameLayout.startShimmer();
        ApiService apiService = new ApiCaller();
        apiService.callArchiveTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), 20, 0, new ResponseCallback<BeingSharedTripResponse>() {
            @Override
            public void onSuccess(BeingSharedTripResponse data) {
                if (data.getList() != null && data.getList().size() > 0) {
                    initAdapter(data);
                } else {
                    tvNoItem.setVisibility(View.VISIBLE);
                }

                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    private void initAdapter(BeingSharedTripResponse data) {
        BeingSharedAdapter mAdapter = new BeingSharedAdapter(RouteApplication.getInstance().getApplicationContext(), false);
        mAdapter.setItems(data.getList());
        mAdapter.setListener(this);
        rvArchivedList.setAdapter(mAdapter);
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
    public void onItemClicked(BeingSharedTripResponse.ListBean item) {
        /*Intent intent = new Intent(getActivity(), MyTripDescriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("trip_id", item.getTrip().getId());
        bundle.putString("shared_trip_id", item.getTrip().getTripSharingId());
        bundle.putBoolean("is_archieved", true);
        bundle.putBoolean("is_removable", false);
        intent.putExtras(bundle);
        startActivity(intent);*/

        Intent intent = new Intent(getActivity(), ArchivedTripActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("trip_id", item.getTrip().getId());
        bundle.putString("shared_trip_id", item.getTrip().getTripSharingId());
        bundle.putBoolean("is_archieved", true);
        bundle.putBoolean("is_removable", false);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
