package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.QuantitativeResponse;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_q_quantitative)
public class QuestionQuantitativeRow extends LinearLayout {
    @ViewById(R.id.tv_question)
    TextView tv_question;

    @ViewById(R.id.rg_labels)
    RadioGroup rg_labels;

    public QuestionQuantitativeRow(Context context) {
        super(context);
    }

    public QuestionQuantitativeRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuestionQuantitativeRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public QuestionQuantitativeRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(QuantitativeQuestion q){
        tv_question.setText(q.getQuestion());
        for(int i = 0; i < q.getLabels().length; i++){
            String label = q.getLabels()[i];
            RadioButton rb_label = new RadioButton(getContext());
            rb_label.setId(i);
            rb_label.setText(label);
            rg_labels.addView(rb_label);
        }
    }

    public TextView getTv_question() {
        return tv_question;
    }

    public RadioGroup getRg_labels() {
        return rg_labels;
    }
}
