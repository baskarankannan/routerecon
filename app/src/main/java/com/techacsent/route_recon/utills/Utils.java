package com.techacsent.route_recon.utills;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mapbox.mapboxsdk.annotations.PolygonOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.techacsent.route_recon.application.RouteApplication;
import com.techacsent.route_recon.model.ErrorResponse;
import com.techacsent.route_recon.model.ReceivedShareTripRequest;
import com.techacsent.route_recon.model.SystemSecurityModel;
import com.techacsent.route_recon.room_db.entity.MarkerDescription;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import retrofit2.Response;
import timber.log.Timber;


public class Utils {

    public static void getHashSignatureForLogin() {
        Constant.JWT_HASH_SIGNETURE_FOR_LOGIN = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, Constant.JWT_SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
                .claim("email", "aaa")
                .claim("password", "aa")
                .compact();
        Timber.i(Constant.JWT_HASH_SIGNETURE_FOR_LOGIN);
        //PreferenceManager.updateValue(Constant.KEY_JWT_HASH_SIGNETURE_FOR_LOGIN, Constant.JWT_HASH_SIGNETURE_FOR_LOGIN);
    }

    public static void getHashSignatureFromTokenAndUserId() {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, Constant.JWT_SECURITY_KEY.getBytes(StandardCharsets.UTF_8))
                .claim("tokenId", PreferenceManager.getString(Constant.KEY_TOKEN_ID))
                .claim("userID", PreferenceManager.getInt(Constant.KEY_USER_ID))
                .compact();

