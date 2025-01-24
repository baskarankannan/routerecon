package com.techacsent.route_recon.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.model.PostCrimeReportModel;
import com.techacsent.route_recon.model.PostCrimeReportResponse;
import com.techacsent.route_recon.retrofit_api.ApiCaller;
import com.techacsent.route_recon.retrofit_api.ApiService;
import com.techacsent.route_recon.retrofit_api.ResponseCallback;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

import java.util.Objects;

public class PostCrimeFragment extends Fragment {
    private ImageView ivClose;
    private EditText etAddress;
    private EditText etFullAddress;
    private EditText etDescription;
    private Button btnPost;
    private double latitude;
    private double longitude;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public PostCrimeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("lonX");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_crime, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivClose = view.findViewById(R.id.iv_close);
        etAddress = view.findViewById(R.id.et_address);
        etFullAddress = view.findViewById(R.id.et_full_address);
        etDescription = view.findViewById(R.id.et_report_description);
        btnPost = view.findViewById(R.id.btn_post);
        btnPost.setOnClickListener(v -> postReport());
        ivClose.setOnClickListener(v -> Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack());
    }

    private void postReport() {
        fragmentActivityCommunication.showProgressDialog(true);
        PostCrimeReportModel postCrimeReportModel = new PostCrimeReportModel();
        postCrimeReportModel.setReportType(1);
        postCrimeReportModel.setLat(latitude);
        postCrimeReportModel.setLongX(longitude);
        postCrimeReportModel.setRadius(0);
        postCrimeReportModel.setAddress(etAddress.getText().toString().trim());
        postCrimeReportModel.setFullAddress(etFullAddress.getText().toString().trim());
        postCrimeReportModel.setDescription(etDescription.getText().toString().trim());
        ApiService apiService = new ApiCaller();
        apiService.postCrimeReport(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), postCrimeReportModel, new ResponseCallback<PostCrimeReportResponse>() {
            @Override
            public void onSuccess(PostCrimeReportResponse data) {
                fragmentActivityCommunication.showProgressDialog(false);
                getActivity().getSupportFragmentManager().popBackStack();
            }

            @Override
            public void onError(Throwable th) {
                fragmentActivityCommunication.showProgressDialog(false);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        super.onAttach(context);
        if (context instanceof FragmentActivityCommunication) {
            fragmentActivityCommunication = (FragmentActivityCommunication) context;
        } else {
            throw new IllegalArgumentException("Containing activity must implement FragmentActivityCommunication interface");
        }
    }
}
