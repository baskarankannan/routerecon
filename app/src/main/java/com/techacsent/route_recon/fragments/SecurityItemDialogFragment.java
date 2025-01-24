package com.techacsent.route_recon.fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.CategorizedChecklistAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecurityItemDialogFragment extends DialogFragment {

    private CategorizedChecklistAdapter mAdapter;

    private RecyclerView rvList;
    private ProgressBar progressBar;

    private int id;



    public SecurityItemDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            id = getArguments().getInt("flag");
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_security_item_dialog, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvList = view.findViewById(R.id.rv_check_list);
        progressBar = view.findViewById(R.id.progress_bar);
        rvList.invalidate();
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setHasFixedSize(true);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
        //loadData();
    }


    /*private void loadData(){
        ApiService apiService = new ApiCaller();
        apiService.getCategoryWiseChecklist(PreferenceManager.getString(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID), id, new ResponseCallback<CategoryWiseChecklistResponse>() {
            @Override
            public void onSuccess(CategoryWiseChecklistResponse data) {
                mAdapter = new CategorizedChecklistAdapter(getActivity());
                mAdapter.setItems(data.getSystemChecklist());
                rvList.setAdapter(mAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable th) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }*/
}
