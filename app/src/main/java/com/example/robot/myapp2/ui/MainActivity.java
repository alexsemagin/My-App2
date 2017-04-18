package com.example.robot.myapp2.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.FrameLayout;

import com.crashlytics.android.Crashlytics;
import com.example.robot.myapp2.R;
import com.example.robot.myapp2.ui.test_task.CollapsingToolbarFragment;
import com.example.robot.myapp2.ui.test_task.MapFragment;
import com.example.robot.myapp2.ui.test_task.PhoneFragment;
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
import io.fabric.sdk.android.Fabric;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.container2)
    FrameLayout container2;

    private Drawer mDrawer = null;
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDrawer();

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
        mDrawer.setSelection(savedInstanceState.getInt("position"));
    }

    @Override
    public void onBackPressed() {
        Fragment titlesFragment = getSupportFragmentManager().findFragmentById(R.id.container2);
        Fragment mapFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        Fragment phoneFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
        if (titlesFragment != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            getSupportFragmentManager().beginTransaction().remove(titlesFragment).commit();
        else if (mDrawer.isDrawerOpen()) mDrawer.closeDrawer();
        else if (mapFragment != null)
            getSupportFragmentManager().beginTransaction().remove(mapFragment).commit();
        else if (phoneFragment != null)
            getSupportFragmentManager().beginTransaction().remove(phoneFragment).commit();
        else super.onBackPressed();
    }

    public void initDrawer() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Alex Semagin").withEmail("semaal@rarus.ru").withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.profile, null))
                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_test1).withIcon(FontAwesome.Icon.faw_home).withBadge("100").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_test2).withIcon(FontAwesome.Icon.faw_list_ul).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_test3).withIcon(FontAwesome.Icon.faw_map).withIdentifier(3),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_phone).withIcon(FontAwesome.Icon.faw_newspaper_o).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_actions).withIcon(FontAwesome.Icon.faw_cube).withIdentifier(5),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_info).withIdentifier(7),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_exit).withIcon(FontAwesome.Icon.faw_times).withIdentifier(8)
                ).withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    this.position = position;
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Fragment secondTaskFragment = getSupportFragmentManager().findFragmentByTag(CollapsingToolbarFragment.class.getName());
                    Fragment mapFragment = getSupportFragmentManager().findFragmentByTag(MapFragment.class.getName());
                    Fragment phoneFragment = getSupportFragmentManager().findFragmentByTag(PhoneFragment.class.getName());
                    switch (position) {
                        case 1:
                            if (secondTaskFragment != null)
                                ft.remove(secondTaskFragment).commit();
                            if (mapFragment != null)
                                ft.remove(mapFragment).commit();
                            if (phoneFragment != null)
                                ft.remove(phoneFragment).commit();
                            break;
                        case 2:
                            ft = getSupportFragmentManager().beginTransaction();
                            if (secondTaskFragment == null) {
                                secondTaskFragment = new CollapsingToolbarFragment();
                                ft.replace(R.id.main_container, secondTaskFragment, CollapsingToolbarFragment.class.getName()).commit();
                            } else ft.replace(R.id.main_container, secondTaskFragment).commit();
                            break;
                        case 3:
                            ft = getSupportFragmentManager().beginTransaction();
                            if (mapFragment == null) {
                                mapFragment = new MapFragment();
                                ft.replace(R.id.main_container, mapFragment, MapFragment.class.getName()).commit();
                            } else ft.replace(R.id.main_container, mapFragment).commit();
                            break;
                        case 5:
                            ft = getSupportFragmentManager().beginTransaction();
                            if (phoneFragment == null) {
                                phoneFragment = new PhoneFragment();
                                ft.replace(R.id.main_container, phoneFragment, PhoneFragment.class.getName()).commit();
                            } else ft.replace(R.id.main_container, phoneFragment).commit();
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
    }

    public Drawer getDrawer() {
        return mDrawer;
    }

}