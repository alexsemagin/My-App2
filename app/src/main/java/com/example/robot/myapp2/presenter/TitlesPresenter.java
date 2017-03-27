package com.example.robot.myapp2.presenter;

import android.util.Log;

import com.example.robot.myapp2.model.ModelItem;

import java.util.List;

/**
 * Created by semaal on 27.03.2017.
 */

public class TitlesPresenter {

    private TitlesInterface titlesInterface;
    private List<ModelItem> list;

    public void setView(TitlesInterface titlesInterface) {
        this.titlesInterface = titlesInterface;
    }

    public void getData() {
        if (list == null) {
            list = ModelItem.getFakeItems();
            if (titlesInterface != null) titlesInterface.setList(list);
        }
    }

    public void onItemSelected(String title, String detail) {
        titlesInterface.openNewFragment(title, detail);
    }
}
