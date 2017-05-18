package com.HCS.ioc.fetch.data;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.crime.mapping.main.CrimeMappingMainTest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author BALA CHANDER May 1, 2017 
 *
 */
public class FetchDataFromCrimeMappingURL {

	static Logger logger = LoggerFactory.getLogger(FetchDataFromCrimeMappingURL.class);
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
			// fetching data from crime mapping

			HttpResponse<String> response = Unirest.post(config.crimeMapSourceURL())
					  .header("authorization", "token "+config.crimeMapAuthorizationToken())
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .header("postman-token", "25c4be52-b33d-2dd6-082a-6a5f2d845f3a")
					  .body(config.crimeMapRequestBody())
					  .asString();
			
			// retrieve the parsed JSONObject from the response
			System.out.println(response.getStatus() +"  :::  "+response.getStatusText());
			//logger.info(myObj.toString());
			try{
				Class.forName("com.ibm.db2.jcc.DB2Driver");
		        
		        conn = DriverManager.getConnection(config.eventsDB2Connection(),config.db2Username(),config.db2Password());
		        
		        PreparedStatement preps = conn.prepareStatement("DELETE FROM "+config.crimeMappingDB2TableName());
		        int numUpdates=preps.executeUpdate();
		        logger.info("{	Delete Status	}	"+numUpdates);
				conn.commit();
				conn.close();
			}catch(Exception e){
				logger.error("{	Xception@ db2 delete oper }	"+e);
			}
			// Creation of events datasource records by using IOC Post URL.			
			CrimeMappingMainTest crimeMappingMainTest = new CrimeMappingMainTest();
			crimeMappingMainTest.createDSRecordForCrimeMapping(response.getBody().toString());
			
		}catch(Exception e){
			logger.error("{ Xception } "+e);
		}
	}

	public static void main(String[] args) {
		FetchDataFromCrimeMappingURL dataFromITMSURL = new FetchDataFromCrimeMappingURL();
		dataFromITMSURL.getDatafromURL();
		try {
//			dataFromITMSURL.sendPost();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}