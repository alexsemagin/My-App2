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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private static List<ModelItem> items = new ArrayList<>();
    private Activity context;
    private OnItemSelected itemSelected;

    RecyclerAdapter(Activity context, OnItemSelected itemSelected) {
        this.context = context;
        this.itemSelected = itemSelected;
    }

    void setList(List<ModelItem> fakeItems) {
        int pos = getItemCount();
        items = fakeItems;
        notifyItemRangeInserted(pos, items.size());
        notifyDataSetChanged();
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

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_title)
        TextView title;
        @BindView(R.id.item_detail)
        TextView detail;
        @BindView(R.id.item_time)
        TextView time;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(ModelItem modelItem) {
            title.setText(modelItem.getMyTitle());
            time.setText(modelItem.getMyTime());
            String temp = modelItem.getMyDetail();
            detail.setVisibility(temp.equals("") ? View.GONE : View.VISIBLE);
            detail.setText(modelItem.getMyDetail());
        }

        @OnClick(R.id.card_content)
        public void onClick(View view) {
            itemSelected.onItemSelected(title.getText().toString(), detail.getText().toString());
        }
    }

    interface OnItemSelected {
        void onItemSelected(String title, String info);
    }
}

