package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.AddChecklistAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.ChecklistObject;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.CreateChecklistResponse;
import com.techacsent.route_recon.model.SystemSecurityModel;
import com.techacsent.route_recon.model.request_body_model.CreateChecklistModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.Utils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChecklistFragment extends DialogFragment implements OnRecyclerClickListener<SystemSecurityModel> {
    private List<SystemSecurityModel> listofOption = new ArrayList<>();
    private int flag;
    private int tripId;
    private NavigationOptionInterface navigationOptionInterface;

    public ChecklistFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flag = getArguments().getInt("flag");
            tripId = getArguments().getInt("trip_id");
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_checklist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvCheckList = view.findViewById(R.id.rv_check_list);
        rvCheckList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCheckList.setHasFixedSize(false);
        rvCheckList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                DividerItemDecoration.VERTICAL));

        Utils.getSystemList(listofOption, flag);
        AddChecklistAdapter mAdapter = new AddChecklistAdapter(RouteApplication.getInstance().getApplicationContext());
        mAdapter.setItems(listofOption);
        mAdapter.setListener(this::addSecurityChecklist);
        rvCheckList.setAdapter(mAdapter);
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

    private void addSecurityChecklist(SystemSecurityModel item) {
        navigationOptionInterface.showProgressDialog(true);
        CreateChecklistModel createChecklistModel = new CreateChecklistModel();
        createChecklistModel.setTripId(tripId);
        /*createChecklistModel.setIsCustom(0);
        createChecklistModel.setTitle(item.getDescripion());
        createChecklistModel.setDescription(item.getDescripion());*/
        ApiService apiService = new ApiCaller();
        apiService.createChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), createChecklistModel, new ResponseCallback<CreateChecklistResponse>() {
            @Override
            public void onSuccess(CreateChecklistResponse data) {
                if (data.getSuccess().getMessage().equals("Security checklist created successfully")) {
                    navigationOptionInterface.showProgressDialog(false);
                    EventBus.getDefault().post(new ChecklistObject(true, data.getChecklistId(), item.getDescripion(), 0));
                    dismiss();
                    //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                } else {
                    navigationOptionInterface.showProgressDialog(false);
                    dismiss();
                    //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                }
            }

            @Override
            public void onError(Throwable th) {
                navigationOptionInterface.showProgressDialog(false);
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onItemClicked(SystemSecurityModel item) {
        addSecurityChecklist(item);
    }
}
