package com.techacsent.route_recon.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.common.reflect.TypeToken;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.activity.HomeActivity;
import com.techacsent.route_recon.event_bus_object.LoadBadge;
import com.techacsent.route_recon.event_bus_object.LoadPendingTripobject;
import com.techacsent.route_recon.interfaces.OnDialogViewReceivedSendRequestListener;
import com.techacsent.route_recon.model.ReceivedShareTripRequest;
import com.techacsent.route_recon.room_db.database.AppDatabase;
import com.techacsent.route_recon.room_db.entity.BasicTripDescription;
import com.techacsent.route_recon.utills.Constant;
import com.techacsent.route_recon.utills.PreferenceManager;
import com.techacsent.route_recon.utills.ShowDialog;
import com.techacsent.route_recon.utills.Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import timber.log.Timber;

public class RouteReconMessagingService extends FirebaseMessagingService {
    private String channelId = "route_id";
    private String channelName = "route_recon";

    Gson gson = new Gson();


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Timber.d(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("PushNoti", remoteMessage.getData().toString());

        try {
            //PreferenceManager.updateValue(Constant.KEY_IS_BADGE_LOADED, false);
            if (remoteMessage.getData() != null) {
                //Timber.d(remoteMessage.getData().toString());
                Map<String, String> params = remoteMessage.getData();
                JSONObject object = new JSONObject(params);
               // Timber.d(object.toString());
                //Logger.json(object.toString());
                JSONObject pushDataobj = new JSONObject(object.getString("push-data"));
              //  Logger.json(pushDataobj.toString());

                switch (pushDataobj.getString("type")) {
                    case "friend-req-send":
                        showNotification(0, remoteMessage);
                        updateBadgeCount(Constant.KEY_FRIEND_REQ_BADGE_COUNT);
                        EventBus.getDefault().post(new LoadBadge(true));
                        break;
                    case "friend-req-accept":
                        showNotification(1, remoteMessage);
                        //updateBadgeCount(Constant.KEY_FRIEND_REQ_BADGE_COUNT);
                        break;
                    case "location-share":
                        showNotification(2, remoteMessage);
                        break;
                    case "trip-share":
                        showNotification(3, remoteMessage);
                        updateBadgeCount(Constant.KEY_TRIP_SHARING_BADGE_COUNT);
                        EventBus.getDefault().post(new LoadPendingTripobject(true));
                        EventBus.getDefault().post(new LoadBadge(true));
                        break;

                    case "trip-accept":
                        showNotification(4, remoteMessage);
                        //JSONObject tripobj = new JSONObject((Map) pushDataobj.getJSONObject("trip_info"));

                        if (pushDataobj.has("trip-info")) {
                            JSONObject tripObj = new JSONObject(pushDataobj.getString("trip-info"));
                            String tripId = tripObj.getString("server_id");
                            Timber.d(tripId);
                            //updateBadgeCount(tripId);
                            updateTripData(tripId);
                        }
                        //updateBadgeCount(Constant.KEY_TRIP_SHARING_BADGE_COUNT);
                        break;
                    case "trip-start":
                        showNotification(5, remoteMessage);
                        break;

                    case "trip-send":


                        JSONObject tripObj = new JSONObject(pushDataobj.getString("trip-info"));
                        String tripId = tripObj.getString("server_id");
                        String tripName = tripObj.getString("trip_name");

                        JSONObject userObj = new JSONObject(pushDataobj.getString("user"));

                        String userName = userObj.getString("username");

                        Log.e("pushmess", "username :"+ userName);


                        Timber.d(tripId);
                        //updateBadgeCount(tripId);

                        Utils.updateTripShareReceiveList(tripId,userName,tripName);

                        showNotification(4, remoteMessage);

                        break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateTripData(String id) {
        try {
            BasicTripDescription basicTripDescription = AppDatabase.getAppDatabase(getApplicationContext()).daoTripBasic().fetchSingleTripById(id);
            basicTripDescription.setFriendShared(basicTripDescription.getFriendShared() + 1);
            AppDatabase.getAppDatabase(getApplicationContext()).daoTripBasic().updateBasicTrip(basicTripDescription);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateBadgeCount(String key) {
        if (PreferenceManager.getInt(key) > 0) {
            PreferenceManager.updateValue(key, PreferenceManager.getInt(key) + 1);
        } else {
            PreferenceManager.updateValue(key, 1);
        }

    }

    private void showNotification(int flag, RemoteMessage remoteMessage) throws JSONException {
        setNotificationChannel(flag);

        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);

        JSONObject pushDataobj = new JSONObject(object.getString("push-data"));

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constant.KEY_IS_FROM_NOTIFICATION, true);
        bundle.putInt(Constant.KEY_FLAG, flag);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_new_push_notification)
                .setColor(getResources().getColor(android.R.color.transparent))
                .setContentTitle(pushDataobj.getString("title"))
                .setTicker(pushDataobj.getString("body"))
                .setWhen(System.currentTimeMillis())
                .setContentText(pushDataobj.getString("body"))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        /*Intent resultIntent = new Intent(this, HomeActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        builder.setContentIntent(resultPendingIntent)
        .setContentIntent(pendingIntent);*/

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    private void setNotificationChannel(int flag) {
        switch (flag) {

            case 0:
                channelId = "friend_req_send";
                channelName = "friend_req_send_name";
                break;
            case 1:
                channelId = "friend_req_accept";
                channelName = "friend_req_accept_name";
                break;
            case 2:
                channelId = "location_share";
                channelName = "location_share_name";
                break;
            case 3:
                channelId = "trip-share";
                channelName = "trip-share_name";
                break;
            case 4:
                channelId = "trip-accept";
                channelName = "trip-accept_name";
                break;
        }
    }
}

