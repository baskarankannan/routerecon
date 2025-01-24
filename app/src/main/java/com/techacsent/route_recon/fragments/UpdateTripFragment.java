/*
package com.techacsent.route_recon.fragments;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.materialrangebar.RangeBar;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.WaypointsAdapter;
import com.techacsent.route_recon.interfaces.OnWaypointRemoveListener;
import com.techacsent.route_recon.model.request_body_model.CreateTripModelClass;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.CreateTripViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.core.constants.Constants.PRECISION_6;

*/
/**
 * A simple {@link Fragment} subclass.
 *//*

public class UpdateTripFragment extends Fragment {
    private View view;
    private MapboxMap map;
    private MapView mapView;
    private Button btnNext;
    private LinearLayout confirmationLayout;
    private LinearLayout layoutSegmented;
    private Button btnSafe;
    private Button btnLandmark;
    private Button btnDanger;
    private Button btnYes;
    private Button btnWayPoint;
    private View waypointFooter;
    private RecyclerView rvWaypoints;
    private WaypointsAdapter waypointsAdapter;
    private TextView tvCreate;
    private TextView tvSuggestion;
    private Icon safeIcon;
    private Icon landmarkIcon;
    private Icon deleteIcon;
    private Icon startMarker;
    private Icon waypointMarker;
    private Icon endMarker;
    private ProgressDialogFragment progressDialogFragment;
    private Point originPosition;
    private Point destinationPosition;
    private LatLng originCoord;
    private LatLng destinationCoord;
    private List<Point> pointList;
    private List<CreateTripModelClass.MarkersBean> markersBeanList;
    private List<CreateTripModelClass.WayPointsBean> wayPointsBeansList;
    private Set<CreateTripModelClass.MarkersBean> markersBeanSet;
    private DirectionsRoute currentRoute;
    private String encodedPath;
    private Polyline optimizedPolyline;
    private LayoutInflater layoutInflater;
    private CreateTripViewModel createTripViewModel;
    private CreateTripModelClass createTripModelClass;
    private int type = 1;
    private boolean isWayPointAdded = false;
    */
