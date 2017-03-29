package com.example.robot.myapp2.presenter;

public class DetailsPresenter {

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

