package com.HCS.ioc.api.Common;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

/**
 * @author OohithVikramRao 23-May-2017
 *
 */

@Sources({"file:C:\\hcs\\HCSAPIConfig.properties",
		"classpath:HCSAPIConfig.properties",
		})
public interface APIConfig  extends Config {	
	/****************************************
	 *	IBM IOC access Credentials
	 ****************************************/
	String host();
	String host_1();
	String port();
	
	/****************************************
	 *	IBM-Session-ID, Value
	 ****************************************/
	String sessionName();
	String sessionValue();
	
	/****************************************
	 *  DB2 Credentials
	 ****************************************/
	String DB2Username();
	String DB2Usersecret();
	String DB2ConnectionURL();
	
	/*****************************************
	 *  IBM IOC Console 
	 *****************************************/
	String username();
	String userSecret();
	
	/*****************************************
	 * 	Camera configurations
	 *****************************************/
	String cameraDataXLSFileAbsPath();
	String camerasDataSourceID();
	String CamerasDB2Table();
	
	/*****************************************
	 * 	Offenders configurations
	 *****************************************/
	String OffendersDataSourceId();
	String OffendersDB2Table();
	
	/*****************************************
	 * 	Events configurations
	 *****************************************/
	String EventsDataSourceId();
	String EventsDB2Table();
	
	/*****************************************
	 * 	Vehicle configurations
	 *****************************************/
	String VechileInfoDataSourceId();
	String VechileInfoDB2Table();
	
	/*****************************************
	 * 	Ambulances configurations
	 *****************************************/
	String AmbulancesDataSourceId();
	String AmbulancesDB2Table();
	
	/*****************************************
	 * 	FireEngines configurations
	 *****************************************/
	String FireEnginesDataSourceId();
	String FireEnginesDB2Table();
	
	/*****************************************
	 * 	CrimeMapping configurations
	 *****************************************/
	String CrimeMappingDataSourceId();
	String CrimeMappingDB2Table();
	
	/*****************************************
	 * 	JRMS configurations
	 *****************************************/
	String JRMSDataSourceId();
	String JRMSDB2Table();
	
}

