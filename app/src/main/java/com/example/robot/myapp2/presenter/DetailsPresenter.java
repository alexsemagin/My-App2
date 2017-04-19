package com.example.robot.myapp2.presenter;

public class DetailsPresenter extends BasePresenter<DetailsInterface> {

    private String title;
    private String detail;

    public DetailsPresenter(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    @Override
    public void setView(DetailsInterface view) {
        super.setView(view);
    }

    @Override
    public void dropView() {
        super.dropView();
    }

    public void setData() {
        mBaseInterface.setTitleAndDetail(title, detail);
    }

}