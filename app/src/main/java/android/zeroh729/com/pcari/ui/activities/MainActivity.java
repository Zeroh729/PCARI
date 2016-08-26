package android.zeroh729.com.pcari.ui.activities;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Admin;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.presenters.MainPresenter;
import android.zeroh729.com.pcari.ui.adapters.PublicSurveyListAdapter;
import android.zeroh729.com.pcari.ui.base.BaseActivity;
import android.zeroh729.com.pcari.ui.base.BaseAdapterRecyclerView;
import android.zeroh729.com.pcari.ui.dialogs.LoginDialog;
import android.zeroh729.com.pcari.ui.dialogs.LoginDialog_;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends BaseActivity implements MainPresenter.MainScreen, BaseAdapterRecyclerView.ClickListener{
    MainPresenter presenter;

    @ViewById(R.id.rv_surveys)
    RecyclerView rv_surveys;

    @Bean
    PublicSurveyListAdapter adapter;

    LoginDialog loginDialog;

    @AfterViews
    public void afterviews(){
        presenter = new MainPresenter(this);
        adapter.setClickListener(this);
        rv_surveys.setLayoutManager(new LinearLayoutManager(this));
        rv_surveys.setAdapter(adapter);
    }

    @OptionsItem(R.id.item_create_survey)
    public void onClickCreateSurvey(){
        presenter.onClickCreateSurvey();
    }

    @OptionsItem(R.id.item_manage_survey)
    public void onClickManageSurvey(){
        presenter.onClickManageSurvey();
    }

    @OptionsItem(R.id.item_logout)
    public void onClickLogout(){
        presenter.onClickLogout();
    }

    @Override
    public void onClick(int index) {
        presenter.onClickSurveyItem(index);
    }

    @Override
    public void showLoginPrompt() {
        loginDialog = new LoginDialog_.FragmentBuilder_().build();
        loginDialog.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickLogin(loginDialog.getEt_username().getText().toString(), loginDialog.getEt_password().getText().toString());
            }
        });
        loginDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showSuccessLogin() {
        _.showToast("Login success!");
    }

    @Override
    public void hideLoginPrompt() {

    }

    @Override
    public void showError(String errorMessage) {
        _.showToast(errorMessage);
    }

    @Override
    public void navigateToCreateSurveyScreen() {
        CreateSurveyActivity_.intent(this).start();
    }

    @Override
    public void navigateToManageSurveyScreen() {
        ManageSurveyActivity_.intent(this).start();
    }

    @Override
    public void updateUserView(Admin user) {

    }

    @Override
    public void navigateToAnswerSurveyScreen(Survey survey) {
        AnswerSurveyActivity_.intent(this).extra("survey", survey).start();
    }

    @Override
    public void updateSurveyList(ArrayList<Survey> surveys) {
        adapter.setItems(surveys);
        adapter.notifyDataSetChanged();
    }
}
