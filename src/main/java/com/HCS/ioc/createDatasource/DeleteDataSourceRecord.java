package com.HCS.ioc.createDatasource;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.authentication.Authentication;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author BALA CHANDER April 18, 2016 
 *
 */
public class DeleteDataSourceRecord {
	static Logger logger = LoggerFactory.getLogger(DeleteDataSourceRecord.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	URI uri;
	URI uri_1;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	String output = null;
	String host = null;
	
	Authentication authentication = new Authentication();
	@SuppressWarnings("static-access")
	public String deleteDataSourceRecord(String dataSourceID,String objectID){
		try{
			//getConnectionStatus();
			uri = new URI("https://"+config.host()+":"+config.port()+"/ibm/ioc/api/data-injection-service/datablocks/"+dataSourceID+"/dataitems/"+objectID);
			webResource = client.resource(uri);
			authentication.getAuthentication();

			response = webResource
					.header(config.sessionName(), config.sessionValue())
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
					.delete(ClientResponse.class);
		}catch(Exception e){
			logger.error("EXCEPTION  [:: Exception ::]  "+e);
			e.printStackTrace();
		}
		return response.toString();
	}
}

