package com.example.MDP_Android.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.MDP_Android.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MapInformation extends AppCompatActivity {
    private final static String TAG = "MapInformation";

    String mapStr;
    String connStats;
    JSONObject mapJsonObject;
    GridView gridView;
    Button obstBtn;
    SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showLog("Entering onCreateView");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_information);

        connStats = "Disconnected";

        sharedPrefs = getApplicationContext().getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);

        if (sharedPrefs.contains("mapJsonObject")) {
            mapStr = sharedPrefs.getString("mapJsonObject", "");
            showLog(mapStr);
            try {
                mapJsonObject = new JSONObject(mapStr);
                showLog("success");
            } catch (JSONException e) {
                e.printStackTrace(); showLog("failed");
            }
            gridView = new GridView(this);
            gridView = findViewById(R.id.mapInformationView);
            gridView.mapJsonObject = mapJsonObject;
        }

        obstBtn = findViewById(R.id.obstacleBtn);

        obstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (obstBtn.getText().equals("Show Explored")) {
                    gridView.plotObstacle = true;
                    Toast.makeText(getApplicationContext(), "Showing obstacle cells", Toast.LENGTH_SHORT).show();
                    gridView.invalidate();
                }
                else if (obstBtn.getText().equals("Show Obstacle")) {
                    gridView.plotObstacle = false;
                    Toast.makeText(getApplicationContext(), "Showing explored cells", Toast.LENGTH_SHORT).show();
                    gridView.invalidate();
                }
            }
        });

        showLog("Exiting.....");
    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }
}