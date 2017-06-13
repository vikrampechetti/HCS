package com.HCS.ioc.fetch.camera.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.createDatasource.CreateDataSourceRecord;
import com.google.gson.JsonObject;

/**
 * @author OohithVikramRao 17-May-2017
 *
 */
public class ReadCameraDataFromXLSFile {

	static APIConfig APIConfig = ConfigFactory.create(APIConfig.class);
	static Logger logger = LoggerFactory.getLogger(ReadCameraDataFromXLSFile.class);
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		try {
			BufferedReader bufferreader = null;
			String filePath = APIConfig.cameraDataXLSFileAbsPath();
			
			if (!new File(filePath).exists()) {
				throw new FileNotFoundException("No such file:" + filePath);
			}

			bufferreader = new BufferedReader(new FileReader(filePath));
			String line = bufferreader.readLine();
			String linesFromsecondLine = null;
			String[] firstLine = line.split(",");
			
			linesFromsecondLine = bufferreader.readLine();
			
			CreateDataSourceRecord createDataSourceRecord = new CreateDataSourceRecord();
			
			while (true) {
				String[] linetokens = null;
				try{
					if (linesFromsecondLine != null) {
						linetokens = linesFromsecondLine.split(",");
						String jsonObjectString = createJson(firstLine, linetokens);
						String line1 = ((line.replaceAll("﻿LATITUDE,LONGITUDE", "LOCATION")).replaceAll("CAMERALINK", "REFERENCES"))+",STATRDATETIME,ENDDATETIME,LASTUPDATETIME,TIMEZONEOFFSET";
						String[] JsonKeys = line1.split(",");
						JSONObject jsonObject = new JSONObject(jsonObjectString);
						String cameraURL = "<a target=\"_blank\" href=\""+jsonObject.get("CAMERALINK")+"\">"+jsonObject.get("CAMERALINK")+"</a>";
						String[] JsonValues = ("POINT(" + (jsonObject.get("LONGITUDE")) + " "
								+ (jsonObject.get("﻿LATITUDE")) + ")," + cameraURL + ","
								+ (jsonObject.get("DESCRIPTION")) + "," + (jsonObject.get("ADDRESS"))+","+currentSysDateTime()+","+currentSysDateTime()+","+currentSysDateTime()).split(",");
						// creation of json object for each line 
						String datasourcePayload = createJson(JsonKeys, JsonValues);
						System.out.println(datasourcePayload);
						//String response = createDataSourceRecord.createDataSourceRecord(APIConfig.camerasDataSourceID(), datasourcePayload);
					}
					if (linesFromsecondLine == null) {
						break;
					}
					linesFromsecondLine = bufferreader.readLine();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Current Date time
	public static String currentSysDateTime(){
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
	
	public static String createJson(String[] firstLine, String[] linetokens) {
		JsonObject obj = new JsonObject();
		try {
			for (int i = 0; i < linetokens.length; i++) {
				for (int j = 0; j < firstLine.length; j++) {
					obj.addProperty(firstLine[i], linetokens[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj.toString();
	}

}
