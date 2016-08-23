package android.zeroh729.com.pcari.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.data.model.Survey;
import android.zeroh729.com.pcari.ui.base.BaseAdapterRecyclerView;
import android.zeroh729.com.pcari.ui.base.ViewWrapper;
import android.zeroh729.com.pcari.ui.views.PublicSurveyRow;
import android.zeroh729.com.pcari.ui.views.PublicSurveyRow_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

@EBean
public class PublicSurveyListAdapter extends BaseAdapterRecyclerView<Survey, PublicSurveyRow>{
    @RootContext
    Context context;

    @Override
    protected PublicSurveyRow onCreateItemView(ViewGroup parent, int viewType) {
        return PublicSurveyRow_.build(context);
    }

    @Override
    public void onBindViewHolder(final ViewWrapper<PublicSurveyRow> holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.getView().bind(items.get(position));
        holder.getView().findViewById(R.id.parent_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }
}
