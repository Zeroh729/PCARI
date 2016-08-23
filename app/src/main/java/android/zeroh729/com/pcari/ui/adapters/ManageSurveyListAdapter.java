package android.zeroh729.com.pcari.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.ui.base.BaseAdapterRecyclerView;
import android.zeroh729.com.pcari.ui.base.ViewWrapper;
import android.zeroh729.com.pcari.ui.views.ManageSurveyRow;
import android.zeroh729.com.pcari.ui.views.ManageSurveyRow_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class ManageSurveyListAdapter extends BaseAdapterRecyclerView<Survey, ManageSurveyRow> {
    @RootContext
    Context context;

    @Override
    protected ManageSurveyRow onCreateItemView(ViewGroup parent, int viewType) {
        return ManageSurveyRow_.build(context);
    }

    @Override
    public void onBindViewHolder(final ViewWrapper<ManageSurveyRow> holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.getView().bind(items.get(position));
        holder.getView().findViewById(R.id.parent_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
                setSelectedIndex(holder, position);
            }
        });
    }
}
