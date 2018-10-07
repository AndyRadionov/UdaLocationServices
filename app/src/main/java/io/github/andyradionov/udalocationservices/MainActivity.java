package io.github.andyradionov.udalocationservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import io.github.andyradionov.udalocationservices.location.LocationsActivity;
import io.github.andyradionov.udalocationservices.recognition.ActivityRecognitionActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button startLocationsButton = findViewById(R.id.start_location_activity);
        Button startRecognitionButton = findViewById(R.id.start_recognition_activity);
        Button startGeofencingButton = findViewById(R.id.start_geofencing_activity);

        startLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocationsActivity.class));
            }
        });

        startRecognitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityRecognitionActivity.class));
            }
        });

        startGeofencingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityRecognitionActivity.class));
            }
        });
    }
}
