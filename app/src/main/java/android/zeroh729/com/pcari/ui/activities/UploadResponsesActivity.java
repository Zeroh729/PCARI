package android.zeroh729.com.pcari.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.events.NetworkEvent;
import android.zeroh729.com.pcari.data.events.UploadStatusEvent;
import android.zeroh729.com.pcari.data.model.response.SurveyResponse;
import android.zeroh729.com.pcari.interactor.services.UploadService_;
import android.zeroh729.com.pcari.presenters.UploadResponsePresenter;
import android.zeroh729.com.pcari.ui.adapters.UploadResponseListAdapter;
import android.zeroh729.com.pcari.ui.base.BaseActivity;
import android.zeroh729.com.pcari.util.OttoBus;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_upload_responses)
public class UploadResponsesActivity extends BaseActivity implements UploadResponsePresenter.UploadResponseScreen{
    UploadResponsePresenter presenter;

    @ViewById(R.id.rv_responses)
    RecyclerView rv_responses;

    @Bean
    UploadResponseListAdapter adapter;

    @AfterViews
    public void afterviews(){
        presenter = new UploadResponsePresenter(this);
        adapter.setSurveyMap(presenter.getSurveyNameMap(), presenter.getUploadStatuses());
        adapter.setItems(presenter.getResponses());
        rv_responses.setLayoutManager(new LinearLayoutManager(this));
        rv_responses.setAdapter(adapter);

        presenter.setup();
    }

    @Override
    public void displayResponses(ArrayList<SurveyResponse> data) {
        adapter.setItems(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateResponseStatus(int responseIndexFromId) {
        if(responseIndexFromId != -1)
            adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onUploadStatusEvent(UploadStatusEvent event){
        presenter.onUploadStatusEvent(event);
    }
    @Subscribe
    public void onNetworkEvent(NetworkEvent event){
        if(event.isConnected()){
            startService(new Intent(this, UploadService_.class));
        }
    }

}
