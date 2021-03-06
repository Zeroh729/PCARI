package android.zeroh729.com.pcari.util.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.zeroh729.com.pcari.data.events.NetworkEvent;
import android.zeroh729.com.pcari.interactor.services.UploadService_;
import android.zeroh729.com.pcari.util.OttoBus;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.SystemService;

@EReceiver
public class NetworkChangeReceiver extends BroadcastReceiver {

    @SystemService
    ConnectivityManager connectivityManager;

    @Bean
    OttoBus bus;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        NetworkEvent event = new NetworkEvent();
        event.setConnected(isOnline());
        bus.post(event);
        context.startService(new Intent(context, UploadService_.class));
    }

    private boolean isOnline() {
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}