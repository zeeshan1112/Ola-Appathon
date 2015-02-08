package com.example.chalokhoj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class FinalActivity extends Activity implements OnMapReadyCallback {

	GoogleMap map;
	String x;
	Location location;
	Context context;
	SharedPreferences sp;
	Double currentlat,currentlong;
	Double latitude,longitude;
	WebView wb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final);
		Intent i=getIntent();
		x=i.getStringExtra("coordinates");
		latitude=Double.parseDouble(x.split(",")[0]);
		longitude=Double.parseDouble(x.split(",")[1]);
		
		//MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
		//mapFragment.getMapAsync(this);
		sp= getSharedPreferences("CurrentLocationData", Context.MODE_PRIVATE);
		currentlat=Double.parseDouble(sp.getString("latitude","0"));
		currentlong=Double.parseDouble(sp.getString("longitude","0"));
		
		String url="http://olaapp.site50.net/map.html/?olat="+currentlat+"&olong="+currentlong+"&dlat="+latitude+"&dlong="+longitude;
		wb=(WebView)findViewById(R.id.web_map);
		wb.getSettings().setJavaScriptEnabled(true);
		wb.loadUrl(url);
		//wb.measure(100, 100);
		//wb.getSettings().setUseWideViewPort(true);
		wb.getSettings().setLoadWithOverviewMode(true);
		wb.setWebViewClient(new WebViewClient());
		wb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	}

	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		map.addMarker(new MarkerOptions()
		.title("Your Location")
        .snippet("")
        .position(new LatLng(currentlat,currentlong)));
		 
		map.addMarker(new MarkerOptions()
		.title("Destination")
        .snippet("")
        .position(new LatLng(latitude,longitude)));
		
		CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(latitude,longitude))      // Sets the center of the map to location user
        .zoom(11)                   // Sets the zoom
        .bearing(90)                // Sets the orientation of the camera to east
        .tilt(10)                   // Sets the tilt of the camera to 30 degrees
        .build();                   // Creates a CameraPosition from the builder
		
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		
		map.addPolyline(new PolylineOptions()
		.geodesic(false)
		.add(new LatLng(currentlat,currentlong))
		.add(new LatLng(latitude,longitude)));
	}
}
