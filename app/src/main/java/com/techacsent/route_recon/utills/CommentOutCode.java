package com.techacsent.route_recon.utills;

public class CommentOutCode {

     /*private void loadNextTrip() {
        if (nextTripList != null) {
            tvNoItem.setVisibility(View.GONE);
            mTripListAdapter = new TripListAdapter(nextTripList, tripId -> {
                Intent intent = new Intent(getActivity(), TripDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.BUNDLE_KEY_TRIP_DETAILS_PARCELABLE, tripId);
                intent.putExtras(bundle);
                startActivity(intent);
            });
            rvNextTrip.setAdapter(mTripListAdapter);
            rvNextTrip.invalidate();
            rvNextTrip.setLayoutManager(new LinearLayoutManager(getActivity()));
            rvNextTrip.setHasFixedSize(true);
            rvNextTrip.setItemAnimator(new DefaultItemAnimator());
            rvNextTrip.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        }

    }*/

     /*private void getBadgeCount() {
        ApiService apiService = new ApiCaller();
        apiService.getBadgeCount(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), new ResponseCallback<BadgeCountResponse>() {
            @Override
            public void onSuccess(BadgeCountResponse data) {
                PreferenceManager.updateValue(Constant.KEY_IS_BADGE_LOADED, true);
                if (data.getBadgecounts().getLocationshare() > 0) {
                    PreferenceManager.updateValue(Constant.KEY_LOCATION_SHARING_BADGE_COUNT, data.getBadgecounts().getLocationshare());
                    showBadge(bottomNavigationView, R.id.action_tracker, String.valueOf(data.getBadgecounts().getLocationshare()));
                }
                if (data.getBadgecounts().getFriendreqsend() + data.getBadgecounts().getFriendreqaccept() > 0) {
                    PreferenceManager.updateValue(Constant.KEY_FRIEND_REQ_BADGE_COUNT, data.getBadgecounts().getFriendreqsend() + data.getBadgecounts().getFriendreqaccept());
                    showBadge(bottomNavigationView, R.id.action_settings, String.valueOf(PreferenceManager.getInt(Constant.KEY_FRIEND_REQ_BADGE_COUNT)));
                }

                if (data.getBadgecounts().getTripshare() + data.getBadgecounts().getTripaccept() > 0) {
                    PreferenceManager.updateValue(Constant.KEY_TRIP_SHARING_BADGE_COUNT, data.getBadgecounts().getTripshare() + data.getBadgecounts().getTripaccept());
                    showBadge(bottomNavigationView, R.id.action_shared, String.valueOf(PreferenceManager.getInt(Constant.KEY_TRIP_SHARING_BADGE_COUNT)));
                }
            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }*/

    /*private void checkStatus() {
     *//*Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        final OneTimeWorkRequest getStatusWorker =
                new OneTimeWorkRequest.Builder(CheckStatusWorker.class)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance().getWorkInfoByIdLiveData(getStatusWorker.getId()).observe(this, workInfo -> {
            if (workInfo != null && workInfo.getState().isFinished()) {
                String data = workInfo.getOutputData().getString("data");
                String msg = workInfo.getOutputData().getString("msg");
                if (data != null) {
                    Gson gson = new Gson();
                    SubscriptionResponse subscriptionResponse = new SubscriptionResponse();
                    subscriptionResponse = gson.fromJson(data, SubscriptionResponse.class);
                    if (subscriptionResponse != null) {
                        if (subscriptionResponse.getSubscriptionStatus().equals("payable")) {
                            PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_STATUS, subscriptionResponse.getSubscriptionStatus());
                            gotoPaymentActivity();

                        } else {
                            PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_STATUS, subscriptionResponse.getSubscriptionStatus());
                        }

                    }
                }
            }
        });*//*



        SubscriptionModel subscriptionModel = new SubscriptionModel();
        subscriptionModel.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
        ApiService apiService = new ApiCaller();
        apiService.getSubscriptionStatus(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                subscriptionModel, new ResponseCallback<SubscriptionResponse>() {
                    @Override
                    public void onSuccess(SubscriptionResponse data) {
                        PreferenceManager.updateValue(Constant.KEY_SUBSCRIPTION_STATUS, data.getSubscriptionStatus());
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(data);
                        Timber.d(data.getSubscriptionStatus());
                        if (data.getSubscriptionStatus().equals("payable")) {
                            gotoPaymentActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable th) {
                        Toast.makeText(getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }*/

   /* private boolean isOnline(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else {
            return false;
        }
    }*/

   /* @Override
    public void onItemClicked(ReportListResponse.MarkerReportListBea item) {
        PostReportAlertFragment postReportAlertFragment = new PostReportAlertFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", latitude);
        bundle.putDouble("lonX", longitude);
        bundle.putInt("trip_id", tripId);
        bundle.putInt("report_type", item.getPosition());
        postReportAlertFragment.setArguments(bundle);
        postReportAlertFragment.show(getChildFragmentManager(), null);
    }*/


