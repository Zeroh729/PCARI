package android.zeroh729.com.pcari.ui.fragments;

import android.support.v4.app.Fragment;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.ui.base.FragmentLoadCallback;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.BubbleChartOnValueSelectListener;
import lecho.lib.hellocharts.model.BubbleChartData;
import lecho.lib.hellocharts.model.BubbleValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.BubbleChartView;

@EFragment(R.layout.fragment_dataview_chart)
public class DataChartFragment extends Fragment {
    private ClickListener listener;

    @ViewById(R.id.chart_data)
    BubbleChartView chartView;

    private BubbleChartData chartData;

    @AfterViews
    public void afterviews(){
        chartView.setZoomEnabled(true);
        chartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);

        chartView.setOnValueTouchListener(new BubbleChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, BubbleValue bubbleValue) {
                if(listener != null)
                    listener.onClick(i);
            }

            @Override
            public void onValueDeselected() {

            }
        });
        if(chartData != null){
            chartView.setBubbleChartData(chartData);
        }
    }

    public void setChartDataWithValues(List<BubbleValue> values){
        chartData = new BubbleChartData(values);
        if(chartView != null){
            chartView.setBubbleChartData(chartData);
        }
    }

    public ClickListener getCallback() {
        return listener;
    }

    public void setCallback(ClickListener listener) {
        this.listener = listener;
    }

    public interface ClickListener{
        void onClick(int index);
    }
}
