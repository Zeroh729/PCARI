package android.zeroh729.com.pcari.views.activities;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.views.base.BaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_create_survey)
public class CreateSurveyActivity extends BaseActivity{
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
}
