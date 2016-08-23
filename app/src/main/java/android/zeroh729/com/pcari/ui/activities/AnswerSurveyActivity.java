package android.zeroh729.com.pcari.ui.activities;

import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.presenters.AnswerSurveyPresenter;
import android.zeroh729.com.pcari.ui.base.BaseActivity;

import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

@EActivity(R.layout.activity_answer_survey)
public class AnswerSurveyActivity extends BaseActivity implements AnswerSurveyPresenter.AnswerSurveyScreen{

    @Override
    public void displaySurveyDetails(Survey survey) {

    }

    @Override
    public void displayDemographicQs(ArrayList<DemographicQuestion> demographicQs) {

    }

    @Override
    public void displayQuantitativeQs(ArrayList<QuantitativeQuestion> quantitativeQs) {

    }

    @Override
    public void displayQualitativeQs(ArrayList<QualitativeQuestion> qualitativeQs) {

    }

    @Override
    public void navigateToRateResponseScreen() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void showLoadingIndicator() {

    }
}
