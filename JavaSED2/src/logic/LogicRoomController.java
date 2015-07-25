package logic;

import iotsuite.pubsubmiddleware.PubSubMiddleware;
import iotsuite.semanticmodel.*;
import framework.*;

public class LogicRoomController extends RoomController {

	public LogicRoomController(PubSubMiddleware pubSubM, Device deviceInfo,
			Object ui) {
		super(pubSubM, deviceInfo);
	}

	@Override
	public void onNewroomAvgTempMeasurement(TempStruct arg) {
		SetTemp(new TempStruct(arg.getproximity(),arg.getlongitude(),arg.getlatitude()));
	}

}