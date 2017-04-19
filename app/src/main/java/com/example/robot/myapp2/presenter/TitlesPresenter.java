package com.example.robot.myapp2.presenter;

import com.example.robot.myapp2.model.ModelItem;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class TitlesPresenter extends BasePresenter<TitlesPresenter.View> {

    private List<ModelItem> list;
    private List<ModelItem> newList;

    public void setView(View view) {
        mBaseInterface = view;
    }

    @Override
    public void dropView() {
        super.dropView();
    }

    public void getData() {
        if (newList == null) {
            list = ModelItem.getModel();
            if (mBaseInterface != null) mBaseInterface.setList(list);
        } else mBaseInterface.setList(newList);
    }

    public void onItemSelected(String title, String detail) {
        mBaseInterface.openNewFragment(title, detail);
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
        mBaseInterface.setList(list);
    }

    public void sortByName() {
        if (newList == null)
            newList = list;
        Observable.from(list)
                .toSortedList((item, item2) -> item.getMyTitle().compareTo(item2.getMyTitle()))
                .subscribe(this::setSortListToList);
    }

    public void sortByTime() {
        if (newList == null)
            newList = list;
        Observable.from(list)
                .toSortedList((item, item2) -> item.getMyTime().compareTo(item2.getMyTime()))
                .subscribe(this::setSortListToList);
    }

    public String getModelSize() {
        return newList == null ? list.size() + "" : newList.size() + "";
    }

    public interface View {

        void setList(List list);

        void openNewFragment(String title, String detail);

    }

}