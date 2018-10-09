package io.github.andyradionov.udalocationservices;

import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrey Radionov
 */
public class Constants {

    private Constants() {
    }

    public static final String PACKAGE_NAME = "io.github.andyradionov.udalocationservices";

    public static final String BROADCAST_ACTION = PACKAGE_NAME + ".BROADCAST_ACTION";

    public static final String ACTIVITY_EXTRA = PACKAGE_NAME + ".ACTIVITY_EXTRA";

    public static final String SHARED_PREFERENCES_NAME = PACKAGE_NAME + ".SHARED_PREFERENCES";

    public static final String ACTIVITY_UPDATES_REQUESTED_KEY = PACKAGE_NAME +
            ".ACTIVITY_UPDATES_REQUESTED";

    public static final String DETECTED_ACTIVITIES = PACKAGE_NAME + ".DETECTED_ACTIVITIES";

    /**
     * The desired time between activity detections. Larger values result in fewer activity
     * detections while improving battery life. A value of 0 results in activity detections at the
     * fastest possible rate. Getting frequent updates negatively impact battery life and a real
     * app may prefer to request less frequent updates.
     */
    public static final long DETECTION_INTERVAL_IN_MILLISECONDS = 0;

    /**
     * List of DetectedActivity types that we monitor in this sample.
     */
    protected static final int[] MONITORED_ACTIVITIES = {
            DetectedActivity.STILL,
            DetectedActivity.ON_FOOT,
            DetectedActivity.WALKING,
            DetectedActivity.RUNNING,
            DetectedActivity.ON_BICYCLE,
            DetectedActivity.IN_VEHICLE,
            DetectedActivity.TILTING,
            DetectedActivity.UNKNOWN
    };


    /**
     * Used to set an expiration time for a geofence. After this amount of time Location Services
     * stops tracking the geofence.
     */
    public static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;

    /**
     * For this sample, geofences expire after twelve hours.
     */
    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
            GEOFENCE_EXPIRATION_IN_HOURS * 60 * 60 * 1000;
    //public static final float GEOFENCE_RADIUS_IN_METERS = 1609; // 1 mile, 1.6 km
    public static final float GEOFENCE_RADIUS_IN_METERS = 1; // 1 mile, 1.6 km

    /**
     * Map for storing information about airports in the San Francisco bay area.
     */
    public static final Map<String, LatLng> OMSK_LANDMARKS = new HashMap<>();
    static {
        // San Francisco International Airport.
        OMSK_LANDMARKS.put("AIRPORT", new LatLng(54.9574354, 73.3164934));

        // Googleplex.
        OMSK_LANDMARKS.put("POLYOT", new LatLng(54.9559492, 73.4142328));

        // Test
        OMSK_LANDMARKS.put("Park VLKSM", new LatLng(54.972468, 73.413515));
    }

}
