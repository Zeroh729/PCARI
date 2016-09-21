package android.zeroh729.com.pcari.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.events.NetworkEvent;
import android.zeroh729.com.pcari.interactor.services.UploadService;
import android.zeroh729.com.pcari.interactor.services.UploadService_;
import android.zeroh729.com.pcari.util.OttoBus;
import android.zeroh729.com.pcari.util.OttoBus_;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

@EActivity
public abstract class BaseActivity extends AppCompatActivity{

    @Bean
    protected OttoBus bus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus = OttoBus_.getInstance_(this);
        bus.register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(findViewById(R.id.toolbar) != null) {
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getSupportActionBar();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

}
