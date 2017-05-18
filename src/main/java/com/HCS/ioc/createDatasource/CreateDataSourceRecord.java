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
public class CreateDataSourceRecord {
	static Logger logger = LoggerFactory.getLogger(CreateDataSourceRecord.class);
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
	public String createDataSourceRecord(String dataSourceID,String payload){
		try{
			//getConnectionStatus();
			uri = new URI("https://"+config.host()+":"+config.port()+"/ibm/ioc/api/data-injection-service/datablocks/"+dataSourceID+"/dataitems");
			webResource = client.resource(uri);
			authentication.getAuthentication();

			response = webResource
					.header(config.sessionName(), config.sessionValue())
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class,payload);
		}catch(Exception e){
			logger.error("EXCEPTION  [:: Exception ::]  "+e);
			e.printStackTrace();
		}
		return response.toString();
	}
	
	public boolean getConnectionStatus(){
		try{
			uri = new URI("https://"+config.host_1()+":"+config.port()+"/ibm/ioc/api/datasource-service/tabs");
			webResource = client.resource(uri);
			response = webResource.header(config.sessionName(), config.sessionValue())
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			if(response.toString().contains("200")){
				host = config.host_1();
				logger.info("[	Able to connect host@ ]"+config.host_1());
			}						
		}catch(Exception e1){
			logger.error("[	Exception@@ CreateDataSourceRecord	]	",e1);
			try{
				uri_1 = new URI("https://"+config.host()+":"+config.port()+"/ibm/ioc/api/datasource-service/tabs");
				webResource = client.resource(uri_1);
				response = webResource.header(config.sessionName(), config.sessionValue())
						.accept(MediaType.APPLICATION_JSON)
		                .type(MediaType.APPLICATION_JSON)
						.get(ClientResponse.class);
				if(response.toString().contains("200")){
					host = config.host();
					logger.info("[	Able to connect host@ ]"+config.host());
				}
			}catch(Exception e){
				logger.error("[	Exception@ conneting host()	]	",e);
			}
		}
		return true;
	}
}

