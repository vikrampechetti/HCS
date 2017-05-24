package com.HCS.ioc.api.Common;

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
	
	/****************************************
	 *  DB2 Credentials
	 ****************************************/
	String DB2Username();
	String DB2Usersecret();
	String DB2ConnectionURL();
	
	/****************************************
	 *  IBM IOC Console 
	 ****************************************/
	String username();
	String userSecret();
	
	/*****************************************
	 * 	Camera configurations
	 *****************************************/
	String cameraDataXLSFileAbsPath();
	String camerasDataSourceID();
	
}

