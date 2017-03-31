package com.codepath.apps.tweeter.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import java.io.IOException;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by hkanekal on 3/30/2017.
 */

public class checknetwork {

    Context mContext;

    public checknetwork(Context mContext) {
        this.mContext = mContext;
    }
    @NonNull
    private static Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    private static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (InterruptedException | IOException e) { e.printStackTrace(); }
        return false;
    }
    public static boolean HaveCloud() {
        boolean IsNetworkAvailable = isNetworkAvailable();
        boolean Am_I_On_Line = isOnline();
        String NetworkErrorMessage="";
        if( !IsNetworkAvailable ) {
            NetworkErrorMessage = "Check for network: Please turn on WiFi.";
            //TimelineActivity.showToast(getContext(), NetworkErrorMessage);
        } else if (!Am_I_On_Line) {
            NetworkErrorMessage = "No access to cloud. Check for SSIDs";
            //TimelineActivity.showToast(getContext(), NetworkErrorMessage);
        }
        return (IsNetworkAvailable && Am_I_On_Line );
    }
}
