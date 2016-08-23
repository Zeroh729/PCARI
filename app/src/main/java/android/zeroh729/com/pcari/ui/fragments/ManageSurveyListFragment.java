package android.zeroh729.com.pcari.ui.fragments;

import android.support.v7.widget.RecyclerView;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.ui.adapters.ManageSurveyListAdapter;
import android.zeroh729.com.pcari.ui.base.BaseFragmentList;
import android.zeroh729.com.pcari.ui.base.BaseAdapterRecyclerView;
import android.zeroh729.com.pcari.ui.views.ManageSurveyRow;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_manage_survey_list)
public class ManageSurveyListFragment extends BaseFragmentList {
    @ViewById(R.id.rv_surveys)
    RecyclerView rv_list;

    @Bean
    ManageSurveyListAdapter adapter;

    @AfterViews
    public void afterviews(){
        super.afterviews();
    }

    @Override
    protected BaseAdapterRecyclerView<Survey, ManageSurveyRow> getAdapter() {
        return adapter;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return rv_list;
    }
}
