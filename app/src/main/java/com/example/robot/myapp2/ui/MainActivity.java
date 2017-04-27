package com.example.robot.myapp2.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.crashlytics.android.Crashlytics;
import com.example.robot.myapp2.R;
import com.example.robot.myapp2.ui.test_task.CollapsingToolbarFragment;
import com.example.robot.myapp2.ui.test_task.MapFragment;
import com.example.robot.myapp2.ui.test_task.PhoneFragment;
import com.example.robot.myapp2.ui.test_task.TitlesFragment;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.container2)
    FrameLayout container2;

    private FragmentTransaction ft;
    private Drawer mDrawer = null;
    private int position = 0;
    private GoogleApiClient mGoogleApiClient;
    private static final int RESULT_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (!opr.isDone()) {
            signIn();
        }
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDrawer();

        ft = getSupportFragmentManager().beginTransaction();
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
        mDrawer.setSelection(savedInstanceState.getInt("position"), false);
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
        else {
            setResult(RESULT_CODE);
            super.onBackPressed();
        }
    }

    public void initDrawer() {
        AccountHeader mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(new ProfileDrawerItem().withName("Alex Semagin").withEmail("semaal@rarus.ru").withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.profile, null)))
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(mAccountHeader)
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
                    ft = getSupportFragmentManager().beginTransaction();
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
                        case 9:
                            break;
                        case 10:
                            signOut();
                            onStart();
                            break;
                    }
                    return false;
                })
                .build();
    }

    public Drawer getDrawer() {
        return mDrawer;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void signIn() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivityForResult(intent, RESULT_CODE);
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status -> {
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE) {
            finish();
        }
    }

}