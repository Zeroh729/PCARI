package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_q_demographic)
public class QuestionDemographicRow extends LinearLayout {
    @ViewById(R.id.tv_question)
    TextView tv_question;

    @ViewById(R.id.et_value)
    EditText et_value;

    @ViewById(R.id.sp_value)
    Spinner sp_value;

    public QuestionDemographicRow(Context context) {
        super(context);
    }

    public QuestionDemographicRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuestionDemographicRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public QuestionDemographicRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(DemographicQuestion q){
        tv_question.setText(q.getQuestion());
        if(q.getInputType() == DemographicQuestion.InputType.NUMBER_FIELD){
            et_value.setVisibility(View.VISIBLE);
        }else if(q.getInputType() == DemographicQuestion.InputType.DROPDOWN){
            sp_value.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,q.getChoices());
            sp_value.setAdapter(adapter);
        }
    }

    public TextView getTv_question() {
        return tv_question;
    }

    public EditText getEt_value() {
        return et_value;
    }

    public Spinner getSp_value() {
        return sp_value;
    }
}
