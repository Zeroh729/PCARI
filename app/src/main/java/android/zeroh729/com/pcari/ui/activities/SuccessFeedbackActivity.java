package android.zeroh729.com.pcari.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.interactor.services.UploadService;
import android.zeroh729.com.pcari.interactor.services.UploadService_;
import android.zeroh729.com.pcari.ui.base.BaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

@EActivity(R.layout.activity_success_feedback)
public class SuccessFeedbackActivity extends BaseActivity {

    @Extra("survey")
    Survey survey;

    @Extra("response")
    SurveyResponse response;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, UploadService_.class));
    }

    @Click(R.id.btn_another_response)
    public void onClickAnotherResponse(){
        AnswerSurveyActivity_.intent(this).extra("survey", survey).start();
    }

    @Click(R.id.btn_home)
    public void onClickHome(){
        MainActivity_.intent(this).start();
    }
}
