package com.techacsent.route_recon.utills;

import java.sql.Struct;

import okhttp3.MediaType;

public class Constant {
    public static final String REALM_BD = "com.techacsent.route_recon.realm";
    public static final String ROOM_DB_NAME = "com.techacsent.route_recon.room";
    public static final long INTERVAL_TIME = 5000;
    public static String JWT_HASH_SIGNETURE_FOR_LOGIN = "";
    public static String JWT_SECURITY_KEY = "1234567890";
    public static final String BUNDLE_KEY_TRIP_DETAILS_PARCELABLE = "BUNDLE_KEY_TRIP_DETAILS_PARCELABLE";
    public static final String X_TRAVEL_PLANNER_TOKEN = "X-TRAVEL-PLANNER-TOKEN";
    public static final String DEFAULT_ADDRESS = "Undefined";
    public static final String SHARED_TRIP = "Shared Routes";
    public static final String MAPS = "Map";
    public static final String Polygon = "POLYGON";
    public static final String TRACKED_TRIPS = "TRACKED TRIPS";
    public static final String TRACKER = "Tracker";
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final String MAPBOX_API_KEY = "pk.eyJ1Ijoic2lvLXRlc3RlcjAxIiwiYSI6ImNqNWk4ajl3YjFzOHMzMm56ZTNtbDVpbWsifQ.Zwybd4RL0ByxYb5ybEiPFA";
    public static final String JWT_HASH_SIGNETURE_FROM_TOKEN_USERID = "JWT_HASH_SIGNETURE_FROM_TOKEN_USERID";
    public static final String BASE_URL = "https://devapi.routerecon.com/";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String EMPTY_WARNING = "Please enter email";
    public static final String EMPTY_FIELD_WARNING = "Please fill all the field";
    public static final String PASSWORD_UNMATCHED_WARNING = "Password didn't match";
    public static final String PASSWORD_WARNING = "Password should be at least 6 character";
    public static final String PASSWORD_MISMATCH_WARNING = "Password didn't match";
    public static final String SHARED_PREFERENCE = "com.techacsent.route_recon";
    public static final String EMERGENCY_NUMBER = "+911";
    public static final String DEFAULT_DATE = "0000-00-00 00:00:00";
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT = "h:mm aa";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String EMERGENCY_SOS = "Emergency SOS";
    public static final int TOTAL_TIME_INTERVAL = 2000;
    public static final int ZOOM_IN_MAP = 16;
    public static final int MORE_ZOOM_IN_MAP = 20;
    public static final String TEAL_COLOR = "#2F7AC6";//#23D2BE
    public static final int POLYLINE_WIDTH = 5;
    public static  boolean IS_FRIEND_CHECKED = false;
    public static final int COMPRESSED_IMAGE_QUALITY = 60;
    public static final int POLYGON = 4;

    public static final String MSG_UPDATE_TRIP = "Update Trip!";
    public static final String MSG_TRIP_NAME_EMPTY = "Trip name should not be empty";
    public static final String MSG_EMPTY_PLACE_SELECTED = "You haven't select any place yet";
    public static final String MSG_START_POINT_NAME_EMPTY = "Trip Start Point should not be empty";
    public static final String MSG_DEST_POINT_NAME_EMPTY = "Trip Destination Point should not be empty";
    public static final String MSG_TRIP_BEGAN_TIME_EMPTY = "Trip Start time should not be empty";
    public static final String MSG_TRIP_END_TIME_EMPTY = "Trip End time should not be empty";
    public static final String MSG_SELECT_AT_LEAST_ONE_USER = "Please Select at least one user or group";
    public static final String MSG_TRIP_CREATED_OK = "Trip Created Successfully";
    public static final String MSG_TRIP_CREATION_FAILED = "Trip Creation Failed";
    public static final String MSG_SELECT_DURATION = "Please Select Time Interval";
    public static final String MSG_SOMETHING_WENT_WRONG = "Something Went Wrong..";
    public static final String PARCELABLE_TRIP_RESPONSE = "PARCELABLE_TRIP_RESPONSE";
    public static final String MSG_ENTER_GROUP_NAME = "Please Enter Grouop Name";
    public static final String MSG_SYNC_WITH_INTERNET = "Do you want to sync your Data with internet?";
    public static final String MSG_WAIT = "Please wait";
    public static final String MSG_FEATURE_UNDER_DEVELOPMENT = "Feature Under Development";
    public static final String MSG_USER_SELECT_WARNING = "Please select at least one user";
    public static final String MSG_GROUP_NAME_WARNING = "Please add a group name";
    public static final String MSG_EMAIL_VALIDATION = "Please Enter a valid Email";
    public static final String MSG_TERMS_AND_CONDITION = " terms & condition";
    public static final String MSG_PRIVACY_POLICY = "privacy policy";
    public static final String MSG_BY_SIGNING_UP = "By signing up you are agree to out";
    public static final String MSG_ALREADY_ACCOUNT = "Already have an account? ";
    public static final String MSG_DONT_HAVE_ACCOUNT = "Do not have an account? ";
    public static final String MSG_NOTHING_TO_SYNC = "Nothing to Sync";
    public static final String MSG_FORGOT_PASSWORD = "Having trouble logging in?";
    public static final String MSG_CREATE_NEW = "Create New!";

