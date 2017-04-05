package com.example.robot.myapp2.presenter;

import android.view.View;

import com.example.robot.myapp2.model.ModelItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TitlesPresenter {

    private TitlesInterface titlesInterface;
    private List<ModelItem> list;
    private List<ModelItem> newList;
    private String query;
    private ExecutorService ex;

    public TitlesPresenter() {
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
        newList = new ArrayList<>();
        titlesInterface.progressBarDoVisible(View.VISIBLE);
        ex.execute(() -> getFilter(query));
    }

    public void updateList(List<ModelItem> list) {
        this.newList = list;
        titlesInterface.progressBarDoVisible(View.INVISIBLE);
        getData();
    }

    private void getFilter(String query) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMyTitle().contains(query) || list.get(i).getMyDetail().contains(query)) {
                newList.add(list.get(i));
            }
        }
        updateList(newList);
    }

    public void sortByName() {
        if (newList == null)
            newList = list;
        Collections.sort(newList, (o1, o2) -> o1.getMyTitle().compareTo(o2.getMyTitle()));
        Collections.sort(list, (o1, o2) -> o1.getMyTitle().compareTo(o2.getMyTitle()));
        titlesInterface.setList(newList);
    }

    public void sortByTime() {
        if (newList == null)
            newList = list;
        Collections.sort(newList, (o1, o2) -> o1.getMyTime().compareTo(o2.getMyTime()));
        Collections.sort(list, (o1, o2) -> o1.getMyTime().compareTo(o2.getMyTime()));
        titlesInterface.setList(newList);
    }

    public String getModelSize() {
        return list.size()+"";
    }
}
