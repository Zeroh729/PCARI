package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_add_q_quantitative)
public class AddQuanqRow extends CardView{
    @ViewById(R.id.tv_question)
    TextView tv_question;

    @ViewById(R.id.et_value)
    EditText et_value;

    public AddQuanqRow(Context context) {
        super(context);
    }

    public AddQuanqRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddQuanqRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(QuantitativeQuestion q){
        tv_question.setText(q.getQuestion());
        String choices = "";
        for(int i = 0; i < q.getLabels().length; i++){
            if(i != 0){
                choices += "\n";
            }
            choices += q.getLabels()[i];
        }
        et_value.setText(choices);
    }
}
