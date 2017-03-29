package com.example.robot.myapp2.presenter;

import android.util.Log;
import android.widget.Filter;

import com.example.robot.myapp2.model.ModelItem;
import com.example.robot.myapp2.ui.test_task.RecyclerAdapter;

import java.util.List;

public class TitlesPresenter {

    private TitlesInterface titlesInterface;
    private List<ModelItem> list;
    private List<ModelItem> newList;
    private RecyclerAdapter mAdapter;
    private RecyclerAdapter.MyFilter filter;
    private String query;

    public TitlesPresenter(RecyclerAdapter mAdapter){
        this.mAdapter = mAdapter;
    }

    public void setView(TitlesInterface titlesInterface) {
        this.titlesInterface = titlesInterface;
    }

    public void getData() {
            if(newList==null) {
                Log.d("tag","new = null");
                list = ModelItem.getFakeItems();
                if (titlesInterface != null) titlesInterface.setList(list);
            }
            else {
                titlesInterface.setList(newList);
                Log.d("tag","vse ok");
            }
        mAdapter.notifyDataSetChanged();
    }

    public void onItemSelected(String title, String detail) {
        titlesInterface.openNewFragment(title, detail);
    }

    public void searchItem(String query) {
        this.query = query;
        getFilter().filter(query);
        getData();
    }

    public void updateList(List<ModelItem> list) {
        Log.d("tag","update");
        this.newList = list;
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new RecyclerAdapter.MyFilter(this, list);
        }
        return filter;
    }
}
