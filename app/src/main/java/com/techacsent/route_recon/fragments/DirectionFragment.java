package com.techacsent.route_recon.fragments;


import android.annotation.SuppressLint;
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

import com.mapbox.api.directions.v5.models.LegStep;
import com.mapbox.api.directions.v5.models.RouteLeg;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.DirectionAdapter;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.model.request_body_model.WaypointModel;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.view_model.RouteViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectionFragment extends Fragment {
    private TextView tvCancel;
    private TextView tvRoute;
    private TextView tvDistance;
    private TextView tvTime;

    private RecyclerView rvDirection;
    private RouteViewModel routeViewModel;
    private BasicTripDescription basicTripDescription;
    private WaypointDescription waypointObjectList;

    private List<Point> pointList;
    private DirectionAdapter mAdapter;

    private NavigationOptionInterface navigationOptionInterface;

    public DirectionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            routeViewModel = ViewModelProviders.of(this).get(RouteViewModel.class);
            basicTripDescription = getArguments().getParcelable("parcel");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_direction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigationOptionInterface.hideToolbar(true);
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvRoute = view.findViewById(R.id.tv_route);
        tvDistance = view.findViewById(R.id.tv_distance);
        tvTime = view.findViewById(R.id.tv_time);
        rvDirection = view.findViewById(R.id.rv_direction);
        rvDirection.invalidate();
        rvDirection.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDirection.setHasFixedSize(true);
        rvDirection.setItemAnimator(new DefaultItemAnimator());
        rvDirection.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        pointList = new ArrayList<>();
        waypointObjectList = AppDatabase.getAppDatabase(getActivity()).daoWaypoint().fetchWaypointById(basicTripDescription.getTripId());
        getRoute();
        initListener();
    }

    private void initListener() {
        tvCancel.setOnClickListener(v -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack());
    }

    private void getRoute() {
        navigationOptionInterface.showProgressDialog(true);
        LatLng originCoord = new LatLng(basicTripDescription.getStartPointLat(), basicTripDescription.getStartPointLonX());
        LatLng destinationCoord = new LatLng(basicTripDescription.getEndPointLat(), basicTripDescription.getEndPointLonX());
        Point originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        Point destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        if (waypointObjectList != null && waypointObjectList.getWayPointsBeanList() != null && waypointObjectList.getWayPointsBeanList().size() > 0) {
            for (WaypointModel.WayPointsBean wayPointsBean : waypointObjectList.getWayPointsBeanList()) {
                LatLng waypointLatLng = new LatLng(wayPointsBean.getLat(), wayPointsBean.getLongX());
                Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                pointList.add(wayPoint);
            }
        }
        getRoute(originPosition, destinationPosition, pointList);
    }

    @SuppressLint("SetTextI18n")
    private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        routeViewModel.getFinalRoute(origin, destination, wayPoints).observe(this, currentRoute -> {
            try {
                if (currentRoute != null) {
                    mAdapter = new DirectionAdapter(getActivity());
                    if (currentRoute.legs() != null && Objects.requireNonNull(currentRoute.legs()).size() > 0) {
                        List<RouteLeg> routeLegList = new ArrayList<>(Objects.requireNonNull(currentRoute.legs()));
                        List<LegStep> legStepList = new ArrayList<>();
                        for (RouteLeg routeLeg : routeLegList) {
                            legStepList.addAll(Objects.requireNonNull(routeLeg.steps()));

                        }
                        mAdapter.setItems(legStepList);
                        rvDirection.setAdapter(mAdapter);
                        if (currentRoute.distance() != null) {
                            tvDistance.setText(new DecimalFormat("##.##").format(currentRoute.distance() / 1000) + " km");
                        }
                        if (currentRoute.duration() != null) {
                            double hours = currentRoute.duration() / 3600;
                            double minutes = (currentRoute.duration() % 3600) / 60;
                            tvTime.setText(new DecimalFormat("##.##").format(hours) + " hour, " + new DecimalFormat("##.##").format(minutes) + "minute");
                        }
                        tvRoute.setText(Objects.requireNonNull(currentRoute.legs()).get(0).summary());
                        navigationOptionInterface.showProgressDialog(false);
                    }

                } else {
                    navigationOptionInterface.showProgressDialog(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                navigationOptionInterface.showProgressDialog(false);
            }

        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationOptionInterface) {
            navigationOptionInterface = (NavigationOptionInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement NavigationOptionInterface interface");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        navigationOptionInterface.hideToolbar(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        navigationOptionInterface.hideToolbar(true);
    }
}
