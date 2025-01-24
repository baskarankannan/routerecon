package com.techacsent.route_recon.utills;

import android.os.AsyncTask;

import com.techacsent.route_recon.interfaces.OnCallbackActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;

public class FineElevation extends AsyncTask<Void, Void, Double> {

    private double latitude;
    private double longitude;
    private OnCallbackActivity onCallbackActivity;

    public FineElevation(double latitude, double longitude, OnCallbackActivity onCallbackActivity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.onCallbackActivity = onCallbackActivity;
    }

    @Override
    protected Double doInBackground(Void... voids) {
        double result= Double.NaN;
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        /*String url = "http://maps.googleapis.com/maps/api/elevation/"
                + "xml?locations=" + String.valueOf(latitude)
                + "," + String.valueOf(longitude)
                + "&sensor=true";*/

        String url = "https://api.mapbox.com/v4/mapbox.mapbox-terrain-v2/" +
                "tilequery/"+String.valueOf(longitude)+","+String.valueOf(latitude)+".json?&access_token=pk.eyJ1IjoiYWxpa3NpbyIsImEiOiJjamFua2ZkZ2kwODMzMnhtaTcxb2pnNTljIn0.bE31a4_QMYGc1QYlh7FNHg";
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                int r = -1;
                StringBuffer respStr = new StringBuffer();
                while ((r = instream.read()) != -1)
                    respStr.append((char) r);
                String tagOpen = "<elevation>";
                String tagClose = "</elevation>";
                if (respStr.indexOf(tagOpen) != -1) {
                    int start = respStr.indexOf(tagOpen) + tagOpen.length();
                    int end = respStr.indexOf(tagClose);
                    String value = respStr.substring(start, end);
                    result = (double) (Double.parseDouble(value) * 3.2808399); // convert from meters to feet
                }
                instream.close();
            }
        } catch (ClientProtocolException ignored) {
        } catch (IOException ignored) {
        }

        return result;
    }

    @Override
    protected void onPostExecute(Double elevation) {
        super.onPostExecute(elevation);
        onCallbackActivity.onTaskDone(elevation);
    }
}