/*private TripListObject tripListObject;*//*

    Handler handler = new Handler();
    private static final String TAG = UpdateTripFragment.class.getSimpleName();

    public UpdateTripFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            createTripModelClass = getArguments().getParcelable(Constant.PARCELABLE_TRIP_RESPONSE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_update_trip, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createTripViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(CreateTripViewModel.class);
        pointList = new ArrayList<>();
        markersBeanList = new ArrayList<>();
        wayPointsBeansList = new ArrayList<>();
        markersBeanSet = new HashSet<>();
        if (createTripModelClass.getMarkers() != null) {
            markersBeanList.addAll(createTripModelClass.getMarkers());
        }
        if (createTripModelClass.getWayPoints() != null) {
            wayPointsBeansList.addAll(createTripModelClass.getWayPoints());
        }
        initializeView(savedInstanceState);
        showRouteAndMap(createTripModelClass);
        initializeListener();
        initiateSwipe();
    }

    private void initializeView(Bundle savedInstanceState) {
        layoutInflater = (LayoutInflater) Objects.requireNonNull(getContext())
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.setStyleUrl(Style.MAPBOX_STREETS);
        layoutSegmented = view.findViewById(R.id.layout_segment);
        btnSafe = view.findViewById(R.id.btn_safe);
        btnLandmark = view.findViewById(R.id.btn_landmark);
        btnDanger = view.findViewById(R.id.btn_danger);
        btnNext = view.findViewById(R.id.btn_next);
        confirmationLayout = view.findViewById(R.id.confirmation_layout);
        btnYes = view.findViewById(R.id.btn_yes);
        btnWayPoint = view.findViewById(R.id.btn_add_waypoints);
        rvWaypoints = view.findViewById(R.id.rv_waypoints);
        waypointFooter = view.findViewById(R.id.waypoint_footer);
        tvCreate = view.findViewById(R.id.tv_create);
        tvSuggestion = view.findViewById(R.id.tv_suggestion);
        progressDialogFragment = new ProgressDialogFragment();
        IconFactory iconFactory = IconFactory.getInstance(Objects.requireNonNull(getActivity()));
        safeIcon = iconFactory.fromResource(R.drawable.safe_marker);
        landmarkIcon = iconFactory.fromResource(R.drawable.landmark_marker);
        deleteIcon = iconFactory.fromResource(R.drawable.delete_marker);
        startMarker = iconFactory.fromResource(R.mipmap.start_trip_marker);
        waypointMarker = iconFactory.fromResource(R.mipmap.waypoint_marker);
        endMarker = iconFactory.fromResource(R.mipmap.end_trip_marker);
        handler.postDelayed(() -> tvSuggestion.setVisibility(View.GONE), 3000);
        rvWaypoints.invalidate();
        rvWaypoints.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWaypoints.setHasFixedSize(true);
        rvWaypoints.setItemAnimator(new DefaultItemAnimator());
        rvWaypoints.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        tvCreate.setText(Constant.MSG_UPDATE_TRIP);
        if (createTripModelClass.getWayPoints() != null && createTripModelClass.getWayPoints().size() > 0) {
            waypointsAdapter = new WaypointsAdapter(wayPointsBeansList, (position, wayPointsBean) -> {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
                LatLng waypointLatLng = new LatLng(wayPointsBean.getLat(), wayPointsBean.getLongX());
                Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                pointList.remove(wayPoint);
                wayPointsBeansList.remove(wayPointsBean);
                waypointsAdapter.notifyItemRemoved(position);
                createTripModelClass.getWayPoints().remove(wayPointsBean);
                getRoute(originPosition, destinationPosition, pointList);
            });
            rvWaypoints.setAdapter(waypointsAdapter);
        }

    }

    private void initiateSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvWaypoints);
    }

    private void moveItem(int oldPosition, int newPosition) {
        CreateTripModelClass.WayPointsBean item = wayPointsBeansList.get(oldPosition);
        wayPointsBeansList.remove(item);
        wayPointsBeansList.add(newPosition, item);
        waypointsAdapter.notifyItemMoved(oldPosition, newPosition);
        updateRoute();
    }

    private void initializeListener() {
        btnSafe.setOnClickListener(v -> {
            btnSafe.setBackgroundResource(R.drawable.next_trip_colored_bg);
            btnLandmark.setBackgroundResource(R.drawable.pending_unselected);
            btnDanger.setBackgroundResource(R.drawable.last_trip_bg);
            btnSafe.setTextColor(getResources().getColor(R.color.white));
            btnLandmark.setTextColor(getResources().getColor(R.color.login_btn));
            btnDanger.setTextColor(getResources().getColor(R.color.login_btn));
            type = 1;
        });

        btnLandmark.setOnClickListener(v -> {
            btnLandmark.setBackgroundResource(R.drawable.pending_selected);
            btnDanger.setBackgroundResource(R.drawable.last_trip_bg);
            btnSafe.setBackgroundResource(R.drawable.next_trip_bg);
            btnLandmark.setTextColor(getResources().getColor(R.color.white));
            btnSafe.setTextColor(getResources().getColor(R.color.login_btn));
            btnDanger.setTextColor(getResources().getColor(R.color.login_btn));
            type = 2;
        });

        btnDanger.setOnClickListener(v -> {
            btnDanger.setBackgroundResource(R.drawable.last_trip_colored_bg);
            btnSafe.setBackgroundResource(R.drawable.next_trip_bg);
            btnLandmark.setBackgroundResource(R.drawable.pending_unselected);
            btnDanger.setTextColor(getResources().getColor(R.color.white));
            btnSafe.setTextColor(getResources().getColor(R.color.login_btn));
            btnLandmark.setTextColor(getResources().getColor(R.color.login_btn));
            type = 3;
        });
        btnNext.setOnClickListener(v -> {
            confirmationLayout.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.GONE);
            layoutSegmented.setVisibility(View.GONE);
        });
       */
/* btnYes.setOnClickListener(v -> updateTrip());
        tvCreate.setOnClickListener(v -> updateTrip());*//*

        btnWayPoint.setOnClickListener(v -> {
            type = 4;
            isWayPointAdded = true;
            confirmationLayout.setVisibility(View.GONE);
            waypointFooter.setVisibility(View.VISIBLE);
            tvSuggestion.setVisibility(View.VISIBLE);
            tvSuggestion.setText(R.string.waypoint_suggestion);
            handler.postDelayed(() -> tvSuggestion.setVisibility(View.GONE), 3000);

        });
    }

    */
