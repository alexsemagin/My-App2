package com.example.robot.myapp2.model;


import android.app.Activity;

import com.example.robot.myapp2.R;

import java.util.ArrayList;
import java.util.List;

public class ModelItem {

    private String title;
    private String detail;

    private ModelItem(String title, String detail){
        this.title = title;
        this.detail = detail;
    }

    public String getMyTitle(){
        return title;
    }

    public String getMyDetail(){
        return detail;
    }

    public static List<ModelItem> getFakeItems(Activity activity){
        ArrayList<ModelItem> itemList = new ArrayList<>();
        for(int i=1;i<10;i++){
            if(i % 2 == 0) itemList.add(new ModelItem(activity.getResources().getString(R.string.title), activity.getResources().getString(R.string.detail)));
            else itemList.add(new ModelItem(activity.getResources().getString(R.string.title), ""));
        }
        return itemList;
    }

}