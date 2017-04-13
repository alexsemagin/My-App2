package com.example.robot.myapp2.presenter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapPresenter {

    private MapInterface mMapInterface;

    public void setView(MapInterface MapInterface) {
        mMapInterface = MapInterface;
    }

    public void onMapReady(GoogleMap googleMap) {
        mMapInterface.setMap(googleMap);
    }

    public void setMyLocation() {
        mMapInterface.setLocation();
    }

    public void onMapLongClick(LatLng latLng) {
        mMapInterface.setMarker(latLng);
    }

    public void onMarkerClick(Marker marker) {
        mMapInterface.clickMarker(marker);
    }
}
