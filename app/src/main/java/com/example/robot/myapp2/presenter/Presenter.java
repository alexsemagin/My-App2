package com.example.robot.myapp2.presenter;

import android.view.View;

/**
 * Created by semaal on 27.03.2017.
 */

public class Presenter {

    private MyInterface myInterface;

    public Presenter() {
    }

    public Presenter(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    public void setView(View view) {
        if (view != null) {
            ///view.set();
        } else {
        }
    }

}
