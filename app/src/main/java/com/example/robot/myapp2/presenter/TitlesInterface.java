package com.example.robot.myapp2.presenter;

import java.util.List;

public interface TitlesInterface extends BaseInterface {

    void setList(List list);

    void openNewFragment(String title, String detail);

}