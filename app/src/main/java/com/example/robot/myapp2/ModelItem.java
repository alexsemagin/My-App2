package com.example.robot.myapp2;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEMAAL on 07.03.2017.
 */

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

    public String getMyDiscription(){
        return discription;
    }


    public static List<ModelItem> getFakeItems(){
        ArrayList<ModelItem> itemList = new ArrayList<>();
        for(int i=1;i<101;i++){
            itemList.add(new ModelItem("title"+i, "discription"+i));
        }
        return itemList;
    }

}
