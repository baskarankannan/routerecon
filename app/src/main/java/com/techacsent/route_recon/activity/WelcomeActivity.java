package com.techacsent.route_recon.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;

import com.techacsent.route_recon.R;
import com.techacsent.route_recon.adapter.MainActivityPagerAdapter;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new MainActivityPagerAdapter(this, getSupportFragmentManager()));
        pager.setPageMargin((int) getResources().getDimension(R.dimen.text_margin_top) / 4);
        pager.setOffscreenPageLimit(3);

        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        ImageView tvSkip = findViewById(R.id.iv_skip);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        tvSkip.setOnClickListener(v -> gotoLogin());

    }

    private void gotoLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

}