   /*postReportModelList.add(new PostReportModel(R.drawable.ic_bad_route, "Bad Route", 1));
        postReportModelList.add(new PostReportModel(R.drawable.ic_confusing_instruction, "Confusing Instruction", 2));
        postReportModelList.add(new PostReportModel(R.drawable.ic_report_crime, "Report Crime", 3));
        postReportModelList.add(new PostReportModel(R.drawable.ic_not_allowed_1, "Not Allowed", 4));
        postReportModelList.add(new PostReportModel(R.drawable.ic_other_map_issue, "Other Map Issue", 5));
        postReportModelList.add(new PostReportModel(R.drawable.ic_report_traffic, "Report Traffic", 6));
        postReportModelList.add(new PostReportModel(R.drawable.ic_road_closed, "Road Closed", 7));
        postReportModelList.add(new PostReportModel(R.drawable.ic_road_accident, "Road Accident", 8));
        PostReportAdapter postReportAdapter = new PostReportAdapter(getActivity());
        postReportAdapter.setItems(postReportModelList);
        postReportAdapter.setListener(this::onItemClicked);
        rvReportList.setAdapter(postReportAdapter);*/

   /*if (listBean.getBeginTime().equals(Constant.DEFAULT_DATE)) {
                        lastTripList.add(listBean);
                    } else {
                        try {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);
                            *//*Date currentDate= Calendar.getInstance().getTime();
                            String todaysTime=simpleDateFormat.format((currentDate));*//*
                            Date tripBeginTime = simpleDateFormat.parse(listBean.getBeginTime());
                            if (System.currentTimeMillis() > tripBeginTime.getTime()) {
                                lastTripList.add(listBean);
                            } else {
                                nextTripList.add(listBean);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }*/

    /* NavigationRoute.builder(getActivity())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
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
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, map, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);

                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.e(TAG, "Error: " + t.getMessage());

                    }
                });*/

    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void updateContact(ContactUpdateObject contactUpdateObject) {
        if (contactUpdateObject.isUpdate()) {
            realm.beginTransaction();
            contactList.get(contactUpdateObject.getPosition()).setContactName(contactUpdateObject.getContactName());
            contactList.get(contactUpdateObject.getPosition()).setContactNo(contactUpdateObject.getContactNo());
            realm.commitTransaction();
            emergencyContactAdapter.notifyItemChanged(contactUpdateObject.getPosition());
        }
    }*/

     /*mapboxMap.addOnCameraMoveListener(() -> {
                viewportWidth = (int) mapboxMap.getWidth();
                viewportHeight = (int) mapboxMap.getHeight();
                southEastLatLng = mapboxMap.getProjection().fromScreenLocation(new PointF(viewportWidth, viewportHeight));
                northWest = mapboxMap.getProjection().fromScreenLocation(new PointF(0, 0));
                mTripMarkerGetModel.setSelat(String.valueOf(southEastLatLng.getLatitude()));
                mTripMarkerGetModel.setSelon(String.valueOf(southEastLatLng.getLongitude()));
                mTripMarkerGetModel.setNwlat(String.valueOf(northWest.getLatitude()));
                mTripMarkerGetModel.setNwlon(String.valueOf(northWest.getLongitude()));
                mTripMarkerGetModel.setLimit(String.valueOf(10));
                mTripMarkerGetModel.setTripSpecific(String.valueOf(0));
                getTripMarkerList(mTripMarkerGetModel, mapboxMap);

            });*/

     /*private void getRoute(Point origin, Point destination) {
        assert Mapbox.getAccessToken() != null;
        NavigationRoute.builder(getActivity())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .alternatives(true)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        Log.e(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, map, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);

                    }

                    @Override
                    public void onFailure(@NonNull Call<DirectionsResponse> call, Throwable t) {
                        Log.e(TAG, "Error: " + t.getMessage());

                    }
                });
    }*/
      /*private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        NavigationRoute.Builder builder = NavigationRoute.builder(getActivity())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination);
        if (wayPoints != null && wayPoints.size() > 0) {
            for (Point point : wayPoints)
                builder.addWaypoint(point);
        }
        builder.build().getRoute(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
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
                if (navigationMapRoute != null) {
                    navigationMapRoute.removeRoute();
                } else {
                    navigationMapRoute = new NavigationMapRoute(null, mapView, map, R.style.NavigationMapRoute);
                }
                navigationMapRoute.addRoute(currentRoute);

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());

            }
        });
    }*/

