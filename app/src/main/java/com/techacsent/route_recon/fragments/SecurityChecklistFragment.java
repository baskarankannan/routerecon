package com.techacsent.route_recon.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.SecurityChecklistAdapter;
import com.techacsent.route_recon.event_bus_object.UpdateTripChecklistModel;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.CreateChecklistResponse;
import com.techacsent.route_recon.model.SecurityChecklistResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.UpdateChecklistResponse;
import com.techacsent.route_recon.model.request_body_model.DeleteTripChecklistModel;
import com.techacsent.route_recon.model.request_body_model.UpdateChecklistModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SecurityChecklistFragment extends Fragment implements OnRecyclerItemClickListener<SecurityChecklistResponse.ChecklistBean> {
    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView rvCheckList;
    private int tripId;
    private SecurityChecklistAdapter mAdapter;
    private List<SecurityChecklistResponse.ChecklistBean> checklistBeanList;

    public SecurityChecklistFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            tripId = getArguments().getInt("trip_id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_security_checklist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checklistBeanList = new ArrayList<>();
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        rvCheckList = view.findViewById(R.id.rv_check_list);
        /*Button btnCustomChecklist = view.findViewById(R.id.btn_add_custom_checklist);
        Button btnSystemCheckList = view.findViewById(R.id.btn_add_system_checklist);*/
        Button btnCategory = view.findViewById(R.id.btn_category);

        rvCheckList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCheckList.setHasFixedSize(false);
        rvCheckList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                DividerItemDecoration.VERTICAL));
        loadSecurityChecklist();
        /*btnCustomChecklist.setOnClickListener(v -> {
            ChecklistDialogFragment fragment = new ChecklistDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("trip_id", tripId);
            bundle.putBoolean("is_update", false);
            fragment.setArguments(bundle);
            fragment.show(getChildFragmentManager(), null);
        });
        btnSystemCheckList.setOnClickListener(v -> {
            Fragment addChecklistFragment = new ChecklistCategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_custom_show", true);
            bundle.putInt("trip_id", tripId);
            addChecklistFragment.setArguments(bundle);
            addChecklistFragment.setArguments(bundle);
            loadFragment(addChecklistFragment);
        });*/
        btnCategory.setOnClickListener(v -> {
            CategoryListFragment addChecklistFragment = new CategoryListFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_add_category", true);
            bundle.putInt("trip_id", tripId);
            addChecklistFragment.setArguments(bundle);
            loadFragment(addChecklistFragment);
        });
    }

    private void deleteSecurityChecklist(SecurityChecklistResponse.ChecklistBean item) {
        DeleteTripChecklistModel deleteTripChecklistModel = new DeleteTripChecklistModel();
        deleteTripChecklistModel.setTripChecklistId(item.getChecklistId());
        ApiService apiService = new ApiCaller();
        apiService.callDeleteTripChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), deleteTripChecklistModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                mAdapter.remove(item);
            }

            @Override
            public void onError(Throwable th) {
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.trip_details_content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
    }

    private void loadSecurityChecklist() {
        shimmerFrameLayout.startShimmer();
        ApiService apiService = new ApiCaller();
        apiService.getSecurityCheckList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), tripId, new ResponseCallback<SecurityChecklistResponse>() {
            @Override
            public void onSuccess(SecurityChecklistResponse data) {
                if (data.getChecklist() != null && data.getChecklist().size() > 0) {
                    checklistBeanList = data.getChecklist();
                    if (mAdapter == null) {
                        initAdapter();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                } else {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable th) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                //Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter() {
        mAdapter = new SecurityChecklistAdapter(getActivity());
        mAdapter.setItems(checklistBeanList);
        mAdapter.setListener(this);
        rvCheckList.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addChecklist(CreateChecklistResponse data) {
        SecurityChecklistResponse.ChecklistBean checklistBean = new SecurityChecklistResponse.ChecklistBean();
        checklistBean.setIsCompleted("no");

        checklistBean.setChecklistId(data.getChecklistId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateList(UpdateTripChecklistModel data) {
        SecurityChecklistResponse.ChecklistBean checklistBean = new SecurityChecklistResponse.ChecklistBean();
        checklistBean.setChecklistText(data.getChecklist());
        checklistBean.setChecklistId(data.getId());
        checklistBean.setIsCompleted(data.getState());
        if (mAdapter != null) {
            mAdapter.add(checklistBean);
            checklistBeanList.add(checklistBean);
            //mAdapter.notifyItemRangeChanged(checklistBeanList.size() - 1, checklistBeanList.size());
        } else {
            initAddAdapter(checklistBean);
        }

    }

    private void initAddAdapter(SecurityChecklistResponse.ChecklistBean checklistBean) {
        mAdapter = new SecurityChecklistAdapter(getActivity());
        mAdapter.add(checklistBean);
        mAdapter.setListener(this);
        rvCheckList.setAdapter(mAdapter);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.action_share_trip);
        MenuItem updateItem = menu.findItem(R.id.action_update_trip);
        updateItem.setVisible(false);
        shareItem.setVisible(false);
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

    private void updateSecurityChecklist(SecurityChecklistResponse.ChecklistBean item, String isComplete) {
        UpdateChecklistModel updateChecklistModel = new UpdateChecklistModel();
        updateChecklistModel.setTripChecklistId(item.getChecklistId());
        updateChecklistModel.setChecklistText(item.getChecklistText());
        updateChecklistModel.setIsCompleted(isComplete);
        ApiService apiService = new ApiCaller();
        apiService.updateChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), updateChecklistModel, new ResponseCallback<UpdateChecklistResponse>() {
            @Override
            public void onSuccess(UpdateChecklistResponse data) {

            }

            @Override
            public void onError(Throwable th) {
                //Toast.makeText(getActivity(), th.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClicked(SecurityChecklistResponse.ChecklistBean item, int itemID) {
        if (itemID == -1) {
            updateSecurityChecklist(item, "yes");

        } else if (itemID == -2) {
            updateSecurityChecklist(item, "no");
        } else {
            deleteSecurityChecklist(item);
        }

    }
}
