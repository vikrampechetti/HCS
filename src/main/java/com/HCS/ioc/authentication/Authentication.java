package com.HCS.ioc.authentication;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.Common.APIConfig;
import com.HCS.ioc.disable.ssl.DisableSSL;
/**
 * @author OohithVikramRao 19-May-2017
 *
 */
public class Authentication {
	static Logger logger = LoggerFactory.getLogger(Authentication.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	static {
		try{
	    	DisableSSL.disableSslVerification();
		}catch(Exception e){
			logger.error("EXCEPTION  [:@ Exception :]	"+e);
			e.printStackTrace();
		}
	}
	static {
		try{
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				
				public boolean verify(String hostname, SSLSession session) {
					if(hostname.equals(config.host())){
						return true;
					}
					return false;
				}
			});
		}catch(Exception e){
			logger.error("EXCEPTION  [:@ Exception :]	"+e);
			e.printStackTrace();
		}
	}
	
	public static void getAuthentication(){
		try{
			java.net.Authenticator.setDefault(new java.net.Authenticator(){
				protected java.net.PasswordAuthentication getPasswordAuthentication(){
					return new java.net.PasswordAuthentication(config.username(),config.userSecret().toString().toCharArray());					
				}
			});
		}catch(Exception e){
			logger.error("EXCEPTION  [:@ Exception :]	"+e);
			e.printStackTrace();
		}
	}
}
