package com.techacsent.route_recon.fragments;


import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.PostReportAdapter;
import com.techacsent.route_recon.interfaces.LoginActivityInterface;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.ReportListResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.ReportFragmentViewModel;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostReportFragment extends Fragment implements OnRecyclerClickListener<ReportListResponse.MarkerReportListBean> {
    private double latitude;
    private double longitude;
    private int tripId;
    private RecyclerView rvReportList;
    private ReportFragmentViewModel reportFragmentViewModel;
    private LoginActivityInterface loginActivityInterface;

    public PostReportFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("lonX");
            tripId = getArguments().getInt("trip_id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reportFragmentViewModel = ViewModelProviders.of(this).get(ReportFragmentViewModel.class);
        rvReportList = view.findViewById(R.id.rv_report_list);
        Button btnClose = view.findViewById(R.id.btn_close);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        rvReportList.setLayoutManager(gridLayoutManager);
        getList();
        btnClose.setOnClickListener(v -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack());
    }

    private void getList() {
        loginActivityInterface.showProgressDialog(true);
        reportFragmentViewModel.getReportMarkList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                "all").observe(this, reportListResponse -> {
            if (reportListResponse != null) {
                initAdapter(reportListResponse);
            } else {
                loginActivityInterface.showProgressDialog(false);
            }
        });
    }

    private void initAdapter(ReportListResponse data) {
        PostReportAdapter postReportAdapter = new PostReportAdapter(getActivity());
        postReportAdapter.setItems(data.getMarkerReportList());
        postReportAdapter.setListener(this);
        rvReportList.setAdapter(postReportAdapter);
        loginActivityInterface.showProgressDialog(false);
    }

    @Override
    public void onItemClicked(ReportListResponse.MarkerReportListBean item) {
        PostReportAlertFragment postReportAlertFragment = new PostReportAlertFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", latitude);
        bundle.putDouble("lonX", longitude);
        bundle.putString("report_title", item.getName());
        bundle.putInt("trip_id", tripId);
        bundle.putInt("report_type", item.getReportType());
        postReportAlertFragment.setArguments(bundle);
        postReportAlertFragment.show(getChildFragmentManager(), null);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginActivityInterface) {
            loginActivityInterface = (LoginActivityInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement LoginActivityInterface interface");
        }
    }
}
