package deviceImpl;

import framework.*;
import logic.*;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import iotsuite.android.sensingframework.*;
import com.google.gson.JsonObject;
import iotsuite.android.sensingframework.ProbeKeys.*;
import iotsuite.android.sensingmiddleware.ISensorListener;
import iotsuite.android.sensingmiddleware.PubSubsSensingFramework;

//TODO: Step1: Device Developers will change implements according to sensor selected.
// For Instance, change to "ProximitySensorKeys" .
public class AndroidTemperatureSensor implements ITemperatureSensor,
		ProximitySensorKeys, ISensorListener,LocationKeys {

	private PubSubsSensingFramework pubSubSensingFramework = null;
	private static JsonObject TemperatureSensorData = new JsonObject();

	private ListenertempMeasurement listenertempMeasurement = null;

	public AndroidTemperatureSensor(Context context, LogicTemperatureSensor obj) {

		// Register the client
		pubSubSensingFramework = PubSubsSensingFramework.getInstance();
		
		// TODO: Step2: Device Developers will change the SensorEvent
		// For instance, Developer has used to simulate ProximitySensor.
		// So, he will change to ProximitySensorEvent.
		//pubSubSensingFramework.registerForSensorData(this, ProximitySensorEvent);
		pubSubSensingFramework.registerForSensorData(this, LocationEvent);

		// Start the service
		// Here we are simulating Proximity sensor as Temperature Sensor.
		
		
		//TODO: Step3: Device Developer change the appropriate class.
		//For instance, He calls ProximitySensor. So, he will change to ProximitySensorProbe.class
		//Intent intent = new Intent(context, ProximitySensorProbe.class);
		Intent intent = new Intent(context, LocationProbe.class);
		Log.i("LocationValue","I am in AndroidTempSensor.java");
		context.startService(intent);

	}

	/*
	 * @Override public TempStruct gettempMeasurement(){ //TODO: Device
	 * Developer write here device logic. //Sample code for reference.
	 * //if(TemperatureSensorData != null) { // return new
	 * TempStruct(Double.parseDouble
	 * (TemperatureSensorData.get(DISTANCE).toString()), "C"); // }else{ // if
	 * data is null, then return a Default value // return new TempStruct(-5 ,
	 * "C"); // }
	 * 
	 * 
	 * return null; }
	 */

	@Override
	public void gettempMeasurement(ListenertempMeasurement handler) {
		listenertempMeasurement = handler;
	}

	@Override
	public boolean isEventDriven() {
		return true;
	}

	@Override
	public void onDataReceived(String eventName, JsonObject dataEvent) {
		
		Log.i("LocationValue", "I am in onDataReceived in Android Temp Sensor.java"+dataEvent);
	/*	Log.i("LocationValue","Latitude is"+Double.parseDouble(dataEvent.get(LONGITUDE).toString())+" Longitude is "
		+Double.parseDouble(dataEvent.get(LATITUDE).toString())+"Proximity is "+Double.parseDouble(dataEvent.get(DISTANCE1).toString()));*/

		//TODO: Step 4: Developer will change appropriate change the appropruate data value that can be used to published.
	    listenertempMeasurement.onNewtempMeasurement(new TempStruct(Double.parseDouble(dataEvent.get(DISTANCE1).toString()),
			Double.parseDouble(dataEvent.get(LONGITUDE).toString())
				,Double.parseDouble(dataEvent.get(LATITUDE).toString())));
		//System.out.println("Temperature Value" + Double.parseDouble(dataEvent.get(DISTANCE).toString()));

		TemperatureSensorData = dataEvent;
	}

}