package gifford.com.loogle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

  private String provider;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    Log.d("INIT", "Initialize started");
    final LocationManager locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
    boolean gpsEnabled = false;

    if (locationManager != null) {
      gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    } else {
      Log.e("INIT", "Location manager null");
    }

    if (!gpsEnabled) {
      Log.e("INIT", "GPS not enabled");
      Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
      startActivity(intent);
    } else {
      Log.d("INIT", "GPS enabled");
    }

    if (ContextCompat.checkSelfPermission(getApplicationContext(),
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
      Log.e("INIT", "Location permission denied");
      ActivityCompat.requestPermissions(HomeActivity.this,
          new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
    } else {
      Log.d("INIT", "Location permission granted");
    }

    Button newToiletButton = findViewById(R.id.home_new);
    newToiletButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("BUTTONCLICK", "Button click");
        Criteria criteria = new Criteria();
        if (locationManager != null) {
          provider = locationManager.getBestProvider(criteria, false);
          if (ContextCompat.checkSelfPermission(getApplicationContext(),
              Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
            Location location = locationManager.getLastKnownLocation(provider);
            Toast.makeText(getApplicationContext(),
                "Location: " + location.toString(), Toast.LENGTH_SHORT).show();
          } else {
            Log.e("BUTTONCLICK", "Permissions denied");
          }
        } else {
          Log.e("BUTTONCLICK", "Location manager null");
        }
      }
    });
  }
}
