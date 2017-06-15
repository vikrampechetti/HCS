package com.HCS.ioc.Publish.data;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.DataBase.DB2Access.FetchCameraIDFromDatabase;
import com.HCS.ioc.DataSourceActions.CreateDataSource.CreateDataSourceRecord;
import com.HCS.ioc.api.Common.APIConfig;

public class PublishCrimeMappingData {
	static Logger logger = LoggerFactory.getLogger(FetchCameraIDFromDatabase.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);

	public static void main(String[] args){
		try {
			run();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void run() throws ConfigurationException, JSONException {
		XMLConfiguration configuration = new XMLConfiguration("F:/message.xml");
		String crimeDataInput=configuration.getProperty("CrimeMapping").toString();
		JSONArray jsonData=new JSONArray(crimeDataInput);
		JSONArray Data=new JSONArray(jsonData.getJSONObject(0).getString("CrimeDetails"));
		JSONObject payload=new JSONObject(Data.get(0).toString());
		
		payload.put("MESSAGE", jsonData.getJSONObject(0).getString("Message"));
		payload.put("LOCATION", "POINT("+payload.getString("Longitude")+" "+payload.getString("Latitude")+")");
		payload.put("ADDRESS",payload.getString("Location"));
		payload.remove("Location");
		System.out.println(payload);
		CreateDataSourceRecord dataSourceRecord = new CreateDataSourceRecord();
		String response = dataSourceRecord.createDataSourceRecord(config.CrimeMappingDataSourceId(), payload.toString());
		System.out.println(response);
	}

}
