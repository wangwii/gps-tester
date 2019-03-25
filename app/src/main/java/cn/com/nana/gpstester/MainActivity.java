package cn.com.nana.gpstester;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements LocationListener {

  private static final Integer MINIMUM_UPDATE_INTERVAL = 5000; // update every 5 seconds
  private static final Integer MINIMUM_UPDATE_DISTANCE = 0;    // update every 0 meters

  private TextView tv;
  private LocationManager lm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    tv = (TextView) findViewById(R.id.tv);
    lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    tv.append("    Startup ...\r\n");
  }

  @SuppressLint("MissingPermission")
  @Override
  protected void onResume() {
    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_UPDATE_INTERVAL, MINIMUM_UPDATE_DISTANCE, this);
    super.onResume();
  }

  @Override
  protected void onPause() {
    // GPS, as it turns out, consumes battery like crazy
    lm.removeUpdates(this);
    super.onPause();
  }

  @Override
  protected void onStop() {
    finish();
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  @Override
  public void onLocationChanged(Location location) {
    tv.append(String.format("%s: [%s, %s]\r\n",
        new Date(location.getTime()),
        location.getLatitude(), location.getLongitude()));
  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {
    switch (status) {
      case LocationProvider.OUT_OF_SERVICE:
        Toast.makeText(this, "Status changed: out of service", Toast.LENGTH_LONG).show();
        break;
      case LocationProvider.TEMPORARILY_UNAVAILABLE:
        Toast.makeText(this, "Status changed: temporarily unavailable", Toast.LENGTH_LONG).show();
        break;
      case LocationProvider.AVAILABLE:
        Toast.makeText(this, "Status changed: available", Toast.LENGTH_LONG).show();
        break;
    }
  }

  @Override
  public void onProviderEnabled(String provider) {
    Toast.makeText(this, "GPS enabled", Toast.LENGTH_LONG).show();
  }

  @Override
  public void onProviderDisabled(String provider) {
    Toast.makeText(this, "GPS disabled", Toast.LENGTH_LONG).show();
  }
}
