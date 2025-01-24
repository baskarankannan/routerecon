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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.GroupMemberAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;
import com.techacsent.route_recon.model.CreateGroupResponse;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.model.GroupMemberResponse;
import com.techacsent.route_recon.model.SuccessArray;
import com.techacsent.route_recon.model.request_body_model.UpdateGroupNameReqBody;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditGroupFragment extends Fragment implements OnRecyclerItemClickListener<FollowersResponse.ListBean>, View.OnClickListener {

    private String groupName;
    private String groupId;
    private ShimmerFrameLayout shimmerFrameLayout;
    private EditText etGroupName;
    private Button updateGrpNameBtn;

    private RecyclerView rvFollower;

    private List<String> userIdList;

    private ArrayList<FollowersResponse.ListBean> listBeans;

    private FragmentActivityCommunication fragmentActivityCommunication;

    public EditGroupFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            groupName = getArguments().getString(Constant.KEY_GROUP_NAME);
            groupId = getArguments().getString(Constant.KEY_GROUP_ID);
            listBeans = getArguments().getParcelableArrayList("list");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_group, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userIdList = new ArrayList<>();
        initializeView(view);
        getGroupMember();


    }

    private void callEditGroupName(String name) {
        ApiService apiService = new ApiCaller();


        UpdateGroupNameReqBody body = new UpdateGroupNameReqBody();
        body.setId(Integer.parseInt(groupId));
        body.setName(name);
        apiService.updateGroupName(body, new ResponseCallback<CreateGroupResponse>() {
            @Override
            public void onSuccess(CreateGroupResponse data) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
                fragmentActivityCommunication.showProgressDialog(false);

            }

            @Override
            public void onError(Throwable th) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                fragmentActivityCommunication.showProgressDialog(false);

            }
        });
    }

    private void initializeView(View view) {
        etGroupName = view.findViewById(R.id.et_group_name);
        updateGrpNameBtn = view.findViewById(R.id.update_grp_name_btn);
        updateGrpNameBtn.setOnClickListener(this);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        rvFollower = view.findViewById(R.id.rv_follower);
        rvFollower.invalidate();
        rvFollower.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFollower.setHasFixedSize(false);
        rvFollower.setItemAnimator(new DefaultItemAnimator());
        rvFollower.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        etGroupName.setText(groupName);
    }

    private void getGroupMember() {
        //fragmentActivityCommunication.showProgressDialog(true);
        shimmerFrameLayout.startShimmer();
        ApiService apiService = new ApiCaller();
        apiService.getGroupMember(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                Integer.valueOf(groupId), 30, 0, new ResponseCallback<GroupMemberResponse>() {
                    @Override
                    public void onSuccess(GroupMemberResponse data) {
                        for (GroupMemberResponse.ListBean listBean : data.getList()) {
                            userIdList.add(listBean.getId());
                        }
                        //fragmentActivityCommunication.showProgressDialog(false);
                        initAdapter();
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

    private void initAdapter() {
        GroupMemberAdapter mAdapter = new GroupMemberAdapter(RouteApplication.getInstance().getApplicationContext(), userIdList);
        mAdapter.setItems(listBeans);
        mAdapter.setListener(this);
        rvFollower.setAdapter(mAdapter);

    }

    private void removeMember(FollowersResponse.ListBean item) {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.removeMemberFromGroup(Integer.parseInt(groupId), PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                Integer.valueOf(item.getUser().getId()), new ResponseCallback<SuccessArray>() {
                    @Override
                    public void onSuccess(SuccessArray data) {
                        fragmentActivityCommunication.showProgressDialog(false);
                        //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable th) {
                        fragmentActivityCommunication.showProgressDialog(false);
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void addMember(FollowersResponse.ListBean item) {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiService apiService = new ApiCaller();
        apiService.addMemberFromGroup(Integer.parseInt(groupId), PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                Integer.valueOf(item.getUser().getId()), new ResponseCallback<SuccessArray>() {
                    @Override
                    public void onSuccess(SuccessArray data) {
                        fragmentActivityCommunication.showProgressDialog(false);
                        //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), data.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(Throwable th) {
                        fragmentActivityCommunication.showProgressDialog(false);
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), th.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemClicked(FollowersResponse.ListBean item, int itemID) {

        switch (itemID) {
            case 0:
                //removeMember(item);
                addMember(item);
                break;

            case 1:
                removeMember(item);
                break;
        }

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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.update_grp_name_btn: {

                if (etGroupName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.group_name_missing, Toast.LENGTH_LONG).show();
                } else {
                    fragmentActivityCommunication.showProgressDialog(true);
                    callEditGroupName(etGroupName.getText().toString());
                }


                break;
            }
        }
    }
}

