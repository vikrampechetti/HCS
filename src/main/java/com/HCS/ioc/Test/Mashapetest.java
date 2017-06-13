package com.HCS.ioc.Test;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class Mashapetest {

	public static void main(String[] args) {
		String previousJsonString = "";
		while (true) {
			try {

				HttpResponse<String> responseFromITMS = Unirest.get("http://192.168.2.106:8280/ITMS/rest/Events")
						.header("content-type", "application/json").header("cache-control", "no-cache")
						.header("postman-token", "c2734c43-73b4-afac-71d3-e682fbdfcbdf").asString();
				JSONObject jsonStringfromGet = new JSONObject(responseFromITMS.getBody());
				if (!(previousJsonString.equals(jsonStringfromGet.toString()))) {
					previousJsonString = jsonStringfromGet.toString();
					HttpResponse<String> responseToITMS = Unirest.post("http://192.168.2.106:8280/ITMS/rest/Events")
							.header("content-type", "application/json").header("cache-control", "no-cache")
							.header("postman-token", "61cf5499-d06a-01d8-57ca-e67be450d9ee")
							.body(jsonStringfromGet.toString()).asString();
					System.out.println(responseToITMS.getStatus()+responseToITMS.getStatusText());
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
