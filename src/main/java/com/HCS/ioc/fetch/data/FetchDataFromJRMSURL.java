package com.HCS.ioc.fetch.data;

import java.net.URI;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.jrms.main.JRMSMainTest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * @author BALA CHANDER May 9, 2017 
 *
 */
public class FetchDataFromJRMSURL {

	static Logger logger = LoggerFactory.getLogger(FetchDataFromJRMSURL.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	URI uri;
	Client client = new Client();
	WebResource webResource;
	HttpResponse<String> response;
	
	public static void main(String[] args) {
		FetchDataFromJRMSURL fetchDataFromJRMSURL = new FetchDataFromJRMSURL();
		fetchDataFromJRMSURL.fetchDataFromJRMSData();
	}
	
	public String fetchDataFromJRMSData(){
		JSONObject payloadObj = null;
		try{
			response = Unirest.post(config.jrmsSystemSourceURL())
					  .header("authorization", "token "+config.jrmsSystemAuthorizationToken())
					  .header("content-type", "application/json")
					  .header("cache-control", "no-cache")
					  .header("postman-token", "25c4be52-b33d-2dd6-082a-6a5f2d845f3a")
					  .body(config.jrmsRequestBody())
					  .asString();
			System.out.println(response.getBody().toString());
			payloadObj = new JSONObject(response.getBody().toString());			
		}catch(Exception e){
			logger.error("{ Xception@ fetching JRMS Data	}	"+e);
		}	
		return payloadObj.toString();
	}
}
