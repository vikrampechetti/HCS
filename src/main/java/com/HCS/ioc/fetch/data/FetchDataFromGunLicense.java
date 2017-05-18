package com.HCS.ioc.fetch.data;

import java.net.URI;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author BALA CHANDER May 1, 2017 
 *
 */
public class FetchDataFromGunLicense {

	static Logger logger = LoggerFactory.getLogger(FetchDataFromGunLicense.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	URI uri;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	
	public void getDatafromURL(){
		try{
			// fetching data from crime mapping

			HttpResponse<String> response = Unirest.post(config.gunLicenseSourceURL())
					  .header("authorization", "token "+config.gunLicenseAuthorizationToken())
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .header("postman-token", "25c4be52-b33d-2dd6-082a-6a5f2d845f3a")
					  .body(config.gunLicenseRequestBody())
					  .asString();
			
			// retrieve the parsed JSONObject from the response
			System.out.println(response.getStatus() +"  :::  "+response.getStatusText());
			//logger.info(myObj.toString());	
			// Creation of events datasource records by using IOC Post URL.
			logger.info(response.getBody().toString());
			
		}catch(Exception e){
			logger.error("{ Xception } "+e);
		}
	}

	public static void main(String[] args) {
		FetchDataFromGunLicense dataFromITMSURL = new FetchDataFromGunLicense();
		dataFromITMSURL.getDatafromURL();
		try {
//			dataFromITMSURL.sendPost();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
