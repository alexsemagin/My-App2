package com.example.robot.myapp2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semaal on 09.03.2017.
 */

public class TitlesFragment extends Fragment{
    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLinearLayout;
    private recyclerAdapter rAdapter;
    private FillEditText fillEditTextCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof FillEditText)
            fillEditTextCallback = (FillEditText) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.recycler_layout, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler);

        verticalLinearLayout = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(verticalLinearLayout);

        rAdapter = new recyclerAdapter();
        recyclerView.setAdapter(rAdapter);
        rAdapter.addAll(ModelItem.getFakeItems());

        return v;
    }

    private class recyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

        private ArrayList<ModelItem> items = new ArrayList<>();

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

    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView title;
        private TextView discription;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            discription = (TextView) itemView.findViewById(R.id.item_discription);
            itemView.setOnClickListener(this);
        }

        public void bind(ModelItem modelItem) {
            title.setText(modelItem.getMyTitle());
            discription.setText(modelItem.getMyDiscription());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                fillEditTextCallback.fillEditText(title.getText().toString(), discription.getText().toString());
            }
        }
    }
}
