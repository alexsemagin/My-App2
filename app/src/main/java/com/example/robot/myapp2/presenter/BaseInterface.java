package com.example.robot.myapp2.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public interface BaseInterface {

    void onCreate(Bundle savedInstanceState);

    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

}