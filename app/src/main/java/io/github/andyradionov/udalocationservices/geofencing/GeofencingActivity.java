package io.github.andyradionov.udalocationservices.geofencing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import io.github.andyradionov.udalocationservices.R;

public class GeofencingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofencing);

        Button addGeofenceButton = findViewById(R.id.btn_add_geofence);
    }
}
