package com.techacsent.route_recon.fragments;


import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.SelectFollwerGroupAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.NavigationOptionInterface;
import com.techacsent.route_recon.model.TripFollowerResponse;
import com.techacsent.route_recon.model.request_body_model.AddMoreUserModel;
import com.techacsent.route_recon.model.request_body_model.ShareTripModel;
import com.techacsent.route_recon.model.GroupListResponse;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.view_model.TripShareViewModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {
    //private EditText etCaption;
    private CheckBox chkAll;
    private RecyclerView rvList;
    private ImageButton ibtnShareAdd;
    private TripShareViewModel tripShareViewModel;
    private SelectFollwerGroupAdapter selectMemberAdapter;
    private List<Object> listBeans;
    private String tripId;
    private boolean isShare;
    //private boolean isFromSuccess;
    private boolean isFromDetails;
    private boolean isFromSendButton;
    private NavigationOptionInterface navigationOptionInterface;
    private boolean isAllSelected;
    private ShareTripModel shareTripModel;
    private AddMoreUserModel addMoreUserModel;
    private String tripSharingID;
    private ShimmerFrameLayout shimmerFrameLayout;

    public ShareFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            tripId = getArguments().getString("trip_id");
            isShare = getArguments().getBoolean(Constant.KEY_IS_SHARE);
            isFromDetails = getArguments().getBoolean("is_from_details");
            isFromSendButton = getArguments().getBoolean("is_trip_send");
            //isFromSuccess = getArguments().getBoolean("is_from_success");
            if (!isShare) {
                //dataBean = getArguments().getParcelable(Constant.KEY_PARCEL_MY_TRIP_DESCRIPTION);
                //private MyTripDescriptionModel.DataBean dataBean;
                String orgTripid = getArguments().getString("org_trip_id");
                tripSharingID = getArguments().getString("trip_sharing_id");
                tripId = orgTripid;
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(isFromSendButton){

            navigationOptionInterface.shareFragmentToolbar(false);

        }else{

            navigationOptionInterface.shareFragmentToolbar(true);

        }
        navigationOptionInterface.hideToolbar(false);
        //navigationOptionInterface.navigationChooseToolbar(true);
        listBeans = new ArrayList<>();
        initializeView(view);
        initializeListener();
        tripShareViewModel = ViewModelProviders.of(this).get(TripShareViewModel.class);
        //countFollower();
        loadUser();
    }

    private void initializeView(View view) {
        //etCaption = view.findViewById(R.id.et_caption);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        chkAll = view.findViewById(R.id.chk_all);
        rvList = view.findViewById(R.id.rv_list);
        ibtnShareAdd = view.findViewById(R.id.btn_share_or_add);
        shareTripModel = new ShareTripModel();
        addMoreUserModel = new AddMoreUserModel();
        rvList.invalidate();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setHasFixedSize(true);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
    }

    private void initializeListener() {
        chkAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                rvList.setVisibility(View.INVISIBLE);
                isAllSelected = true;
            } else {
                rvList.setVisibility(View.VISIBLE);
                isAllSelected = false;
            }
        });
        ibtnShareAdd.setOnClickListener(v -> {
            if (!isShare) {
                addMoreUserModel.setIspublic("no");
                addMoreUserModel.setUsers(getSelectedUser());
                addMoreUserModel.setGroups(getSelectedGroup());
                addMoreUserModel.setTripSharingId(Integer.valueOf(tripSharingID));
                if (isAllSelected) {
                    addMoreUserModel.setAll("yes");
                } else {
                    addMoreUserModel.setAll("no");
                }
                if (!isAllSelected) {
                    if ((getSelectedGroup() != null && getSelectedGroup().size() > 0) || (getSelectedUser() != null && getSelectedUser().size() > 0)) {
                        addUserOnTrip(addMoreUserModel);
                    } else {
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_select_at_least_one_user), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    addUserOnTrip(addMoreUserModel);
                }


            } else {

                if(isFromSendButton){

                    if (!isAllSelected) {
                        if ((getSelectedGroup() != null && getSelectedGroup().size() > 0) || (getSelectedUser() != null && getSelectedUser().size() > 0)) {
                            shareTripForSendButton();
                        } else {
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_select_at_least_one_user), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        shareTripForSendButton();
                    }

                }else {
                    if (!isAllSelected) {
                        if ((getSelectedGroup() != null && getSelectedGroup().size() > 0) || (getSelectedUser() != null && getSelectedUser().size() > 0)) {
                            shareTrip();
                        } else {
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), getResources().getString(R.string.text_select_at_least_one_user), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        shareTrip();
                    }
                }
            }

        });
    }

    private void addUserOnTrip(AddMoreUserModel addMoreUserModel) {
        navigationOptionInterface.showProgressDialog(true);
        tripShareViewModel.addMoreUser(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), addMoreUserModel)
                .observe(this, addMoreUserResponse -> {
                    if (addMoreUserResponse != null) {
                        if (addMoreUserResponse.getSuccess().getMessage().equals("Add More Friends successfully")) {
                            if (isFromDetails) {
                                Toast.makeText(getActivity(), "User added successfully in your route", Toast.LENGTH_SHORT).show();
                                navigationOptionInterface.showProgressDialog(false);
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                            } else {
                                Toast.makeText(getActivity(), addMoreUserResponse.getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                                navigationOptionInterface.showProgressDialog(false);
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                //getActivity().finish();
                            }
                        }else {
                            navigationOptionInterface.showProgressDialog(false);
                        }
                    }

                });

    }

    private List<Integer> getSelectedUser() {
        List<Integer> userList = new ArrayList<>();
        for (Object bean : listBeans) {
            if (bean instanceof TripFollowerResponse.ListBean) {
                if (((TripFollowerResponse.ListBean) bean).getUser().isSelected()) {
                    userList.add(Integer.parseInt(((TripFollowerResponse.ListBean) bean).getUser().getId()));
                }
            }
        }
        return userList;
    }

    private List<Integer> getSelectedGroup() {
        List<Integer> groupList = new ArrayList<>();
        for (Object bean : listBeans) {
            if (bean instanceof GroupListResponse.ListBean) {
                if (((GroupListResponse.ListBean) bean).isSelected()) {
                    groupList.add(Integer.parseInt(((GroupListResponse.ListBean) bean).getId()));
                }
            }
        }
        return groupList;
    }


    //for creating same trip in receiver end
    private void shareTripForSendButton() {
        /*if (getSelectedUser().size() < 0 || getSelectedGroup().size() < 0) {
            Toast.makeText(getActivity(), Constant.MSG_SELECT_AT_LEAST_ONE_USER, Toast.LENGTH_SHORT).show();
        } else {

        }*/

        shareTripModel.setStatus("demo");
        shareTripModel.setIspublic("no");
        shareTripModel.setUsers(getSelectedUser());
        shareTripModel.setGroups(getSelectedGroup());
        shareTripModel.setTrip_send("yes");
        shareTripModel.setTripSharingId(Integer.valueOf(tripId));
        if (isAllSelected) {
            shareTripModel.setAll("yes");
        } else {
            shareTripModel.setAll("no");
        }
        navigationOptionInterface.showProgressDialog(true);
        tripShareViewModel.callSendTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                shareTripModel).observe(this, tripShareResponse -> {
            if (tripShareResponse != null) {

                Toast.makeText(getActivity(),"Trip sent successful !", Toast.LENGTH_SHORT).show();

                Log.e("SendTripResponse","Data "+tripShareResponse.toString());

                    if (isFromDetails) {
                        navigationOptionInterface.showProgressDialog(false);
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                    } else {
                        navigationOptionInterface.showProgressDialog(false);
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getActivity().finish();
                    }

                } else {
                    navigationOptionInterface.showProgressDialog(false);
                }

        });
    }


    private void shareTrip() {
        /*if (getSelectedUser().size() < 0 || getSelectedGroup().size() < 0) {
            Toast.makeText(getActivity(), Constant.MSG_SELECT_AT_LEAST_ONE_USER, Toast.LENGTH_SHORT).show();
        } else {

        }*/

        shareTripModel.setStatus("demo");
        shareTripModel.setIspublic("no");
        shareTripModel.setUsers(getSelectedUser());
        shareTripModel.setGroups(getSelectedGroup());
        shareTripModel.setTripSharingId(Integer.valueOf(tripId));

        if (isAllSelected) {
            shareTripModel.setAll("yes");
        } else {
            shareTripModel.setAll("no");
        }

        navigationOptionInterface.showProgressDialog(true);
        tripShareViewModel.callShareTrip(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                shareTripModel).observe(this, tripShareResponse -> {
            if (tripShareResponse != null) {
                if (tripShareResponse.getId() > 0) {
                    if (isFromDetails) {
                        navigationOptionInterface.showProgressDialog(false);
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                    } else {
                        navigationOptionInterface.showProgressDialog(false);
                        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getActivity().finish();
                    }

                } else {

                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                    getActivity().finish();
                    navigationOptionInterface.showProgressDialog(false);
                }
            }
        });
    }

    private void loadUser() {
        //navigationOptionInterface.showProgressDialog(true);
        shimmerFrameLayout.startShimmer();
        tripShareViewModel.callTripFollower(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), "yes", Integer.parseInt(tripId), 50, 0).observe(this, followersResponse -> {
            if (followersResponse != null && followersResponse.getList() != null) {
                //AsyncTask.execute(() -> listBeans.addAll(followersResponse.getList()));
                AsyncTask.execute(() -> {
                    for (TripFollowerResponse.ListBean listBean : followersResponse.getList()) {
                        if (listBean.getUser().getTripSharedStatus().equals("notAccepted")) {
                            listBeans.add(listBean);
                        }
                    }
                });

                loadGroupList();
            } else {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

        });
    }

    private void loadGroupList() {
        tripShareViewModel.callGroupList(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                50, 0).observe(this, groupListResponse -> {
            if (groupListResponse != null && groupListResponse.getList() != null) {
                AsyncTask.execute(() -> listBeans.addAll(groupListResponse.getList()));
                Collections.shuffle(listBeans);
                selectMemberAdapter = new SelectFollwerGroupAdapter(listBeans);
                rvList.setAdapter(selectMemberAdapter);
                //navigationOptionInterface.showProgressDialog(false);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            } else {
                //navigationOptionInterface.showProgressDialog(false);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        navigationOptionInterface.tripDetailsToolbar();
        //navigationOptionInterface.navigationChooseToolbar(false);
    }
}
