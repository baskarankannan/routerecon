package com.techacsent.route_recon.fragments;


import android.annotation.SuppressLint;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.PackageAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.model.PackageResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.PackageViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PackageFragment extends Fragment implements OnRecyclerClickListener<PackageResponse.AllPackageBean> {

    private RecyclerView rvPackage;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private TextView tvAlreadySubscribed;
    private PackageViewModel packageViewModel;

    public PackageFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_package, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        packageViewModel = ViewModelProviders.of(this).get(PackageViewModel.class);
        rvPackage = view.findViewById(R.id.rv_package_list);
        tvAlreadySubscribed = view.findViewById(R.id.tv_already_subscribed);
        rvPackage.invalidate();
        rvPackage.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPackage.setHasFixedSize(false);
        rvPackage.setItemAnimator(new DefaultItemAnimator());
        rvPackage.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
/*
        if(PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_STATUS).equals("payable")){
            getPackage();
        }*/
        checkSubscriptionDateValidity();
        getPackage();
    }

    private void getPackage() {
        fragmentActivityCommunication.showProgressDialog(true);
        packageViewModel.callGetPackage(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "all").observe(this, packageResponse -> {
            if (packageResponse != null) {
                initAdapter(packageResponse);
                fragmentActivityCommunication.showProgressDialog(false);
            } else {
                fragmentActivityCommunication.showProgressDialog(false);
            }
        });

    }

    private void checkSubscriptionDateValidity() {
        if (PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_DATE) != null && PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_DATE).length() > 2) {
            long time = System.currentTimeMillis();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String localTime = sdf.format(new Date(time));
            Date localDate;
            Date lastSubscriptionDate;
            try {
                localDate = sdf.parse(localTime);
                lastSubscriptionDate = sdf.parse(PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_DATE)/*"2019-06-27 13:32:25"*/);
                long diff = localDate.getTime() - lastSubscriptionDate.getTime();
                long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                Timber.d(String.valueOf(days));

                if (PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_STATUS).equals("payable")) {
                    tvAlreadySubscribed.setVisibility(View.GONE);
                    rvPackage.setVisibility(View.VISIBLE);
                } else {
                    if (days > 30) {
                        tvAlreadySubscribed.setVisibility(View.GONE);
                        rvPackage.setVisibility(View.VISIBLE);
                        //getPackage();
                    } else {
                        tvAlreadySubscribed.setVisibility(View.VISIBLE);
                        rvPackage.setVisibility(View.GONE);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            if (PreferenceManager.getString(Constant.KEY_SUBSCRIPTION_STATUS).equals("demo")) {
                tvAlreadySubscribed.setVisibility(View.VISIBLE);
                rvPackage.setVisibility(View.GONE);
                tvAlreadySubscribed.setText(getResources().getString(R.string.text_free_trial));

            } else {
                getPackage();
            }

        }


    }

    private void initAdapter(PackageResponse data) {
        PackageAdapter mAdapter = new PackageAdapter(RouteApplication.getInstance().getApplicationContext());
        mAdapter.setItems(data.getAllPackage());
        mAdapter.setListener(this);
        rvPackage.setAdapter(mAdapter);
        fragmentActivityCommunication.hideBottomNav(true);
    }

    @Override
    public void onItemClicked(PackageResponse.AllPackageBean item) {
        PaymentFragment paymentFragment = new PaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("parcel", item);
        paymentFragment.setArguments(bundle);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().add(R.id.frame_parent, paymentFragment).commit();


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
