package com.techacsent.route_recon.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.TrackerActivity;
import com.techacsent.route_recon.event_bus_object.StopServiceObject;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditDeleteLocationShareFragment extends Fragment {
    private FrameLayout frameLayout;
    private Button btnEditSession;
    private Button btnDeleteSession;


    public EditDeleteLocationShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_delete_location_share, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        initializeListener();
    }

    private void initializeView(View view) {
        frameLayout = view.findViewById(R.id.content);
        btnEditSession = view.findViewById(R.id.btn_edit_session);
        btnDeleteSession = view.findViewById(R.id.btn_delete_session);
    }

    private void initializeListener() {
        frameLayout.setOnClickListener(v -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack());
        btnEditSession.setOnClickListener(v -> {
            /*Fragment fragment = new ShareLiveLocationFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.KEY_IS_LIVE_LOCATION_SHARE_EDIT, true);
            loadShareFragment(fragment);*/
            //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
            Intent intent = new Intent(getActivity(), TrackerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_edit", true);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        btnDeleteSession.setOnClickListener(v -> deleteLiveSession());
    }

    private void loadShareFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    private void deleteLiveSession() {
        ApiService apiService = new ApiCaller();
        apiService.deleteShareLiveLocation(PreferenceManager.getInt(Constant.KEY_LOCATION_SHARE_ID), PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                PreferenceManager.updateValue(Constant.KEY_LOCATION_SHARE_ID, 0);
                PreferenceManager.updateValue(Constant.KEY_IS_LOCATION_SHARING, false);
                PreferenceManager.updateValue(Constant.KEY_DURATION, 0);
                PreferenceManager.updateValue(Constant.KEY_END_TIME, 0L);
                EventBus.getDefault().post(new StopServiceObject(true));
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                /*if (RouteService.getInstance() != null) {
                    RouteService.getInstance().stopService();
                }
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();*/
            }

            @Override
            public void onError(Throwable th) {
                try {
                    Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

}
