package com.animator_abhi.flyrobestore.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Nithin on 01/09/15.
 */
public class NetUtils {

    public static NetworkInfo getActiveNetwork(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //should check null because in air plan mode it will be null
        return cm.getActiveNetworkInfo();
    }
}
