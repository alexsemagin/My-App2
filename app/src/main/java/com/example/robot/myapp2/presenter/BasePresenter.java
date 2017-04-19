package com.example.robot.myapp2.presenter;

class BasePresenter<T extends BaseInterface> {

    T mBaseInterface;

    public void setView(T view) {
        mBaseInterface = view;
    }

    public void dropView() {
        mBaseInterface = null;
    }

}