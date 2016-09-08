package android.zeroh729.com.pcari.interactor.FirebaseInteractor;

import android.graphics.Point;
import android.support.annotation.NonNull;
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
import java.util.Map;

public class AnswerSurveySystemImpl implements AnswerSurveyPresenter.AnswerSurveySystem{
    private Survey survey;

    private int queryCount;
    private int completeCount = 0;
    boolean isAllSuccessful;
    private BasePresenter.Callback callback;

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
        this.callback = callback;
        DatabaseReference surveyRef = FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_RESPONSE).child(survey.getId());
        DatabaseReference responseRef = surveyRef.child(DbConstants.CHILD_RESPONSE_DETAILS).push();
        final DatabaseReference demographicRef = surveyRef.child(DbConstants.CHILD_DEMOGRAPHIC);
        final DatabaseReference qualitativeRef = surveyRef.child(DbConstants.CHILD_QUALITATIVE);
        final DatabaseReference quantitativeRef = surveyRef.child(DbConstants.CHILD_QUANTITATIVE);
        final DatabaseReference coordinatesRef = surveyRef.child(DbConstants.CHILD_COORDINATES);

        queryCount = response.getDemographicResponses().size() + response.getQualitativeResponses().size() + response.getQuantitativeResponses().size() + 1; //+1 for Coordinates
        completeCount = 0;
        isAllSuccessful = true;

        final String responseId = responseRef.getKey();
        DateFormat format = new SimpleDateFormat("MMMM dd, yyyy hh:mm:ss");
        responseRef.child(DbConstants.KEY_DATE_CREATED).setValue(format.format(new Date())).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                for(DemographicResponse r : response.getDemographicResponses()){
                    HashMap map = new HashMap();
                    map.put(DbConstants.KEY_ANSWER, r.getAnswer());
                    demographicRef.child(r.getQuestionId()).child(responseId).setValue(map).addOnCompleteListener(onCompleteListener);
                }

                int[] quanAnswers = new int[response.getQuantitativeResponses().size()];
                int i = 0;
                for(QuantitativeResponse r : response.getQuantitativeResponses()){
                    quanAnswers[i++] = r.getAnswer();
                    HashMap map = new HashMap();
                    map.put(DbConstants.KEY_ANSWER, r.getAnswer());
                    quantitativeRef.child(r.getQuestionId()).child(responseId).setValue(map).addOnCompleteListener(onCompleteListener);
                }

                Point point = AlgorithmUtil.calculatePCA(quanAnswers);
                HashMap map1 = new HashMap();
                map1.put(DbConstants.KEY_X, point.x);
                map1.put(DbConstants.KEY_Y, point.y);
                coordinatesRef.child(responseId).setValue(map1).addOnCompleteListener(onCompleteListener);

                for(QualitativeResponse r : response.getQualitativeResponses()){
                    HashMap map = new HashMap();
                    map.put(DbConstants.KEY_ANSWER, r.getAnswer());
                    qualitativeRef.child(r.getQuestionId()).child(responseId).setValue(map).addOnCompleteListener(onCompleteListener);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(0);
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
    public ArrayList<DemographicQuestion> getDemographicQuestions() {
        return survey.getDemographicQs();
    }

    @Override
    public ArrayList<QualitativeQuestion> getQualtitativeQuestions() {
        return survey.getQualitativeQs();
    }

    @Override
    public ArrayList<QuantitativeQuestion> getQuantitativeQuestions() {
        return survey.getQuantitativeQs();
    }
}
