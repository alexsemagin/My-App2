package com.example.robot.myapp2.presenter;

public interface BaseInterface<V> {

    void setView(V view);

    void dropView();

}