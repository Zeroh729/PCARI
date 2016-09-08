package android.zeroh729.com.pcari.ui.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Coordinates;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.data.model.response.QualitativeResponse;
import android.zeroh729.com.pcari.presenters.BasePresenter;
import android.zeroh729.com.pcari.ui.base.BaseActivityDetailsList;
import android.zeroh729.com.pcari.ui.base.BaseFragmentDetails;
import android.zeroh729.com.pcari.ui.views.RateResponseRow;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment(R.layout.fragment_rate_response)
public class RateQuestionFragment extends BaseFragmentDetails<Coordinates>{
    private ClickListener listener;

    @ViewById(R.id.tv_label_rateresponse)
    TextView tv_label_rateresponse;

    @ViewById(R.id.ll_rateresponse)
    LinearLayout ll_rateresponse;

    @ViewById(R.id.btn_done)
    Button btn_done;

    String labelText = "";
    ArrayList<RateResponseRow> rateRowViews;

    @AfterViews
    public void afterviews(){
        super.afterviews();
        tv_label_rateresponse.setText(labelText);
        if(rateRowViews != null){
            ll_rateresponse.removeAllViews();
            for(RateResponseRow row : rateRowViews){
                ll_rateresponse.addView(row);
            }
        }
    }

    @Override
    public void bind(@Nullable Coordinates data) {
        if(data != null){
            tv_label_rateresponse.setText("");
            btn_done.setVisibility(View.VISIBLE);
        }else{
            ll_rateresponse.removeAllViews();
            btn_done.setVisibility(View.GONE);
        }
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }

    @Click(R.id.btn_done)
    public void onClickDone(){
        if(listener != null) {
            listener.onClick();
        }
    }

    public void setResponses(ArrayList<QualitativeResponse> usersResponse){

    }

    public void setRowViews(ArrayList<RateResponseRow> rows){
        rateRowViews = rows;
        if(ll_rateresponse != null){
            for(RateResponseRow row : rateRowViews){
                ll_rateresponse.addView(row);
            }
        }
    }

    public void setTextLabel(String text){
        labelText = text;
        if(tv_label_rateresponse != null)
            tv_label_rateresponse.setText(labelText);
    }

    public interface ClickListener{
        void onClick();
    }
}
