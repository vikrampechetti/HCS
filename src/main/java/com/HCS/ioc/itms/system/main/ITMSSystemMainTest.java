/**
 * 
 */
package com.HCS.ioc.itms.system.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.createDatasource.CreateDataSourceRecord;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

/**
 * @author BALA CHANDER May 6, 2017
 *
 */
public class ITMSSystemMainTest {

	static Logger logger = LoggerFactory.getLogger(ITMSSystemMainTest.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	public void readJSONDataFromFile() {
		try {
			String fileAsString = null;
			HttpResponse<String> response = null;
			JSONObject payloadObj = null;
			
			if(config.itmsSystemSourceURL().length()>0){
				response = Unirest.post(config.itmsSystemSourceURL())
						  .header("authorization", "token "+config.itmsSystemAuthorizationToken())
						  .header("content-type", "application/json")
						  .header("cache-control", "no-cache")
						  .header("postman-token", "25c4be52-b33d-2dd6-082a-6a5f2d845f3a")
						  .body(config.offendersRequestBody())
						  .asString();
				payloadObj = new JSONObject(response.getBody().toString());
			}else{
			
				File jsonfile = new File(config.jsonCapFileLocation());
	
				BufferedReader br = null;
				FileReader fr = new FileReader(jsonfile);
				String sCurrentLine;
				br = new BufferedReader(fr);
				StringBuilder sb = new StringBuilder();
				
				while ((sCurrentLine = br.readLine()) != null) {				
					sb.append(sCurrentLine).append("\n");					
				}
					
				fileAsString = sb.toString(); 
				payloadObj = new JSONObject(fileAsString);
			}
			JSONObject datasourcePayload = new JSONObject();
			
			JSONObject alertObj = new JSONObject(payloadObj.getString("alert"));
			
			alertObj.getString("identifier");
			alertObj.getString("sender");
			alertObj.getString("sent");
			alertObj.getString("status");
			alertObj.getString("msgType");
			alertObj.getString("scope");
			
			JSONObject infoObj = new JSONObject(alertObj.getString("info"));
			
			datasourcePayload.put("category",infoObj.getString("category"));
			datasourcePayload.put("event",infoObj.getString("event"));
			datasourcePayload.put("urgency",infoObj.getString("urgency"));
			datasourcePayload.put("severity",infoObj.getString("severity"));
			datasourcePayload.put("certainty",infoObj.getString("certainty"));
			datasourcePayload.put("senderName",infoObj.getString("senderName"));
			datasourcePayload.put("headline",infoObj.getString("headline"));
			datasourcePayload.put("description",infoObj.getString("description"));
			datasourcePayload.put("instruction",infoObj.getString("instruction"));
			datasourcePayload.put("contact",infoObj.getString("contact"));
			
			JSONObject areaObj = new JSONObject(infoObj.getString("area"));
			areaObj.getString("areaDesc");
			areaObj.getString("geocode");
			
			String[] latLong = areaObj.getString("geocode").split(",");
			String latitude = latLong[0];
			String longitude = latLong[1];
			
			String Location = "POINT("+longitude+" "+ latitude+")";
			datasourcePayload.put("LOCATION", Location);
			datasourcePayload.put("effective",infoObj.getString("effective"));
			datasourcePayload.put("onset",infoObj.getString("onset"));
			datasourcePayload.put("expires",infoObj.getString("expires"));
			datasourcePayload.put("STARTDATETIME", currentSysDateTime());
			datasourcePayload.put("ENDDATETIME", currentSysDateTime());
			datasourcePayload.put("LASTUPDATEDATETIME", currentSysDateTime());
			
			JSONArray parametrArray = new JSONArray(infoObj.getString("parameter"));  
			for(int count=0; count<parametrArray.length(); count++){
				JSONObject paramObj = new JSONObject(parametrArray.getString(count));
				datasourcePayload.put(paramObj.getString("valueName"), paramObj.getString("value"));
			}
			System.out.println(datasourcePayload);
			CreateDataSourceRecord createDSRecord = new CreateDataSourceRecord();
			String responseCode = createDSRecord.createDataSourceRecord(config.itmsSystemDataSourceID(), datasourcePayload.toString());
			logger.info("{ Response status ITMS } "+responseCode);
			
		} catch (Exception e) {
			logger.error("{ Xception@  } "+e);
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
		ITMSSystemMainTest itmsSystemMainTest = new ITMSSystemMainTest();
		itmsSystemMainTest.readJSONDataFromFile();
	}
}
