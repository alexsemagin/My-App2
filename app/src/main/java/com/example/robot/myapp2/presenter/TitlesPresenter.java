package com.example.robot.myapp2.presenter;

import com.example.robot.myapp2.model.ModelItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class TitlesPresenter {

    private TitlesInterface titlesInterface;
    private List<ModelItem> list;
    private List<ModelItem> newList;

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
        newList = new ArrayList<>();
        getFilter(query);
    }

    private void updateList(ModelItem list) {
        newList.add(list);
        getData();
    }

    private void getFilter(String query) {
        Observable.from(list)
                .filter(s -> s.getMyTitle().contains(query) || s.getMyDetail().contains(query))
                .subscribe(this::updateList);
    }

    private void setSortListToList(List list) {
        this.list = list;
        titlesInterface.setList(list);
    }

    public void sortByName() {
        if (newList == null)
            newList = list;
        Observable.from(list)
                .toSortedList((item, item2) -> item.getMyTitle().compareTo(item2.getMyTitle()))
                .subscribe(this::setSortListToList);
        //сохранить отсортированный список в list
    }

    public void sortByTime() {
        if (newList == null)
            newList = list;
        Observable.from(list)
                .toSortedList((item, item2) -> item.getMyTime().compareTo(item2.getMyTime()))
                .subscribe(this::setSortListToList);
        //сохранить отсортированный список в list
    }

    public String getModelSize() {
        if (newList == null)
            return list.size() + "";
        else return newList.size() + "";
    }
}
