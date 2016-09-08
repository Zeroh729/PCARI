package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;


@EViewGroup(R.layout.row_add_q_qualitative)
public class AddQualqRow extends CardView{
    @ViewById(R.id.tv_question)
    TextView tv_question;

    @ViewById(R.id.tv_ratequestion)
    TextView tv_questionForRating;

    @ViewById(R.id.tv_rateRange)
    TextView tv_ratingRange;

    public AddQualqRow(Context context) {
        super(context);
    }

    public AddQualqRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddQualqRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(QualitativeQuestion q){
        tv_question.setText(q.getQuestion());
        tv_questionForRating.setText(q.getQuestionForRating());
        String label = getResources().getString(R.string.add_qualq_label_ratingrange) + " " + q.getRatingRange();
        tv_ratingRange.setText(label);
    }
}
