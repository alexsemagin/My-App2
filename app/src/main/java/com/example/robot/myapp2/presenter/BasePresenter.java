package com.example.robot.myapp2.presenter;

class BasePresenter<T> implements BaseInterface<T> {

    T mBaseInterface;

    @Override
    public void setView(T view) {
        mBaseInterface = view;
    }

    @Override
    public void dropView() {
        mBaseInterface = null;
    }

}