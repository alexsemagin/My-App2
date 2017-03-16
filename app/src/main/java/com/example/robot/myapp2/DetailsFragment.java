package com.example.robot.myapp2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by semaal on 09.03.2017.
 */

public class DetailsFragment extends Fragment {

    private static TextView tvDiscription;
    private static TextView tvTitle;
    private static ImageView imageBack;
    public static final String TAG = "DetailsTag";
    private static String DETAIL = "Detail";
    private static String TITLE = "Title";
    private static Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.details, container, false);

        tvDiscription = (TextView) v.findViewById(R.id.textView);
        tvTitle = (TextView) v.findViewById(R.id.textView2);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);

        tvTitle.setText(TITLE);
        tvDiscription.setText(DETAIL);

        imageBack = (ImageView) v.findViewById(R.id.imageView);
        imageBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(DetailsFragment.this).commit();
            }
        });

        return v;
    }

    public void setText(String title, String discription ){
        TITLE = title;
        DETAIL = discription;
        tvTitle.setText(title);
        tvDiscription.setText(discription);
    }

}
