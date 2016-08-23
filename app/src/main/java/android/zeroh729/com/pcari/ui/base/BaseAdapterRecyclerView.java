package android.zeroh729.com.pcari.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.zeroh729.com.pcari.R;
import android.zeroh729.com.pcari.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapterRecyclerView<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> {
    protected ClickListener listener;

    protected List<T> items = new ArrayList<T>();

    protected int selectedIndex = -1;

    protected ViewWrapper<V> selectedHolder;

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<T> items){
        this.items = items;
    }

    public void setClickListener(ClickListener listener) {
        this.listener = listener;
    }

    @Override
    public final ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    protected void setSelectedIndex(int position){
        selectedIndex = position;
        notifyDataSetChanged();
    }

    protected void setSelectedIndex(ViewWrapper<V> holder, int position){
        this.selectedIndex = position;
        if(ScreenUtil.isScreenLandscape(holder.getView().getContext())) {
            if (selectedHolder != null)
                unselectView(selectedHolder);
            selectView(holder);
            selectedHolder = holder;
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewWrapper<V> holder, int position) {
        if(ScreenUtil.isScreenLandscape(holder.getView().getContext()))
            updateView(holder, position);
    }

    private void updateView(ViewWrapper<V> holder, int position){
        if(position == selectedIndex){
            selectView(holder);
        }else{
            unselectView(holder);
        }
    }

    private void unselectView(ViewWrapper<V> holder){
        holder.getView().findViewById(R.id.parent_view).setBackgroundResource(0);
    }
    private void selectView(ViewWrapper<V> holder){
        holder.getView().findViewById(R.id.parent_view).setBackgroundResource(R.drawable.circle);
    }

    public interface ClickListener{
        void onClick(int index);
    }
}