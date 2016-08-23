package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_survey_manage)
public class ManageSurveyRow extends LinearLayout{
    @ViewById(R.id.tv_suveryname)
    TextView tv_name;

    @ViewById(R.id.tv_suverydetails)
    TextView tv_details;


    public ManageSurveyRow(Context context) {
        super(context);
    }

    public void bind(Survey survey){
        tv_name.setText(survey.getName());
        tv_details.setText(survey.getObjective());
    }
}
