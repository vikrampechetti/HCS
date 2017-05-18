/**
 * 
 */
package com.HCS.ioc.devices.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.createDatasource.CreateDataSourceRecord;
import com.google.api.client.util.Data;

/**
 * @author BALA CHANDER Apr 18, 2017 
 *
 */
public class DevicesMainTestBackUp {
	static Logger logger = LoggerFactory.getLogger(DevicesMainTestBackUp.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);

	public void createDataSourceRecordInIOCforDevices(String itmspayload){
		String payload = "{\r\n  \"meta\": {\r\n    "
				+ "\"count\": 1,\r\n    "
				+ "\"previous\": null,"
				+ "\"next\": null},"
				+ "\"results\": [{"
				+ "\"id\": 1,"
				+ "\"from_subscriber\": {"
				+ "\"id\": 2,"
				+ "\"manufacturer\": {"
				+ "\"id\": 1,"
				+ "\"created\": \"2016-08-24 08:03:39\","
				+ "\"modified\": \"2016-08-24 08:03:39\","
				+ "\"name\": \"test manufacturer 2\""
				+ "},"
				+ "\"created\": \"2017-04-18 05:42:23\","
				+ "\"modified\": \"2017-04-18 05:47:24\","
				+ "\"type\": \"ecb\","
				+ "\"name\": \"ECB1\","
				+ "\"description\": \"ECB1\","
				+ "\"model\": \"ECB1\","
				+ "\"serial_number\": \"2332\","
				+ "\"latitude\": 17.3534425384,"
				+ "\"longitude\": 78.4732073394,"
				+ "\"is_forward_link_direction\": true,"
				+ "\"is_enabled\": true,"
				+ "\"is_blocked\": false,"
				+ "\"is_status_check_enabled\": true,"
				+ "\"is_state_control_enabled\": true,"
				+ "\"is_diagnostic_testing_enabled\": true,"
				+ "\"has_alternate_control_source\": true,"
				+ "\"is_communicating\": true,"
				+ "\"is_operational\": true,"
				+ "\"has_warnings\": false,"
				+ "\"link_id\": 72,"
				+ "\"ip_address\": \"\","
				+ "\"roadway\": \"\","
				+ "\"device_code\": 103,"
				+ "\"group_code\": 1,"
				+ "\"zone_code\": 1"
				+ "},"
				+ "\"to_subscriber\": {"
				+ "\"id\": 3,"
				+ "\"manufacturer\": {"
				+ "\"id\": 3,"
				+ "\"created\": \"2016-09-08 08:18:02\","
				+ "\"modified\": \"2016-09-08 08:18:02\","
				+ "\"name\": \"Inrix\"},"
				+ "\"created\": \"2017-04-18 05:47:14\","
				+ "\"modified\": \"2017-04-18 05:47:14\","
				+ "\"type\": \"ecb\","
				+ "\"name\": \"Control Desk\","
				+ "\"description\": \"Control Desk\","
				+ "\"model\": \"3\","
				+ "\"serial_number\": \"2\","
				+ "\"latitude\": 17.4401384861871,"
				+ "\"longitude\": 78.3772915449829,"
				+ "\"is_forward_link_direction\": true,"
				+ "\"is_enabled\": true,"
				+ "\"is_blocked\": false,"
				+ "\"is_status_check_enabled\": true,"
				+ "\"is_state_control_enabled\": true,"
				+ "\"is_diagnostic_testing_enabled\": true,"
				+ "\"has_alternate_control_source\": true,"
				+ "\"is_communicating\": true,"
				+ "\"is_operational\": true,"
				+ "\"has_warnings\": false,"
				+ "\"link_id\": 72,"
				+ "\"ip_address\": \"\","
				+ "\"roadway\": \"\","
				+ "\"device_code\": 101,"
				+ "\"group_code\": 1,"
				+ "\"zone_code\": 1"
				+ "},"
				+ "\"created\": \"2017-04-18 05:47:43\","
				+ "\"modified\": \"2017-04-18 05:47:43\","
				+ "\"status\": \"ended\""
				+ "}"
				+ "]}";
		try {
			// Reading temp file for last inserted row ID
			File file = new File(config.tempFile());
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int ID = Integer.parseInt(bufferedReader.readLine());
			
			String tempID = null;
						
			JSONObject inputPayload = new JSONObject(itmspayload);
			JSONArray resultsArray = (JSONArray)inputPayload.get("results");
			String response = null;
			CreateDataSourceRecord createDSRecord = new CreateDataSourceRecord();

			for(int count = 0 ;count < resultsArray.length(); count ++){
				try{
					JSONObject resultObjFromArray = new JSONObject(resultsArray.getString(count));
					JSONObject resultArrayToObj = (JSONObject)resultObjFromArray;
					JSONObject dataSourcePayload = new JSONObject();
					
					dataSourcePayload.put("Event_ID", resultObjFromArray.getString("id"));
					dataSourcePayload.put("status", resultObjFromArray.getString("status"));
					if(Integer.parseInt(resultObjFromArray.getString("id")) > ID){
						if(resultArrayToObj.getString("from_subscriber")!=null || resultArrayToObj.getString("from_subscriber").length()> 0){
							JSONObject resultObjFrom = new JSONObject(resultArrayToObj.getString("from_subscriber"));
							String location = "POINT("+resultObjFrom.getString("longitude")+" "+resultObjFrom.getString("latitude")+")";
							dataSourcePayload.put("ID", resultObjFrom.getString("id"));
							dataSourcePayload.put("STARTDATETIME", resultObjFrom.getString("created"));
							dataSourcePayload.put("ENDDATETIME", resultObjFrom.getString("modified"));
							dataSourcePayload.put("NAME", resultObjFrom.getString("name"));
							dataSourcePayload.put("DESCRIPTION", resultObjFrom.getString("description"));
							dataSourcePayload.put("LASTUPDATEDATETIME", resultObjFrom.getString("modified"));
							dataSourcePayload.put("TIMEZONEOFFSET", "0");
							dataSourcePayload.put("LOCATION", location);					
							dataSourcePayload.put("Errors", "no message");
							dataSourcePayload.put("type", resultObjFrom.getString("type"));						
							dataSourcePayload.put("model", resultObjFrom.getString("model"));
								
						}
						if(resultArrayToObj.getString("to_subscriber")!=null || resultArrayToObj.getString("to_subscriber").length()>0){
							JSONObject resultObjTo = new JSONObject(resultArrayToObj.getString("to_subscriber"));					
							dataSourcePayload.put("serial number", resultObjTo.getString("serial_number"));										
						}				
						if(resultObjFromArray.getString("status").equals("initiated") || 
								resultObjFromArray.getString("status").equals("ended")||
								resultObjFromArray.getString("status").equals("inprogress") ){
							logger.info(dataSourcePayload.toString());
							response = createDSRecord.createDataSourceRecord(config.initiatedDataSourceID(), dataSourcePayload.toString());
							logger.info(response);
							if(response.contains("200")){
								FileWriter writer = new FileWriter(file);
								writer.write(resultObjFromArray.getString("id"));
								writer.close();
							}
						}
					}
//					if(resultObjFromArray.getString("status").equals("ended")){
//						logger.info(dataSourcePayload.toString());
//						response = createDSRecord.createDataSourceRecord(config.endedDataSourceID(), dataSourcePayload.toString());
//						logger.info(response);
//					}
//					if(resultObjFromArray.getString("status").equals("inprogress")){
//						logger.info(dataSourcePayload.toString());
//						response = createDSRecord.createDataSourceRecord(config.initiatedDataSourceID(), dataSourcePayload.toString());
//						logger.info(response);
//					}
				}catch(Exception e){
					logger.error("{Exception } "+e);
				}
			}
		} catch (JSONException | NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public String createJSONObject(JSONObject jsonObject){
		JSONObject dataSourcePayload = new JSONObject();
		try{
			String location = "POINT("+jsonObject.getString("longitude")+" "+jsonObject.getString("latitude")+")";
			dataSourcePayload.put("ID", jsonObject.getString("id"));
			dataSourcePayload.put("STARTDATETIME", jsonObject.getString("created"));
			dataSourcePayload.put("ENDDATETIME", jsonObject.getString("modified"));
			dataSourcePayload.put("NAME", jsonObject.getString("name"));
			dataSourcePayload.put("DESCRIPTION", jsonObject.getString("description"));
			dataSourcePayload.put("LASTUPDATEDATETIME", jsonObject.getString("modified"));
			dataSourcePayload.put("TIMEZONEOFFSET", "120");
			dataSourcePayload.put("LOCATION", location);
			dataSourcePayload.put("Event_ID", jsonObject.getString("id"));
			dataSourcePayload.put("is_enabled", jsonObject.getString("is_enabled"));
			dataSourcePayload.put("is_operational", jsonObject.getString("is_operational"));
			dataSourcePayload.put("is_communicating", jsonObject.getString("is_communicating"));
			dataSourcePayload.put("Errors", "no message");
			dataSourcePayload.put("Type", jsonObject.getString("type"));
			dataSourcePayload.put("Modified", jsonObject.getString("modified"));
			dataSourcePayload.put("Created", jsonObject.getString("created"));
			dataSourcePayload.put("Place", location);
		}catch(Exception e){
			logger.error("{	Exception @ creating json payload	}"+e);
		}
		return dataSourcePayload.toString();		
	}
}
