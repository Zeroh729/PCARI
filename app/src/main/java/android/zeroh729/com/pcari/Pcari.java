package android.zeroh729.com.pcari;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

import org.androidannotations.annotations.EApplication;

@EApplication
public class Pcari extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public Context getContext(){
        return context;
    }
}
