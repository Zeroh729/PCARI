package android.zeroh729.com.pcari.ui.base;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.zeroh729.com.pcari.util._;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

@EFragment
public abstract class BaseFragmentList<T extends Parcelable> extends Fragment  implements BaseAdapterRecyclerView.ClickListener{

    @AfterViews
    public void afterviews(){
        BaseActivityDetailsList<T> activity;
        try {
            activity = ((BaseActivityDetailsList<T>) getActivity());

            getAdapter().setClickListener(this);
            getAdapter().setSelectedIndex(activity.getSelectedIndex());
            getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
            getRecyclerView().setAdapter(getAdapter());
            ArrayList<T> data = (activity).getDataList();
            if(data != null){
                bind(data);
            }
        }catch (ClassCastException e){
            _.logError(getClass().getName() + " is under an Activity that does not extend from" + BaseActivityDetailsList.class.getName());
        }
    }

    abstract protected BaseAdapterRecyclerView getAdapter();
    abstract protected RecyclerView getRecyclerView();

    private void bind(ArrayList<T> data){
        getAdapter().setItems(data);
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onClick(int index) {
        try {
            ((BaseActivityDetailsList)getActivity()).onItemSelect(index);
        }catch (ClassCastException e){
            _.logError(getClass().getName() + " is under an Activity that does not extend from" + BaseActivityDetailsList.class.getName());
        }
    }
}
