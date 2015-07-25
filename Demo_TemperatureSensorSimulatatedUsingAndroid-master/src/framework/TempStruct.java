package framework;

import java.io.Serializable;

public class TempStruct implements Serializable {
	private static final long serialVersionUID = 1L;

	private double longitude;
	private double latitude;
	private double proximity;
	
	public double getlongitude() {
		return longitude;
	}
	
	public double getlatitude() {
		return latitude;
	}
	
	public double getproximity() {
		return proximity;
	}

	/*public double gettempValue() {
		return tempValue;
	}

	private String unitOfMeasurement;

	public String getunitOfMeasurement() {
		return unitOfMeasurement;
	}

	public TempStruct(double tempValue, String unitOfMeasurement) {

		this.tempValue = tempValue;
		this.unitOfMeasurement = unitOfMeasurement;
	}*/

	public TempStruct(double proximity , double longitude ,
			double latitude) {
		
		this.longitude=longitude;
		this.latitude=latitude;
		this.proximity=proximity;
		// TODO Auto-generated constructor stub
	}
}