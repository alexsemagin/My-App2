package com.example.robot.myapp2.presenter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapPresenter extends BasePresenter<MapInterface> {

    public void setView(MapInterface view) {
        mBaseInterface = view;
    }

    public void onMapReady(GoogleMap googleMap) {
        mBaseInterface.setGoogleMap(googleMap);
    }

    public void setMyLocation() {
        mBaseInterface.setLocation();
    }

    public void onMapLongClick(LatLng latLng) {
        mBaseInterface.setMarker(latLng);
    }

    public void onMarkerClick(Marker marker) {
        mBaseInterface.clickMarker(marker);
    }

}