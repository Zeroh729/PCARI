package android.zeroh729.com.pcari.interactor.firebaseInteractor;

import android.graphics.Point;
import android.support.annotation.NonNull;
import android.zeroh729.com.pcari.Pcari;
import android.zeroh729.com.pcari.Pcari_;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.DemographicResponse;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.data.model.response.QuantitativeResponse;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.presenters.AnswerSurveyPresenter;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.util.AlgorithmUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AnswerSurveySystemImpl implements AnswerSurveyPresenter.AnswerSurveySystem{
    private Survey survey;

    private Realm realm;
    private RealmConfiguration realmConfig;

    public AnswerSurveySystemImpl() {
        realmConfig = new RealmConfiguration.Builder(Pcari_.getInstance()).build();
        realm = Realm.getInstance(realmConfig);
    }

    @Override
    public void loadSurveyQuestions(final FetchCallback fetchCallback) {
        FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_QUESTION).child(survey.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HashMap> demoQs = (ArrayList<HashMap>)dataSnapshot.child(DbConstants.CHILD_DEMOGRAPHIC).getValue();
                ArrayList<HashMap> quanQs = (ArrayList<HashMap>)dataSnapshot.child(DbConstants.CHILD_QUANTITATIVE).getValue();
                ArrayList<HashMap> qualQs = (ArrayList<HashMap>)dataSnapshot.child(DbConstants.CHILD_QUALITATIVE).getValue();

                for(int i = 0; i < demoQs.size(); i++){
                    HashMap data = demoQs.get(i);
                    DemographicQuestion q = new DemographicQuestion();
                    String id = i + "";
                    int orderNum = (int)(long)data.get(DbConstants.KEY_ORDERNUM);
                    String question = (String)data.get(DbConstants.KEY_QUESTION);
                    DemographicQuestion.InputType inputType = DemographicQuestion.InputType.valueOf((String)data.get(DbConstants.KEY_INPUTTYPE));

                    if(inputType == DemographicQuestion.InputType.DROPDOWN){
                        ArrayList<String> data1 = (ArrayList<String>) data.get(DbConstants.KEY_CHOICES);
                        String[] choices = data1.toArray(new String[data1.size()]);

                        q.setChoices(choices);
                    }

                    q.setId(id);
                    q.setOrderNumber(orderNum);
                    q.setQuestion(question);
                    q.setInputType(inputType);
                    survey.getDemographicQs().add(q);
                }

                for(int i = 0; i < quanQs.size(); i++){
                    HashMap data = quanQs.get(i);
                    QuantitativeQuestion q = new QuantitativeQuestion();
                    String id = i + "";
                    int orderNum = (int)(long)data.get(DbConstants.KEY_ORDERNUM);

                    String question = (String)data.get(DbConstants.KEY_QUESTION);

                    ArrayList<String> data1 = (ArrayList)data.get(DbConstants.KEY_LABELS);
                    String[] labels = data1.toArray(new String[data1.size()]);

                    q.setId(id);
                    q.setOrderNumber(orderNum);
                    q.setQuestion(question);
                    q.setLabels(labels);

                    survey.getQuantitativeQs().add(q);
                }

                for(int i = 0; i < qualQs.size(); i++){
                    HashMap data = qualQs.get(i);
                    QualitativeQuestion q = new QualitativeQuestion();
                    String id = i + "";
                    int orderNum = (int)(long)data.get(DbConstants.KEY_ORDERNUM);

                    String question = (String)data.get(DbConstants.KEY_QUESTION);
                    String questionForRating = (String)data.get(DbConstants.KEY_RATING_QUESTION);
                    int ratingRange = (int)(long) data.get(DbConstants.KEY_RATING_RANGE);

                    q.setId(id);
                    q.setOrderNumber(orderNum);
                    q.setQuestion(question);
                    q.setQuestionForRating(questionForRating);
                    q.setRatingRange(ratingRange);

                    survey.getQualitativeQs().add(q);
                }

                fetchCallback.onSuccess(survey);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fetchCallback.onFail(0);
            }
        });
    }

    @Override
    public Survey getSurvey() {
        return survey;
    }

    @Override
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public void uploadsResponse(final SurveyResponse response, final BasePresenter.Callback callback) {
        DatabaseReference surveyRef = FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_RESPONSE).child(survey.getId());
        DatabaseReference responseRef = surveyRef.child(DbConstants.CHILD_RESPONSE_DETAILS).push();

        final String responseId = responseRef.getKey();
        DateFormat format = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss");
        response.setId(responseId);
        response.setDateCreated(format.format(new Date()));
        response.setSurveyId(survey.getId());
        response.setFinishedUploading(false);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(response.getDemographicResponses());
                realm.copyToRealm(response.getQuantitativeResponses());
                realm.copyToRealm(response.getQualitativeResponses());
                realm.copyToRealm(response);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                callback.onFail(0);
            }
        });
    }

    @Override
    public void setDateCreated(SurveyResponse response) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy hh:mm a");
        response.setDateCreated(format.format(new Date()));
    }
}
