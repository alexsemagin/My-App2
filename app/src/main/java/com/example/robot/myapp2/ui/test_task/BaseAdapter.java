package com.example.robot.myapp2.ui.test_task;

import android.support.v7.widget.RecyclerView;

import com.example.robot.myapp2.model.ModelItem;

import java.util.ArrayList;
import java.util.List;

abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    static List<ModelItem> mItems = new ArrayList<>();

    void setList(List<ModelItem> fakeItems) {
        int pos = getItemCount();
        mItems = fakeItems;
        notifyItemRangeInserted(pos, mItems.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}