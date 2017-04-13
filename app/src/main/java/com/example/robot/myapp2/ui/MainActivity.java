package com.example.robot.myapp2.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.ui.test_task.MapFragment;
import com.example.robot.myapp2.ui.test_task.SecondTaskFragment;
import com.example.robot.myapp2.ui.test_task.TitlesFragment;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.container2)
    FrameLayout container2;
    private Drawer result = null;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Alex Semagin").withEmail("semaal@rarus.ru").withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.profile, null))
                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withHeader(R.layout.drawer_header)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_test1).withIcon(FontAwesome.Icon.faw_home).withBadge("100").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_test2).withIcon(FontAwesome.Icon.faw_list_ul).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_test3).withIcon(FontAwesome.Icon.faw_map).withIdentifier(3),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_news).withIcon(FontAwesome.Icon.faw_newspaper_o).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_actions).withIcon(FontAwesome.Icon.faw_cube).withIdentifier(5),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_info).withIdentifier(7),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_exit).withIcon(FontAwesome.Icon.faw_times).withIdentifier(8)
                ).withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    this.position = position;
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment secondTaskFragment = getSupportFragmentManager().findFragmentByTag(SecondTaskFragment.class.getName());
                    Fragment mapFragment = getSupportFragmentManager().findFragmentByTag(MapFragment.class.getName());
                    switch (position) {
                        case 1:
                            if (secondTaskFragment != null)
                                ft.remove(secondTaskFragment).commit();
                            if (mapFragment != null)
                                ft.remove(mapFragment).commit();
                            break;
                        case 2:
                            ft = getSupportFragmentManager().beginTransaction();
                            if (secondTaskFragment == null) {
                                secondTaskFragment = new SecondTaskFragment();
                                ft.replace(R.id.main_container, secondTaskFragment, SecondTaskFragment.class.getName()).commit();
                            } else ft.replace(R.id.main_container, secondTaskFragment).commit();
                            break;
                        case 3:
                            ft = getSupportFragmentManager().beginTransaction();
                            if (mapFragment == null) {
                                mapFragment = new MapFragment();
                                ft.replace(R.id.main_container, mapFragment, MapFragment.class.getName()).commit();
                            } else ft.replace(R.id.main_container, mapFragment).commit();
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                    }
                    return false;
                })
                .build();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment titlesFragment = getSupportFragmentManager().findFragmentByTag(TitlesFragment.class.getName());
        if (titlesFragment == null) {
            titlesFragment = new TitlesFragment();
            ft.replace(R.id.container, titlesFragment, TitlesFragment.class.getName()).commit();
        } else ft.replace(R.id.container, titlesFragment).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        result.setSelection(savedInstanceState.getInt("position"));
    }

    @Override
    public void onBackPressed() {
        Fragment titlesFragment = getSupportFragmentManager().findFragmentById(R.id.container2);
        Fragment mapFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (titlesFragment != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            getSupportFragmentManager().beginTransaction().remove(titlesFragment).commit();
        else if (result.isDrawerOpen()) result.closeDrawer();
        else if (mapFragment != null)
            getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
        else super.onBackPressed();
    }

    public Drawer getDrawer() {
        return result;
    }
}