/*private void updateTrip() {
        progressDialogFragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);
        createTripModelClass.setTripJson(encodedPath);
        markersBeanList = new ArrayList<>(markersBeanSet);
        createTripModelClass.setMarkers(markersBeanList);
        createTripViewModel.getUpdateTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), createTripModelClass)
                .observe(getActivity(), createTripResponse -> {
                    if (createTripResponse != null) {
                        if (createTripResponse.getMessage().equals("Update trip successfully")) {
                            tripListObject = RealmController.with(getActivity()).getTripDetails(String.valueOf(PreferenceManager.getInt(Constant.KEY_USER_ID)), Integer.parseInt(createTripModelClass.getId()));
                            new UpdateTripRealm(getActivity(), tripListObject, createTripResponse.getData(), encodedPath, () -> {
                                progressDialogFragment.dismiss();
                                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                Fragment fragment = new SuccessFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("trip_id", Integer.parseInt(createTripResponse.getData().getId()));
                                bundle.putBoolean("is_new_trip", false);
                                fragment.setArguments(bundle);
                                loadSuccessFragment(fragment);
                            }).execute();

                        } else {
                            progressDialogFragment.dismiss();
                            Toast.makeText(getActivity(), Constant.MSG_TRIP_CREATION_FAILED, Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }*//*


    private void loadSuccessFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    private void showRouteAndMap(CreateTripModelClass createTripModelClass) {
        mapView.getMapAsync(mapboxMap -> {
            map = mapboxMap;
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(createTripModelClass.getStartPoint().getLat(), createTripModelClass.getStartPoint().getLongX()))
                    .zoom(9)
                    .build();
            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), Constant.TOTAL_TIME_INTERVAL);
            originCoord = new LatLng(createTripModelClass.getStartPoint().getLat(), createTripModelClass.getStartPoint().getLongX());
            destinationCoord = new LatLng(createTripModelClass.getEndPoint().getLat(), createTripModelClass.getEndPoint().getLongX());
            originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
            destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
            if (createTripModelClass.getWayPoints() != null && createTripModelClass.getWayPoints().size() > 0) {
                showRoute();
            } else {
                addStartPointMarker(originPosition);
                addDestinationMarker(destinationPosition);
                getRoute(originPosition, destinationPosition, null);
            }
            if (createTripModelClass.getMarkers() != null && createTripModelClass.getMarkers().size() > 0) {
                markersBeanSet = new HashSet<>(createTripModelClass.getMarkers());
                for (CreateTripModelClass.MarkersBean markersBean : markersBeanSet)
                    if (markersBean.getMarkType() == 1) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .title(markersBean.getAddress())
                                .snippet("marker")
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                .setIcon(safeIcon);
                    } else if (markersBean.getMarkType() == 2) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .title(markersBean.getAddress())
                                .snippet("marker")
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                .setIcon(landmarkIcon);
                    } else if (markersBean.getMarkType() == 3) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .title(markersBean.getAddress())
                                .snippet("marker")
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                .setIcon(deleteIcon);
                        mapboxMap.addPolygon(Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()), markersBean.getRadius(), 64));
                    }
            }

            mapboxMap.addOnMapLongClickListener(point -> {
                if (!isWayPointAdded) {
                    CreateTripModelClass.MarkersBean markersBean = new CreateTripModelClass.MarkersBean();
                    markersBean.setLat(point.getLatitude());
                    markersBean.setLongX(point.getLongitude());
                    markersBean.setTripSpecific(1);
                    markersBean.setAddress(getAddress(point, false));
                    markersBean.setFullAddress(getAddress(point, true));
                    if (type == 1) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .title(markersBean.getAddress())
                                .snippet("marker")
                                .position(point))
                                .setIcon(safeIcon);
                        markersBean.setMarkType(1);
                    } else if (type == 2) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .title(markersBean.getAddress())
                                .snippet("marker")
                                .position(point))
                                .setIcon(landmarkIcon);
                        markersBean.setMarkType(2);
                    } else if (type == 3) {
                        markersBean.setMarkType(3);
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                        LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity())
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        assert inflater != null;
                        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.fragment_set_radius, null);
                        alertBuilder.setView(view);
                        alertBuilder.setCancelable(false);
                        final AlertDialog dialog = alertBuilder.create();
                        dialog.show();
                        RangeBar rangeBar = view.findViewById(R.id.rangebar);
                        TextView tvRadius = view.findViewById(R.id.tv_radius);
                        TextView tvOk = view.findViewById(R.id.tv_ok);
                        rangeBar.setOnRangeBarChangeListener((rangeBar1, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> tvRadius.setText(rightPinValue));
                        tvOk.setOnClickListener(v -> {
                            markersBean.setRadius(Double.parseDouble(tvRadius.getText().toString().trim()));
                            dialog.dismiss();
                            mapboxMap.addMarker(new MarkerOptions()
                                    .title(markersBean.getAddress())
                                    .snippet("marker")
                                    .position(point))
                                    .setIcon(deleteIcon);
                            mapboxMap.addPolygon(Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()), markersBean.getRadius(), 64));
                        });
                    }
                    markersBeanSet.add(markersBean);

                } else {
                    CreateTripModelClass.WayPointsBean wayPointsBean = new CreateTripModelClass.WayPointsBean();
                    wayPointsBean.setLat(point.getLatitude());
                    wayPointsBean.setLongX(point.getLongitude());
                    wayPointsBean.setType(1);
                    wayPointsBean.setAddress(getAddress(point, false));
                    wayPointsBean.setFullAddress(getAddress(point, true));
                    Marker marker = mapboxMap.addMarker(new MarkerOptions()
                            .position(new LatLng(point.getLatitude(), point.getLongitude()))
                            .title(getAddress(point, false))
                            .snippet("waypoint")
                            .icon(waypointMarker));
                    wayPointsBean.setMarkerId(marker.getId());
                    wayPointsBeansList.add(wayPointsBean);
                    waypointsAdapter = new WaypointsAdapter(wayPointsBeansList, (position1, wayPointsBean1) -> {
                        LatLng waypointLatLng = new LatLng(wayPointsBean1.getLat(), wayPointsBean1.getLongX());
                        Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                        pointList.remove(wayPoint);
                        wayPointsBeansList.remove(wayPointsBean1);
                        waypointsAdapter.notifyItemRemoved(position1);
                        updateRoute();
                        mapboxMap.removeAnnotation(wayPointsBean1.getMarkerId());

                    });
                    rvWaypoints.setAdapter(waypointsAdapter);
                    waypointsAdapter.notifyDataSetChanged();
                    updateRoute();
                    createTripModelClass.setWayPoints(wayPointsBeansList);
                }
            });
            mapboxMap.setInfoWindowAdapter(marker -> {
                @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.marker_popup, null);
                TextView tvAddress = view.findViewById(R.id.tv_address);
                TextView tvRemove = view.findViewById(R.id.tv_delete);
                TextView tvEdit = view.findViewById(R.id.tv_edit);
                tvAddress.setText(marker.getTitle());
                if (!marker.getSnippet().equals("marker")) {
                    tvRemove.setVisibility(View.GONE);
                    tvEdit.setVisibility(View.GONE);
                }
                tvRemove.setOnClickListener(v -> {
                    try {
                        for (CreateTripModelClass.MarkersBean markersBean : markersBeanSet) {
                            if (marker.getPosition().getLatitude() == markersBean.getLat()) {
                                markersBeanSet.remove(markersBean);
                            }
                        }
                        marker.hideInfoWindow();
                        mapboxMap.removeAnnotation(marker.getId());
                    } catch (ConcurrentModificationException e) {
                        e.printStackTrace();
                    }
                });

                tvEdit.setOnClickListener(v -> {
                    for (CreateTripModelClass.MarkersBean markersBean : createTripModelClass.getMarkers()) {
                        if (marker.getPosition().getLatitude() == markersBean.getLat()) {
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                            LayoutInflater inflater = (LayoutInflater) Objects.requireNonNull(getActivity())
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            assert inflater != null;
                            @SuppressLint("InflateParams") View view1 = inflater.inflate(R.layout.fragment_edit_marker, null);
                            alertBuilder.setView(view1);
                            alertBuilder.setCancelable(false);
                            final AlertDialog dialog = alertBuilder.create();
                            dialog.show();
                            EditText etAddress = view1.findViewById(R.id.et_address);
                            EditText etFullAddress = view1.findViewById(R.id.et_full_address);
                            Button btnOkay = view1.findViewById(R.id.btn_edit_marker);
                            etAddress.setText(markersBean.getAddress());
                            etFullAddress.setText(markersBean.getFullAddress());
                            btnOkay.setOnClickListener(v1 -> {
                                markersBean.setAddress(etAddress.getText().toString().trim());
                                markersBean.setFullAddress(etFullAddress.getText().toString().trim());
                                dialog.dismiss();
                                marker.hideInfoWindow();
                            });
                        }
                    }
                });
                return view;
            });
        });
    }

    private void updateRoute() {
        pointList.clear();
        for (int i = 0; i < wayPointsBeansList.size(); i++) {
            LatLng waypointLatLng = new LatLng(wayPointsBeansList.get(i).getLat(), wayPointsBeansList.get(i).getLongX());
            Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
            pointList.add(wayPoint);
        }
        addStartPointMarker(originPosition);
        addDestinationMarker(destinationPosition);
        getRoute(originPosition, destinationPosition, pointList);
    }

    private void addStartPointMarker(Point point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .snippet("start")
                .title(getAddress(new LatLng(point.latitude(), point.longitude()), false))
                .icon(startMarker));
    }

    private void addWaypointtMarker(Point point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .snippet("waypoint")
                .title(getAddress(new LatLng(point.latitude(), point.longitude()), false))
                .icon(waypointMarker));
    }

    private void addDestinationMarker(Point point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.latitude(), point.longitude()))
                .snippet("end")
                .title(getAddress(new LatLng(point.latitude(), point.longitude()), false))
                .icon(endMarker));
    }

    private void showRoute() {
        if (createTripModelClass.getWayPoints().size() > 0) {
            for (int i = 0; i < createTripModelClass.getWayPoints().size(); i++) {
                LatLng waypointLatLng = new LatLng(createTripModelClass.getWayPoints().get(i).getLat(), createTripModelClass.getWayPoints().get(i).getLongX());
                Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                addWaypointtMarker(wayPoint);
                pointList.add(wayPoint);
            }
            addStartPointMarker(originPosition);
            addDestinationMarker(destinationPosition);
            getRoute(originPosition, destinationPosition, pointList);
        }
    }


    private String getAddress(LatLng point, boolean isFullAddress) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(point.getLatitude(), point.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            if (isFullAddress) {
                return address.concat(", " + city).concat(", " + country);
            } else {
                return address;
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        assert Mapbox.getAccessToken() != null;
        NavigationRoute.Builder builder = NavigationRoute.builder(getActivity())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .destination(destination);
        if (wayPoints != null && wayPoints.size() > 0) {
            for (Point point : wayPoints)
                builder.addWaypoint(point);
        }
        builder.build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
                Log.e(TAG, "Response code: " + response.code());
                Log.e(TAG, "Response body: " + response.body());
                if (response.body() == null) {
                    Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Log.e(TAG, "No routes found");
                    return;
                }
                currentRoute = response.body().routes().get(0);
                drawOptimizedRoute(currentRoute);

                encodedPath = Base64.encodeToString(currentRoute.geometry().getBytes(), Base64.DEFAULT);
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());

            }
        });
    }

    private void drawOptimizedRoute(DirectionsRoute route) {
        if (optimizedPolyline != null) {
            map.removePolyline(optimizedPolyline);
        }
        LatLng[] pointsToDraw = convertLineStringToLatLng(route);
        optimizedPolyline = map.addPolyline(new PolylineOptions()
                .add(pointsToDraw)
                .color(Color.parseColor(Constant.TEAL_COLOR))
                .width(Constant.POLYLINE_WIDTH));
    }

    private LatLng[] convertLineStringToLatLng(DirectionsRoute route) {
        LineString lineString = LineString.fromPolyline(Objects.requireNonNull(route.geometry()), PRECISION_6);
        List<Point> coordinates = lineString.coordinates();
        LatLng[] points = new LatLng[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            points[i] = new LatLng(
                    coordinates.get(i).latitude(),
                    coordinates.get(i).longitude());
        }
        return points;
    }

}
*/
