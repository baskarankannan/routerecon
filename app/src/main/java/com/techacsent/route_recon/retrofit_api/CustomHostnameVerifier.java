package com.techacsent.route_recon.retrofit_api;

import com.techacsent.route_recon.BuildConfig;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class CustomHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return hostname.equals("devapi.routerecon.com");
    }
}
