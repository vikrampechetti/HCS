/**
 * 
 */
package com.HCS.ioc.devices.main;

import java.io.BufferedReader;
import java.io.File;
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

/**
 * @author BALA CHANDER Apr 18, 2017 
 *
 */
public class DevicesMainTest {
	static Logger logger = LoggerFactory.getLogger(DevicesMainTest.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);

	public void createDataSourceRecordInIOCforDevices(String itmspayload){
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
					dataSourcePayload.put("STARTDATETIME", resultObjFromArray.getString("created"));
					dataSourcePayload.put("ENDDATETIME", resultObjFromArray.getString("created"));
					dataSourcePayload.put("LASTUPDATEDATETIME", resultObjFromArray.getString("created"));
					
					if(Integer.parseInt(resultObjFromArray.getString("id")) > ID){
						if(resultArrayToObj.getString("from_subscriber")!=null || resultArrayToObj.getString("from_subscriber").length()> 0){
							JSONObject resultObjFrom = new JSONObject(resultArrayToObj.getString("from_subscriber"));
							dataSourcePayload.put("model", resultObjFrom.getString("model"));
							String location = "POINT("+resultObjFrom.getString("longitude")+" "+resultObjFrom.getString("latitude")+")";							
							
							dataSourcePayload.put("NAME", resultObjFrom.getString("name"));
							dataSourcePayload.put("DESCRIPTION", resultObjFrom.getString("description"));
							dataSourcePayload.put("TIMEZONEOFFSET", "0");
							dataSourcePayload.put("LOCATION", location);					
							dataSourcePayload.put("Errors", "no message");
							dataSourcePayload.put("type", resultObjFrom.getString("type"));															
						}
						if(resultArrayToObj.getString("to_subscriber")!=null || resultArrayToObj.getString("to_subscriber").length()>0){
							JSONObject resultObjTo = new JSONObject(resultArrayToObj.getString("to_subscriber"));					
							dataSourcePayload.put("serial_number", resultObjTo.getString("serial_number"));										
						}				
						if(resultObjFromArray.getString("status").equals("initiated") || 
								resultObjFromArray.getString("status").equals("ended")||
								resultObjFromArray.getString("status").equals("inprogress") ){
							response = createDSRecord.createDataSourceRecord(config.initiatedDataSourceID(), dataSourcePayload.toString());
							logger.info(response);
							if(response.contains("200")){
								FileWriter writer = new FileWriter(file);
								writer.write(resultObjFromArray.getString("id"));
								writer.close();
							}
						}
					}
				}catch(Exception e){
					logger.error("{Exception } "+e);
				}
			}
		} catch (JSONException | NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
