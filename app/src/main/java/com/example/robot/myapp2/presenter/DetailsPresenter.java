package com.example.robot.myapp2.presenter;

import com.example.robot.myapp2.presenter.interfaces.DetailsInterface;

public class DetailsPresenter extends BasePresenter {

    private DetailsInterface detailsInterface;

    private String title;
    private String detail;

    public DetailsPresenter(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public void setView(DetailsInterface detailsInterface) {
        this.detailsInterface = detailsInterface;
    }

    public void setData() {
        detailsInterface.setTitleAndDetail(title, detail);
    }

}