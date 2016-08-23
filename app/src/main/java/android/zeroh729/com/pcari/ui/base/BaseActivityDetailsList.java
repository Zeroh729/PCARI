package android.zeroh729.com.pcari.ui.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import java.util.ArrayList;

@EActivity
public abstract class BaseActivityDetailsList<T extends Parcelable> extends BaseActivity {
    protected ArrayList<T> dataList;
    protected T selectedData = null;
    protected int selectedIndex = -1;

    private Bundle savedInstanceState;

    public ArrayList<T> getDataList(){
        if(dataList == null){
            dataList = fetchData();
        }
        return dataList;
    }

    @Nullable
    public T getSelectedData() {
        if(selectedData == null && selectedIndex != -1){
            selectedData = getDataList().get(selectedIndex);
        }else if(selectedIndex == -1){
            selectedData = null;
        }
        return selectedData;
    }

    public int getSelectedIndex(){
        return selectedIndex;
    }

    abstract protected ArrayList<T> fetchData();
    abstract protected void onActivityItemSelect(int index);

    public void onItemSelect(int index){
        selectedIndex = index;
        dataList = getDataList();
        selectedData = getDataList().get(index);
        onActivityItemSelect(index);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            this.savedInstanceState = savedInstanceState;
            selectedData = (T)savedInstanceState.getParcelable("survey");
            dataList = savedInstanceState.<T>getParcelableArrayList("surveyList");
            selectedIndex = savedInstanceState.getInt("position");
        }
    }

    protected Bundle getSavedInstanceState(){
        return savedInstanceState;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("survey", dataList);
        outState.putParcelable("surveyList", selectedData);
        outState.putInt("position", selectedIndex);
    }
}
