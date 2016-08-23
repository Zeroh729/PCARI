package android.zeroh729.com.pcari.ui.fragments;

import android.support.annotation.Nullable;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.ui.base.BaseFragmentDetails;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_manage_survey_details)
public class ManageSurveyDetailsFragment extends BaseFragmentDetails<Survey> {
    @ViewById(R.id.tv_test)
    TextView tv_test;

    @AfterViews
    public void afterviews(){
        super.afterviews();
    }

    @Override
    public void bind(@Nullable Survey model) {
        if(model != null) {
            String tag = getTag();
            _.log("This details fragment is : " + tag);
            tv_test.setText(model.getName() + "");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _.log("Detaching: " + getTag());
    }
}
