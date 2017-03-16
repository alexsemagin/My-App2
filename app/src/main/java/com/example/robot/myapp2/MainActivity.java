package com.example.robot.myapp2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements FillEditText{

    private FragmentManager fm;
    private FragmentTransaction ft;
    private static ImageView imageBack;

    @Override
    public void fillEditText(String title, String discription) {
        DetailsFragment det = (DetailsFragment) fm.findFragmentByTag(DetailsFragment.TAG);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        if(det==null){
            det = new DetailsFragment();
            ft.add(R.id.container2, det, DetailsFragment.TAG);
            ft.commit();
        }
        det.setText(title, discription);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.container, new TitlesFragment(), null);
        Fragment det = fm.findFragmentByTag(DetailsFragment.TAG);
            if (det == null) {
                det = new DetailsFragment();
                ft.replace(R.id.container2, det, DetailsFragment.TAG);
            } else ft.replace(R.id.container2, det);
        ft.commit();
    }

}