package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_add_q_demographic)
public class AddDemoqRow extends CardView {
    @ViewById(R.id.tv_question)
    public TextView tv_question;

    @ViewById(R.id.et_value)
    public EditText et_question;

    public AddDemoqRow(Context context) {
        super(context);
    }

    public AddDemoqRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddDemoqRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(DemographicQuestion q){
        tv_question.setText(q.getQuestion());
        if(q.getInputType() == DemographicQuestion.InputType.NUMBER_FIELD){
            et_question.setText("Number field");
        }else {
            String choices = "";
            for(int i = 0 ; i < q.getChoices().length; i++){
                if(i!=0){
                    choices += "\n";
                }
                choices += q.getChoices()[i];
            }
            et_question.setText(choices);
        }
    }
}
