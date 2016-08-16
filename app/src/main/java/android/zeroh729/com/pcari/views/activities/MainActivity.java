package android.zeroh729.com.pcari.views.activities;

import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.views.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseActivity {

    @AfterViews
    public void afterviews(){

    }

    @OptionsItem(R.id.item_create_survey)
    public void onClickCreateSurvey(){
        CreateSurveyActivity_.intent(this).start();
    }

    @OptionsItem(R.id.item_manage_survey)
    public void onClickManageSurvey(){
        ManageSurveyActivity_.intent(this).start();
    }

    @OptionsItem(R.id.item_logout)
    public void onClickLogout(){

    }

}
