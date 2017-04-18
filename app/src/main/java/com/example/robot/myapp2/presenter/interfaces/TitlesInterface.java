package com.example.robot.myapp2.presenter.interfaces;

import java.util.List;

public interface TitlesInterface extends BaseInterface {

    void setList(List list);

    void openNewFragment(String title, String detail);

}