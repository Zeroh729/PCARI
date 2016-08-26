package android.zeroh729.com.pcari.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Admin;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.data.model.response.QuantitativeResponse;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.presenters.CreateSurveyPresenter;
import android.zeroh729.com.pcari.presenters.MainPresenter;
import android.zeroh729.com.pcari.ui.adapters.AddDemoqListAdapter;
import android.zeroh729.com.pcari.ui.adapters.AddQualqListAdapter;
import android.zeroh729.com.pcari.ui.adapters.AddQuanqListAdapter;
import android.zeroh729.com.pcari.ui.base.BaseActivity;
import android.zeroh729.com.pcari.ui.dialogs.AddDemoqDialog;
import android.zeroh729.com.pcari.ui.dialogs.AddDemoqDialog_;
import android.zeroh729.com.pcari.ui.dialogs.AddQualqDialog;
import android.zeroh729.com.pcari.ui.dialogs.AddQualqDialog_;
import android.zeroh729.com.pcari.ui.dialogs.AddQuanqDialog;
import android.zeroh729.com.pcari.ui.dialogs.AddQuanqDialog_;
import android.zeroh729.com.pcari.ui.views.AddDemoqRow;
import android.zeroh729.com.pcari.ui.views.AddDemoqRow_;
import android.zeroh729.com.pcari.ui.views.AddQualqRow;
import android.zeroh729.com.pcari.ui.views.AddQualqRow_;
import android.zeroh729.com.pcari.ui.views.AddQuanqRow;
import android.zeroh729.com.pcari.ui.views.AddQuanqRow_;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;

@EActivity(R.layout.activity_create_survey)
public class CreateSurveyActivity extends BaseActivity implements CreateSurveyPresenter.CreateSurveyScreen{
    CreateSurveyPresenter presenter;

    @ViewById(R.id.et_surveyname)
    EditText et_surveyname;

    @ViewById(R.id.ll_demographicq)
    LinearLayout ll_demographicq;

    @ViewById(R.id.ll_qualitativeq)
    LinearLayout ll_qualitativeq;

    @ViewById(R.id.ll_quantitativeq)
    LinearLayout ll_quantitativeq;

    @ViewById(R.id.btn_add_quantitativeq)
    Button btn_add_quanq;

    @ViewById(R.id.btn_add_qualitativeq)
    Button btn_add_qualq;

    @ViewById(R.id.btn_add_demographicq)
    Button btn_add_demoq;

    @ViewById(R.id.btn_done)
    Button btn_done;

//    @Bean
//    AddDemoqListAdapter addDemoqListAdapter;
//
//    @Bean
//    AddQuanqListAdapter addQuanqListAdapter;
//
//    @Bean
//    AddQualqListAdapter addQualqListAdapter;

    HashMap<DemographicQuestion, AddDemoqRow> demoRowViews;
    HashMap<QuantitativeQuestion, AddQuanqRow> quanRowViews;
    HashMap<QualitativeQuestion, AddQualqRow> qualRowViews;

    private AddDemoqDialog addDemoqDialog;
    private AddQualqDialog addQualqDialog;
    private AddQuanqDialog addQuanqDialog;

    private final String fragDemoTag = "AddDemoFrag";
    private final String fragQuanTag = "AddQuanFrag";
    private final String fragQualTag = "AddQualFrag";

    private final String demoKey = "demoKey";
    private final String quanKey = "quanKey";
    private final String qualKey = "qualKey";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(presenter == null)
            presenter = new CreateSurveyPresenter(this);

