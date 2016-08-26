package android.zeroh729.com.pcari.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.zeroh729.com.pcari.data.model.question.QuantitativeQuestion;
import android.zeroh729.com.pcari.ui.views.AddQualqRow;
import android.zeroh729.com.pcari.ui.views.AddQualqRow_;
import android.zeroh729.com.pcari.ui.views.AddQuanqRow;
import android.zeroh729.com.pcari.ui.views.AddQuanqRow_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@EBean
public class AddQuanqListAdapter extends BaseAdapter{
    private List<QuantitativeQuestion> q = Collections.emptyList();

    @RootContext
    Context context;

    public void setQuestions(List<QuantitativeQuestion> q) {
        this.q = q;
    }

    @Override
    public int getCount() {
        return q.size();
    }

    @Override
    public QuantitativeQuestion getItem(int position) {
        return q.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddQuanqRow itemView;
        if (convertView == null) {
            itemView = AddQuanqRow_.build(context);
        } else {
            itemView = (AddQuanqRow) convertView;
        }

        itemView.bind(getItem(position));

        return itemView;
    }
}
