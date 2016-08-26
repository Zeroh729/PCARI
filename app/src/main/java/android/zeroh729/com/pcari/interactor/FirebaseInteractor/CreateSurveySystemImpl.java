package android.zeroh729.com.pcari.interactor.FirebaseInteractor;

import android.support.annotation.NonNull;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.presenters.CreateSurveyPresenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateSurveySystemImpl implements CreateSurveyPresenter.CreateSurveySystem{
    private Survey survey = new Survey();
    private ArrayList<DemographicQuestion> demoQs = new ArrayList<>();
    private ArrayList<QualitativeQuestion> qualQs = new ArrayList<>();
    private ArrayList<QuantitativeQuestion> quanQs = new ArrayList<>();

    private int queryCount;
    private int completeCount = 0;
    private BasePresenter.Callback callback;

    public CreateSurveySystemImpl() {
        survey.setDemographicQs(demoQs);
        survey.setQualitativeQs(qualQs);
        survey.setQuantitativeQs(quanQs);
    }

    @Override
    public void addDemographicQ(DemographicQuestion q) {
        demoQs.add(q);
    }

    @Override
    public void addQuantitativeQ(QuantitativeQuestion q) {
        quanQs.add(q);
    }

    @Override
    public void addQualtitativeQ(QualitativeQuestion q) {
        qualQs.add(q);
    }

    @Override
    public ArrayList<DemographicQuestion> getDemographicQuestions() {
        return demoQs;
    }

    @Override
    public ArrayList<QualitativeQuestion> getQualtitativeQuestions() {
        return qualQs;
    }

    @Override
    public ArrayList<QuantitativeQuestion> getQuantitativeQuestions() {
        return quanQs;
    }

    boolean isAllSuccessful;

    @Override
    public void uploadSurvey(final BasePresenter.Callback callback) {
        this.callback = callback;
        HashMap survey = new HashMap();
        survey.put(DbConstants.KEY_ADMIN_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
        survey.put(DbConstants.KEY_ISAVAILABLE, true);
        final DatabaseReference surveyRef = FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_SURVEY).push();
        surveyRef.setValue(survey).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                isAllSuccessful = true;
                if(task.isSuccessful()) {
                    DatabaseReference questionRef = FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_QUESTION).child(surveyRef.getKey());
                    DatabaseReference demographicRef = questionRef.child(DbConstants.CHILD_DEMOGRAPHIC);
                    DatabaseReference qualitativeRef = questionRef.child(DbConstants.CHILD_QUALITATIVE);
                    DatabaseReference quantitativeRef = questionRef.child(DbConstants.CHILD_QUANTITATIVE);

                    ArrayList<HashMap> demoQuestions = new ArrayList<HashMap>();
                    ArrayList<HashMap> quanQuestions = new ArrayList<HashMap>();
                    ArrayList<HashMap> qualQuestions = new ArrayList<HashMap>();

                    for(int i = 0; i < demoQs.size(); i++){
                        DemographicQuestion q = demoQs.get(i);
                        HashMap map = new HashMap();
                        map.put(DbConstants.KEY_QUESTION, q.getQuestion());
                        map.put(DbConstants.KEY_INPUTTYPE, q.getInputType().toString());
                        map.put(DbConstants.KEY_ORDERNUM, (i+1));
                        if(q.getInputType() == DemographicQuestion.InputType.DROPDOWN) {
                            HashMap choices = new HashMap();
                            for(int j = 0; j < q.getChoices().length; j++){
                                choices.put((j+1) + "", q.getChoices()[j]);
                            }
                            map.put(DbConstants.KEY_CHOICES, choices);
                        }
                        demoQuestions.add(map);
                    }

                    for(int i = 0; i < quanQs.size(); i++){
                        QuantitativeQuestion q = quanQs.get(i);
                        HashMap map = new HashMap();
                        map.put(DbConstants.KEY_QUESTION, q.getQuestion());
                        map.put(DbConstants.KEY_ORDERNUM, (i+1));
                        HashMap labels = new HashMap();
                        for(int j = 0; j < q.getLabels().length; j++){
                            labels.put((j+1) + "", q.getLabels()[j]);
                        }
                        map.put(DbConstants.KEY_LABELS, labels);
                        quanQuestions.add(map);
                    }

                    for(int i = 0; i < qualQs.size(); i++){
                        QualitativeQuestion q = qualQs.get(i);
                        HashMap map = new HashMap();
                        map.put(DbConstants.KEY_QUESTION, q.getQuestion());
                        map.put(DbConstants.KEY_ORDERNUM, (i+1));
                        map.put(DbConstants.KEY_RATING_QUESTION, q.getQuestionForRating());
                        qualQuestions.add(map);
                    }


                    queryCount = demoQuestions.size() + quanQuestions.size() + qualQuestions.size();
                    completeCount = 0;

                    for(int i = 0 ; i < demoQuestions.size(); i++){
                        demographicRef.child((i+1)+"").setValue(demoQuestions.get(i)).addOnCompleteListener(onCompleteListener);
                    }

                    for(int i = 0 ; i < quanQuestions.size(); i++){
                        quantitativeRef.child((i+1)+"").setValue(quanQuestions.get(i)).addOnCompleteListener(onCompleteListener);
                    }

                    for(int i = 0 ; i < qualQuestions.size(); i++){
                        qualitativeRef.child((i+1)+"").setValue(qualQuestions.get(i)).addOnCompleteListener(onCompleteListener);
                    }
                }
            }
        });
    }

    private OnCompleteListener onCompleteListener = new OnCompleteListener() {
        @Override
        public void onComplete(@NonNull Task task) {
            completeCount++;
            if(!task.isSuccessful()){
                isAllSuccessful = false;
            }
            if(queryCount == completeCount){
                if(isAllSuccessful){
                    callback.onSuccess();
                }else{
                    callback.onFail(0);
                }
            }
        }
    };

    @Override
    public void setSurveyName(String surveyName) {
        survey.setName(surveyName);
    }
}
