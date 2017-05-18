/**
 * 
 */
package com.HCS.ioc.fetch.data;

import java.net.URI;
import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.devices.main.DevicesMainTest;
import com.HCS.ioc.events.main.EventsMainTest;
import com.HCS.ioc.updateDatasourceRecord.UpdateEventsDSRecordInDB2;
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
public class FetchDataFromEventsURL {
	
	static Logger logger = LoggerFactory.getLogger(FetchDataFromEventsURL.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	URI uri;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	public void getDatafromURL(){
		try{

			HttpResponse<JsonNode> response = Unirest.get(config.eventsSourceURL())
					  .header("authorization", "token "+config.eventsauthorizationToken())
					  .header("cache-control", "no-cache")
					  .header("postman-token", "e36f2e29-8417-9358-7b57-e6f034cca74b")
					  .asJson();

			// retrieve the parsed JSONObject from the response
			JSONObject myObj = response.getBody().getObject();
			//logger.info(myObj.toString());
			
			// Updating of events datasource records in DB2
			try{
				UpdateEventsDSRecordInDB2 updateeventsDSRecordInDB2 = new UpdateEventsDSRecordInDB2();
				updateeventsDSRecordInDB2.updateEventsDSRecords(myObj.toString());
			}catch(Exception e){
				logger.error("{	Xception	}	"+e);
			}

			// Creation of events datasource records by using IOC Post URL.
			EventsMainTest eventsMainTest = new EventsMainTest();
			eventsMainTest.createDataSourceForEvents(myObj.toString());
			
		}catch(Exception e){
			logger.error("{ } "+e);
		}
	}
	public static void main(String[] args) {
		FetchDataFromEventsURL dataFromURL = new FetchDataFromEventsURL();
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
