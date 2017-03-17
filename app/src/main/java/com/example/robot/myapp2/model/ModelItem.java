package com.example.robot.myapp2.model;


import java.util.ArrayList;
import java.util.List;

public class ModelItem {

    private String title;
    private String discription;

    public ModelItem(String title, String discription){
        this.title = title;
        this.discription = discription;
    }

    public String getMyTitle(){
        return title;
    }

    public String getMyDetail(){
        return discription;
    }


    public static List<ModelItem> getFakeItems(){
        ArrayList<ModelItem> itemList = new ArrayList<>();
        for(int i=1;i<101;i++){
            itemList.add(new ModelItem("Title"+i, "Discription"+i));
        }
        return itemList;
    }

}