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
import android.widget.RelativeLayout;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.SystemChecklistCategoryAdapter;

import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerItemClickListener;

import com.techacsent.route_recon.model.SystemSecurityModel;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChecklistCategoryFragment extends Fragment implements OnRecyclerItemClickListener<SystemSecurityModel> {
    private int tripId;
    private boolean isCustomShow;
    private FragmentActivityCommunication fragmentActivityCommunication;

    public ChecklistCategoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tripId = getArguments().getInt("trip_id");
            isCustomShow = getArguments().getBoolean("is_custom_show");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_checklist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvSystemChecklist = view.findViewById(R.id.rv_system_check_list);
        fragmentActivityCommunication.setTitle(getResources().getString(R.string.security_checklist));

        RelativeLayout layoutCustom = view.findViewById(R.id.layout_custom);
        /*if (!isCustomShow) {
            layoutCustom.setVisibility(View.GONE);
        } else {
            layoutCustom.setVisibility(View.VISIBLE);
        }*/
        rvSystemChecklist.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSystemChecklist.setHasFixedSize(false);
        rvSystemChecklist.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()),
                DividerItemDecoration.VERTICAL));
        List<SystemSecurityModel> listofOption = new ArrayList<>();
        //listofOption.add(new SystemSecurityModel("AA", "CustomChecklist"));
        listofOption.add(new SystemSecurityModel("AA", "Routes"));
        listofOption.add(new SystemSecurityModel("AA", "Route Survey Checklist"));
        listofOption.add(new SystemSecurityModel("AA", "Route Analysis"));
        listofOption.add(new SystemSecurityModel("AA", "Movement-on Foot or Vehicle"));
        listofOption.add(new SystemSecurityModel("AA", "Security Awareness"));
        listofOption.add(new SystemSecurityModel("AA", "Surveillance Indicators"));
        listofOption.add(new SystemSecurityModel("AA", "Vehicle Emergency Equipment"));
        SystemChecklistCategoryAdapter systemChecklistAdapter = new SystemChecklistCategoryAdapter(getActivity());
        systemChecklistAdapter.setItems(listofOption);
        systemChecklistAdapter.setListener(this);
        rvSystemChecklist.setAdapter(systemChecklistAdapter);

        layoutCustom.setOnClickListener(v -> {
            CustomChecklistFragment customChecklistFragment = new CustomChecklistFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("is_add", isCustomShow);
            bundle.putInt("trip_id",tripId);
            customChecklistFragment.setArguments(bundle);
            customChecklistFragment.show(getChildFragmentManager(), null);

        });
    }

    @Override
    public void onItemClicked(SystemSecurityModel item, int itemID) {
        if (tripId != 0) {
            ChecklistFragment checklistFragmentFragment = new ChecklistFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("flag", itemID);
            bundle.putInt("trip_id", tripId);
            checklistFragmentFragment.setArguments(bundle);
            checklistFragmentFragment.show(getChildFragmentManager(), null);
        } else {
            SecurityItemDialogFragment checklistFragmentFragment = new SecurityItemDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("flag", itemID + 1);
            checklistFragmentFragment.setArguments(bundle);
            checklistFragmentFragment.show(getChildFragmentManager(), null);
            //Toast.makeText(RouteApplication.getInstance().getApplicationContext(), Constant.MSG_FEATURE_UNDER_DEVELOPMENT,Toast.LENGTH_SHORT).show();
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
