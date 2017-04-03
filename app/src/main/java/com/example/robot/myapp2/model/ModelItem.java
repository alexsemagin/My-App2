package com.example.robot.myapp2.model;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

public class ModelItem {

    private String title;
    private String detail;
    private long time;

    private ModelItem(String title, String detail, long time) {
        this.title = title;
        this.detail = detail;
        this.time = time;
    }

    public String getMyTitle() {
        return title;
    }

    public String getMyDetail() {
        return detail;
    }

    @SuppressLint("DefaultLocale")
    public String getMyTime() {
        return String.format("%02d:%02d:%02d", time / 3600, time / 60 % 60, time % 60);
    }

    public static List<ModelItem> getFakeItems() {
        ArrayList<ModelItem> itemList = new ArrayList<>();
        for (int i = 1; i < 101; i++) {
            if (i % 2 == 0)
                itemList.add(new ModelItem(i + ". Title", i + ". Detail", 1234L * (int) (Math.random() * 100)));
            else
                itemList.add(new ModelItem(i + ". Title", "", 3456L * (int) (Math.random() * 10)));
        }
        return itemList;
    }

}