      /*    private void setupMap(TripDetailsResponse.DataBean tripDetailsResponse) {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                map = mapboxMap;
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(tripDetailsResponse.getStartpoint().getLat(), tripDetailsResponse.getStartpoint().getLongX()))
                        .zoom(9)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), Constant.TOTAL_TIME_INTERVAL);

                originCoord = new LatLng(tripDetailsResponse.getStartpoint().getLat(), tripDetailsResponse.getStartpoint().getLongX());
                destinationCoord = new LatLng(tripDetailsResponse.getEndpoint().getLat(), tripDetailsResponse.getEndpoint().getLongX());
                originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                if (tripDetailsResponse.getWayPoints().size() > 0) {
                    for (int i = 0; i < tripDetailsResponse.getWayPoints().size(); i++) {
                        LatLng waypointLatLng = new LatLng(tripDetailsResponse.getWayPoints().get(i).getLat(), tripDetailsResponse.getWayPoints().get(i).getLongX());
                        Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                        pointList.add(wayPoint);
                        addWaypointtMarker(wayPoint);

                    }
                    pointList.add(0, originPosition);
                    pointList.add(pointList.size() - 1, destinationPosition);
                    addStartPointMarker(originPosition);
                    addDestinationMarker(destinationPosition);
                    getOptimizedRoute(pointList);
                } else {
                    pointList.add(originPosition);
                    pointList.add(destinationPosition);
                    addStartPointMarker(originPosition);
                    addDestinationMarker(destinationPosition);
                    getOptimizedRoute(pointList);
                }

                tvTripName.setText(tripDetailsResponse.getTripName());
                tvStarts.setText(tripDetailsResponse.getStartpoint().getAddress());
                tvEnds.setText(tripDetailsResponse.getEndpoint().getAddress());
                if (tripDetailsResponse.getCreationDate() != null) {
                    tvStartTime.setText(tripDetailsResponse.getCreationDate());
                    tvEndTime.setText(tripDetailsResponse.getEndTime().toString());
                }

                if (markerObjectList.size() > 0) {
                    for (MarkerObject markersBean : markerObjectList) {

                        if (markersBean.getMarkerType() == 1) {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                    .setIcon(safeIcon);
                        } else if (markersBean.getMarkerType() == 2) {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                    .setIcon(landmarkIcon);
                        } else if (markersBean.getMarkerType() == 3) {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                    .setIcon(deleteIcon);
                            //drawPolygon(mapboxMap, markersBean);
                        }

                    }
                }

                *//*if (tripDetailsResponse.getMarkers().size() > 0) {
                    LatLng point;
                    for (TripDetailsResponse.DataBean.MarkersBean markersBean : tripDetailsResponse.getMarkers()) {

                        if (markersBean.getMarkType() == 1) {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                    .setIcon(safeIcon);
                        } else if (markersBean.getMarkType() == 2) {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                    .setIcon(landmarkIcon);
                        } else if (markersBean.getMarkType() == 3) {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(markersBean.getLat(), markersBean.getLongX())))
                                    .setIcon(deleteIcon);
                            drawPolygon(mapboxMap, markersBean);
                        }

                    }
                }*//*
            }
        });
    }*/

/*    private void getOptimizedRoute(List<Point> coordinates) {
        optimizedClient = MapboxOptimization.builder()
                .source(Constant.FIRST)
                .destination(Constant.ANY)
                .coordinates(coordinates)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(Mapbox.getAccessToken())
                .build();

        optimizedClient.enqueueCall(new Callback<OptimizationResponse>() {
            @Override
            public void onResponse(Call<OptimizationResponse> call, Response<OptimizationResponse> response) {
                if (!response.isSuccessful()) {
                    Log.d("DirectionsActivity", getString(R.string.no_success));
                    Toast.makeText(getActivity(), R.string.no_success, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (response.body().trips().isEmpty()) {
                        Log.d("DirectionsActivity", getString(R.string.successful_but_no_routes) + " size = "
                                + response.body().trips().size());
                        Toast.makeText(getActivity(), R.string.successful_but_no_routes,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

// Get most optimized route from API response
                optimizedRoute = response.body().trips().get(0);
                drawOptimizedRoute(optimizedRoute);
            }

            @Override
            public void onFailure(Call<OptimizationResponse> call, Throwable throwable) {
                Log.d("DirectionsActivity", "Error: " + throwable.getMessage());
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
        LineString lineString = LineString.fromPolyline(route.geometry(), PRECISION_6);
        List<Point> coordinates = lineString.coordinates();
        LatLng[] points = new LatLng[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            points[i] = new LatLng(
                    coordinates.get(i).latitude(),
                    coordinates.get(i).longitude());
        }
        return points;
    }

    private boolean alreadyTwelveMarkersOnMap() {
        if (pointList.size() == 12) {
            return true;
        } else {
            return false;
        }
    }

    private void addDestinationMarker(LatLng point) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(point.getLatitude(), point.getLongitude())));
    }

    private void addPointToStopsList(LatLng point) {
        pointList.add(Point.fromLngLat(point.getLongitude(), point.getLatitude()));
    }

    private void addFirstStopToStopsList() {
        originPosition = Point.fromLngLat(30.335098600000038, 59.9342802);
        pointList.add(originPosition);
    }*/

/*if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);*/
    //Fabric.with(this, new Crashlytics());

