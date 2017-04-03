package com.example.robot.myapp2.presenter;

import android.view.View;
import android.widget.Filter;

import com.example.robot.myapp2.model.ModelItem;
import com.example.robot.myapp2.ui.test_task.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TitlesPresenter {

    private TitlesInterface titlesInterface;
    private List<ModelItem> list;
    private List<ModelItem> newList;
    private RecyclerAdapter mAdapter;
    private RecyclerAdapter.MyFilter filter;
    private String query;
    private ExecutorService ex;

    public TitlesPresenter(RecyclerAdapter mAdapter) {
        this.mAdapter = mAdapter;
        ex = Executors.newSingleThreadExecutor();
    }

    public void setView(TitlesInterface titlesInterface) {
        this.titlesInterface = titlesInterface;
    }

    public void getData() {
        if (newList == null) {
            list = ModelItem.getFakeItems();
            if (titlesInterface != null) titlesInterface.setList(list);
        } else titlesInterface.setList(newList);
    }

    public void onItemSelected(String title, String detail) {
        titlesInterface.openNewFragment(title, detail);
    }

    public void searchItem(String query) {
        this.query = query;
        Filter filter = getFilter();
        titlesInterface.progressBarDoVisible(View.VISIBLE);
        ex.execute(() -> filter.filter(query));
    }

    public void updateList(List<ModelItem> list) {
        this.newList = list;
        titlesInterface.progressBarDoVisible(View.INVISIBLE);
        getData();
    }

    private Filter getFilter() {
        if (filter == null) filter = new RecyclerAdapter.MyFilter(this, list);
        return filter;
    }

    public void sortByName() {
        Collections.sort(newList, (o1, o2) -> o1.getMyTitle().compareTo(o2.getMyTitle()));
        titlesInterface.setList(list);
    }

    public void sortByTime() {
        Collections.sort(newList, (o1, o2) -> o1.getMyTime().compareTo(o2.getMyTime()));
        titlesInterface.setList(list);
    }
}
