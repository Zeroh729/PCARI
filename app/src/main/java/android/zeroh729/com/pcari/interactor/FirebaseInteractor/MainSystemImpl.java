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

import java.util.ArrayList;

public class MainSystemImpl  implements MainPresenter.MainSystem{
    private FirebaseAuth.AuthStateListener listener;
    private ArrayList<Survey> surveys;

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
    public void fetchSurveyList(BasePresenter.Callback callback) {

    }

    @Override
    public ArrayList<Survey> getSurveys() {
        return surveys;
    }
}
