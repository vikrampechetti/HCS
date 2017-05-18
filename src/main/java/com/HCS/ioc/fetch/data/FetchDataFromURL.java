/**
 * 
 */
package com.HCS.ioc.fetch.data;

import java.net.URI;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.devices.main.DevicesMainTest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author BALA CHANDER Apr 18, 2017 
 *
 */
public class FetchDataFromURL {
	
	static Logger logger = LoggerFactory.getLogger(FetchDataFromURL.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	URI uri;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	public void getDatafromURL(){
		try{
			HttpResponse<JsonNode> response = Unirest.get(config.sourceURL())
					  .header("authorization", "token "+config.authorizationToken())
					  .header("cache-control", "no-cache")
					  .header("postman-token", "1ac33406-0f3d-d9c4-036c-46acdab9a88c")
					  .asJson();

			// retrieve the parsed JSONObject from the response
			JSONObject myObj = response.getBody().getObject();
			
			DevicesMainTest devicesMainTest = new DevicesMainTest();
			devicesMainTest.createDataSourceRecordInIOCforDevices(myObj.toString());
			
		}catch(Exception e){
			logger.error("{ } "+e);
		}
	}
	public static void main(String[] args) {
		FetchDataFromURL dataFromURL = new FetchDataFromURL();
		dataFromURL.getDatafromURL();
	}
	
	public static Response clientResponseToResponse(ClientResponse r) {
	    // copy the status code
	    ResponseBuilder rb = Response.status(r.getStatus());
	    // copy all the headers
	    for (Entry<String, List<String>> entry : r.getHeaders().entrySet()) {
	        for (String value : entry.getValue()) {
	        	logger.info(entry.getKey() + "  :  "+ value);
	            rb.header(entry.getKey(), value);
	        }
	    }
	    // copy the entity
	    rb.entity(r.getEntityInputStream());
	    logger.info(rb.toString());
	    // return the response
	    return rb.build();
	}

}
