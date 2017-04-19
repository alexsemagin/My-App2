package com.example.robot.myapp2.presenter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public interface MapInterface extends BaseInterface {

    void setGoogleMap(GoogleMap googleMap);

    void setLocation();

    void setMarker(LatLng latLng);

    void clickMarker(Marker marker);

}