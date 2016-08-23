package android.zeroh729.com.pcari.ui.activities;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.presenters.CreateSurveyPresenter;
import android.zeroh729.com.pcari.ui.base.BaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_create_survey)
public class CreateSurveyActivity extends BaseActivity implements CreateSurveyPresenter.CreateSurveySystem{
    @ViewById(R.id.et_surveyname)
    EditText et_surveyname;

    @ViewById(R.id.ll_demographicq)
    LinearLayout ll_demographicq;

    @ViewById(R.id.ll_qualitativeq)
    LinearLayout ll_qualitativeq;

    @ViewById(R.id.ll_quantitativeq)
    LinearLayout ll_quantitativeq;

    @Click(R.id.btn_add_demographicq)
    public void onClickAddDemographicQ(){

    }

    @Click(R.id.btn_add_qualitativeq)
    public void onClickAddQualitativeQ(){

    }

    @Click(R.id.btn_add_quantitativeq)
    public void onClickAddQuantitativeQ(){

    }

    @Click(R.id.btn_done)
    public void onClickDone(){

    }

    @Override
    public void addDemographicQ(DemographicQuestion q) {

    }

    @Override
    public void checkAddedDemographicQ(DemographicQuestion q, BasePresenter.Callback callback) {

    }

    @Override
    public ArrayList<DemographicQuestion> getDemographicQuestions() {
        return null;
    }

    @Override
    public void addQuantitativeQ(QuantitativeQuestion q) {

    }

    @Override
    public ArrayList<DemographicQuestion> getQualtitativeQuestions() {
        return null;
    }

    @Override
    public void addQualtitativeQ(QualitativeResponse q) {

    }

    @Override
    public void checkAddedQualtitativeQ(QualitativeResponse q, BasePresenter.Callback callback) {

    }

    @Override
    public ArrayList<DemographicQuestion> getQuantitativeQuestions() {
        return null;
    }

    @Override
    public void checkAddedQuantitativeQ(QuantitativeQuestion q, BasePresenter.Callback callback) {

    }

    @Override
    public void uploadSurvey(BasePresenter.Callback callback) {

    }

    @Override
    public void setSurveyName(String surveyName) {

    }
}
