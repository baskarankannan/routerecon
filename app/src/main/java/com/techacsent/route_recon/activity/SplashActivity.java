package com.techacsent.route_recon.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotificationManager notificationManager = (NotificationManager) RouteApplication.getInstance().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        if (PreferenceManager.getBool(Constant.KEY_IS_LOGGED_IN)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent;
            if (PreferenceManager.getBool(Constant.KEY_IS_FIRST_RUN)) {
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();

            } else {
                PreferenceManager.updateValue(Constant.KEY_IS_FIRST_RUN, true);
                intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
