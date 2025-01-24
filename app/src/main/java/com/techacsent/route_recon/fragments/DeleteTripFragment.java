package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;
import com.techacsent.route_recon.room_db.entity.WaypointDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteTripFragment extends DialogFragment {

    private BasicTripDescription item;
    private List<MarkerDescription> markerObjectList;
    private WaypointDescription waypointObjectList;
    private FragmentActivityCommunication fragmentActivityCommunication;



    public DeleteTripFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = getArguments().getParcelable("parcel");
        }
        markerObjectList = AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoMarker().fetchMarkerByTripId(item.getTripId());
        waypointObjectList = AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoWaypoint().fetchWaypointById(item.getTripId());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_trip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnOkay = view.findViewById(R.id.btn_ok);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> dismiss());
        btnOkay.setOnClickListener(v -> deleteTrip());
    }

    private void deleteTrip() {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.deleteTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), Integer.parseInt(item.getTripId()), new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoTripBasic().deleteTripBasic(item);
                if(waypointObjectList!=null){
                    AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoWaypoint().deleteWaypoint(waypointObjectList);
                }if(markerObjectList!=null){
                    AppDatabase.getAppDatabase(RouteApplication.getInstance().getApplicationContext()).daoMarker().deleteMarkerList(markerObjectList);
                }
                EventBus.getDefault().post(item);
                fragmentActivityCommunication.showProgressDialog(false);
                dismiss();
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                fragmentActivityCommunication.showProgressDialog(false);
                dismiss();
            }
        });

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
}
