package com.HCS.ioc.updateDatasourceRecord;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.authentication.Authentication;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author BALA CHANDER Aug 19, 2016 
 *
 */
public class UpdateDataSourceRecord {
	static Logger logger = LoggerFactory.getLogger("analytics");
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	URI uri;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	String output = null;
	
	Authentication authentication = new Authentication();
	@SuppressWarnings("static-access")
	public String updateDataSourceRecord(String objectid, String dataSourceID,String payload){
		try{
			uri = new URI("https://"+config.host()+":"+config.port()+"/ibm/ioc/api/data-injection-service/datablocks/"+dataSourceID+"/dataitems/"+objectid);
			
			webResource = client.resource(uri);
			
			authentication.getAuthentication();

			response = webResource
					.header(config.sessionName(), config.sessionValue())
					.header("IBM-User-Time-Zone", "-")
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
					.put(ClientResponse.class,payload);
		}catch(Exception e){
			logger.error("EXCEPTION  [:: Exception ::]  "+e);
			e.printStackTrace();
		}
		return response.toString();
	}
	
	public static void main(String[] args) throws JSONException {
		UpdateDataSourceRecord dataSourceRecord = new UpdateDataSourceRecord();
		String payload = ""
				+ "{\"NAME\": \"Sensor_1\","
//				+"\"DATASOURCEID\": 1,"
				+ "\"SENSORLABEL\": \"NW Sensor #100\","
				+ "\"STARTDATETIME\": \"3/24/2016 15:47\","
				+ "\"ENDDATETIME\": \"3/24/2015 20:00\","
				+ "\"LASTUPDATETIME\": \"3/24/2016 15:47\","
				+ "\"TIMEZONEOFFSET\": \"150\","
				+ "\"QUANITY\": \"123567\","
				+ "\"LOCATION\": \"POINT(79.40029 28.072955)\","
				+ "\"LAT\": \"79.40029\","
				+ "\"OBJECTID\":\"11636\""
				+ "}";
		
		JSONObject postBody = new JSONObject();
		postBody.put("NAME", "Sensor_6");
		postBody.put("SENSORLABEL", "NW Sensor #6");
		postBody.put("STARTDATETIME", "3/25/2015 15:57");
		postBody.put("ENDDATETIME", "3/25/2015 20:00");
		postBody.put("LASTUPDATETIME", "3/25/2015 15:57");
		postBody.put("TIMEZONEOFFSET", 120);
		postBody.put("QUANITY", 4);
		postBody.put("LOCATION", "POINT(72.400269 24.072955)");
		postBody.put("LATITUDE", "70.400266");
		postBody.put("LONGITUDE", "22.072955");
		postBody.put("OBJECTID", "11636");
		
		System.out.println(payload);
		String objectid = String.valueOf(11636);
		String response = dataSourceRecord.updateDataSourceRecord(objectid,"1", postBody.toString());
		System.out.println(response);
	}
}

