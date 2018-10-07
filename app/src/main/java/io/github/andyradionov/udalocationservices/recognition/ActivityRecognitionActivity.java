package io.github.andyradionov.udalocationservices.recognition;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;

import io.github.andyradionov.udalocationservices.Constants;
import io.github.andyradionov.udalocationservices.R;

public class ActivityRecognitionActivity extends AppCompatActivity {

    private static final String TAG = ActivityRecognitionActivity.class.getSimpleName();

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final String KEY_REQUESTING_DETECTION_UPDATES = "request_detection";

    private boolean mRequestingDetectionUpdates;

    private Button mStartUpdatesButton;
    private Button mStopUpdatesButton;

    private ActivityRecognitionClient mActivityRecognitionClient;
    private ActivityDetectionBroadcastReceiver mActivityDetectionReceiver;

    private TextView mDetectedActivities;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognition);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Activity Recognition");

        mStartUpdatesButton = findViewById(R.id.start_updates_button);
        mStopUpdatesButton = findViewById(R.id.stop_updates_button);
        mDetectedActivities = findViewById(R.id.detected_activities);

        mActivityRecognitionClient = ActivityRecognition.getClient(this);
        mActivityDetectionReceiver = new ActivityDetectionBroadcastReceiver();

        updateValuesFromBundle(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mActivityDetectionReceiver,
                new IntentFilter(Constants.BROADCAST_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeActivityUpdates();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mActivityDetectionReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mRequestingDetectionUpdates) requestActivityUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        removeActivityUpdates();
    }

    public void startUpdatesButtonHandler(View view) {
        mRequestingDetectionUpdates = true;
        updateUi();
        requestActivityUpdates();
    }

    public void stopUpdatesButtonHandler(View view) {
        mRequestingDetectionUpdates = false;
        updateUi();
        removeActivityUpdates();
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_DETECTION_UPDATES)) {
                mRequestingDetectionUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_DETECTION_UPDATES);
                updateUi();
            }
        }
    }

    private void updateUi() {
        if (mRequestingDetectionUpdates) {
            mDetectedActivities.setVisibility(View.VISIBLE);
            mStartUpdatesButton.setEnabled(false);
            mStopUpdatesButton.setEnabled(true);
        } else {
            mDetectedActivities.setVisibility(View.INVISIBLE);
            mStartUpdatesButton.setEnabled(true);
            mStopUpdatesButton.setEnabled(false);
        }
    }

    private void requestActivityUpdates() {
        mActivityRecognitionClient.requestActivityUpdates(UPDATE_INTERVAL_IN_MILLISECONDS,
                getActivityDetectionPendingIntent());

    }

    private void removeActivityUpdates() {
        mActivityRecognitionClient.removeActivityUpdates(getActivityDetectionPendingIntent());
    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, DetectedActivitiesIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private String getActivityString(int detectedActivityType) {
        Resources resources = this.getResources();
        switch (detectedActivityType) {
            case DetectedActivity.IN_VEHICLE:
                return resources.getString(R.string.in_vehicle);
            case DetectedActivity.ON_BICYCLE:
                return resources.getString(R.string.on_bicycle);
            case DetectedActivity.ON_FOOT:
                return resources.getString(R.string.on_foot);
            case DetectedActivity.RUNNING:
                return resources.getString(R.string.running);
            case DetectedActivity.STILL:
                return resources.getString(R.string.still);
            case DetectedActivity.TILTING:
                return resources.getString(R.string.tilting);
            case DetectedActivity.UNKNOWN:
                return resources.getString(R.string.unknown);
            case DetectedActivity.WALKING:
                return resources.getString(R.string.walking);
            default:
                return resources.getString(R.string.unidentifiable_activity, detectedActivityType);
        }
    }

    private class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> activities = intent
                    .getParcelableArrayListExtra(Constants.ACTIVITY_EXTRA);

            StringBuilder sb = new StringBuilder();
            for (DetectedActivity activity : activities) {
                sb.append(getActivityString(activity.getType()))
                        .append(": ")
                        .append(activity.getConfidence())
                        .append("%\n");
            }
            mDetectedActivities.setText(sb.toString());
        }
    }
}