        PreferenceManager.updateValue(Constant.JWT_HASH_SIGNETURE_FROM_TOKEN_USERID, jwt);

    }


    public static String showErrorMessage(Response response) {
        String errorMessage = null;
        JSONObject jObjError = null;
        try {
            if (response.errorBody() != null) {
                jObjError = new JSONObject(response.errorBody().string());
            }
            JSONObject errorObj = null;
            if (jObjError != null) {
                errorObj = jObjError.getJSONObject("error");
            }
            if (errorObj != null) {
                errorMessage = errorObj.get("message").toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorMessage;
    }

   /* public static void showSnackbarText(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();

    }*/

    public static void handleError(Response<?> response) {

        Gson gson = new GsonBuilder().create();
        ErrorResponse mError = new ErrorResponse();
        try {
            mError = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            Toast.makeText(RouteApplication.getInstance().getApplicationContext(), mError.getResponseJSON().getError().getMessage(), Toast.LENGTH_LONG).show();
            /*int code = mError.getStatus();
            if (code == 401) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), mError.getResponseJSON().getError().getMessage(), Toast.LENGTH_LONG).show();
            } else if (code == 400) {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), mError.getResponseJSON().getError().getMessage(), Toast.LENGTH_LONG).show();
            } *//*else {
                Toast.makeText(RouteApplication.getInstance().getApplicationContext(), mError.getResponseJSON().getError().getMessage(), Toast.LENGTH_LONG).show();
            }*/

        } catch (IOException e) {
            // handle failure to read error
        } catch (IllegalStateException i) {
            // handle failure to read error
        } catch (JsonSyntaxException je) {

        }

    }

    @SuppressLint("HardwareIds")
    public static String getDeviceId() {
        return Settings.Secure.getString(RouteApplication
                        .getInstance()
                        .getApplicationContext()
                        .getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /*public static ArrayList<LatLng> getCirclePoints(LatLng position, double radius) {
        int degreesBetweenPoints = 10; // change here for shape
        int numberOfPoints = (int) Math.floor(360 / degreesBetweenPoints);
        double distRadians = radius / 6371000.0; // earth radius in meters
        double centerLatRadians = position.getLatitude() * Math.PI / 180;
        double centerLonRadians = position.getLongitude() * Math.PI / 180;
        ArrayList<LatLng> polygons = new ArrayList<>(); // array to hold all the points
        for (int index = 0; index < numberOfPoints; index++) {
            double degrees = index * degreesBetweenPoints;
            double degreeRadians = degrees * Math.PI / 180;
            double pointLatRadians = Math.asin(sin(centerLatRadians) * cos(distRadians)
                    + cos(centerLatRadians) * sin(distRadians) * cos(degreeRadians));
            double pointLonRadians = centerLonRadians + Math.atan2(sin(degreeRadians)
                            * sin(distRadians) * cos(centerLatRadians),
                    cos(distRadians) - sin(centerLatRadians) * sin(pointLatRadians));
            double pointLat = pointLatRadians * 180 / Math.PI;
            double pointLon = pointLonRadians * 180 / Math.PI;
            LatLng point = new LatLng(pointLat, pointLon);
            polygons.add(point);
        }
        polygons.add(polygons.get(0));
        return polygons;
    }*/

    public static String getAddress(double lat,double lng, boolean isFullAddress, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String address = addresses.get(0).getAddressLine(0); //0 to obtain first possible address
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            if (isFullAddress) {
                return address.concat("," + city).concat("," + country);
            } else {
                return address;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return "";

    }

   /* public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    }*/

    /*public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }*/

    public static PolygonOptions generatePerimeter(LatLng centerCoordinates, double radiusInKilometers, int numberOfSides) {
        List<LatLng> positions = new ArrayList<>();
        double distanceX = radiusInKilometers / (111.319 * Math.cos(centerCoordinates.getLatitude() * Math.PI / 180));
        double distanceY = radiusInKilometers / 110.574;

        double slice = (2 * Math.PI) / numberOfSides;

        double theta;
        double x;
        double y;
        LatLng position;
        for (int i = 0; i < numberOfSides; ++i) {
            theta = i * slice;
            x = distanceX * Math.cos(theta);
            y = distanceY * Math.sin(theta);

            position = new LatLng(centerCoordinates.getLatitude() + y,
                    centerCoordinates.getLongitude() + x);
            positions.add(position);
        }
        return new PolygonOptions()
                .addAll(positions)
                .fillColor(Color.RED)
                .alpha(0.1f);
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin((lat1 * Math.PI / 180.0))
                * Math.sin((lat2 * Math.PI / 180.0))
                + Math.cos((lat1 * Math.PI / 180.0))
                * Math.cos((lat2 * Math.PI / 180.0))
                * Math.cos(theta * Math.PI / 180.0);
        dist = Math.acos(dist);
        dist = (double) (dist * 180.0 / Math.PI);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }



    public static double getDistance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6371;   // Earth radius in km
        double deltaLat = Math.toRadians(toLat - fromLat);
        double deltaLon = Math.toRadians(toLon - fromLon);
        double lat1 = Math.toRadians(fromLat);
        double lat2 = Math.toRadians(toLat);
        double aVal = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double cVal = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));

        return radius * cVal;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static List<MarkerDescription> sortLocations(List<MarkerDescription> locations, final double myLatitude, final double myLongitude) {
        Comparator comp = new Comparator<Location>() {
            @Override
            public int compare(Location o, Location o2) {
                float[] result1 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude, o.getLatitude(), o.getLongitude(), result1);
                Float distance1 = result1[0];

                float[] result2 = new float[3];
                android.location.Location.distanceBetween(myLatitude, myLongitude, o2.getLatitude(), o2.getLongitude(), result2);
                Float distance2 = result2[0];

                return distance1.compareTo(distance2);
            }
        };


        Collections.sort(locations, comp);
        return locations;
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void getSystemList(List<SystemSecurityModel> listofOption, int flag) {
        switch (flag) {
            case 0:
                listofOption.add(new SystemSecurityModel("AA", "Do you have at least three different routes so you can vary your route to and from work and all other primary areas?"));
                listofOption.add(new SystemSecurityModel("AA", "Do you vary your routes?"));
                listofOption.add(new SystemSecurityModel("AA", "Do you know how many choke points exist on each route and their boundaries?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified your critical navigation areas?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you prioritized the choke points and critical navigation areas in terms of how valuable they will be to an attacker?"));
                listofOption.add(new SystemSecurityModel("AA", "Is it possible for surveillance to be conducted in these areas for the time required to develop an attack plan?"));
                listofOption.add(new SystemSecurityModel("AA", "Can you be controlled long enough in these areas for the attack to be effective?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified the most likely covers or ruses your enemies would use to operate in these areas and how long they can maintain these ruses or covers?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified whether the attack will be a standoff or close-in attack, based on the layout of each area?"));
                listofOption.add(new SystemSecurityModel("AA", "Do these areas offer any other tactical advantages to the threat?"));
                listofOption.add(new SystemSecurityModel("AA", "Do they offer any tactical advantages to you?"));
                listofOption.add(new SystemSecurityModel("AA", "Are there any escape routes in these areas?"));
                listofOption.add(new SystemSecurityModel("AA", "What types of attacks has the threat carried out in the past?"));
                listofOption.add(new SystemSecurityModel("AA", "Do you know the most likely position for surveillance in the choke points and critical navigation areas?"));
                listofOption.add(new SystemSecurityModel("AA", "Do you have a plan to detect this surveillance in these areas?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified the best attack sites along each route?"));
                listofOption.add(new SystemSecurityModel("AA", "Is one of these attack sites near a choke point? And is that near your residence or work?"));
                listofOption.add(new SystemSecurityModel("AA", "Do you or your security always check with the correct officials or companies about the validity of utility crews in your attack sites, choke points, critical navigation areas, and the areas right before you enter each?"));
                listofOption.add(new SystemSecurityModel("AA", "At the potential attack site, choke point, and critical navigation area, will varying your times make a difference in the environment, lessening your chances of being a target"));
                listofOption.add(new SystemSecurityModel("AA", "Do you know how much of a time difference it would take, and can you make the adjustment?"));
                listofOption.add(new SystemSecurityModel("AA", "Do the choke points or critical navigation areas meet the requirements for a good attack site, using past threat attacks as a reference?"));
                listofOption.add(new SystemSecurityModel("AA", "Can your high-threat areas be covered by counter surveillance or surveillance detection?"));
                listofOption.add(new SystemSecurityModel("AA", "Can the lead vehicle in a convoy or motorcade travel through these areas at least 30 seconds ahead of you"));
                break;

            case 1:
                listofOption.add(new SystemSecurityModel("AA", "What is the date and time that the route is to be used?"));
                listofOption.add(new SystemSecurityModel("AA", "Holidays-different traffic flow"));
                listofOption.add(new SystemSecurityModel("AA", "Weekends—different traffic flow"));
                listofOption.add(new SystemSecurityModel("AA", "If near factories, government buildings, schools, major entertainment facilities"));
                listofOption.add(new SystemSecurityModel("AA", "What are the shift changes or when crowds are released?"));
                listofOption.add(new SystemSecurityModel("AA", "Train tracks"));
                listofOption.add(new SystemSecurityModel("AA", "Bridges, tunnels"));
                listofOption.add(new SystemSecurityModel("AA", "Overpasses, underpasses"));
                listofOption.add(new SystemSecurityModel("AA", "Construction areas"));
                listofOption.add(new SystemSecurityModel("AA", "Special events, parades, demonstrations, sporting events"));
                listofOption.add(new SystemSecurityModel("AA", "Parks and wooded areas"));
                listofOption.add(new SystemSecurityModel("AA", "Number of intersections, stop lights, and stop signs"));
                listofOption.add(new SystemSecurityModel("AA", "Number of buildings and windows"));
                listofOption.add(new SystemSecurityModel("AA", "Storage areas, mail boxes, sidewalk obstruction"));
                listofOption.add(new SystemSecurityModel("AA", "Pedestrian traffic"));
                listofOption.add(new SystemSecurityModel("AA", "SAFE HAVENS - Hospitals, police stations, fire stations, medical facilities"));
                listofOption.add(new SystemSecurityModel("AA", "Detailed routes should be completed with accompanying map route cards"));
                listofOption.add(new SystemSecurityModel("AA", "Alternative routes in detail with accompanying maps or route cards"));
                listofOption.add(new SystemSecurityModel("AA", "Time and date survey must be conducted"));
                break;

            case 2:
                listofOption.add(new SystemSecurityModel("AA", "Have you identified three routes to all your primary locations?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified safe havens along all routes and primary areas?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified choke points and danger areas where vehicular movement is restricted and you are forced to slow down, or your movement and speed are dictated by some other means outside your control?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified travel times and distances to all primary areas and safe havens for each route?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified hospitals throughout the routes and time and distances to them"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified checkpoints along the route?"));
                listofOption.add(new SystemSecurityModel("AA", "Have you identified observation points for conducting fixed-site counter surveillance along your routes?"));
                break;

            case 3:
                listofOption.add(new SystemSecurityModel("AA", "Is the movement formal or informal"));
                listofOption.add(new SystemSecurityModel("AA", "Is the route or time predictable?"));
                listofOption.add(new SystemSecurityModel("AA", "Is the route the shortest one possible?"));
                listofOption.add(new SystemSecurityModel("AA", "Does it provide minimum exposure?"));
                listofOption.add(new SystemSecurityModel("AA", "Is there an alternative route available?"));
                listofOption.add(new SystemSecurityModel("AA", "Where are the danger points?"));
                listofOption.add(new SystemSecurityModel("AA", "What is the best evacuation route in the event of an emergency?"));
                listofOption.add(new SystemSecurityModel("AA", "Is there a safe area available?"));
                listofOption.add(new SystemSecurityModel("AA", "Where is the nearest medical facility and what is the best route to it?"));
                listofOption.add(new SystemSecurityModel("AA", "Are there any physical hazards such as construction sites, bad footing, vicious dogs, toverhead balconies, etc."));
                listofOption.add(new SystemSecurityModel("AA", "What types of formations are best suited for movement?"));
                listofOption.add(new SystemSecurityModel("AA", "What support is available or needed from other security/law enforcement services?"));
                listofOption.add(new SystemSecurityModel("AA", "Number, type, and location of security posts?"));
                listofOption.add(new SystemSecurityModel("AA", "Is there direct communication with the command post or embassy?"));
                listofOption.add(new SystemSecurityModel("AA", "What special equipment is needed?"));
                listofOption.add(new SystemSecurityModel("AA", "How many people will be in the movement?"));
                break;

            case 4:
                listofOption.add(new SystemSecurityModel("AA", "Know the threat"));
                listofOption.add(new SystemSecurityModel("AA", "Be aware of your location at all times"));
                listofOption.add(new SystemSecurityModel("AA", "Practice surveillance detection all the time, at every location"));
                listofOption.add(new SystemSecurityModel("AA", "Be at your highest level of alertness when entering or leaving your vehicle"));
                listofOption.add(new SystemSecurityModel("AA", "When you’re outside your vehicle, stand facing it, which offers the greatest view of the exterior"));
                listofOption.add(new SystemSecurityModel("AA", "Be suspicious of anything that causes you to stop your vehicle"));
                listofOption.add(new SystemSecurityModel("AA", "Don’t stop to help anyone; use a phone and call for help"));
                listofOption.add(new SystemSecurityModel("AA", "Plan ahead while moving; play the what-if game"));
                listofOption.add(new SystemSecurityModel("AA", "Do not allow yourself to have a routine or become time or place predictable"));
                listofOption.add(new SystemSecurityModel("AA", "Know the routines, vehicles, and people in your neighborhood and around your work sites"));
                listofOption.add(new SystemSecurityModel("AA", "Use major thoroughfares, roads, and streets when possible"));
                listofOption.add(new SystemSecurityModel("AA", "If stopped, move as quickly as you can around the obstruction"));
                listofOption.add(new SystemSecurityModel("AA", "Always be on the lookout for any situations where your vehicle may become blocked, and take preemptive action"));
                listofOption.add(new SystemSecurityModel("AA", "Take note of and report any unusual activity as soon as possible"));
                listofOption.add(new SystemSecurityModel("AA", "Know all your choke points, danger areas, and zones of predictability, and be extra alert when approaching these areas"));
                listofOption.add(new SystemSecurityModel("AA", "Never assume that someone else is aware of and taking care of an issue when it comes to your personal security"));
                listofOption.add(new SystemSecurityModel("AA", "Always assume that the worst will happen and have a plan for it"));
                listofOption.add(new SystemSecurityModel("AA", "Rehearse all your emergency action plans, at home, en route, and at the office; the more you practice, the quicker you will be able to respond."));
                listofOption.add(new SystemSecurityModel("AA", "Do not become complacent!"));
                break;

            case 5:
                listofOption.add(new SystemSecurityModel("AA", "Are parked in the same spot for long periods of time with people sitting in the front seat or in a prohibited zone"));
                listofOption.add(new SystemSecurityModel("AA", "Are driving erratically, too fast or too slow, or appear to be rounding corners slowly"));
                listofOption.add(new SystemSecurityModel("AA", "Follow you through a red light"));
                listofOption.add(new SystemSecurityModel("AA", "Maintain the same distance from you at varying speeds"));
                listofOption.add(new SystemSecurityModel("AA", "Try to “hide” in or behind traffic"));
                listofOption.add(new SystemSecurityModel("AA", "Flash lights between vehicles."));
                listofOption.add(new SystemSecurityModel("AA", "Pause in traffic circles until you take an exit."));
                listofOption.add(new SystemSecurityModel("AA", "Close in on you in heavy traffic and drop back in light traffic"));
                listofOption.add(new SystemSecurityModel("AA", "Stop and pull over when you stop"));
                listofOption.add(new SystemSecurityModel("AA", "Park without occupants getting out"));
                listofOption.add(new SystemSecurityModel("AA", "Vehicles with obstructed license plates, especially if they are in an ideal attack site"));
                listofOption.add(new SystemSecurityModel("AA", "Vehicles where the occupants appear to be involved in a surveillance shift change"));
                listofOption.add(new SystemSecurityModel("AA", "Park without occupants getting out"));
                listofOption.add(new SystemSecurityModel("AA", "Turn away when observed"));
                listofOption.add(new SystemSecurityModel("AA", "Are running"));
                listofOption.add(new SystemSecurityModel("AA", "Exit from a vehicle when you stop"));
                listofOption.add(new SystemSecurityModel("AA", "Hesitate or lock around when entering a building that you just entered"));
                listofOption.add(new SystemSecurityModel("AA", "Stop or move when you do."));
                listofOption.add(new SystemSecurityModel("AA", "Read newspapers or magazines while standing in lobbies or on the street"));
                listofOption.add(new SystemSecurityModel("AA", "Appear inappropriately dressed for the area"));
                listofOption.add(new SystemSecurityModel("AA", "Is part of a work crew in or on a choke point"));
                listofOption.add(new SystemSecurityModel("AA", "Are seen in more than one choke point or more than once in the same choke point."));
                break;


            case 6:
                listofOption.add(new SystemSecurityModel("AA", "2 full-size spare tire"));
                listofOption.add(new SystemSecurityModel("AA", "4 cans of a Fix-A-Flat"));
                listofOption.add(new SystemSecurityModel("AA", "Extra tow strap (tow straps should be pre-mounted in all vehicles)"));
                listofOption.add(new SystemSecurityModel("AA", "Rope"));
                listofOption.add(new SystemSecurityModel("AA", "Snap links"));
                listofOption.add(new SystemSecurityModel("AA", "Jack"));
                listofOption.add(new SystemSecurityModel("AA", "2 tire blocks"));
                listofOption.add(new SystemSecurityModel("AA", "2 or more fire extinguishers"));
                listofOption.add(new SystemSecurityModel("AA", "Spare hoses and belts"));
                listofOption.add(new SystemSecurityModel("AA", "Replacement vehicle fluids"));
                listofOption.add(new SystemSecurityModel("AA", "Water for vehicle and drinking purposes"));
                listofOption.add(new SystemSecurityModel("AA", "Flashlights/chemical lights"));
                listofOption.add(new SystemSecurityModel("AA", "Jumper cables"));
                listofOption.add(new SystemSecurityModel("AA", "Epoxy putty (can be used to repair radiators and oil pans in an emergency)"));
                listofOption.add(new SystemSecurityModel("AA", "Pry bar for helping to open stuck doors"));
                listofOption.add(new SystemSecurityModel("AA", "2 sledgehammers"));
                listofOption.add(new SystemSecurityModel("AA", "Charger for cell phones and other devices"));
                listofOption.add(new SystemSecurityModel("AA", "GPS (should be on during movement to track your route)"));
                listofOption.add(new SystemSecurityModel("AA", "Digital and/or video camera"));
                break;

        }

    }

    public static double getElevationFromGoogleMaps(double longitude, double latitude) {
        double result = Double.NaN;
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        String url = "http://maps.googleapis.com/maps/api/elevation/"
                + "xml?locations=" + String.valueOf(latitude)
                + "," + String.valueOf(longitude)
                + "&sensor=true";
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(httpGet, localContext);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                int r = -1;
                StringBuffer respStr = new StringBuffer();
                while ((r = instream.read()) != -1)
                    respStr.append((char) r);
                String tagOpen = "<elevation>";
                String tagClose = "</elevation>";
                if (respStr.indexOf(tagOpen) != -1) {
                    int start = respStr.indexOf(tagOpen) + tagOpen.length();
                    int end = respStr.indexOf(tagClose);
                    String value = respStr.substring(start, end);
                    result = (double) (Double.parseDouble(value) * 3.2808399); // convert from meters to feet
                }
                instream.close();
            }
        } catch (ClientProtocolException ignored) {
        } catch (IOException ignored) {
        }

        return result;
    }

    public static double calculateDistanceDifferenceInMeter(double startLng, double startLat,
                                                            double endLng, double endLat){

        Location startPoint=new Location("locationStart");
        startPoint.setLatitude(startLat);
        startPoint.setLongitude(startLng);

        Location endPoint=new Location("locationEnd");
        endPoint.setLatitude(endLat);
        endPoint.setLongitude(endLng);

        double distance=startPoint.distanceTo(endPoint);


        return  distance;

    }

    public  static boolean checkTrackingStatus(){

        return  PreferenceManager.getBool(Constant.KEY_IS_TRACKING_STARTED);

    }


    public  static void updateTripShareReceiveList( String tripId, String userName, String tripName){

        Gson gson = new Gson();


        String tripSendReceived = getSendTripReceiveList();

        if (tripSendReceived.isEmpty()) {

            ArrayList<ReceivedShareTripRequest>receivedShareTripRequestList = new ArrayList<>();

            ReceivedShareTripRequest receivedShareTripRequests = new ReceivedShareTripRequest(tripId,userName,tripName);

            receivedShareTripRequestList.add(receivedShareTripRequests);

            String receivedShareTripRequestListString = gson.toJson(receivedShareTripRequestList);

            PreferenceManager.updateValue(Constant.KEY_TRIP_SEND_RECEIVED_LIST,receivedShareTripRequestListString);


        } else {


            Type sendListType = new TypeToken<ArrayList<ReceivedShareTripRequest>>() {}.getType();

            ArrayList<ReceivedShareTripRequest> receivedTripSendList = gson.fromJson(tripSendReceived, sendListType);

            receivedTripSendList.add(new ReceivedShareTripRequest(tripId,userName,tripName));


            PreferenceManager.updateValue(Constant.KEY_TRIP_SEND_RECEIVED_LIST,gson.toJson(receivedTripSendList));



        }

    }

    public  static String getSendTripReceiveList(){


        return PreferenceManager.getString(Constant.KEY_TRIP_SEND_RECEIVED_LIST);


    }

    public  static  void removeTripRequestFromLocalStorage (){

        Gson gson = new Gson();


        Type sendListType = new TypeToken<ArrayList<ReceivedShareTripRequest>>() {}.getType();

        ArrayList<ReceivedShareTripRequest> receivedTripSendList = gson.fromJson(getSendTripReceiveList(), sendListType);


        //receivedTripSendList.clear();

        PreferenceManager.updateValue(Constant.KEY_TRIP_SEND_RECEIVED_LIST,"");
    }

    public  static  double getMilesFromMeter( Double distanceInMeter){

        return  distanceInMeter*0.00062137119 ;

    }

    public  static  double getFeetFromMeter( Double distanceInMeter){

        return  distanceInMeter*3.281 ;

    }



}
