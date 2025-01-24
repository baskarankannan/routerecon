package com.techacsent.route_recon.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;

public class RSSViewModel extends ViewModel {

    private MutableLiveData<List<Article>> mResponse;

    private MutableLiveData<String> mError = new MutableLiveData<>();

    private MutableLiveData<Boolean> mLoading = new MutableLiveData<>();

    public LiveData<List<Article>> getRssdata() {
        if (mResponse == null) {
            mResponse = new MutableLiveData<>();
        }
        return mResponse;
    }

    public LiveData<String> getError() {
        return mError;
    }

    public LiveData<Boolean> getLoading() {
        return mLoading;
    }

    public void getRssFeedFromUrl() {
        mLoading.setValue(true);
        String url = "https://travel.state.gov/_res/rss/TAsTWs.xml";
        Parser parser = new Parser();
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> arrayList) {
                mResponse.postValue(arrayList);
                mLoading.setValue(false);

            }

            @Override
            public void onError() {
                mError.postValue("Something went wrong");
                mLoading.setValue(false);

            }
        });

    }

}
