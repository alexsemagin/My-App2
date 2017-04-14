package com.example.robot.myapp2.ui.test_task;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.robot.myapp2.R;
import com.example.robot.myapp2.presenter.MapInterface;
import com.example.robot.myapp2.presenter.MapPresenter;
import com.example.robot.myapp2.ui.MainActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.materialdrawer.Drawer;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;
import static com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import static com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

public class MapFragment extends Fragment implements OnMapLongClickListener, OnMapReadyCallback, MapInterface, OnMarkerClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mapview)
    MapView mapView;

    private MapPresenter mMapPresenter;
    private GoogleMap map;
    private MenuItem mHybridMapType;
    private MenuItem mNormalMapType;
    private MenuItem mSatelliteMapType;
    private MenuItem mTerrainMapType;

    private static final String KEY_MAP_SAVED_STATE = "mapState";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setRetainInstance(true);
        mMapPresenter = new MapPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle mapState = (savedInstanceState != null)
                ? savedInstanceState.getBundle(KEY_MAP_SAVED_STATE) : null;
        mapView.onCreate(mapState);
        mapView.getMapAsync(this);
        mapView.onResume();

        toolbar.setTitle(R.string.map_fragment);
        toolbar.inflateMenu(R.menu.menu_map_type);

        mHybridMapType = toolbar.getMenu().findItem(R.id.map_type_hybrid);
        mNormalMapType = toolbar.getMenu().findItem(R.id.map_type_normal);
        mSatelliteMapType = toolbar.getMenu().findItem(R.id.map_type_satellite);
        mTerrainMapType = toolbar.getMenu().findItem(R.id.map_type_terrain);

        mHybridMapType.setOnMenuItemClickListener(item -> {
            map.setMapType(MAP_TYPE_HYBRID);
            mHybridMapType.setChecked(true);
            return false;
        });

        mNormalMapType.setOnMenuItemClickListener(item -> {
            map.setMapType(MAP_TYPE_NORMAL);
            mNormalMapType.setChecked(true);
            return false;
        });

        mSatelliteMapType.setOnMenuItemClickListener(item -> {
            map.setMapType(MAP_TYPE_SATELLITE);
            mSatelliteMapType.setChecked(true);
            return false;
        });

        mTerrainMapType.setOnMenuItemClickListener(item -> {
            map.setMapType(MAP_TYPE_TERRAIN);
            mTerrainMapType.setChecked(true);
            return false;
        });

        MainActivity ma = (MainActivity) this.getActivity();
        Drawer drawer = ma.getDrawer();
        drawer.setToolbar(ma, toolbar, true);

        mMapPresenter.setView(this);
        mMapPresenter.setMyLocation();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapState = new Bundle();
        mapView.onSaveInstanceState(mapState);
        outState.putBundle(KEY_MAP_SAVED_STATE, mapState);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null && location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10);
                map.animateCamera(cameraUpdate);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapPresenter.onMapReady(googleMap);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMapPresenter.onMapLongClick(latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMapPresenter.onMarkerClick(marker);
        return false;
    }

    @Override
    public void setMap(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(this);
        map.setOnMarkerClickListener(this);
    }

    @Override
    public void setLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        } else
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
    }

    @Override
    public void setMarker(LatLng latLng) {
        Marker marker = map.addMarker(new MarkerOptions().position(latLng).title("Home").draggable(true));
        marker.showInfoWindow();
    }

    @Override
    public void clickMarker(Marker marker) {
        marker.remove();
    }

}
