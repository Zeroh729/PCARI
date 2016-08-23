package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_survey_public)
public class PublicSurveyRow extends CardView{
    @ViewById(R.id.parent_view)
    ViewGroup parent_view;

    @ViewById(R.id.tv_suveryname)
    TextView tv_name;

    @ViewById(R.id.tv_suverydetails)
    TextView tv_details;

    public PublicSurveyRow(Context context) {
        super(context);
    }

    public void bind(Survey survey){
        tv_name.setText(survey.getName());
        tv_details.setText(survey.getObjective());
    }
}
