package android.zeroh729.com.pcari.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;
import android.zeroh729.com.pcari.data.events.UploadStatusEvent;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.ui.base.BaseAdapterRecyclerView;
import android.zeroh729.com.pcari.ui.base.ViewWrapper;
import android.zeroh729.com.pcari.ui.views.UploadResponseRow;
import android.zeroh729.com.pcari.ui.views.UploadResponseRow_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.HashMap;

@EBean
public class UploadResponseListAdapter extends BaseAdapterRecyclerView<SurveyResponse, UploadResponseRow>{
    @RootContext
    Context context;

    private HashMap<String, String> surveyMap;
    private HashMap<String, UploadStatusEvent> statuses;

    public void setSurveyMap(HashMap<String, String> surveyMap, HashMap<String, UploadStatusEvent> statuses) {
        this.surveyMap = surveyMap;
        this.statuses = statuses;
    }

    @Override
    protected UploadResponseRow onCreateItemView(ViewGroup parent, int viewType) {
        return UploadResponseRow_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<UploadResponseRow> holder, int position) {
        super.onBindViewHolder(holder, position);
        SurveyResponse response  = items.get(position);
        holder.getView().bind(surveyMap != null ? surveyMap.get(response.getSurveyId()) : "", response, statuses.get(response.getId()));
    }
}
