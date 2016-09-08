package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_q_qualitative)
public class QuestionQualitativeRow extends LinearLayout {
    @ViewById(R.id.tv_question)
    TextView tv_question;

    @ViewById(R.id.et_value)
    EditText et_value;

    public QuestionQualitativeRow(Context context) {
        super(context);
    }

    public QuestionQualitativeRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QuestionQualitativeRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public QuestionQualitativeRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(QualitativeQuestion q){
        tv_question.setText(q.getQuestion());
    }

    public TextView getTv_question() {
        return tv_question;
    }

    public EditText getEt_value() {
        return et_value;
    }
}