    /*Fragment fragment = new ImageDisplayFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("image_url",landmarkImageBean.getImageUrl());
                                            fragment.setArguments(bundle);
                                            getSupportFragmentManager().beginTransaction().add(android.R.id.content,fragment, fragment.getClass().getSimpleName())
                                                    .addToBackStack(fragment.getClass().getSimpleName()).commit();*/

     /*originCoord = new LatLng(latitude, longitude);
        destinationCoord = new LatLng(endLatitude, endLongitude);
        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        pointList = new ArrayList<>();
        tripListObject = RealmController.with(this).getTripDetails(String.valueOf(PreferenceManager.getInt(Constant.KEY_USER_ID)), tripId);
        if (tripListObject.getWaypointList() != null && tripListObject.getWaypointList().size() > 0) {
            for (int i = 0; i < tripListObject.getWaypointList().size(); i++) {
                LatLng waypointLatLng = new LatLng(tripListObject.getWaypointList().get(i).getLat(), tripListObject.getWaypointList().get(i).getLongX());
                Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                pointList.add(wayPoint);
            }
            getRoute(originPosition, destinationPosition, pointList);
        } else {
            getRoute(originPosition, destinationPosition, null);
        }*/

     /*private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        NavigationRoute.Builder builder = NavigationRoute.builder(getActivity())
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .destination(destination);
        if (wayPoints != null) {
            for (Point point : wayPoints)
                builder.addWaypoint(point);
        }
        builder.build().getRoute(new Callback<DirectionsResponse>() {
            @SuppressLint("TimberArgCount")
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                Timber.e("Response code: " + response.code(), response);
                if (response.body() == null) {
                    Timber.e("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Timber.e("No routes found");
                    return;
                }
                currentRoute = response.body().routes().get(0);

            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Timber.e("Error: %s", t.getMessage());

            }
        });
    }
*/
     /*private void setNavigationOnMapbox() {
        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                .directionsRoute(currentRoute)
                .shouldSimulateRoute(true)
                .directionsProfile(navigationType)
                .enableOffRouteDetection(true)
                .snapToRoute(true)
                .build();
        NavigationLauncher.startNavigation(getActivity(), options);
    }*/
    /*implementation 'com.mapbox.mapboxsdk:mapbox-android-navigation:0.22.0'*/
    /*implementation('com.mapbox.mapboxsdk:mapbox-android-directions:1.0.0@aar') {
        transitive = true
    }*/


    /*private void login(String email, String password, final View view) {
        loginActivityInterface.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.login(email, password, new ResponseCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                PreferenceManager.updateValue(Constant.KEY_USER_ID, data.getUserId());
                PreferenceManager.updateValue(Constant.KEY_TOKEN_ID, data.getTokenId());
                PreferenceManager.updateValue(Constant.KEY_IS_LOGGED_IN, true);
                PreferenceManager.updateValue(Constant.KEY_IS_LOADED_TRIPS, false);
                Utils.getHashSignatureFromTokenAndUserId();
                updateFirebaseTokenToServer();
            }

            @Override
            public void onError(Throwable th) {
                loginActivityInterface.showProgressDialog(false);
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }*/

