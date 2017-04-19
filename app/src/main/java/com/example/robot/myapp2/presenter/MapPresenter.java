package com.example.robot.myapp2.presenter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapPresenter extends BasePresenter<MapPresenter.View> {

    public void setView(View view) {
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

    public interface View {

        void setGoogleMap(GoogleMap googleMap);

        void setLocation();

        void setMarker(LatLng latLng);

        void clickMarker(Marker marker);

    }

}