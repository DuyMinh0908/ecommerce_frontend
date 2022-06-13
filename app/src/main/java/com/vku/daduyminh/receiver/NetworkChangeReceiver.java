package com.vku.daduyminh.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import static com.vku.daduyminh.utils.InternetUtils.isNetworkConnected;
import com.vku.daduyminh.utils.OnNetworkListener;

public class NetworkChangeReceiver extends BroadcastReceiver {

    OnNetworkListener onNetworkListener;

    public void setOnNetworkListener(OnNetworkListener onNetworkListener) {
        this.onNetworkListener = onNetworkListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isNetworkConnected(context)) {
            onNetworkListener.onNetworkDisconnected();
        } else {
            onNetworkListener.onNetworkConnected();
        }
    }
}
