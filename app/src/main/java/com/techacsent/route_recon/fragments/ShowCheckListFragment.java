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
import android.widget.Button;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.CategorizedChecklistAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.event_bus_object.UpdateTripChecklistModel;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.CategoryWiseChecklistResponse;
import com.techacsent.route_recon.model.ChecklistCreateResponse;
import com.techacsent.route_recon.model.CreateTripChecklistResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.CreateTripChecklistModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowCheckListFragment extends DialogFragment implements OnRecyclerItemClickListener<CategoryWiseChecklistResponse.ListBean> {
    private RecyclerView rvItem;
    private CategorizedChecklistAdapter mAdapter;
    private int id;
    private boolean isAddFlag;
    private int tripId;
    private int categoryId;
    private ShimmerFrameLayout shimmerFrameLayout;
    private NavigationOptionInterface navigationOptionInterface;

    public ShowCheckListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("category_id");
            isAddFlag = getArguments().getBoolean("is_add_category");
            tripId = getArguments().getInt("trip_id");
            categoryId = getArguments().getInt("category_id");
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_check_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvItem = view.findViewById(R.id.rv_item);
        Button btnSave = view.findViewById(R.id.btn_save_item);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_attending);

        rvItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvItem.setHasFixedSize(false);
        rvItem.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                DividerItemDecoration.VERTICAL));

        if (isAddFlag) {
            btnSave.setVisibility(View.VISIBLE);
        } else {
            btnSave.setVisibility(View.GONE);
        }

        btnSave.setOnClickListener(v -> addIteminCategory());

        loadItemInCategory();
    }

    private void loadItemInCategory() {
        shimmerFrameLayout.startShimmer();
        ApiService apiService = new ApiCaller();
        apiService.getCategoryWiseChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), id, new ResponseCallback<CategoryWiseChecklistResponse>() {
            @Override
            public void onSuccess(CategoryWiseChecklistResponse data) {
                initAdapter(data);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable th) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

            }
        });
    }


    private void addIteminCategory() {
        CreateItemFragment createItemFragment = new CreateItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("category_id", categoryId);
        createItemFragment.setArguments(bundle);
        createItemFragment.show(getChildFragmentManager(), null);

    }

    private void initAdapter(CategoryWiseChecklistResponse data) {
        mAdapter = new CategorizedChecklistAdapter(getActivity(), isAddFlag);
        mAdapter.setItems(data.getList());
        if (isAddFlag) {
            mAdapter.setListener(this);
        }
        rvItem.setAdapter(mAdapter);
    }

    @Override
    public void onItemClicked(CategoryWiseChecklistResponse.ListBean item, int id) {
        switch (id){
            case 0:
                deleteChecklist(item);
                break;
            case 1:
                addChecklist(item);
                break;
        }
    }

    private void addChecklist(CategoryWiseChecklistResponse.ListBean item) {
        navigationOptionInterface.showProgressDialog(true);
        CreateTripChecklistModel createTripChecklistModel = new CreateTripChecklistModel();
        createTripChecklistModel.setCategoryId(categoryId);
        createTripChecklistModel.setChecklistId(item.getChecklistId());
        createTripChecklistModel.setTripId(tripId);
        ApiService apiService = new ApiCaller();
        apiService.callTripCheckList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), createTripChecklistModel, new ResponseCallback<CreateTripChecklistResponse>() {
            @Override
            public void onSuccess(CreateTripChecklistResponse data) {
                EventBus.getDefault().post(new UpdateTripChecklistModel(item.getChecklistText(), data.getChecklist().getTripChecklistId(), data.getChecklist().getIsCompleted()));
                navigationOptionInterface.showProgressDialog(false);
            }

            @Override
            public void onError(Throwable th) {
                navigationOptionInterface.showProgressDialog(false);

            }
        });
    }

    private void deleteChecklist(CategoryWiseChecklistResponse.ListBean item){
        navigationOptionInterface.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.deleteSecurityChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), item.getChecklistId(), categoryId, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                mAdapter.remove(item);
                navigationOptionInterface.showProgressDialog(false);
            }

            @Override
            public void onError(Throwable th) {
                navigationOptionInterface.showProgressDialog(false);

            }
        });
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
    public void updateList(ChecklistCreateResponse data) {
        CategoryWiseChecklistResponse.ListBean listBean = new CategoryWiseChecklistResponse.ListBean();
        listBean.setChecklistId(data.getChecklist().getChecklistId());
        listBean.setChecklistText(data.getChecklist().getChecklistText());
        listBean.setDeleteable(data.getChecklist().getDeleteable());
        if(mAdapter!=null){
            mAdapter.add(listBean);
        }else {
            List<CategoryWiseChecklistResponse.ListBean> list = new ArrayList<>();
            list.add(listBean);
            mAdapter = new CategorizedChecklistAdapter(RouteApplication.getInstance().getApplicationContext(),isAddFlag);
            mAdapter.setItems(list);
            mAdapter.setListener(this::onItemClicked);
            rvItem.setAdapter(mAdapter);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            navigationOptionInterface = (NavigationOptionInterface) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement NavigationOptionInterface interface");
        }
    }
}
