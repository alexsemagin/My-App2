package com.example.robot.myapp2.ui.test_task;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.model.ModelItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semaal on 20.03.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private ArrayList<ModelItem> items = new ArrayList<>();
    public Activity context;

    private OnItemSelected itemSelected;

    public interface OnItemSelected {
        void onItemSelected(String title, String info);
    }

    public RecyclerAdapter(Activity context, OnItemSelected itemSelected) {
        this.context = context;
        this.itemSelected = itemSelected;
    }

    public void addAll(List<ModelItem> fakeItems) {
        int pos = getItemCount();
        this.items.addAll(fakeItems);
        notifyItemRangeInserted(pos, this.items.size());
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView detail;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            detail = (TextView) itemView.findViewById(R.id.item_detail);
            itemView.setOnClickListener(this);
        }

        public void bind(ModelItem modelItem) {
            title.setText(modelItem.getMyTitle());
            detail.setText(modelItem.getMyDetail());
        }

        @Override
        public void onClick(View view) {
            itemSelected.onItemSelected(title.getText().toString(), detail.getText().toString());
        }
    }
}