    /*private void updateFirebaseTokenToServer() {
        ApiService apiService = new ApiCaller();
        userTokenModel.setUserId(PreferenceManager.getInt(Constant.KEY_USER_ID));
        userTokenModel.setToken(PreferenceManager.getString(Constant.KEY_FIREBASE_TOKEN_ID));
        userTokenModel.setDeviceType(2);
        apiService.updateFirebaseToken(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), userTokenModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                loginActivityInterface.showProgressDialog(false);
                Objects.requireNonNull(getActivity()).finish();
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }*/
    /*private void setupMap(TripListObject tripListObject) {
        getSupportActionBar().setTitle(tripListObject.getTripName());
        mapView.getMapAsync(mapboxMap -> {
            map = mapboxMap;
            CameraPosition position = new CameraPosition.Builder()
                    .target(new LatLng(tripListObject.getStartLat(), tripListObject.getStartLonX()))
                    .zoom(9)
                    .tilt(30)
                    .bearing(180)
                    .build();

            mapboxMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(position), Constant.TOTAL_TIME_INTERVAL);

            originCoord = new LatLng(tripListObject.getStartLat(), tripListObject.getStartLonX());
            destinationCoord = new LatLng(tripListObject.getEndLat(), tripListObject.getEndLonX());
            originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
            destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
            if (tripListObject.getWaypointList().size() > 0) {
                for (int i = 0; i < tripListObject.getWaypointList().size(); i++) {
                    LatLng waypointLatLng = new LatLng(tripListObject.getWaypointList().get(i).getLat(), tripListObject.getWaypointList().get(i).getLongX());
                    Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                    pointList.add(wayPoint);
                    addWaypointtMarker(tripListObject.getWaypointList().get(i));

                }
                addStartPointMarker(tripListObject);
                addDestinationMarker(tripListObject);
                drawOptimizedRoute();
            } else {
                pointList.add(originPosition);
                pointList.add(destinationPosition);
                addStartPointMarker(tripListObject);
                addDestinationMarker(tripListObject);
                drawOptimizedRoute();
            }

            tvTripName.setText(tripListObject.getTripName());
            tvStarts.setText(tripListObject.getStartAddress());
            tvEnds.setText(tripListObject.getEndAddress());
            if (markerObjectList.size() > 0) {
                for (MarkerObject markersBean : markerObjectList) {
                    if (markersBean.getMarkerType() == 1) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                                .title(markersBean.getAddress())
                                .setSnippet(markersBean.getMarkerID())
                                .icon(safeIcon));
                    } else if (markersBean.getMarkerType() == 2) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                                .title(markersBean.getAddress())
                                .setSnippet(markersBean.getMarkerID())
                                .icon(landmarkIcon));
                    } else if (markersBean.getMarkerType() == 3) {
                        mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(markersBean.getLat(), markersBean.getLongX()))
                                .title(markersBean.getAddress())
                                .setSnippet(markersBean.getMarkerID())
                                .icon(deleteIcon));
                        com.mapbox.mapboxsdk.annotations.Polygon polygon = map.addPolygon(Utils.generatePerimeter(new LatLng(markersBean.getLat(), markersBean.getLongX()), markersBean.getRadius(), 64));
                        polygonList.add(polygon);
                    }
                }
            }
            mapboxMap.setInfoWindowAdapter(marker -> {
                @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.marker_popup, null);
                TextView tvAddress = view.findViewById(R.id.tv_address);
                TextView tvRemove = view.findViewById(R.id.tv_delete);
                TextView tvEdit = view.findViewById(R.id.tv_edit);
                tvAddress.setText(marker.getTitle());
                tvRemove.setVisibility(View.GONE);
                if (marker.getSnippet().equals("0")) {
                    tvEdit.setVisibility(View.GONE);
                }
                tvEdit.setOnClickListener(v -> {
                    Intent intent = new Intent(TripDetailsActivity.this, LandmarkActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("marker_id", marker.getSnippet());
                    bundle.putLong("long_marker", marker.getId());
                    bundle.putBoolean("is_marker_from_trip", true);
                    bundle.putInt(Constant.KEY_TRIP_ID, tripId);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, REQUEST_UPDATE_MARKER);
                    marker.hideInfoWindow();
                });
                return view;
            });
        });
    }*/
/*
    private void addStartPointMarker(TripListObject tripListObject) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(tripListObject.getStartLat(), tripListObject.getStartLonX()))
                .title(tripListObject.getStartAddress())
                .setSnippet("0")
                .icon(startMarker));
    }

    private void addWaypointtMarker(WaypointObject waypointObject) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(waypointObject.getLat(), waypointObject.getLongX()))
                .setSnippet("0")
                .title(waypointObject.getAddress())
                .icon(waypointMarker));
    }

    private void addDestinationMarker(TripListObject tripListObject) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(tripListObject.getEndLat(), tripListObject.getEndLonX()))
                .title(tripListObject.getEndAddress())
                .setSnippet("0")
                .icon(endMarker));
    }

    private void drawOptimizedRoute() {
        if (optimizedPolyline != null) {
            map.removePolyline(optimizedPolyline);
        }
        String decodedPath = new String(Base64.decode(tripListObject.getRouteGeometry(), Base64.DEFAULT));
        LatLng[] pointsToDraw = convertLineStringToLatLng(decodedPath);
        optimizedPolyline = map.addPolyline(new PolylineOptions()
                .add(pointsToDraw)
                .color(Color.parseColor(Constant.TEAL_COLOR))
                .width(Constant.POLYLINE_WIDTH));
    }

    private LatLng[] convertLineStringToLatLng(String geometry) {
        LineString lineString = LineString.fromPolyline(geometry, PRECISION_6);
        List<Point> coordinates = lineString.coordinates();
        LatLng[] points = new LatLng[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            points[i] = new LatLng(
                    coordinates.get(i).latitude(),
                    coordinates.get(i).longitude());
        }
        return points;
    }*/

/*if (data != null) {
                    for (TripListResponse.ListBean listBean : data.getList()) {
                        BasicTripDescription basicTripDescription = new BasicTripDescription();
                        basicTripDescription.setTripId(listBean.getId());
                        basicTripDescription.setTripName(listBean.getTripName());
                        basicTripDescription.setBeginTime(listBean.getBeginTime());
                        basicTripDescription.setEndTime(listBean.getEndTime());
                        basicTripDescription.setStartPointLat(listBean.getStartPoint().getLat());
                        basicTripDescription.setStartPointLonX(listBean.getStartPoint().getLongX());
                        basicTripDescription.setStartPointAddress(listBean.getStartPoint().getAddress());
                        basicTripDescription.setStartPointFullAddress(listBean.getStartPoint().getFullAddress());
                        basicTripDescription.setEndPointLat(listBean.getEndPoint().getLat());
                        basicTripDescription.setEndPointLonX(listBean.getEndPoint().getLongX());
                        basicTripDescription.setEndPointAddress(listBean.getEndPoint().getAddress());
                        basicTripDescription.setEndPointFullAddress(listBean.getEndPoint().getFullAddress());
                        AppDatabase.getAppDatabase(Objects.requireNonNull(getActivity()).getApplicationContext()).daoTripBasic().insertTripBasic(basicTripDescription);

                        if (listBean.getMarkers() != null && listBean.getMarkers().size() > 0) {
                            for (TripListResponse.ListBean.MarkersBean markersBean : listBean.getMarkers()) {
                                MarkerDescription markerDescription = new MarkerDescription();
                                markerDescription.setMarkType(markersBean.getMarkType());
                                markerDescription.setLat(markersBean.getLat());
                                markerDescription.setLongX(markersBean.getLongX());
                                markerDescription.setRadius(markersBean.getRadius());
                                markerDescription.setAddress(markersBean.getAddress());
                                markerDescription.setFullAddress(markersBean.getFullAddress());
                                markerDescription.setTripId(Integer.parseInt(listBean.getId()));
                                markerDescription.setMarkerId(String.valueOf(listBean.getId()));
                                AppDatabase.getAppDatabase(getActivity()).daoMarker().insertMarker(markerDescription);
                            }
                        }
                        if (listBean.getWayPoints() != null && listBean.getWayPoints().size() > 0) {
                            WaypointDescription waypointDescription = new WaypointDescription();
                            waypointDescription.setTripId(Integer.parseInt(listBean.getId()));
                            List<WaypointModel.WayPointsBean> wayPointsListBeans = new ArrayList<>();
                            for (TripListResponse.ListBean.WayPointsBean wayPointsBean : listBean.getWayPoints()) {
                                WaypointModel.WayPointsBean waypoint = new WaypointModel.WayPointsBean();
                                waypoint.setLat(wayPointsBean.getLat());
                                waypoint.setLongX(wayPointsBean.getLongX());
                                waypoint.setType(wayPointsBean.getType());
                                waypoint.setAddress(wayPointsBean.getAddress());
                                waypoint.setFullAddress(wayPointsBean.getFullAddress());
                                wayPointsListBeans.add(waypoint);
                            }
                            waypointDescription.setWayPointsBeanList(wayPointsListBeans);
                            AppDatabase.getAppDatabase(getActivity()).daoWaypoint().insertWaypoint(waypointDescription);
                        }

                        PreferenceManager.updateValue(Constant.KEY_IS_LOADED_TRIPS, true);
                        fragment = new NextTripFragment();
                        loadFragment(fragment);
                        fragmentActivityCommunication.showProgressDialog(false);
                    }
                } else {
                    fragmentActivityCommunication.showProgressDialog(false);
                }*/

/*if (isUpdate) {
            btnNext.setVisibility(View.GONE);
            waypointFooter.setVisibility(View.VISIBLE);
            //mAdapter = new UpdatedWaypointAdapter(getActivity());
            WaypointDescription waypointDescription = AppDatabase.getAppDatabase(getActivity().getApplicationContext()).daoWaypoint().fetchWaypointById(dataBean.getId());
            if (waypointDescription != null) {
                if (waypointDescription.getWayPointsBeanList() != null && waypointDescription.getWayPointsBeanList().size() > 0) {
                    mAdapter = new UpdatedWaypointAdapter(getActivity());
                    wayPointsListBeans.addAll(waypointDescription.getWayPointsBeanList());
                    mAdapter.setItems(wayPointsListBeans);
                    mAdapter.setListener(this);
                    rvWaypoints.setAdapter(mAdapter);
                    //updateRoute();
                }
            } else {
                Toast.makeText(getActivity(), "No Waypoint", Toast.LENGTH_SHORT).show();
            }

        }*/

