package com.HCS.ioc.updateDatasourceRecord;

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
import com.ibm.db2.jcc.am.lo;

/**
 * @author BALA CHANDER May 1, 2017 
 *
 */
public class UpdateEventsDSRecordInDB2 {

	/******************************************
	 * Job of this class is to update 
	 * the Datasource records of events system 
	 ******************************************/
	static Logger logger = LoggerFactory.getLogger(UpdateEventsDSRecordInDB2.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	Connection conn = null;	
    PreparedStatement pstmt = null;    
    ResultSet rset=null;
    
	public void updateEventsDSRecords(String payload){
		try{
			Class.forName("com.ibm.db2.jcc.DB2Driver");
	        
	        conn = DriverManager.getConnection(config.eventsDB2Connection(),config.db2Username(),config.db2Password());
	        
	        JSONObject payloadObj = new JSONObject(payload);
			JSONArray payloadResultsArray = (JSONArray)payloadObj.get("results");
			
			PreparedStatement preps = conn.prepareStatement("UPDATE IOC.TARGET_TABLE_ECB_EVENTS SET STATUS=? WHERE EVENT_ID=?");
			// columns list
			List<String> columns = new ArrayList<String>();
			columns.add("status");
			columns.add("Event_ID");
			
			for(int counter = 0 ; counter < payloadResultsArray.length(); counter ++){
				JSONObject eventsDataSourcePayload = new JSONObject();
				JSONObject resultRowObj = new JSONObject( payloadResultsArray.getString(counter));
				try{
					eventsDataSourcePayload.put("Event_ID", resultRowObj.getString("id"));
					eventsDataSourcePayload.put("STARTDATETIME", resultRowObj.getString("created"));
					eventsDataSourcePayload.put("ENDDATETIME", resultRowObj.getString("created"));
					eventsDataSourcePayload.put("LASTUPDATEDATETIME", resultRowObj.getString("modified"));				
					String location = "POINT("+resultRowObj.getString("longitude")+" "+resultRowObj.getString("latitude")+")";							
					eventsDataSourcePayload.put("LOCATION", location);
					eventsDataSourcePayload.put("TIMEZONEOFFSET", "0");
					eventsDataSourcePayload.put("type", resultRowObj.getString("type"));	
					eventsDataSourcePayload.put("severity", resultRowObj.getString("severity"));
					eventsDataSourcePayload.put("cause", resultRowObj.getString("cause"));
					eventsDataSourcePayload.put("status", resultRowObj.getString("status"));
					try{
						JSONObject ownerObj = new JSONObject(resultRowObj.getString("owner"));
						eventsDataSourcePayload.put("username", ownerObj.getString("username"));
					}catch(Exception e){
						logger.error("{	Exception@ owner obj	}	"+e);
						e.printStackTrace();
					}
					
					if(resultRowObj.getString("responder_vehicle").length()>0 || resultRowObj.getString("responder_vehicle")!=null){
						JSONArray responderVehicleArray = (JSONArray)resultRowObj.get("responder_vehicle");
						for(int count = 0 ;count < responderVehicleArray.length(); count++){
							if(responderVehicleArray.get(count)!=null){
								try{
									JSONObject responderVechicleObj = (JSONObject)responderVehicleArray.get(count);
									eventsDataSourcePayload.put("vehicletype", responderVechicleObj.getString("vehicle_type"));
									eventsDataSourcePayload.put("agencyname", responderVechicleObj.getString("agency_name"));
								}catch(Exception e){
									logger.error("{	Exception@ vehicle type }  "+e);
								}
							}
						}					
					}					
				}catch(Exception e){
					logger.error("{	Exception }	"+e);
				}
				// setting update fields for prepare statement
				// by itterating over columns list
				System.out.println(eventsDataSourcePayload);
				for(int count = 0; count < columns.size(); count++){
					if(eventsDataSourcePayload.getString(columns.get(count))!=null || 
							eventsDataSourcePayload.getString(columns.get(count)).length()>0){
						try{	
							System.out.println(count + "  :::  " +eventsDataSourcePayload.getString(columns.get(count)));
							preps.setString(count+1, eventsDataSourcePayload.getString(columns.get(count)));
						}catch(Exception e){
							preps.setString(count+1, null);
							logger.error("{	Xception@ preps set }	"+e);
						}
					}else{
						preps.setString(count, null);
					}					
				}
				preps.addBatch();
				
			}
			int [] numUpdates=preps.executeBatch();				
			conn.commit();			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("{	xception @ updateEventsDSRecords }	"+e);
		}finally {
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();				
			}
		}
	}

}
