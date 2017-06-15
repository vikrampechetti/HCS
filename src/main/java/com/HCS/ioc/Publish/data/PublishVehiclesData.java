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

/**
 * 
 * @Author OohithVikramRao 15-Jun-2017
 *
 */
public class PublishVehiclesData {
	static Logger logger = LoggerFactory.getLogger(FetchCameraIDFromDatabase.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);

	public static void main(String[] args) throws ConfigurationException, JSONException {
		run();
	}
	private static void run() throws ConfigurationException, JSONException {
		XMLConfiguration configuration = new XMLConfiguration("F:/message.xml");
		String offendersDataInput=configuration.getProperty("Vehicle").toString();
		
		JSONArray jsonData=new JSONArray(offendersDataInput);
		JSONObject payload=new JSONObject(jsonData.get(0).toString());
		System.out.println(payload);
		CreateDataSourceRecord createDataSourceRecord=new CreateDataSourceRecord();
		String response=createDataSourceRecord.createDataSourceRecord(config.VechileInfoDataSourceId(), payload.toString());
		System.out.println(response);
	}

}
