package com.techacsent.route_recon.fragments;


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
import android.widget.ProgressBar;
import android.widget.Toast;

import io.github.rupinderjeet.kprogresshud.KProgressHUD;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.CustomChecklistAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.ChecklistObject;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.CreateChecklistResponse;
import com.techacsent.route_recon.model.CustomChecklistResponse;
import com.techacsent.route_recon.model.request_body_model.CreateChecklistModel;
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
public class CustomChecklistFragment extends DialogFragment implements OnRecyclerClickListener<CustomChecklistResponse.CustomChecklistBean> {

    private RecyclerView rvList;
    private ProgressBar progressBar;
    //private NavigationOptionInterface navigationOptionInterface;
    private int tripid;
    private boolean isAdd;
    private KProgressHUD progressDialogFragment;


    public CustomChecklistFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripid = getArguments().getInt("trip_id");
            isAdd = getArguments().getBoolean("is_add");
        }
    }

    private void loadProgressHud() {
        progressDialogFragment = KProgressHUD.create(Objects.requireNonNull(getActivity()))
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .setDimAmount(0.5f)
                .setLabel("Loading...");
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_checklist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvList = view.findViewById(R.id.rv_check_list);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setHasFixedSize(false);
        rvList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                DividerItemDecoration.VERTICAL));
        progressBar = view.findViewById(R.id.progress_bar);
        loadCustomChecklist();
    }

    private void loadCustomChecklist() {
        ApiService apiService = new ApiCaller();
        apiService.getCustomChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "custom", new ResponseCallback<CustomChecklistResponse>() {
            @Override
            public void onSuccess(CustomChecklistResponse data) {
                loadAdapter(data);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable th) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadAdapter(CustomChecklistResponse data) {
        CustomChecklistAdapter mAdapter = new CustomChecklistAdapter(RouteApplication.getInstance().getApplicationContext());
        mAdapter.setItems(data.getCustomChecklist());
        mAdapter.setListener(this);
        rvList.setAdapter(mAdapter);

    }

    private void addSecurityChecklist(CustomChecklistResponse.CustomChecklistBean item) {
        //navigationOptionInterface.showProgressDialog(true);
        loadProgressHud();
        CreateChecklistModel createChecklistModel = new CreateChecklistModel();
        createChecklistModel.setTripId(tripid);
        /*createChecklistModel.setIsCustom(0);
        createChecklistModel.setTitle(item.getTitle());
        createChecklistModel.setDescription(item.getTitle());*/
        ApiService apiService = new ApiCaller();
        apiService.createChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), createChecklistModel, new ResponseCallback<CreateChecklistResponse>() {
            @Override
            public void onSuccess(CreateChecklistResponse data) {
                if (data.getSuccess().getMessage().equals("Security checklist created successfully")) {
                    //navigationOptionInterface.showProgressDialog(false);
                    progressDialogFragment.dismiss();
                    EventBus.getDefault().post(new ChecklistObject(true, data.getChecklistId(), item.getDescription(), 0));
                    dismiss();
                    //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                } else {
                   // navigationOptionInterface.showProgressDialog(false);
                    progressDialogFragment.dismiss();
                    dismiss();
                    //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                }
            }

            @Override
            public void onError(Throwable th) {
                progressDialogFragment.dismiss();
                //navigationOptionInterface.showProgressDialog(false);
                Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }

   /* @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigationOptionInterface) {
            navigationOptionInterface = (NavigationOptionInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement NavigationOptionInterface interface");
        }
    }*/

    @Override
    public void onItemClicked(CustomChecklistResponse.CustomChecklistBean item) {
        if(isAdd){
            addSecurityChecklist(item);
        }
    }
}