    public static final String KEY_TRIP_SEND_RECEIVED_LIST = "trip_send_receive_list";



    public static final String KEY_USER_ID = "KEY_USER_ID";
    public static final String KEY_IS_FIRST_RUN = "KEY_IS_FIRST_RUN";
    public static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    public static final String KEY_IS_ELEVATION_IN_METER = "KEY_IS_ELEVATION_IN_METER";
    public static final String KEY_UNIT_COORDINATE = "unit_coordinate";
    public static final String KEY_SUBSCRIPTION_DATE = "KEY_SUBSCRIPTION_DATE";
    public static final String KEY_USER_NAME_SEARCH = "KEY_USER_NAME_SEARCH";
    public static final String KEY_SUBSCRIPTION_STATUS = "KEY_SUBSCRIPTION_STATUS";
    public static final String KEY_LOCATION_TRACK_START_TIME = "location_track_start_time";
    public static final String KEY_IS_TRACKING_STARTED = "is_tracking_started";
    public static final String KEY_IS_TRIP_NAME = "trip_name";
    public static final String KEY_TRACKED_TIME = "time";
    public static final String KEY_TRACKED_DISTANCE = "distance";
    public static final String KEY_TRACKED_SPEED = "speed";
    public static final String KEY_TOKEN_ID = "KEY_TOKEN_ID";
    public static final String KEY_LATITUDE = "KEY_LATITUDE";
    public static final String KEY_LONGITUDE = "KEY_LONGITUDE";
    public static final String KEY_IS_LOCATION_SHARING = "KEY_IS_LOCATION_SHARING";
    public static final String KEY_PARCEL_MARKER_OBJ = "KEY_PARCEL_MARKER_OBJ";
    public static final String KEY_PARCEL_MARKER_LIST = "KEY_PARCEL_MARKER_LIST";
    public static final String KEY_GROUP_ID = "KEY_GROUP_ID";
    public static final String KEY_GROUP_NAME = "KEY_GROUP_NAME";
    public static final String KEY_IS_LOGGED_IN = "KEY_IS_LOGGED_IN";
    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_PARCELABLE_NEXT_TRIP_LIST = "KEY_PARCELABLE_NEXT_TRIP_LIST";
    public static final String KEY_PARCELABLE_LAST_TRIP_LIST = "KEY_PARCELABLE_LAST_TRIP_LIST";
    public static final String KEY_PARCELABLE_MARKER_OBJ = "KEY_PARCELABLE_MARKER_OBJ";
    public static final String KEY_START_TIME = "KEY_START_TIME";
    public static final String KEY_DURATION = "KEY_DURATION";
    public static final String KEY_END_TIME = "KEY_END_TIME";
    public static final String KEY_LOCATION_SHARE_ID = "KEY_LOCATION_SHARE_ID";
    public static final String KEY_IS_UPDATE = "KEY_IS_UPDATE";
    public static final String KEY_IS_SHARE = "KEY_IS_SHARE";
    public static final String KEY_PARCELABLE_TRIP_DETAILS = "KEY_PARCELABLE_TRIP_DETAILS";
    public static final String KEY_IS_EMERGENCY_CONTACT_LOADED = "KEY_IS_EMERGENCY_CONTACT_LOADED";
    public static final String KEY_TRIP_ID = "KEY_TRIP_ID";
    public static final String KEY_IS_LOADED_TRIPS = "KEY_IS_LOADED_TRIPS";
    public static final String KEY_IS_MARKER_LOAD = "KEY_IS_MARKER_LOAD";
    public static final String KEY_TYPE_OF_RADIUS = "KEY_TYPE_OF_RADIUS";
    public static final String KEY_USER_LIST_BY_NAME = "KEY_USER_LIST_BY_NAME";
    public static final String KEY_IS_LIVE_LOCATION_SHARE_EDIT = "KEY_IS_LIVE_LOCATION_SHARE_EDIT";
    public static final String KEY_BROADCAST_RECEIVER = "com.techacsent.broadcast_receiver.receiver_locaation_update";
    public static final String KEY_FIREBASE_TOKEN_ID = "KEY_FIREBASE_TOKEN_ID";
    public static final String KEY_IS_FROM_NOTIFICATION = "KEY_IS_FROM_NOTIFICATION";
    public static final String KEY_FLAG = "KEY_FLAG";
    public static final String KEY_PARCEL_MY_TRIP_DESCRIPTION = "KEY_PARCEL_MY_TRIP_DESCRIPTION";
    public static final String KEY_TRIP_LIST = "Trip List";
    public static final String KEY_CREATE_TRIP = "Create Trip";
    public static final String KEY_PARCEL_CREATE_TRIP_RESPONSE = "KEY_PARCEL_CREATE_TRIP_RESPONSE";
    public static final String KEY_PARCEL_UPDATE_TRIP_RESPONSE = "KEY_PARCEL_UPDATE_TRIP_RESPONSE";
    public static final String KEY_IS_CONTACT_SYNCED = "KEY_IS_CONTACT_SYNCED";
    public static final String KEY_CREATE_TRIP_RESPONSE = "KEY_CREATE_TRIP_RESPONSE";
    public static final String KEY_SHARED_USER_RESPONSE = "KEY_SHARED_USER_RESPONSE";
    public static final String KEY_MARKER_RESPONSE = "KEY_MARKER_RESPONSE";
    public static final String KEY_MARKER_INFO = "KEY_MARKER_INFO";
    public static final String KEY_TRIP_MARKER_ID = "KEY_TRIP_MARKER_ID";
    public static final String KEY_IS_MARKER_LOADED = "KEY_IS_MARKER_LOADED";
    public static final String KEY_IS_FINISHED = "KEY_IS_FINISHED";
    public static final String KEY_WAS_IN_TUNNEL = "KEY_WAS_IN_TUNNEL";
    public static final String KEY_IS_BADGE_LOADED = "KEY_IS_BADGE_LOADED";
    public static final String KEY_FRIEND_REQ_BADGE_COUNT = "KEY_FRIEND_REQ_BADGE_COUNT";
    public static final String KEY_TRIP_SHARING_BADGE_COUNT = "KEY_TRIP_SHARING_BADGE_COUNT";
    public static final String KEY_LOCATION_SHARING_BADGE_COUNT = "KEY_LOCATION_SHARING_BADGE_COUNT";
    public static final String KEY_MAP_STYLE_ID = "KEY_MAP_STYLE_ID";
    public static final String KEY_MAPBOX_STYLE_VALUE = "KEY_MAPBOX_STYLE_VALUE";
    public static final String KEY_IS_TRAFFIC_SELECTED = "KEY_IS_TRAFFIC_SELECTED";
    public static final String KEY_IS_3D_MAP_SELECTED = "key_is_3d_map_selected";
    public static final String KEY_IS_FROM_MAP_UI = "key_is_from_map_ui";
    public static final String KEY_DISTANCE_UNIT = "key_distance_unit";


    public static final String[] REPORT_ARRAY = {
            "not_allowed",
            "road_closed",
            "report_traffic",
            "confusing_instruction",
            "other_map_issue",
            "routing_error"
    };

}
