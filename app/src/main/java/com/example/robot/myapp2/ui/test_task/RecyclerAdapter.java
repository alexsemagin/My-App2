package com.example.robot.myapp2.ui.test_task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.model.ModelItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

class RecyclerAdapter extends BaseAdapter {

    private OnItemSelected mItemSelected;

    RecyclerAdapter(OnItemSelected itemSelected) {
        mItemSelected = itemSelected;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
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
            mItemSelected.onItemSelected(title.getText().toString(), detail.getText().toString());
        }
    }

    interface OnItemSelected {
        void onItemSelected(String title, String info);
    }

}