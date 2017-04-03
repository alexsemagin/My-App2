package com.example.robot.myapp2.ui.test_task;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.model.ModelItem;
import com.example.robot.myapp2.presenter.TitlesPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

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
        notifyItemRangeInserted(pos, this.items.size());
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

    public static class MyFilter extends Filter {

        private final List<ModelItem> originalList;
        private final List<ModelItem> filteredList;
        private final TitlesPresenter titlesPresenter;

        public MyFilter(TitlesPresenter titlesPresenter, List<ModelItem> list) {
            super();
            this.titlesPresenter = titlesPresenter;
            this.originalList = list;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (int i = 0; i < originalList.size(); i++) {
                    if (originalList.get(i).getMyTitle().toLowerCase().contains(filterPattern) || originalList.get(i).getMyDetail().toLowerCase().contains(filterPattern)) {
                        filteredList.add(originalList.get(i));
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            titlesPresenter.updateList((List<ModelItem>) filterResults.values);
        }
    }
}

