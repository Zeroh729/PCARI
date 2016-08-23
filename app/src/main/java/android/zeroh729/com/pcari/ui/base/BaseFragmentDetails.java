package android.zeroh729.com.pcari.ui.base;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment
public abstract class BaseFragmentDetails<T extends Parcelable> extends Fragment {

    @AfterViews
    public void afterviews(){
        try {
            T data = ((BaseActivityDetailsList<T>) getActivity()).getSelectedData();
            if(data == null && getArguments() != null){
                data = getArguments().getParcelable("survey");
            }
            if(data != null) {
                bind(data);
            }
        }catch (ClassCastException e){
            _.logError(getClass().getName() + " is under an Activity that does not extend from" + BaseActivityDetailsList.class.getName());
        }
    }

    abstract public void bind(@Nullable T data);
}
