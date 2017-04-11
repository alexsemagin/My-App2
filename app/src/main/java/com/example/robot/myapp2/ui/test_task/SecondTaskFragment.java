package com.example.robot.myapp2.ui.test_task;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.ui.MainActivity;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.Drawer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondTaskFragment extends Fragment {

    @BindView(R.id.toolbar2)
    Toolbar toolbar;
    Drawer drawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.drawer_item_collapsing_toolbar));
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floating_action_button);
        fab.setImageDrawable(new IconicsDrawable(getActivity(), GoogleMaterial.Icon.gmd_favorite).actionBar().color(Color.WHITE));
        MainActivity ma = (MainActivity) this.getActivity();
        drawer = ma.getDrawer();
        toolbar.setNavigationIcon(R.drawable.ic_hamburger);
        drawer.setToolbar(ma, toolbar);
    }
}
