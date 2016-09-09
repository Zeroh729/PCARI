package android.zeroh729.com.pcari.presenters;

import java.util.Collections;

public interface BasePresenter {
    int onStart = 1;
    int onStop = 4;

    void setup();
    void updateState();
    void setState(int state);

    interface SystemInterface{

    }

    interface ScreenInterface{

    }

    interface Callback{
        void onSuccess();
        void onFail(int errorCode);
    }
}
