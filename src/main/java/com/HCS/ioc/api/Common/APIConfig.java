package com.HCS.ioc.api.Common;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

/**
 * @author OohithVikramRao 23-May-2017
 *
 */

@Sources({ "file:C:\\hcs\\HCSAPIConfig.properties", "classpath:HCSAPIConfig.properties", })
public interface APIConfig extends Config {
	/****************************************
	 * IBM IOC access Credentials
	 ****************************************/
	String host();

	String host_1();

	String port();

	/****************************************
	 * IBM-Session-ID, Value
	 ****************************************/
	String sessionName();

	String sessionValue();

	/****************************************
	 * DB2 Credentials
	 ****************************************/
	String DB2Username();

	String DB2Usersecret();

	String DB2ConnectionURL();

	/*****************************************
	 * IBM IOC Console
	 *****************************************/
	String username();

	String userSecret();

	/*****************************************
	 * Camera configurations
	 *****************************************/
	String CamerasDataSourceName();

	String cameraDataXLSFileAbsPath();

	String camerasDataSourceID();

	String CamerasDB2Table();

	String CamerasRequiredFields();

	/*****************************************
	 * Offenders configurations
	 *****************************************/
	String OffendersDataSourceName();

	String OffendersDataSourceId();

	String OffendersDB2Table();

	String OffendersRequiredFields();

	/*****************************************
	 * Events configurations
	 *****************************************/
	String EventsDataSourceName();

	String EventsDataSourceId();

	String EventsDB2Table();

	String EventsRequiredFields();

	/*****************************************
	 * Vehicle configurations
	 *****************************************/
	String VehicleInfoDataSourceName();

	String VehicleInfoDataSourceId();

	String VehicleInfoDB2Table();

	String VehicleInfoRequiredFields();

	/*****************************************
	 * Ambulances configurations
	 *****************************************/
	String AmbulancesDataSourceName();

	String AmbulancesDataSourceId();

	String AmbulancesDB2Table();

	String AmbulancesRequiredFields();

	/*****************************************
	 * FireEngines configurations
	 *****************************************/
	String FireEnginesDataSourceName();

	String FireEnginesDataSourceId();

	String FireEnginesDB2Table();

	String FireEnginesRequiredFields();

	/*****************************************
	 * CrimeMapping configurations
	 *****************************************/
	String CrimeMappingDataSourceName();

	String CrimeMappingDataSourceId();

	String CrimeMappingDB2Table();

	String CrimeMappingRequiredFields();

	/*****************************************
	 * JRMS configurations
	 *****************************************/
	String JRMSDataSourceName();

	String JRMSDataSourceId();

	String JRMSDB2Table();

	String JRMSRequiredFields();

	/*****************************************
	 * Vehicle Hotlist configurations
	 *****************************************/
	String VehicleHotlistDataSourceName();

	String VehicleHotlistDataSourceId();

	String VehicleHotlistDB2Table();

	String VehicleHotListRequiredFields();

	/*****************************************
	 * Hospitals configurations
	 *****************************************/
	String HospitalsDataSourceName();

	String HospitalsDataSourceId();

	String HospitalsDB2Table();

	String HospitalsRequiredFields();
}
