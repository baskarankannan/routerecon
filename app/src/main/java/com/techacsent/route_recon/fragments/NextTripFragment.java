package com.techacsent.route_recon.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.TripDetailsActivity;
import com.techacsent.route_recon.adapter.SendTripsAdapter;
import com.techacsent.route_recon.adapter.TripAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.RemoveListObject;
import com.techacsent.route_recon.event_bus_object.SyncInternetObject;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.interfaces.SendTripsAcceptListener;
import com.techacsent.route_recon.model.ReceivedShareTripRequest;
import com.techacsent.route_recon.model.sendtrip.List;
import com.techacsent.route_recon.model.sendtrip.SendRouteListResponse;
import com.techacsent.route_recon.model.sendtrip.SendTripRejectResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.SharedMapViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NextTripFragment extends Fragment implements OnRecyclerItemClickListener<BasicTripDescription>, SendTripsAcceptListener {
    private RecyclerView rvNextTrip;
    private RecyclerView rvSendTrip;
    private TripAdapter mAdapter;
    private SendTripsAdapter sendTripsAdapter;
    private TextView tvEmpty;
    private TextView tvTitleUpcomingRoutes;

    private SharedMapViewModel sharedMapViewModel;

    ArrayList<ReceivedShareTripRequest> receivedTripSendList = new ArrayList<>();


    Gson gson = new Gson();


    public NextTripFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_next_trip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvNextTrip = view.findViewById(R.id.rv_next_trip_list);
        rvSendTrip = view.findViewById(R.id.rv_send_trip_list);
        tvEmpty = view.findViewById(R.id.tv_no_item);
        tvTitleUpcomingRoutes = view.findViewById(R.id.tv_upcoming_route_tittle);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvNextTrip.setLayoutManager(layoutManager);
        rvNextTrip.setItemAnimator(new DefaultItemAnimator());
        rvNextTrip.addItemDecoration(new DividerItemDecoration(RouteApplication.getInstance().getApplicationContext(), DividerItemDecoration.VERTICAL));

        final LinearLayoutManager layoutManagerV2 = new LinearLayoutManager(getActivity());
        layoutManagerV2.setOrientation(LinearLayoutManager.VERTICAL);
        rvSendTrip.setLayoutManager(layoutManagerV2);
        rvSendTrip.setItemAnimator(new DefaultItemAnimator());
        rvSendTrip.addItemDecoration(new DividerItemDecoration(RouteApplication.getInstance().getApplicationContext(), DividerItemDecoration.VERTICAL));

        loadNextTrip();

        sharedMapViewModel = ViewModelProviders.of(requireActivity()).get(SharedMapViewModel.class);

        loadSendRoutList();

        //    checkTripSendReceivingRequest();

    }

    private void loadNextTrip() {

        mAdapter = new TripAdapter(getActivity());
        mAdapter.setItems(AppDatabase.getAppDatabase(getActivity()).daoTripBasic().getNextTrip(System.currentTimeMillis()));
        mAdapter.setListener(this);

        rvNextTrip.setAdapter(mAdapter);



        if (mAdapter.getItemCount() < 1  ) {
            tvEmpty.setVisibility(View.VISIBLE);
            tvTitleUpcomingRoutes.setVisibility(View.GONE);

        } else {
            tvEmpty.setVisibility(View.GONE);
            tvTitleUpcomingRoutes.setVisibility(View.VISIBLE);
        }


    }


    private  void loadSendRoutList(){


        ApiService apiService = new ApiCaller();

        apiService.getSendRouteList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                new ResponseCallback<SendRouteListResponse>() {
                    @Override
                    public void onSuccess(SendRouteListResponse data) {


                        if( data.getList() !=null){

                            showSendRouteList((ArrayList<List>) data.getList());

                        }else{


                            java.util.List<List> emptyArrayList = new ArrayList<>();

                            showSendRouteList((ArrayList<List>) emptyArrayList);

                        }


                    }

                    @Override
                    public void onError(Throwable th) {

                    }
                });





    }

    private void  showSendRouteList( ArrayList<List>sendRoutList){


        sendTripsAdapter = new SendTripsAdapter(getActivity(), sendRoutList, this);
        //  sendTripsAdapter.setItems(receivedTripSendList);
        //sendTripsAdapter.setListener(this);
        rvSendTrip.setAdapter(sendTripsAdapter);



    }

    @Override
    public void onResume() {

        super.onResume();
        if (mAdapter != null) {
            mAdapter.clear();
            mAdapter.setItems(AppDatabase.getAppDatabase(getActivity()).daoTripBasic().getNextTrip(System.currentTimeMillis()));
            /*if (mAdapter.getItemCount() < 1) {
                tvEmpty.setVisibility(View.VISIBLE);
            } else {
                tvEmpty.setVisibility(View.GONE);
            }*/
        }



       // loadSendRoutList();


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

                /*Intent intent = new Intent(getActivity(), TestActivity.class);
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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateList(BasicTripDescription item) {
       // mAdapter.remove(item);
        loadNextTrip();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dismissLoading(Boolean bool) {
        if (mAdapter != null) {
            mAdapter.clear();
        }
        loadNextTrip();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void dismissLoading(RemoveListObject removeListObject) {
        if (mAdapter != null) {
            mAdapter.clear();
        }




      //  loadSendRoutList();

    }





    /*@Subscribe(threadMode = ThreadMode.MAIN)
    public void loadSendTrip(LoadSendTripObject loadSendTripObject) {

        Log.e("NextTripFrag" ," loadSendTrip event bus");
        loadSendRoutList();
    }*/




    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSendTripAccept(boolean status, String tripId) {

        Log.e("NextTripFrag","onSendTripAccept");

        if(status){

            sharedMapViewModel.liveDataTripRequest(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), tripId).observe(getActivity(), successArray -> {

                Log.e("TripAccept", "Successful");

                loadSendRoutList();

                EventBus.getDefault().post(new SyncInternetObject(true));






            });


        }else{


            ApiService apiService = new ApiCaller();

            apiService.sendTripRejectRequest(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),tripId,
                    new ResponseCallback<SendTripRejectResponse>() {
                        @Override
                        public void onSuccess(SendTripRejectResponse data) {


                            loadSendRoutList();


                        }

                        @Override
                        public void onError(Throwable th) {

                        }
                    });


           /* if (!receivedTripSendList.isEmpty()) {


                *//*for( ReceivedShareTripRequest receivedShareTripRequest : receivedTripSendList){


                    if(receivedShareTripRequest.getTripId().equals(tripId) ){

                        receivedTripSendList.remove(receivedShareTripRequest);
                    }

                }*//*

                receivedTripSendList.removeIf(i -> i.getTripId().equals(tripId));


                Log.e("NextTrip ", "After Cancel : "+ receivedTripSendList.size());


                String receivedShareTripRequestListString = gson.toJson(receivedTripSendList);

                PreferenceManager.updateValue(Constant.KEY_TRIP_SEND_RECEIVED_LIST,receivedShareTripRequestListString);


               // sendTripsAdapter = new SendTripsAdapter(getActivity(), receivedTripSendList, this);
                //  sendTripsAdapter.setItems(receivedTripSendList);
                //sendTripsAdapter.setListener(this);
                rvSendTrip.setAdapter(sendTripsAdapter);
               // sendTripsAdapter.notifyDataSetChanged();

            }*/




        }

    }
}