 /* private void addDangerMarker(List<LatLng> latLngList) {
        CreateTripMarkerModelClass hazardMarkerModel = new CreateTripMarkerModelClass();
        hazardMarkerModel.setMarkType(4);
        hazardMarkerModel.setTripId(0);
        hazardMarkerModel.setLat(latLngList.get(0).getLatitude());
        hazardMarkerModel.setLongX(latLngList.get(0).getLongitude());
        hazardMarkerModel.setRadius(0);
        hazardMarkerModel.setAddress(getAddress(new LatLng(hazardMarkerModel.getLat(), hazardMarkerModel.getLongX()), false));
        hazardMarkerModel.setFullAddress(getAddress(new LatLng(hazardMarkerModel.getLat(), hazardMarkerModel.getLongX()), true));
        hazardMarkerModel.setDescription("demo");
        List<CreateTripMarkerModelClass.LocationsBean> locationsBeanList = new ArrayList<>();
        for (LatLng latLng : latLngList) {
            CreateTripMarkerModelClass.LocationsBean locationsBean = new CreateTripMarkerModelClass.LocationsBean();
            locationsBean.setLat(latLng.getLatitude());
            locationsBean.setLongX(latLng.getLongitude());
            locationsBeanList.add(locationsBean);
        }
        hazardMarkerModel.setLocations(locationsBeanList);
        ApiService apiService = new ApiCaller();
        apiService.createTripMarker(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), hazardMarkerModel, new ResponseCallback<TripMarkerCreateResponse>() {
            @Override
            public void onSuccess(TripMarkerCreateResponse data) {
                Gson gson = new Gson();
                String tripResponse = gson.toJson(hazardMarkerModel);
                Data markerData = new Data.Builder()
                        .putString(Constant.KEY_MARKER_RESPONSE, tripResponse)
                        .putString(Constant.KEY_TRIP_MARKER_ID, String.valueOf(data.getSuccess().getMarkerID()))
                        .build();

                final OneTimeWorkRequest addMarkerWorker = new OneTimeWorkRequest.Builder(AddMarkerWorker.class)
                        .setInputData(markerData)
                        .build();
                WorkManager.getInstance().enqueue(addMarkerWorker);
                WorkManager.getInstance().getWorkInfoByIdLiveData(addMarkerWorker.getId()).observe(Objects.requireNonNull(getActivity()), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if (workInfo != null && workInfo.getState().isFinished()) {
                            map.addPolygon(new PolygonOptions()
                                    .addAll(latLngList)
                                    .fillColor(Color.YELLOW)
                                    .alpha(0.1f));

                        }
                    }
                });


            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }*/

