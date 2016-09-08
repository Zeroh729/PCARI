package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_rate_question)
public class RateResponseRow extends LinearLayout{

    @ViewById(R.id.tv_question)
    TextView tv_question;

    @ViewById(R.id.tv_response)
    TextView tv_response;

    @ViewById(R.id.tv_questionForRating)
    TextView tv_questionForRating;

    @ViewById(R.id.rg_rating)
    RadioGroup rg_rating;

    public RateResponseRow(Context context) {
        super(context);
    }

    public RateResponseRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RateResponseRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RateResponseRow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void bind(QualitativeQuestion q, QualitativeResponse response){
        tv_question.setText(q.getQuestion());
        tv_questionForRating.setText(q.getQuestionForRating());
        tv_response.setText(response.getAnswer());

        for(int i = 0; i < q.getRatingRange(); i++){
            RadioButton rb_label = new RadioButton(getContext());
            rb_label.setText((i+1) + "");
            rb_label.setId(i+1);
            rg_rating.addView(rb_label);
        }
    }

    public RadioGroup getRg_rating() {
        return rg_rating;
    }
}
