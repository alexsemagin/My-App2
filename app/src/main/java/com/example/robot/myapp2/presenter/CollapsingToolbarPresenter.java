package com.example.robot.myapp2.presenter;

import com.example.robot.myapp2.presenter.interfaces.CollapsingToolbarInterface;

public class CollapsingToolbarPresenter extends BasePresenter {

    private CollapsingToolbarInterface secondTaskInterface;

    public void setView(CollapsingToolbarInterface secondTaskInterface) {
        this.secondTaskInterface = secondTaskInterface;
    }

}