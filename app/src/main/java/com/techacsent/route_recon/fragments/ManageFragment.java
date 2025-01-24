package com.techacsent.route_recon.fragments;

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

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.ItemAdapter;
import com.techacsent.route_recon.event_bus_object.NotificationBadgeObject;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.ItemData;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.SetBadgeModel;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageFragment extends Fragment implements OnRecyclerItemClickListener {
    private RecyclerView rvList;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public ManageFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView(view);
        fragmentActivityCommunication.setTitle("Manage Friends");
    }

    private void initializeView(View view) {
        rvList = view.findViewById(R.id.rv_settings_list);
        rvList.invalidate();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        ItemData itemData = new ItemData("User Search", R.drawable.ic_manage_friends,false,false,false,false);
        ItemData itemData1 = new ItemData("Friends", R.drawable.ic_manage_friends,false,false,false,false);
        ItemData itemData2 = new ItemData("Create & Manage Groups", R.drawable.ic_manage_friends,false,false,false,false);
        List<ItemData> itemDataList = new ArrayList<>();
        itemDataList.add(itemData);
        itemDataList.add(itemData1);
        itemDataList.add(itemData2);
        initAdapter(itemDataList);
    }

    private void initAdapter(List<ItemData> itemDataList) {
        ItemAdapter mAdapter = new ItemAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(),false);
        mAdapter.setItems(itemDataList);
        mAdapter.setListener(this);
        rvList.setAdapter(mAdapter);
    }


    private void loadFragment(Fragment fragment) {
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName()).commit();
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

    @Override
    public void onItemClicked(Object item, int itemID) {
        switch (itemID) {
            case 0:
                Fragment fragment = new UserSearchFragment();
                loadFragment(fragment);
                break;

            case 1:
                fragment = new FriendsFragment();
                loadFragment(fragment);
                List<String> badgeTypeList = new ArrayList<>();
                badgeTypeList.add("friend-req-send");
                badgeTypeList.add("friend-req-accept");
                resetBadge(badgeTypeList, 2 );
                EventBus.getDefault().post(new NotificationBadgeObject(true, "friend_action"));
                //PreferenceManager.updateValue(Constant.KEY_FRIEND_REQ_BADGE_COUNT,0);
                Constant.IS_FRIEND_CHECKED = true;
                break;

            case 2:
                fragment = new CreateGroupFragment();
                loadFragment(fragment);
                break;
        }
    }

    private void resetBadge(List<String> listBadgeType, int count) {
        SetBadgeModel setBadgeModel = new SetBadgeModel();
        List<SetBadgeModel.BadgesBean> badgesBeanList = new ArrayList<>();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                SetBadgeModel.BadgesBean badgesBean = new SetBadgeModel.BadgesBean();
                badgesBean.setBadgeCount(0);
                badgesBean.setBadgeType(listBadgeType.get(i));
                badgesBeanList.add(badgesBean);
            }
        } else {
            SetBadgeModel.BadgesBean badgesBean = new SetBadgeModel.BadgesBean();
            badgesBean.setBadgeCount(0);
            badgesBean.setBadgeType(null);
            badgesBeanList.add(badgesBean);
        }

        setBadgeModel.setBadges(badgesBeanList);
        ApiService apiService = new ApiCaller();
        apiService.setBadgeCount(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), setBadgeModel, new ResponseCallback<SuccessArray>() {
            @Override
            public void onSuccess(SuccessArray data) {
                PreferenceManager.updateValue(Constant.KEY_TRIP_SHARING_BADGE_COUNT, 0);

            }

            @Override
            public void onError(Throwable th) {

            }
        });
    }
}
