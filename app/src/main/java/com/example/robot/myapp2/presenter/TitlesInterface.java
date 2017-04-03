package com.example.robot.myapp2.presenter;

import java.util.List;

public interface TitlesInterface {

    void setList(List list);

    void openNewFragment(String title, String detail);

    void progressBarDoVisible(int visibility);
}
