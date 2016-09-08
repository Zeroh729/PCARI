package android.zeroh729.com.pcari.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.DemographicResponse;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.data.model.response.QuantitativeResponse;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.presenters.AnswerSurveyPresenter;
import android.zeroh729.com.pcari.ui.base.BaseActivity;
import android.zeroh729.com.pcari.ui.views.QuestionDemographicRow;
import android.zeroh729.com.pcari.ui.views.QuestionDemographicRow_;
import android.zeroh729.com.pcari.ui.views.QuestionQualitativeRow;
import android.zeroh729.com.pcari.ui.views.QuestionQualitativeRow_;
import android.zeroh729.com.pcari.ui.views.QuestionQuantitativeRow;
import android.zeroh729.com.pcari.ui.views.QuestionQuantitativeRow_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.activity_answer_survey)
public class AnswerSurveyActivity extends BaseActivity implements AnswerSurveyPresenter.AnswerSurveyScreen{
    private AnswerSurveyPresenter presenter;

    @Extra("survey")
    Survey survey;

    @ViewById(R.id.ll_demographicq)
    LinearLayout ll_demographicq;

    @ViewById(R.id.ll_qualitativeq)
    LinearLayout ll_qualitativeq;

    @ViewById(R.id.ll_quantitativeq)
    LinearLayout ll_quantitativeq;

    @ViewById(R.id.btn_done)
    Button btn_done;

    HashMap<DemographicQuestion, QuestionDemographicRow> demoRowViews;
    HashMap<QuantitativeQuestion, QuestionQuantitativeRow> quanRowViews;
    HashMap<QualitativeQuestion, QuestionQualitativeRow> qualRowViews;

    private final String surveyKey = "surveyKey";
    private final String demoKey = "demoKey";
    private final String quanKey = "quanKey";
    private final String qualKey = "qualKey";

    private String responseId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(presenter == null)
            presenter = new AnswerSurveyPresenter(this, survey);

        if(savedInstanceState != null){
            survey = savedInstanceState.getParcelable(surveyKey);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(surveyKey, survey);
        outState.putParcelableArrayList(demoKey, presenter.getDemoQuestions());
        outState.putParcelableArrayList(qualKey, presenter.getQualQuestions());
        outState.putParcelableArrayList(quanKey, presenter.getQuanQuestions());
    }


    @AfterViews
    public void afterviews(){
        demoRowViews = new HashMap<>();
        quanRowViews = new HashMap<>();
        qualRowViews= new HashMap<>();

        presenter.setup();
    }

    @Click(R.id.btn_done)
    public void onClickDone(){
        SurveyResponse response = new SurveyResponse();

        for (Map.Entry<DemographicQuestion, QuestionDemographicRow> entry : demoRowViews.entrySet()){
            QuestionDemographicRow rowView = entry.getValue();
            DemographicQuestion question = entry.getKey();

            DemographicResponse r = new DemographicResponse();
            r.setQuestionId(question.getId());
            if(rowView.getEt_value().getVisibility() == View.VISIBLE)
                r.setAnswer(entry.getValue().getEt_value().getText().toString());
            else if(rowView.getSp_value().getVisibility() == View.VISIBLE){
                r.setAnswer(entry.getValue().getSp_value().getSelectedItem().toString());
            }
            response.getDemographicResponses().add(r);
        }

        for (Map.Entry<QuantitativeQuestion, QuestionQuantitativeRow> entry : quanRowViews.entrySet()){
            QuestionQuantitativeRow rowView = entry.getValue();
            QuantitativeQuestion question = entry.getKey();

            QuantitativeResponse r = new QuantitativeResponse();
            r.setQuestionId(question.getId());
            r.setAnswer(rowView.getRg_labels().getCheckedRadioButtonId());

            response.getQuantitativeResponses().add(r);
        }

        for (Map.Entry<QualitativeQuestion, QuestionQualitativeRow> entry : qualRowViews.entrySet()){
            QuestionQualitativeRow rowView = entry.getValue();
            QualitativeQuestion question = entry.getKey();

            QualitativeResponse r = new QualitativeResponse();
            r.setQuestionId(question.getId());
            r.setAnswer(rowView.getEt_value().getText().toString());

            response.getQualitativeResponses().add(r);
        }

        presenter.onClickSubmit(response);
    }

    @Override
    public void displaySurveyDetails(Survey survey) {
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(survey.getName());
    }

    @Override
    public void displayDemographicQs(ArrayList<DemographicQuestion> demographicQs) {
        for(DemographicQuestion q : demographicQs){
            QuestionDemographicRow row = QuestionDemographicRow_.build(this);
            row.bind(q);
            demoRowViews.put(q, row);
            ll_demographicq.addView(row);
        }
    }

    @Override
    public void displayQuantitativeQs(ArrayList<QuantitativeQuestion> quantitativeQs) {
        for(QuantitativeQuestion q : quantitativeQs){
            QuestionQuantitativeRow row = QuestionQuantitativeRow_.build(this);
            row.bind(q);
            quanRowViews.put(q, row);
            ll_quantitativeq.addView(row);
        }
    }

    @Override
    public void displayQualitativeQs(ArrayList<QualitativeQuestion> qualitativeQs) {
        for(QualitativeQuestion q : qualitativeQs){
            QuestionQualitativeRow row = QuestionQualitativeRow_.build(this);
            row.bind(q);
            qualRowViews.put(q, row);
            ll_qualitativeq.addView(row);
        }
    }

    @Override
    public void navigateToRateResponseScreen() {
        RateResponsesActivity_.intent(this).extra("survey", survey).extra("responseId",responseId).start();
    }

    @Override
    public void hideLoadingIndicator() {
        btn_done.setEnabled(true);
    }

    @Override
    public void showLoadingIndicator() {
        btn_done.setEnabled(false);
    }
}
