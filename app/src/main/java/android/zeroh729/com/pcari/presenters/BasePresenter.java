package android.zeroh729.com.pcari.presenters;

public interface BasePresenter {
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
