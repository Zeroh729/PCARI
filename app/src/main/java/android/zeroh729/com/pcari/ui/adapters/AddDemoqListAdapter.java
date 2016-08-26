package android.zeroh729.com.pcari.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.zeroh729.com.pcari.data.model.question.DemographicQuestion;
import android.zeroh729.com.pcari.ui.views.AddDemoqRow;
import android.zeroh729.com.pcari.ui.views.AddDemoqRow_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@EBean
public class AddDemoqListAdapter extends BaseAdapter {
    List<DemographicQuestion> questions = Collections.emptyList();

    @RootContext
    Context context;

    public void setQuestions(List<DemographicQuestion> questions) {
        this.questions = questions;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public DemographicQuestion getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AddDemoqRow itemView;
        if (convertView == null) {
            itemView = AddDemoqRow_.build(context);
        } else {
            itemView = (AddDemoqRow) convertView;
        }

        itemView.bind(getItem(position));

        return itemView;
    }
}
