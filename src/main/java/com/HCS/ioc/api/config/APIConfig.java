package com.HCS.ioc.api.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"file:C:\\hcs\\HCSAPIConfig.properties",
		"classpath:HCSAPIConfig.properties",
		})
public interface APIConfig  extends Config {	
	String host();
	
	String host_1();
	
	String port();
	
	String sessionName();
	
	String sessionValue();
	
	String username();
	
	String userSecret();
	
	String DevicesDataSourceID();
	
	String initiatedDataSourceID();
	
	String endedDataSourceID();
	
	String inProgressDataSourceID();
	
	String tempFile();
	
	String sourceURL();
	
	String authorizationToken();
	
	/*
	 * 	Events Configurations
	 * */
	String eventstempFile();
	
	String eventsSourceURL();
	
	String eventsauthorizationToken();
	
	String eventsDatasourceID();
	
	String eventsDSRecordsIDTempFile();
	
	/*************************************
	 * 	ITMS Configurations
	 *************************************/
	String itmstempFile();
	
	String itmsSourceURL();
	
	String itmsauthorizationToken();
	
	String itmsDatasourceID();
	
	String itmsRequestBody();
	
	/**************************************
	 * 	Crime Mapping Configurations
	 **************************************/
	String crimeMapSourceURL();
	
	String crimeMapAuthorizationToken();
	
	String crimeMapDataSourceID();
	
	String crimeMapRequestBody();
	
	String crimeMappingDB2TableName();
	
	/**************************************
	 * 	Offenderes Configurations
	 **************************************/
	String offendersSourceURL();
	
	String offendersAuthorizationToken();
	
	String offendersDataSourceID();
	
	String offendersRequestBody();
	
	/**************************************
	 * 	Gun License Configurations
	 **************************************/
	String gunLicenseSourceURL();
	
	String gunLicenseAuthorizationToken();
	
	String gunLicenseDataSourceID();
	
	String gunLicenseRequestBody();
	
	/*************************************
	 * DB2 connection configuration's
	 *************************************/
	String db2Username();
	
	String db2Password();
	
	String eventsDB2Connection();
	
	/**************************************
	 * ITMS System Configurtions 
	 **************************************/
	
	String jsonCapFileLocation();
	
	String itmsSystemDataSourceID();
	
	String itmsSystemSourceURL();
	
	String itmsSystemAuthorizationToken(); 
	
	/****************************************
	 * 		JRMS Configuration
	 ****************************************/
	String jrmsSystemDataSourceID();
	
	String jrmsSystemSourceURL();
	
	String jrmsSystemAuthorizationToken(); 
	
	String jrmsRequestBody();
	
	/******************************************
	 * 	Google Maps API Configuration
	 ******************************************/
	String apiURL();
	
	String XPathExpression();
	
	String XPathExpressionLat();
	
	String XPathExpressionLong();
	
	String apiURLForStateDataJSON();
	
	int apiRequestSleepTimer();
	
	/*****************************************
	 * 	Camere configurations
	 *****************************************/
	String cameraDataXLSFileAbsPath();
	
	String camerasDataSourceID();
	
}

