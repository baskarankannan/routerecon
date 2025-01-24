package com.techacsent.route_recon.fragments;


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
import android.widget.Toast;

import com.prof.rssparser.Article;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.RssAdapter;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.interfaces.FragmentActivityCommunication;
import com.techacsent.route_recon.interfaces.OnRecyclerClickListener;
import com.techacsent.route_recon.utills.Utils;
import com.techacsent.route_recon.view_model.RSSViewModel;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class RSSFeedFragment extends Fragment implements OnRecyclerClickListener<Article> {

    private RSSViewModel mViewModel;
    private FragmentActivityCommunication fragmentActivityCommunication;
    private RecyclerView rvList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RSSViewModel.class);
        fragmentActivityCommunication.setTitle(getString(R.string.text_rss_feed));
    }

    public RSSFeedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rssfeed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        if (Utils.isNetworkConnected(RouteApplication.getInstance().getApplicationContext())) {
            mViewModel.getRssFeedFromUrl();
        } else {
            makeToast(getResources().getString(R.string.text_error_occured));
        }
        observeViewModel();
    }

    private void initView(@NonNull View view) {
        rvList = view.findViewById(R.id.rv_feed_list);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));
    }

    private void observeViewModel() {

        mViewModel.getRssdata().observe(this, this::initAdapter);

        mViewModel.getError().observe(this, s -> Toast.makeText(RouteApplication.getInstance().getApplicationContext(), s, Toast.LENGTH_SHORT).show());

        mViewModel.getLoading().observe(this, isLoading -> fragmentActivityCommunication.showProgressDialog(isLoading));
    }

    private void initAdapter(List<Article> arrayList) {
        RssAdapter mAdapter = new RssAdapter(RouteApplication.getInstance().getApplicationContext());
        mAdapter.setItems(arrayList);
        mAdapter.setListener(this);
        rvList.setAdapter(mAdapter);

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
    public void onDetach() {
        super.onDetach();
        fragmentActivityCommunication = null;

    }

    private void loadFragment( String description,String title) {
        Bundle bundle = new Bundle();
        bundle.putString("description", description);
        bundle.putString("title", title);

        Fragment fragment = new RssDescriptionFragment();
        fragment.setArguments(bundle);

        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    private void makeToast(String msg) {
        Toast.makeText(RouteApplication.getInstance().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(Article item) {

        String[]splitArray=item.getTitle().split("-");


        loadFragment(item.getDescription(),splitArray[0]);
    }
}
