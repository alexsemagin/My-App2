package com.example.robot.myapp2.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.ui.test_task.TitlesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.container2)
    FrameLayout container2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment tit = getSupportFragmentManager().findFragmentByTag(TitlesFragment.class.getName());
        if (tit == null) {
            tit = new TitlesFragment();
            ft.replace(R.id.container, tit, TitlesFragment.class.getName()).commit();
        } else ft.replace(R.id.container, tit).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment tit = getSupportFragmentManager().findFragmentById(R.id.container2);
        if (tit != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().beginTransaction().remove(tit).commit();
        } else super.onBackPressed();
    }

}