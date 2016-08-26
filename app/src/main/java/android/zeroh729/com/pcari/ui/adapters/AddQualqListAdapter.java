package android.zeroh729.com.pcari.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.zeroh729.com.pcari.data.model.question.QualitativeQuestion;
import android.zeroh729.com.pcari.ui.views.AddDemoqRow;
import android.zeroh729.com.pcari.ui.views.AddDemoqRow_;
import android.zeroh729.com.pcari.ui.views.AddQualqRow;
import android.zeroh729.com.pcari.ui.views.AddQualqRow_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EBean
public class AddQualqListAdapter extends BaseAdapter{
    private List<QualitativeQuestion> q = Collections.emptyList();

    @RootContext
    Context context;

    public void setQuestions(List<QualitativeQuestion> q) {
        this.q = q;
    }

    @Override
    public int getCount() {
        return q.size();
    }

    @Override
    public QualitativeQuestion getItem(int position) {
        return q.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddQualqRow itemView;
        if (convertView == null) {
            itemView = AddQualqRow_.build(context);
        } else {
            itemView = (AddQualqRow) convertView;
        }

        itemView.bind(getItem(position));

        return itemView;
    }
}
