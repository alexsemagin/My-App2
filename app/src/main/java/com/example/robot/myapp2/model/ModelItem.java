package com.example.robot.myapp2.model;

import java.util.ArrayList;
import java.util.List;

public class ModelItem {

    private String title;
    private String detail;

    private ModelItem(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getMyTitle() {
        return title;
    }

    public String getMyDetail() {
        return detail;
    }

    public static List<ModelItem> getFakeItems() {
        ArrayList<ModelItem> itemList = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            if (i % 2 == 0)
                itemList.add(new ModelItem(i + ". Title", i + ". Detail"));
            else
                itemList.add(new ModelItem(i + ". Title", ""));
        }
        return itemList;
    }
}