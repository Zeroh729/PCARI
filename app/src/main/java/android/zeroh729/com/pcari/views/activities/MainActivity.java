package android.zeroh729.com.pcari.views.activities;

import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.views.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseActivity {

    @AfterViews
    public void afterviews(){
    }

    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().setTitle("List of Surveys");
    }
}
