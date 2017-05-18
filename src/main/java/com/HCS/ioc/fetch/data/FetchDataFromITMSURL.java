/**
 * 
 */
package com.HCS.ioc.fetch.data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.net.ssl.HttpsURLConnection;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.itms.main.ITMSMainTest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author BALA CHANDER May 1, 2017 
 *
 */
public class FetchDataFromITMSURL {

	static Logger logger = LoggerFactory.getLogger(FetchDataFromITMSURL.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	URI uri;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	
	Connection conn = null;	
    PreparedStatement pstmt = null;    
    ResultSet rset=null;
    
	public void getDatafromURL(){
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
			// Deleting records from db2 table
			try{
				Class.forName("com.ibm.db2.jcc.DB2Driver");
		        
		        conn = DriverManager.getConnection(config.eventsDB2Connection(),config.db2Username(),config.db2Password());
		        
		        PreparedStatement preps = conn.prepareStatement("DELETE FROM IOC.TARGET_TABLE_VEHICLE_INFORMATION");
		        int numUpdates=preps.executeUpdate();
		        logger.info("{	Delete Status	}	"+numUpdates);
				conn.commit();
				conn.close();
				// Creation of events datasource records by using IOC Post URL.
				ITMSMainTest eventsMainTest = new ITMSMainTest();
				eventsMainTest.createDataSourceRecordsForITMS(response.getBody().toString());
			}catch(Exception e){
				logger.error("{	Xception@ db2 delete oper }	"+e);
			}
			
			
		}catch(Exception e){
			logger.error("{ Xception } "+e);
		}
	}

	public static void main(String[] args) {
		FetchDataFromITMSURL dataFromITMSURL = new FetchDataFromITMSURL();
		dataFromITMSURL.getDatafromURL();
		try {
			//dataFromITMSURL.sendPost();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
