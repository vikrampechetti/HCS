/**
 * 
 */
package com.HCS.ioc.updateDatasourceRecord;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.fetch.data.FetchDataFromITMSURL;
import com.HCS.ioc.itms.main.ITMSMainTest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author BALA CHANDER May 3, 2017 
 *
 */
public class UpdateVehicleInfoDSRecordInDB2 {

	static Logger logger = LoggerFactory.getLogger(FetchDataFromITMSURL.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	URI uri;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	
	Connection conn = null;	
    PreparedStatement pstmt = null;    
    ResultSet rset=null;
    
	public void updateVehicleInfoInDB2(){
		try{
			// fetching data from ITMS system
			// http://220.225.38.123:8081/LogicShore.svc/GetVehicleInfo/
			// http://10.10.27.119:8280/services/GetVehicleInfo
			HttpResponse<String> response = Unirest.post(config.itmsSourceURL())
					  .header("authorization", "token "+config.itmsauthorizationToken())
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .header("postman-token", "25c4be52-b33d-2dd6-082a-6a5f2d845f3a")
					  .body(config.itmsRequestBody())
					  .asString();
			
			// retrieve the parsed JSONObject from the response
			System.out.println(response.getStatus() +"  :::  "+response.getStatusText());
			//logger.info(myObj.toString());	
			setDataFroUpdate(response.getBody().toString());
		}catch(Exception e){
			logger.error("{ Xception } "+e);
		}
	}
	
	public void setDataFroUpdate(String payload){
		try{
			
			Class.forName("com.ibm.db2.jcc.DB2Driver");
	        
	        conn = DriverManager.getConnection(config.eventsDB2Connection(),config.db2Username(),config.db2Password());
	        
	        PreparedStatement preps = conn.prepareStatement("UPDATE IOC.TARGET_TABLE_VEHICLE_INFO SET LOCATION=db2gse.st_pointfromtext('POINT(? ?)',1) WHERE VEHICLENUMBER=?");
			// columns list
			List<String> columns = new ArrayList<String>();
			columns.add("LOCATION");
			columns.add("VEHICLENUMBER");
			
			
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
				// Longitude
				try{
					if(itmsVehicleDataObj.getString("Longitude")!=null)
						itmsdataSourcePayload.put("Longitude", itmsVehicleDataObj.getString("Longitude")); 
								//itmsVehicleDataObj.getString("Address"));
				}catch(Exception e){
					logger.error("{	X@ latitude	}	"+e);
				}
				// latitude
				try{
					if(itmsVehicleDataObj.getString("Latitude")!=null)
						itmsdataSourcePayload.put("Latitude", itmsVehicleDataObj.getString("Latitude")); 
								//itmsVehicleDataObj.getString("Address"));
				}catch(Exception e){
					logger.error("{	X@ latitude	}	"+e);
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

				logger.info(itmsdataSourcePayload.toString());
				
				preps.setDouble(1, Double.parseDouble(itmsdataSourcePayload.getString("Longitude")));
				preps.setDouble(2, Double.parseDouble(itmsdataSourcePayload.getString("Latitude")));
				preps.setString(3, itmsdataSourcePayload.getString("VEHICLENUMBER"));
//				for(int counter = 0; counter < columns.size(); counter++){
//					if(itmsdataSourcePayload.getString(columns.get(count))!=null || 
//							itmsdataSourcePayload.getString(columns.get(count)).length()>0){
//						try{	
//							System.out.println(count + "  :::  " +itmsdataSourcePayload.getString(columns.get(count)));
//							preps.setString(count+1, itmsdataSourcePayload.getString(columns.get(count)));
//						}catch(Exception e){
//							preps.setString(count+1, null);
//							logger.error("{	Xception@ preps set }	"+e);
//						}
//					}else{
//						preps.setString(count, null);
//					}					
//				}
				preps.addBatch();
			}
			int [] numUpdates=preps.executeBatch();				
			conn.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("{	xception @ updateVehicleInfoDSRecords }	"+e);
		}finally {
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();				
			}
		}
	}
	public static void main(String[] args) {
		UpdateVehicleInfoDSRecordInDB2 updateVehicleInfoDSRecordInDB2 = new UpdateVehicleInfoDSRecordInDB2();
		updateVehicleInfoDSRecordInDB2.updateVehicleInfoInDB2();
	}
}