 /*com.mapbox.mapboxsdk.annotations.Polygon hazardPolygon = map.addPolygon(new PolygonOptions()
                                                .addAll(latLngList)
                                                .fillColor(Color.YELLOW)
                                                .alpha(0.3f));
                                        hazardPolygonHashMap.put(String.valueOf(data.getSuccess().getMarkerID()), hazardPolygon);*/

 /*private void getRoute(Point origin, Point destination, List<Point> wayPoints) {
        detailsViewModel.getFinalRoute(origin, destination, wayPoints).observe(this, currentRoute -> {
            if (currentRoute != null) {
                drawOptimizedRoute(currentRoute);
            }
        });
    }*/

 /*private void drawOptimizedRoute(DirectionsRoute route) {
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
    }*/

 /*@TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void createShortcut() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        Intent tripListIntent = new Intent(getApplicationContext(), HomeActivity.class);
        tripListIntent.setAction(Intent.ACTION_VIEW);
        ShortcutInfo shortcutTripList = new ShortcutInfo.Builder(this, "shortcut1")
                .setIntent(tripListIntent)
                .setShortLabel(Constant.KEY_TRIP_LIST)
                .setLongLabel(Constant.KEY_TRIP_LIST)
                .setShortLabel(Constant.KEY_TRIP_LIST)
                .setDisabledMessage("Login to open this")
                .setIcon(Icon.createWithResource(this, R.drawable.trip_name))
                .build();

        Intent createTripIntent = new Intent(getApplicationContext(), HomeActivity.class);
        createTripIntent.setAction(Intent.ACTION_VIEW);
        ShortcutInfo shortcutCreateTrip = new ShortcutInfo.Builder(this, "shortcut2")
                .setIntent(createTripIntent)
                .setShortLabel(Constant.KEY_CREATE_TRIP)
                .setLongLabel(Constant.KEY_CREATE_TRIP)
                .setShortLabel(Constant.KEY_CREATE_TRIP)
                .setDisabledMessage("Login to open this")
                .setIcon(Icon.createWithResource(this, R.drawable.enter_starting_point))
                .build();
        if (shortcutManager != null) {
            shortcutManager.addDynamicShortcuts(Arrays.asList(shortcutTripList, shortcutCreateTrip));
        }
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void removeShorcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.disableShortcuts(Arrays.asList("shortcut1", "shortcut2"));
        shortcutManager.removeAllDynamicShortcuts();
    }*/

 /*if (Build.VERSION.SDK_INT >= 25) {
            createShortcut();
        } else {
            removeShorcuts();
        }*/