        if(savedInstanceState != null){
            ArrayList<DemographicQuestion> demoQs = savedInstanceState.getParcelableArrayList(demoKey);
            ArrayList<QuantitativeQuestion> quanQs = savedInstanceState.getParcelableArrayList(quanKey);
            ArrayList<QualitativeQuestion> qualQs = savedInstanceState.getParcelableArrayList(qualKey);

            for(DemographicQuestion q : demoQs){
                presenter.getDemoQuestions().add(q);
            }

            for(QuantitativeQuestion q : quanQs){
                presenter.getQuanQuestions().add(q);
            }

            for(QualitativeQuestion q : qualQs){
                presenter.getQualQuestions().add(q);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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

        addDemoqDialog = (AddDemoqDialog)getSupportFragmentManager().findFragmentByTag(fragDemoTag);
        if(addDemoqDialog == null)
            addDemoqDialog = new AddDemoqDialog_.FragmentBuilder_().build();
        addDemoqDialog.setListener(new ClickListener() {
            @Override
            public void onClick() {
                DemographicQuestion q = new DemographicQuestion();

                String question = addDemoqDialog.getEt_question().getText().toString();
                boolean isDropdown = addDemoqDialog.getRb_choices().isChecked();
                String[] choicesArr;
                DemographicQuestion.InputType inputType;
                if(isDropdown){
                    String choices = addDemoqDialog.getEt_choices().getText().toString();
                    choicesArr = choices.split("\n");
                    q.setChoices(choicesArr);
                    inputType = DemographicQuestion.InputType.DROPDOWN;
                }else{
                    inputType = DemographicQuestion.InputType.NUMBER_FIELD;
                }

                q.setQuestion(question);
                q.setInputType(inputType);
                presenter.onSubmitDemographicQ(q);
            }
        });
        addQuanqDialog = (AddQuanqDialog) getSupportFragmentManager().findFragmentByTag(fragQuanTag);
        if(addQuanqDialog == null)
            addQuanqDialog = new AddQuanqDialog_.FragmentBuilder_().build();
        addQuanqDialog.setListener(new ClickListener() {
            @Override
            public void onClick() {
                QuantitativeQuestion q = new QuantitativeQuestion();

                String question = addQuanqDialog.getEt_question().getText().toString();
                String[] ratingLabels = addQuanqDialog.getEt_ratingLabels().getText().toString().split("\n");
                q.setQuestion(question);
                q.setLabels(ratingLabels);
                presenter.onSubmitQuantitativeQ(q);
            }
        });
        addQualqDialog = (AddQualqDialog) getSupportFragmentManager().findFragmentByTag(fragQualTag);
        if(addQualqDialog == null)
            addQualqDialog = new AddQualqDialog_.FragmentBuilder_().build();
        addQualqDialog.setListener(new ClickListener() {
            @Override
            public void onClick() {
                QualitativeQuestion q = new QualitativeQuestion();

                String question = addQualqDialog.getEt_question().getText().toString();
                String questionForRating = addQualqDialog.getEt_questionForRating().getText().toString();
                q.setQuestion(question);
                q.setQuestionForRating(questionForRating);
                presenter.onSubmitQualtitativeQ(q);
            }
        });
    }

    @Click(R.id.btn_add_demographicq)
    public void onClickAddDemographicQ(){
        presenter.onClickAddDemographicQ();
    }

    @Click(R.id.btn_add_qualitativeq)
    public void onClickAddQualitativeQ(){
        presenter.onClickAddQualitativeQ();
    }

    @Click(R.id.btn_add_quantitativeq)
    public void onClickAddQuantitativeQ(){
        presenter.onClickAddQuantitativeQ();
    }

    @Click(R.id.btn_done)
    public void onClickDone(){
//        dummyOnClickDone();
        presenter.onClickSubmitSurvey(et_surveyname.getText().toString());
    }

    @Override
    public void showAddDemographicForm() {
        addDemoqDialog.show(getSupportFragmentManager(), fragDemoTag);
    }

    @Override
    public void showAddQuantitativeForm() {
        addQuanqDialog.show(getSupportFragmentManager(), fragQuanTag);
    }

    @Override
    public void showAddQualitativeForm() {
        addQualqDialog.show(getSupportFragmentManager(), fragQualTag);
    }

    @Override
    public void updateDemoList(ArrayList<DemographicQuestion> demographicQuestions) {
        ll_demographicq.removeAllViews();
        for(DemographicQuestion q : demographicQuestions){
            if(!demoRowViews.containsKey(q)){
                AddDemoqRow row = AddDemoqRow_.build(this);
                row.bind(q);
                demoRowViews.put(q,row);
            }
            ll_demographicq.addView(demoRowViews.get(q));
        }
    }

    @Override
    public void updateQuanList(ArrayList<QuantitativeQuestion> quantitativeQuestions) {
        ll_quantitativeq.removeAllViews();
        for(QuantitativeQuestion q : quantitativeQuestions){
            if(!quanRowViews.containsKey(q)){
                AddQuanqRow row = AddQuanqRow_.build(this);
                row.bind(q);
                quanRowViews.put(q,row);
            }
            ll_quantitativeq.addView(quanRowViews.get(q));
        }
    }

    @Override
    public void updateQualList(ArrayList<QualitativeQuestion> qualitativeQuestions) {
        ll_qualitativeq.removeAllViews();
        for(QualitativeQuestion q : qualitativeQuestions){
            if(!qualRowViews.containsKey(q)){
                AddQualqRow row = AddQualqRow_.build(this);
                row.bind(q);
                qualRowViews.put(q,row);
            }
            ll_qualitativeq.addView(qualRowViews.get(q));
        }
    }

    @Override
    public void showLoadingUploadSurvey() {
        btn_add_demoq.setEnabled(false);
        btn_add_qualq.setEnabled(false);
        btn_add_quanq.setEnabled(false);
        btn_done.setEnabled(false);
    }

    @Override
    public void hideLoadingUploadSurvey() {
        btn_add_demoq.setEnabled(true);
        btn_add_qualq.setEnabled(true);
        btn_add_quanq.setEnabled(true);
        btn_done.setEnabled(true);
    }

    @Override
    public void navigateToManageMySurveys() {
        ManageSurveyActivity_.intent(this).start();
    }

    @Override
    public void showError(String s) {
        _.showToast(s);
    }

    private void dummyOnClickDone(){
        //TEST STARTS
        DemographicQuestion q = new DemographicQuestion();
        q.setQuestion("Where do broken hearts go?");
        q.setInputType(DemographicQuestion.InputType.DROPDOWN);
        q.setChoices(new String[]{"Good", "Great", "Nah"});
        presenter.onSubmitDemographicQ(q);

        DemographicQuestion q2 = new DemographicQuestion();
        q2.setQuestion("Age mo?");
        q2.setInputType(DemographicQuestion.InputType.NUMBER_FIELD);
        presenter.onSubmitDemographicQ(q2);

        QualitativeQuestion q3 = new QualitativeQuestion();
        q3.setQuestion("How can we change the ph?");
        q3.setQuestionForRating("Is this idea innovative?");
        presenter.onSubmitQualtitativeQ(q3);

        QuantitativeQuestion q4 = new QuantitativeQuestion();
        q4.setQuestion("Funny");
        q4.setLabels(new String[]{"Yes", "Mej", "No"});
        presenter.onSubmitQuantitativeQ(q4);

        QuantitativeQuestion q5 = new QuantitativeQuestion();
        q5.setQuestion("Dramativ");
        q5.setLabels(new String[]{"Not really", "Maybe", "No"});
        presenter.onSubmitQuantitativeQ(q5);

        QuantitativeQuestion q6 = new QuantitativeQuestion();
        q6.setQuestion("Boring");
        q6.setLabels(new String[]{"Schwoop", "Stop it", "Hate", "Fuck"});
        presenter.onSubmitQuantitativeQ(q6);

        QuantitativeQuestion q7 = new QuantitativeQuestion();
        q7.setQuestion("Tamad");
        q7.setLabels(new String[]{"Day", "To", "Remember"});
        presenter.onSubmitQuantitativeQ(q7);

        QuantitativeQuestion q8 = new QuantitativeQuestion();
        q8.setQuestion("Tigang");
        q8.setLabels(new String[]{"Where", "Do", "I", "Fit", "In"});
        presenter.onSubmitQuantitativeQ(q8);

        QuantitativeQuestion q9 = new QuantitativeQuestion();
        q9.setQuestion("Gudetama");
        q9.setLabels(new String[]{"Not really", "Maybe", "No"});
        presenter.onSubmitQuantitativeQ(q9);
        //TEST STOPS
    }

    public interface ClickListener{
        void onClick();
    }
}
