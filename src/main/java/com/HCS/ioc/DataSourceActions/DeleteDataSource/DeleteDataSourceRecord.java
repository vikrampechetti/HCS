package com.HCS.ioc.DataSourceActions.DeleteDataSource;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.Common.APIConfig;
import com.HCS.ioc.authentication.Authentication;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author OohithVikramRao 19-May-2017
 *
 */
public class DeleteDataSourceRecord {
	static Logger logger = LoggerFactory.getLogger(DeleteDataSourceRecord.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	URI uri;
	Client client = new Client();
	WebResource webResource;
	ClientResponse response;
	
	public String deleteDataSourceRecord(String dataSourceID,String objectID){
		try{
			logger.info("Deleting DataSource Record with OBJECTID: "+objectID+" due to a record already exists on the same cameraID");
			uri = new URI("https://"+config.host()+":"+config.port()+"/ibm/ioc/api/data-injection-service/datablocks/"+dataSourceID+"/dataitems/"+objectID);
			webResource = client.resource(uri);
			Authentication.getAuthentication();

			response = webResource
					.header(config.sessionName(), config.sessionValue())
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
					.delete(ClientResponse.class);
		}catch(Exception e){
			logger.error("EXCEPTION  @ deleteDataSourceRecord } "+e);
			e.printStackTrace();
		}
		return response.toString();
	}
	
}