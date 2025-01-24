package com.techacsent.route_recon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.maps.android.SphericalUtil;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.FillManager;
import com.mapbox.mapboxsdk.plugins.annotation.FillOptions;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.utils.ColorUtils;
import com.techacsent.route_recon.R;
import com.techacsent.route_recon.service.LocationService;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static java.lang.String.format;

public class TestActivity extends AppCompatActivity //implements OnMapReadyCallback//

 {


    private MapView mapView;

     private LocationService locationService;

     private static final String SOURCE_ID = "SOURCE_ID";
     private static final String LAYER_ID = "LAYER_ID";
     private static final String ICON_ID = "ICON_ID";



     List<Feature> symbolLayerIconFeatureList = new ArrayList<>();



     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        mapView = findViewById(R.id.mapViewTestActivity);
        mapView.onCreate(savedInstanceState);

        locationService = new LocationService(Objects.requireNonNull(this).getApplicationContext());



         mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {


                symbolLayerIconFeatureList.add(Feature.fromGeometry(
                        Point.fromLngLat(91.8079, 22.3795)));
                symbolLayerIconFeatureList.add(Feature.fromGeometry(
                        Point.fromLngLat(91.8081, 22.3797)));
                symbolLayerIconFeatureList.add(Feature.fromGeometry(
                        Point.fromLngLat(91.8080, 22.3795)));



                /*new Style.Builder().fromUri(Style.MAPBOX_STREETS).withImage(
                        ICON_ID, BitmapFactory.decodeResource(
                                TestActivity.this.getResources(), R.drawable.hazard_60
                        )).withSource(

                        new GeoJsonSource(SOURCE_ID,
                                FeatureCollection.fromFeatures(symbolLayerIconFeatureList))).withLayer(

                        new SymbolLayer(LAYER_ID, SOURCE_ID)
                                .withProperties(
                                        iconImage(ICON_ID),
                                        iconAllowOverlap(true),
                                        iconIgnorePlacement(true)
                                )

                )*/

                //
                mapboxMap.setStyle(Style.MAPBOX_STREETS,

            new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

      // Map is set up and the style has loaded. Now you can add data or make other map adjustments.

                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(locationService.getLatitude(), locationService.getLongitude()))
                                .zoom(16)
                                .tilt(30)
                                .build();
                        mapboxMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position), 1000);
                        mapboxMap.getUiSettings().setCompassGravity(Gravity.CENTER | Gravity.END);


                    FillManager fillManager = new FillManager(mapView, mapboxMap, style);

                   //fillManager.setIconAllowOverlap(true);
                    //fillManager.setIconIgnorePlacement(true);


                    List<LatLng> innerLatLngs = new ArrayList<>();
                    innerLatLngs.add(new LatLng(22.3795, 91.8079));
                    innerLatLngs.add(new LatLng(22.3797, 91.8081));
                    innerLatLngs.add(new LatLng(22.3795, 91.8080));

                  /*  List<List<LatLng>> latLngs = new ArrayList<>();
                    latLngs.add(innerLatLngs);

                    FillOptions fillOptions = new FillOptions()
                            .withLatLngs(latLngs)
                            .withFillColor(ColorUtils.colorToRgbaString(Color.RED));

                    fillManager.create(fillOptions);

//                 random add fills across the globe
                    List<FillOptions> fillOptionsList = new ArrayList<>();*/

                   /* for (int i = 0; i < 20; i++) {
                        int color = Color.argb(255, new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
                        fillOptionsList.add(new FillOptions()
                                .withLatLngs(createRandomLatLngs())
                                .withFillColor(ColorUtils.colorToRgbaString(color))
                        );
                    }

                    fillManager.create(fillOptionsList);*/


                        com.mapbox.mapboxsdk.annotations.Polygon hazardPolygon = mapboxMap.addPolygon(new PolygonOptions()
                                .addAll(innerLatLngs)
                                .fillColor(Color.YELLOW)
                                .alpha(0.2f));


                        //add marker



                        for(LatLng  latLng : innerLatLngs ) {

                            IconFactory iconFactory = IconFactory.getInstance(TestActivity.this);
                            Icon icon = iconFactory.fromResource(R.drawable.hazard_60);



                            mapboxMap.addMarker(new MarkerOptions()
                                   .position(new LatLng(latLng.getLatitude(), latLng.getLongitude()))
                                   .icon(icon)
                                   .title("Eiffel Tower"));




                       }


                        for (int i = 0; i < innerLatLngs.size(); i++) {

                            if (i == innerLatLngs.size() - 1) {





                                double distance = calculateDistanceDifferenceInMeter(innerLatLngs.get(i).getLongitude(),
                                        innerLatLngs.get(i).getLatitude(),
                                        innerLatLngs.get(0).getLongitude(),
                                        innerLatLngs.get(0).getLatitude());


                                IconFactory iconFactory = IconFactory.getInstance(TestActivity.this);
                                Icon icon = iconFactory.fromBitmap(getBitmapFromView(String.valueOf(format("%.2f", distance))));



                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(midPoint(innerLatLngs.get(i).getLatitude(), innerLatLngs.get(i).getLongitude(),
                                                innerLatLngs.get(0).getLatitude(), innerLatLngs.get(0).getLongitude()))
                                        .icon(icon));


                            } else {

                                Log.e("midpoint", "l");

                                double distance = calculateDistanceDifferenceInMeter(innerLatLngs.get(i).getLongitude(),
                                        innerLatLngs.get(i).getLatitude(),
                                        innerLatLngs.get(i + 1).getLongitude(),
                                        innerLatLngs.get(i + 1).getLatitude());

                                IconFactory iconFactory = IconFactory.getInstance(TestActivity.this);
                                Icon icon = iconFactory.fromBitmap(getBitmapFromView(String.valueOf(format("%.2f", distance))));


                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(midPoint(innerLatLngs.get(i).getLatitude(), innerLatLngs.get(i).getLongitude(),
                                                innerLatLngs.get(i + 1).getLatitude(), innerLatLngs.get(i + 1).getLongitude()))
                                        .snippet("d")
                                        .icon(icon));
                            }
                        }

                        //convert latlong type to google maps typw
                        List<com.google.android.gms.maps.model.LatLng>latLngList  = new ArrayList<>();

                        for(LatLng latLng : innerLatLngs ){


                            latLngList.add(new com.google.android.gms.maps.model.LatLng(latLng.getLatitude(),latLng.getLongitude()));
                        };



                       double area = SphericalUtil.computeArea(latLngList);




                        // for showing area

                        IconFactory iconFactory = IconFactory.getInstance(TestActivity.this);
                        Icon icon = iconFactory.fromBitmap(getBitmapFromViewForArea(String.valueOf(format("%.2f", area))));

                        mapboxMap.addMarker(new MarkerOptions()
                                .position( new LatLng(centroid(innerLatLngs)[0],centroid(innerLatLngs)[1]))
                                .snippet("d")
                                .icon(icon));




                }
            });




            }
        });

    }


    /*@Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {

        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

                // Use a layer manager here


                            mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {

                    FillManager fillManager = new FillManager(mapView, mapboxMap, style);

                   // fillManager.setIconAllowOverlap(true);
                    //fillManager.setIconIgnorePlacement(true);


                    List<LatLng> innerLatLngs = new ArrayList<>();
                    innerLatLngs.add(new LatLng(22.3795, 91.8079));
                    innerLatLngs.add(new LatLng(22.3797, 91.8081));
                    innerLatLngs.add(new LatLng(22.3795, 91.8080));

                    List<List<LatLng>> latLngs = new ArrayList<>();
                    latLngs.add(innerLatLngs);

                    FillOptions fillOptions = new FillOptions()
                            .withLatLngs(latLngs)
                            .withFillColor(ColorUtils.colorToRgbaString(Color.parseColor("#ff5252")));
                    fillManager.create(fillOptions);

//                 random add fills across the globe
                    List<FillOptions> fillOptionsList = new ArrayList<>();

                   *//* for (int i = 0; i < 20; i++) {
                        int color = Color.argb(255, new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256));
                        fillOptionsList.add(new FillOptions()
                                .withLatLngs(createRandomLatLngs())
                                .withFillColor(ColorUtils.colorToRgbaString(color))
                        );
                    }

                    fillManager.create(fillOptionsList);*//*



                   *//* ScaleBarPlugin scaleBarPlugin = new ScaleBarPlugin(mapView, mapboxMap);

                    // Create a ScaleBarOptions object to use the Plugin's default styling
                    scaleBarPlugin.create(new ScaleBarOptions(getActivity()));*//*
                }
            });



            }
        });

    }*/

     // Add the mapView lifecycle to the activity's lifecycle methods
     @Override
     public void onResume() {
         super.onResume();
         mapView.onResume();
     }

     @Override
     protected void onStart() {
         super.onStart();
         mapView.onStart();
     }

     @Override
     protected void onStop() {
         super.onStop();
         mapView.onStop();
     }

     @Override
     public void onPause() {
         super.onPause();
         mapView.onPause();
     }

     @Override
     public void onLowMemory() {
         super.onLowMemory();
         mapView.onLowMemory();
     }

     @Override
     protected void onDestroy() {
         super.onDestroy();
         mapView.onDestroy();
     }

     @Override
     protected void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
         mapView.onSaveInstanceState(outState);
     }


     public double calculateDistanceDifferenceInMeter(double startLng, double startLat,
                                                      double endLng, double endLat) {

         Location startPoint = new Location("locationStart");
         startPoint.setLatitude(startLat);
         startPoint.setLongitude(startLng);

         Location endPoint = new Location("locationEnd");
         endPoint.setLatitude(endLat);
         endPoint.setLongitude(endLng);

         double distance = startPoint.distanceTo(endPoint);


         return distance;


     }

     public LatLng midPoint(double lat1, double lon1, double lat2, double lon2) {

         double dLon = Math.toRadians(lon2 - lon1);

         //convert to radians
         lat1 = Math.toRadians(lat1);
         lat2 = Math.toRadians(lat2);
         lon1 = Math.toRadians(lon1);

         double Bx = Math.cos(lat2) * Math.cos(dLon);
         double By = Math.cos(lat2) * Math.sin(dLon);
         double lat3 = Math.atan2(Math.sin(lat1) + Math.sin(lat2), Math.sqrt((Math.cos(lat1) + Bx) * (Math.cos(lat1) + Bx) + By * By));
         double lon3 = lon1 + Math.atan2(By, Math.cos(lat1) + Bx);

         return new LatLng(Math.toDegrees(lat3), Math.toDegrees(lon3));

     }


     private Bitmap getBitmapFromView(String value) {
         //    View customView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_text_layout, null);

         View customView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_text, null);

         TextView textView = customView.findViewById(R.id.textView);
         textView.setText(value + " " + "m ");
         customView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
         customView.layout(0, 0, customView.getMeasuredWidth(), customView.getMeasuredHeight());
         customView.buildDrawingCache();
         Bitmap returnedBitmap = Bitmap.createBitmap(customView.getMeasuredWidth(), customView.getMeasuredHeight(),
                 Bitmap.Config.ARGB_8888);
         Canvas canvas = new Canvas(returnedBitmap);
         canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
         Drawable drawable = customView.getBackground();
         if (drawable != null) {
             drawable.draw(canvas);
         }
         customView.draw(canvas);
         return returnedBitmap;
     }


     private Bitmap getBitmapFromViewForArea(String value) {
         //    View customView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_text_layout, null);

         View customView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_text_area, null);

         TextView textView = customView.findViewById(R.id.textView);
         textView.setText(value + " " + "m²");
         customView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
         customView.layout(0, 0, customView.getMeasuredWidth(), customView.getMeasuredHeight());
         customView.buildDrawingCache();
         Bitmap returnedBitmap = Bitmap.createBitmap(customView.getMeasuredWidth(), customView.getMeasuredHeight(),
                 Bitmap.Config.ARGB_8888);
         Canvas canvas = new Canvas(returnedBitmap);
         canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
         Drawable drawable = customView.getBackground();
         if (drawable != null) {
             drawable.draw(canvas);
         }
         customView.draw(canvas);
         return returnedBitmap;
     }


     public static double[] centroid(List<LatLng> points) {
         double[] centroid = { 0.0, 0.0 };

         for (int i = 0; i < points.size(); i++) {
             centroid[0] += points.get(i).getLatitude();
             centroid[1] += points.get(i).getLongitude();
         }

         int totalPoints = points.size();
         centroid[0] = centroid[0] / totalPoints;
         centroid[1] = centroid[1] / totalPoints;

         return centroid;
     }


}