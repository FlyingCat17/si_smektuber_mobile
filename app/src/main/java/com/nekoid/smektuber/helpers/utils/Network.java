package com.nekoid.smektuber.helpers.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

public class Network extends ConnectivityManager.NetworkCallback {
    private Context context;
    private Listener callback;

    /**
     * <p>Listen is Network is connected and disconnected</p>
     *
     * <p>Listen with callback</p>
     * */
    public Network(Context context, Listener callback) {
        this.context = context;
        this.callback = callback;
        startListening();
    }

    protected void startListening() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest networkRequest = new NetworkRequest.Builder().build();
        connectivityManager.registerNetworkCallback(networkRequest, this);
    }

    protected void stopListening() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(this);
    }

    @Override
    public void onAvailable(@NonNull android.net.Network network) {
        super.onAvailable(network);
        if (callback != null) {
            callback.onNetworkAvailable();
        }
    }

    @Override
    public void onLost(@NonNull android.net.Network network) {
        super.onLost(network);
        if (callback != null) {
            callback.onNetworkUnavailable();
        }
    }

    public interface Listener {
        void onNetworkAvailable();
        void onNetworkUnavailable();
    }
}
