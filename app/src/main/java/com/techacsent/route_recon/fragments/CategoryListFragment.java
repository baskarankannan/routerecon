package com.techacsent.route_recon.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.CategoryAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.CategoryCreateResponse;
import com.techacsent.route_recon.model.CategoryListResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.DeleteCategoryModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment implements OnRecyclerItemClickListener<CategoryListResponse.ListBean> {
    private RecyclerView rvCategoryList;
    private CategoryAdapter mAdapter;
    private boolean isAddFlag;
    private int tripId;
    private NavigationOptionInterface navigationOptionInterface;
    private ShimmerFrameLayout shimmerFrameLayout;

    public CategoryListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCategoryList = view.findViewById(R.id.rv_category_list);
        Button btnSave = view.findViewById(R.id.btn_save_category);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        navigationOptionInterface.tripDetailsToolbar();

        rvCategoryList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategoryList.setHasFixedSize(false);
        rvCategoryList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),

                DividerItemDecoration.VERTICAL));

        if (getArguments() != null) {
            isAddFlag = getArguments().getBoolean("is_add_category");
            tripId = getArguments().getInt("trip_id");
        }

        if (isAddFlag) {
            btnSave.setVisibility(View.VISIBLE);
        } else {
            btnSave.setVisibility(View.GONE);
        }
        loadCategory();

        btnSave.setOnClickListener(v -> loadAddCategoryFragment());
    }

    private void loadAddCategoryFragment() {
        AddCategoryDialogFragment newCategoryFragment = new AddCategoryDialogFragment();
        newCategoryFragment.show(getChildFragmentManager(), null);
    }

    private void loadCategory() {
        //navigationOptionInterface.showProgressDialog(true);
        shimmerFrameLayout.startShimmer();
        ApiService apiService = new ApiCaller();
        apiService.callGetCategory(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "all", new ResponseCallback<CategoryListResponse>() {
            @Override
            public void onSuccess(CategoryListResponse data) {
                initAdapter(data);
            }

            @Override
            public void onError(Throwable th) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    private void initAdapter(CategoryListResponse data) {
        mAdapter = new CategoryAdapter(RouteApplication.getInstance().getApplicationContext(), isAddFlag);
        mAdapter.setItems(data.getList());
        /*if(isAddFlag){

        }*/
        mAdapter.setListener(this);

        rvCategoryList.setAdapter(mAdapter);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClicked(CategoryListResponse.ListBean item, int id) {
        switch (id) {
            case 1:
                ShowCheckListFragment showCheckListFragment = new ShowCheckListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("category_id", item.getCategoryId());
                bundle.putBoolean("is_add_category", isAddFlag);
                bundle.putInt("trip_id", tripId);
                bundle.putInt("category_id", item.getCategoryId());
                showCheckListFragment.setArguments(bundle);
                showCheckListFragment.show(getChildFragmentManager(), null);
                break;

            case 0:
                deleteCategory(item);
                break;


        }

    }

    private void deleteCategory(CategoryListResponse.ListBean item) {
        DeleteCategoryModel deleteCategoryModel = new DeleteCategoryModel();
        deleteCategoryModel.setCategoryId(item.getCategoryId());
        ApiService apiService = new ApiCaller();
        apiService.deleteCategory(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), deleteCategoryModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                mAdapter.remove(item);
            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.trip_details_content, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
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
    public void addCategory(CategoryCreateResponse categoryCreateResponse) {
        CategoryListResponse.ListBean listBean = new CategoryListResponse.ListBean();
        listBean.setCategoryId(categoryCreateResponse.getCustomCategory().getCategoryId());
        listBean.setCategoryName(categoryCreateResponse.getCustomCategory().getCategoryName());
        listBean.setEditable(categoryCreateResponse.getCustomCategory().getDeleteable());
        mAdapter.add(listBean);

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

}
