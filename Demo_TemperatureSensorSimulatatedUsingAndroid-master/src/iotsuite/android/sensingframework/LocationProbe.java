package iotsuite.android.sensingframework;

import iotsuite.android.sensingframework.ProbeKeys.LocationKeys;
import iotsuite.android.sensingframework.ProbeKeys.ProximitySensorKeys;
import iotsuite.android.sensingmiddleware.PubSubsSensingFramework;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.JsonObject;

import edu.mit.media.funf.time.DecimalTimeUnit;
import edu.mit.media.funf.time.TimeUtil;

public class LocationProbe extends Service implements LocationListener,
		LocationKeys,SensorEventListener, ProximitySensorKeys {
	public double longitude;
	public double  latitude;
	public  double proximity;
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	JsonObject locationData = new JsonObject();

	private LocationManager locationManager;

	//private boolean useGps = true;

	//private boolean useNetwork = true;

	//private boolean useCache = true;
	private PubSubsSensingFramework pubSubSensingFramework = null;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		
		Log.i("LocationVlalue", "I am in LocationProbe.java");

		pubSubSensingFramework = PubSubsSensingFramework.getInstance();
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(getSensorType());
		mSensorManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// if (useGps) {
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
				0, this);
		// }

		/*
		 * if (useNetwork) { locationManager.requestLocationUpdates(
		 * LocationManager.NETWORK_PROVIDER, 0, 0, this); } if (useCache) {
		 * 
		 * this.onLocationChanged(locationManager
		 * .getLastKnownLocation(LocationManager.GPS_PROVIDER));
		 * this.onLocationChanged(locationManager
		 * .getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
		 * 
		 * }
		 */

	}

	private int getSensorType() {
		// TODO Auto-generated method stub
		return Sensor.TYPE_PROXIMITY;
	}

	@Override
	public void onDestroy() {

		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {

		//JsonObject locationData = new JsonObject();
		
		latitude=location.getLatitude();
		longitude=location.getLongitude();
		
		Log.i("LocationValue", "Latitude is "+latitude+"Longitude is "+longitude);

		// if (location != null) {
		// String provider = location.getProvider();
		/*
		 * if (provider == null || (useGps &&
		 * LocationManager.GPS_PROVIDER.equals(provider)) || (useNetwork &&
		 * LocationManager.NETWORK_PROVIDER .equals(provider))) {
		 */
		locationData.addProperty(LONGITUDE, location.getLongitude());
		locationData.addProperty(LATITUDE, location.getLatitude());
		//locationData.addProperty(TIMESTAMP, DecimalTimeUnit.MILLISECONDS
			//	.toSeconds(locationData.get("mTime").getAsBigDecimal()));
		sendData(locationData);
		// }
		// }

	}

	private void sendData(final JsonObject data) {

		
		Log.i("LocationValue","I am in sendData method of LocationProbe.java"+data);
		if (data == null) {
			return;
		}

		pubSubSensingFramework.publish(LocationEvent, data);

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		//JsonObject sensingData = new JsonObject();
		
		proximity=event.values[0];
		Log.i("LocationValue","Proximity is "+proximity);

		
		locationData.addProperty(DISTANCE1,proximity);
		/*sensingData.addProperty(TIMESTAMP,
				TimeUtil.uptimeNanosToTimestamp(event.timestamp));
		sensingData.addProperty(ACCURACY, event.accuracy);*/

		/*final String[] valueNames = getValueNames();
		int valuesLength = Math.min(event.values.length, valueNames.length);

		for (int i = 0; i < valuesLength; i++) {
			String valueName = valueNames[i];
			sensingData.addProperty(valueName, event.values[i]);
		}*/
		
		sendData(locationData);

		
	}

	private String[] getValueNames() {
		// TODO Auto-generated method stub
		return new String[] {DISTANCE1};
	}

}
