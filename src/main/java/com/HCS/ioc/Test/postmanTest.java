/**
 * @author Oohithvikramrao 16-Jun-2017
 */
package com.HCS.ioc.Test;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.HCS.ioc.disable.ssl.DisableSSL;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @Author OohithVikramRao 16-Jun-2017
 *
 */
public class postmanTest {

	public static void main(String[] args) {
		try {
			XMLConfiguration configuration = new XMLConfiguration("F:/message.xml");
			String fireEngineDataInput = configuration.getProperty("FireEngines").toString();
			DisableSSL.disableSslVerification();
			HttpResponse<String> response = Unirest
					.post("https://192.168.2.120:9443/ibm/ioc/api/data-injection-service/datablocks/14/dataitems")
					.header("content-type", "application/json").header("ibm-session-id", "!<fksjsowWoj")
					.header("authorization", "Basic c3lzYWRtaW46dXMzcnBhODg=").header("cache-control", "no-cache")
					.header("postman-token", "44bab3b2-b2e4-40f8-7fb7-33ec6e21cddb").body(fireEngineDataInput)
					.asString();
			System.out.println(response.getBody());
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
