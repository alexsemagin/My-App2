package com.example.robot.myapp2.presenter;

public class DetailsPresenter extends BasePresenter<DetailsPresenter.View> {

    private String title;
    private String detail;

    public DetailsPresenter(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    @Override
    public void setView(View view) {
        super.setView(view);
    }

    @Override
    public void dropView() {
        super.dropView();
    }

    public void setData() {
        mBaseInterface.setTitleAndDetail(title, detail);
    }

    public interface View {

        void setTitleAndDetail(String title, String detail);

    }

}