 /*CustomNavigationFragment customNavigationFragment = new CustomNavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_share", isShare);
        bundle.putInt("trip_id", tripId);
        bundle.putDouble("lat", latitude);
        bundle.putDouble("lonX", longitude);
        bundle.putString("navigation_type", navigationType);
        bundle.putBoolean("is_trip_route", isTripRoute);
        customNavigationFragment.setArguments(bundle);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame_custom_nav_content, customNavigationFragment, customNavigationFragment.getClass().getSimpleName())
                .addToBackStack(customNavigationFragment.getClass().getSimpleName()).commit();

        dismiss();*/

 /*if (waypointObjectList != null && waypointObjectList.getWayPointsBeanList().size() > 0) {
                    for (WaypointModel.WayPointsBean wayPointsBean : waypointObjectList.getWayPointsBeanList()) {
                        addWaypointtMarker(wayPointsBean);
                    }
                }*/

                /*if (waypointObjectList != null && waypointObjectList.getWayPointsBeanList().size() > 0) {
                    for (WaypointModel.WayPointsBean wayPointsBean : waypointObjectList.getWayPointsBeanList()) {
                        LatLng waypointLatLng = new LatLng(wayPointsBean.getLat(), wayPointsBean.getLongX());
                        Point wayPoint = Point.fromLngLat(waypointLatLng.getLongitude(), waypointLatLng.getLatitude());
                        pointList.add(wayPoint);
                        addWaypointtMarker(wayPointsBean);
                    }
                }

                if (waypointObjectList.getTripJson() != null) {
                    drawOptimizedRoute();
                } else {
                    getRoute(originPosition, destinationPosition, pointList);
                }*/

                /*Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            boolean isFromNotification = bundle.getBoolean(Constant.KEY_IS_FROM_NOTIFICATION);
            if (isFromNotification) {
                flag = bundle.getInt(Constant.KEY_FLAG);
                Intent intent = new Intent(this, NotificationActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                *//*notificationAction();
                makeToast("from_notification");*//*

            }
        }*/

    /*if (RouteService.getInstance() != null) {
                    RouteService.getInstance().stopService();
                }*/
                /*if(handler!=null){
                    handler.removeCallbacks(sendUpdatesToUI)
                }*/

    /*private void startLocationUpdateService(int duration, int shareId) {
        if (RouteService.getInstance() != null) {
            RouteService.getInstance().stopService();
            Intent intent = new Intent(this, RouteService.class);
            startService(intent);
        } else {
            startService(new Intent(this, RouteService.class));
        }
    }*/

    /*@SuppressLint("ResourceType")
    private void notificationAction() {
        switch (flag) {
            case 0:
                fragment = new TripsFragment();
                loadInitialFragment(fragment);
                break;

            case 1:
                fragment = new FriendsFragment();
                loadInitialFragment(fragment);
                break;

            case 2:
                fragment = new TrackerFragment();
                loadInitialFragment(fragment);
                bottomNavigationView.setSelectedItemId(3);
                break;

            case 3:
                fragment = new SharedTripFragment();
                loadInitialFragment(fragment);
                bottomNavigationView.setSelectedItemId(2);
                break;

            case 4:
                fragment = new SharedTripFragment();
                loadInitialFragment(fragment);
                bottomNavigationView.setSelectedItemId(2);
                break;
        }
    }*/

     /*<!-- <com.google.android.material.appbar.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:theme="@style/AppTheme.AppBarOverlay">

        <androidx.appcompat.widget.Toolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary"
    app:popupTheme="@style/AppTheme.PopupOverlay" />

    </com.google.android.material.appbar.AppBarLayout>



    <com.google.android.material.floatingactionbutton.FloatingActionButton
    android:id="@+id/fab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom|end"
    android:layout_margin="@dimen/fab_margin"
    app:srcCompat="@android:drawable/ic_dialog_email" />-->*/


     /*if (model.getData().getTripSharedStatus().equals("new")) {
            if (addMoreObject.isCancel()) {
                ConfirmationCancelFragment confirmationCancelFragment = new ConfirmationCancelFragment();
                Bundle bundle = new Bundle();
                bundle.putString("trip_sharing_id", String.valueOf(tripSharingId));
                bundle.putParcelable("parcel", item);
                confirmationCancelFragment.setArguments(bundle);
                confirmationCancelFragment.show(getChildFragmentManager(), null);

            } else {
                Date currentDate = null;
                Date endDate = null;
                try {
                    Calendar c = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT);
                    String strDate = sdf.format(c.getTime());
                    currentDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(strDate);
                    endDate = new SimpleDateFormat(Constant.SIMPLE_DATE_FORMAT).parse(model.getData().getEndTime());

                    if (currentDate.compareTo(endDate) > 0) {
                        Snackbar.make(btnAddMore, getResources().getString(R.string.text_previous_trip), Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                        loadAddMoreFragment(model);
                    }
                } catch (ParseException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getString(R.string.text_trip_message), Toast.LENGTH_SHORT).show();
        }*/


}
