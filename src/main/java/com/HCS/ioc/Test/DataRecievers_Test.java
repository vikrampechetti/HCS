package com.HCS.ioc.Test;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;

import com.HCS.ioc.authentication.Authentication;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class DataRecievers_Test {
	static URI uri;
	static Client client = new Client();
	static WebResource webResource;
	static ClientResponse response;

	public static void main(String[] args) {
		try {
			uri = new URI("https://"+"192.168.2.68:9443"+"/ibm/ioc/api/receiver-service/datareceivers/"+"92");
			webResource = client.resource(uri);
			Authentication.getAuthentication();
			
			response=webResource
					.header("IBM-Session-ID", "--")
					.accept(MediaType.APPLICATION_JSON)
	                .type(MediaType.APPLICATION_JSON)
	                .get(ClientResponse.class);
			
			System.out.println(response.toString());
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
	}
	}