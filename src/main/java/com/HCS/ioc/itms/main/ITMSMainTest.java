package com.HCS.ioc.itms.main;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.createDatasource.CreateDataSourceRecord;

/**
 * @author BALA CHANDER May 1, 2017 
 *
 */
public class ITMSMainTest {
	static Logger logger = LoggerFactory.getLogger(ITMSMainTest.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	public void createDataSourceRecordsForITMS(String payload){
		try{
			String response = null;
			
			CreateDataSourceRecord createDSRecord = new CreateDataSourceRecord();
			
			// parsing ITMS response 
			JSONObject itmsResponseObj = new JSONObject(payload);
			JSONArray itmsVehicleDetailsArray = new JSONArray(itmsResponseObj.getString("VehicleDetails"));
			
			for(int count =0 ;count < itmsVehicleDetailsArray.length(); count ++){
				JSONObject itmsdataSourcePayload = new JSONObject();
				JSONObject itmsVehicleDataObj = (JSONObject)itmsVehicleDetailsArray.get(count);
				// ADDRESS
				try{
					if(itmsVehicleDataObj.getString("Address")!=null)
						itmsdataSourcePayload.put("ADDRESS","test"); 
								//itmsVehicleDataObj.getString("Address"));
				}catch(Exception e){
					logger.error("{	X@ address	}	"+e);
				}
				// Direction
				try{
					if(itmsVehicleDataObj.getString("Direction")!=null)
						itmsdataSourcePayload.put("DIRECTION", itmsVehicleDataObj.getString("Direction"));
				}catch(Exception e){
					logger.error("{	X@ direction	}	"+e);
				}
				// DriverName
				
				try{
					if(itmsVehicleDataObj.getString("DriverName")!=null)
						itmsdataSourcePayload.put("DRIVERNAME", itmsVehicleDataObj.getString("DriverName"));
				}catch(Exception e){
					logger.error("{	X@ driverName	}	"+e);
				}
				// DriverNumber
				try{
					if(itmsVehicleDataObj.getString("DriverNumber")!=null)
						itmsdataSourcePayload.put("DRIVERNUMBER", itmsVehicleDataObj.getString("DriverNumber"));
				}catch(Exception e){
					logger.error("{	X@ driverNumber	}	"+e);
				}
				// PhoneNo
				try{
					if(itmsVehicleDataObj.getString("PhoneNo")!=null)
						itmsdataSourcePayload.put("PHONENO", itmsVehicleDataObj.getString("PhoneNo"));
				}catch(Exception e){
					logger.error("{	X@ phoneNo	}	"+e);
				}
				// VehicleName
				try{
					if(itmsVehicleDataObj.getString("VehicleName")!=null)
						itmsdataSourcePayload.put("VEHICLENAME", itmsVehicleDataObj.getString("VehicleName"));
				}catch(Exception e){
					logger.error("{	X@ vehivleName	}	"+e);
				}
				// VehicleNumber
				try{
					if(itmsVehicleDataObj.getString("VehicleNumber")!=null)
						itmsdataSourcePayload.put("VEHICLENUMBER", itmsVehicleDataObj.getString("VehicleNumber"));
				}catch(Exception e){
					logger.error("{	X@ vehicleNo	}	"+e);
				}
				// VehicleStatus
				try{
					if(itmsVehicleDataObj.getString("VehicleStatus")!=null)
						itmsdataSourcePayload.put("VEHICLESTATUS", itmsVehicleDataObj.getString("VehicleStatus"));
				}catch(Exception e){
					logger.error("{	X@ vehicleStat	}	"+e);
				}
				// Location
				String location = "POINT("+ itmsVehicleDataObj.getString("Longitude")+" "+itmsVehicleDataObj.getString("Latitude")+")";
				itmsdataSourcePayload.put("LOCATION", location);
				itmsdataSourcePayload.put("STARTDATETIME", currentSysDateTime());
				itmsdataSourcePayload.put("ENDDATETIME", currentSysDateTime());
				itmsdataSourcePayload.put("LASTUPDATEDATETIME", currentSysDateTime());
				itmsdataSourcePayload.put("TIMEZONEOFFSET", "0");

				response = createDSRecord.createDataSourceRecord(config.itmsDatasourceID(), itmsdataSourcePayload.toString());
				logger.info(response.toString());
			}
			

		}catch(Exception e){
			logger.error("{	Xception@ ITMS CreateDSRecordITMS	}	"+e);
		}
	}
	
	public String currentSysDateTime(){
		String curDateTime = null;
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			curDateTime = simpleDateFormat.format(curDate);
		}catch(Exception e){
			logger.error("{	Xception @ sysDateTime	}	"+e);
		}
		return curDateTime;
	}
	public static void main(String[] args) {
		ITMSMainTest itmsMainTest = new ITMSMainTest();
		itmsMainTest.createDataSourceRecordsForITMS(null);
	}
}
