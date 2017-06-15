package com.HCS.ioc.fetch.data;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ExtractEventsDataFromITMS {
	public static void main(String[] args) {
		String payload = "";
		while (true) {
			try {

				HttpResponse<String> responseFromITMS = Unirest.get("http://192.168.2.106:8280/ITMS/rest/Events")
						.header("content-type", "application/json").header("cache-control", "no-cache")
						.header("postman-token", "c2734c43-73b4-afac-71d3-e682fbdfcbdf").asString();
				
				
				JSONObject jsonStringfromGet = new JSONObject(responseFromITMS.getBody());
				if (!(payload.equals(jsonStringfromGet.toString()))) {
					System.out.println(" PAYLOAD FROM ITMS >>---->>> "+responseFromITMS.getBody());
					payload = new ReadEventsJsonData().readJSONDataFromURL(jsonStringfromGet.toString());
					
					HttpResponse<String> responseToITMS = Unirest.post("http://192.168.2.106:8280/ITMS/rest/Events")
							.header("content-type", "application/json").header("cache-control", "no-cache")
							.header("postman-token", "61cf5499-d06a-01d8-57ca-e67be450d9ee")
							.body(payload).asString();
					
					System.out.println(" PAYLOAD FROM IOC >>---->>> " + responseToITMS.getBody());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
