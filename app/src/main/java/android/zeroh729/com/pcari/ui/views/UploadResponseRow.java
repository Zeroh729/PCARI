package android.zeroh729.com.pcari.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.events.UploadStatusEvent;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.row_upload_response)
public class UploadResponseRow extends CardView{
    @ViewById(R.id.tv_suveryname)
    TextView tv_surveyName;

    @ViewById(R.id.tv_datecreated)
    TextView tv_dateCreated;

    @ViewById(R.id.tv_status)
    TextView tv_status;

    @ViewById(R.id.pb_upload)
    ProgressBar pb_upload;

    public UploadResponseRow(Context context) {
        super(context);
    }

    public UploadResponseRow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UploadResponseRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(String surveyName, SurveyResponse response, UploadStatusEvent status){
        tv_surveyName.setText(surveyName + " - Response " + response.getId());
        tv_dateCreated.setText("Created at " + response.getDateCreated());
        bind(status);
    }

    public void bind(UploadStatusEvent event){
        if(event.getProgress() == 0){
            pb_upload.setVisibility(View.GONE);
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("On hold.");
        }else if(event.getProgress() == event.getTotalProgress()){
            pb_upload.setVisibility(View.GONE);
            tv_status.setVisibility(View.VISIBLE);
            tv_status.setText("Uploaded.");
        }else{
            pb_upload.setVisibility(View.VISIBLE);
            tv_status.setVisibility(View.GONE);
            pb_upload.setMax(event.getTotalProgress());
            pb_upload.setProgress(event.getProgress());
        }
    }
}
