package android.zeroh729.com.pcari.interactor.FirebaseInteractor;

import android.support.annotation.NonNull;
import android.zeroh729.com.pcari.data.model.Admin;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.presenters.MainPresenter;
import android.zeroh729.com.pcari.util._;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainSystemImpl  implements MainPresenter.MainSystem{
    private FirebaseAuth.AuthStateListener listener;
    private ArrayList<Survey> surveys;

    public MainSystemImpl() {
        surveys = new ArrayList<>();
    }

    @Override
    public void reloadUserData(final BasePresenter.Callback callback) {
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                callback.onSuccess();
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(listener);
    }

    @Override
    public boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    @Override
    public void login(String email, String password, final BasePresenter.Callback callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    callback.onSuccess();
                }else{
                    callback.onFail(0);
                    _.log(task.getException().toString());
                }
            }
        });
    }

    @Override
    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public Admin getUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Admin admin = new Admin();
        admin.setId(user.getUid());
        admin.setUsername(user.getDisplayName());
        admin.setEmail(user.getEmail());
        return admin;
    }

    @Override
    public void setup() {

    }

    @Override
    public void cleanup() {
        if(listener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(listener);
        }
    }

    @Override
    public Survey getSurvey(int index) {
        return surveys.get(index);
    }

    @Override
    public void fetchSurveyList(final BasePresenter.Callback callback) {
        FirebaseDatabase.getInstance().getReference().child(DbConstants.CHILD_SURVEY).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap map = (HashMap)dataSnapshot.getValue();
                String adminId = map.get(DbConstants.KEY_ADMIN_ID).toString();
                String name = map.get(DbConstants.KEY_SURVEY_NAME).toString();
                String details = map.get(DbConstants.KEY_SURVEY_DETAILS).toString();
                boolean isAvailable = (boolean)map.get(DbConstants.KEY_ISAVAILABLE);
                Survey survey = new Survey();
                survey.setId(dataSnapshot.getKey());
                survey.setName(name);
                survey.setAdminID(adminId);
                survey.setObjective(details);
                survey.setAvailable(isAvailable);
                surveys.add(survey);
                callback.onSuccess();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public ArrayList<Survey> getSurveys() {
        return surveys;
    }
}
