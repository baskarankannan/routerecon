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
import android.widget.SearchView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.SelectMemberAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.CreateGroupResponse;
import com.techacsent.route_recon.model.FollowersResponse;
import com.techacsent.route_recon.retrofit_api.ApiClient;
import com.techacsent.route_recon.retrofit_api.ApiInterface;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupWithFollowerFragment extends Fragment {

    private SelectMemberAdapter selectMemberAdapter;
    private RecyclerView rvFollowerList;
    private SearchView searchView;
    private List<FollowersResponse.ListBean> listBeans;
    private Button btnCreateGroup;
    private EditText etGroupName;
    //private ProgressBar progressBar;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private ShimmerFrameLayout shimmerFrameLayout;


    public CreateGroupWithFollowerFragment() {

    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_group_with_follower, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view);
        //progressBar = view.findViewById(R.id.progress_bar);
        btnCreateGroup = view.findViewById(R.id.btn_create);
        etGroupName = view.findViewById(R.id.et_group_name);
        searchView = view.findViewById(R.id.search_user);
        rvFollowerList = view.findViewById(R.id.rv_followers_list);
        rvFollowerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFollowerList.setHasFixedSize(true);
        rvFollowerList.setItemAnimator(new DefaultItemAnimator());
        rvFollowerList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        getFollower(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID)
        );

        searchView.setOnClickListener(v -> searchView.onActionViewExpanded());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //loadUserSearch(query);
                selectMemberAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //loadUserSearch(newText);
                try {
                    selectMemberAdapter.getFilter().filter(newText);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            }
        });

        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSelectedUser().length() < 1) {
                    Toast.makeText(getActivity(), Constant.MSG_USER_SELECT_WARNING, Toast.LENGTH_SHORT).show();
                } else if (etGroupName.getText().toString().trim().length() < 1) {
                    Toast.makeText(getActivity(), Constant.MSG_GROUP_NAME_WARNING, Toast.LENGTH_SHORT).show();
                } else {
                    createGroup(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID),
                            etGroupName.getText().toString().trim(), getSelectedUser());
                }

            }
        });
    }

    private void getFollower(String token) {
        shimmerFrameLayout.startShimmer();
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.callFollowers(token, "yes", 50, 0).enqueue(new Callback<FollowersResponse>() {
            @Override
            public void onResponse(@NonNull Call<FollowersResponse> call, @NonNull Response<FollowersResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getList() != null && response.body().getList().size() > 0) {
                        listBeans = (ArrayList<FollowersResponse.ListBean>) response.body().getList();
                        selectMemberAdapter = new SelectMemberAdapter(listBeans);
                        rvFollowerList.setAdapter(selectMemberAdapter);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                } else {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                        String key;
                        JSONObject errorObj = jObjError.getJSONObject("error");
                        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), errorObj.get("message").toString(), Toast.LENGTH_SHORT).show();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                    } catch (IOException e) {
                        e.printStackTrace();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FollowersResponse> call, @NonNull Throwable t) {
                t.printStackTrace();
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);

            }
        });
    }

    private JSONArray getSelectedUser() {
        JSONArray jsonArray = new JSONArray();
        if (listBeans != null) {
            for (FollowersResponse.ListBean listBean : listBeans) {
                if (listBean.getUser().isSelected()) {
                    jsonArray.put(Integer.parseInt(listBean.getUser().getId()));
                }
            }
        }

        return jsonArray;
    }

    private void createGroup(String token, String name, JSONArray jsonArray) {
        fragmentActivityCommunication.showProgressDialog(true);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("users", jsonArray);
            RequestBody requestBody = RequestBody.create(Constant.JSON, jsonObject.toString());
            apiInterface.callCreateGroup(token, requestBody).enqueue(new Callback<CreateGroupResponse>() {
                @Override
                public void onResponse(@NonNull Call<CreateGroupResponse> call, @NonNull Response<CreateGroupResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getSuccess().getMessage().equals("Create group successfully")) {
                            //createGroupResponseMutableLiveData.postValue(response.body());
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.body().getSuccess().getMessage(), Toast.LENGTH_SHORT).show();
                            fragmentActivityCommunication.showProgressDialog(false);
                            Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                            fragmentActivityCommunication.showProgressDialog(false);
                            //createGroupResponseMutableLiveData.postValue(null);
                        }
                    } else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            String key;
                            JSONObject errorObj = jObjError.getJSONObject("error");
                            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), errorObj.get("message").toString(), Toast.LENGTH_SHORT).show();
                            fragmentActivityCommunication.showProgressDialog(false);
                            //responseCallback.onError(new Exception(errorObj.get("message").toString()));

                        } catch (IOException e) {
                            e.printStackTrace();
                            fragmentActivityCommunication.showProgressDialog(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            fragmentActivityCommunication.showProgressDialog(false);
                        }
                        //createGroupResponseMutableLiveData.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<CreateGroupResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(RouteApplication.getInstance().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    fragmentActivityCommunication.showProgressDialog(false);
                    //createGroupResponseMutableLiveData.postValue(null);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
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

}
