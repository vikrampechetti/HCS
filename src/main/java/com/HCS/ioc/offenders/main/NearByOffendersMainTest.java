/**
 * 
 */
package com.HCS.ioc.offenders.main;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.createDatasource.CreateDataSourceRecord;
import com.HCS.ioc.fetch.data.FetchDataFromJRMSURL;
import com.ibm.db2.jcc.am.lo;

/**
 * @author BALA CHANDER May 5, 2017 
 *
 */
public class NearByOffendersMainTest {

	static Logger logger = LoggerFactory.getLogger(NearByOffendersMainTest.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	public void createDataSourceRecordForNearByOffenders(String payload){
		FetchDataFromJRMSURL  dataFromJRMSURL = new FetchDataFromJRMSURL();
		String jrmsPayload = dataFromJRMSURL.fetchDataFromJRMSData();
		try{
			JSONObject offendersPayload = new JSONObject(payload);
			JSONArray offendersDetailsArray = new JSONArray(offendersPayload.getString("MOmainList"));
			
			CreateDataSourceRecord createDSRecord = new CreateDataSourceRecord();
			
			for(int count=0; count<offendersDetailsArray.length(); count++){				
				JSONObject dataSourcePayload = new JSONObject();
				JSONObject offenderDetailsObj = new JSONObject(offendersDetailsArray.getString(count));
				dataSourcePayload.put("AGE",offenderDetailsObj.getString("AGE"));
				dataSourcePayload.put("ALIASES",offenderDetailsObj.getString("ALIASES"));
				dataSourcePayload.put("CRIMENO",offenderDetailsObj.getString("CRIMENO"));
				dataSourcePayload.put("CURRENTACTIVITY",offenderDetailsObj.getString("CURRENTACTIVITY"));
				dataSourcePayload.put("DATEOFLASTARREST",offenderDetailsObj.getString("DATEOFLASTARREST"));
				dataSourcePayload.put("DATEOFRELEASE",offenderDetailsObj.getString("DATEOFRELEASE"));
				dataSourcePayload.put("DOB",offenderDetailsObj.getString("DOB"));
				dataSourcePayload.put("EMAIL",offenderDetailsObj.getString("EMAIL"));
				dataSourcePayload.put("FULLIMAGE",offenderDetailsObj.getString("FULLIMAGE"));
				dataSourcePayload.put("Flag",offenderDetailsObj.getString("Flag"));
				dataSourcePayload.put("HISTORYSHEET",offenderDetailsObj.getString("HISTORYSHEET"));
				dataSourcePayload.put("ID",offenderDetailsObj.getString("ID"));
				dataSourcePayload.put("LATESTPHOTOGRAPHCOLLECTED",offenderDetailsObj.getString("LATESTPHOTOGRAPHCOLLECTED"));
				
				dataSourcePayload.put("MOBILENO",offenderDetailsObj.getString("MOBILENO"));
				dataSourcePayload.put("MODUSOPERENDI",offenderDetailsObj.getString("MODUSOPERENDI"));
				dataSourcePayload.put("OFFENDERNAME",offenderDetailsObj.getString("OFFENDERNAME"));
				dataSourcePayload.put("OFFENDER_HOUSE_OWNER",offenderDetailsObj.getString("OFFENDER_HOUSE_OWNER"));
				dataSourcePayload.put("OFFENDER_ID",offenderDetailsObj.getString("OFFENDER_ID"));
				dataSourcePayload.put("PERMANENT_ADDRESS",offenderDetailsObj.getString("PERMANENT_ADDRESS"));
				dataSourcePayload.put("PHOTOGRAPH_PATH",offenderDetailsObj.getString("PHOTOGRAPH_PATH"));
				dataSourcePayload.put("PHOTO_VIEW",offenderDetailsObj.getString("PHOTO_VIEW"));
				dataSourcePayload.put("PRESENT_ADDRESS",offenderDetailsObj.getString("PRESENT_ADDRESS"));
				dataSourcePayload.put("PSARRESTED",offenderDetailsObj.getString("PSARRESTED"));
				dataSourcePayload.put("PSID",offenderDetailsObj.getString("PSID"));
				dataSourcePayload.put("VEHCILEDETAILS",offenderDetailsObj.getString("VEHCILEDETAILS"));
				
				dataSourcePayload.put("STARTDATETIME", currentSysDateTime());
				dataSourcePayload.put("ENDDATETIME", currentSysDateTime());
				dataSourcePayload.put("LASTUPDATEDATETIME", currentSysDateTime());				
				String location = "POINT("+offenderDetailsObj.getString("LONGITUDE")+" "+offenderDetailsObj.getString("LATITUDE")+")";							
				dataSourcePayload.put("LOCATION", location);
				
				boolean OFFENDER_STATUS = createDataSourceRecordForJRMSData(jrmsPayload, offenderDetailsObj.getString("CRIMENO"),offenderDetailsObj.getString("OFFENDERNAME"));
				if(OFFENDER_STATUS==true){
					dataSourcePayload.put("OFFENDER_STATUS", "YES");
				}else{
					dataSourcePayload.put("OFFENDER_STATUS", "NO");
				}
				
				String response = createDSRecord.createDataSourceRecord(config.offendersDataSourceID(), dataSourcePayload.toString());
				logger.info("{ offenders Response }	"+response);
				
			}			
		}catch(Exception e){
			logger.error("{ Xception@ offender DS payload }	"+e);
		}
	}
	
	public boolean createDataSourceRecordForJRMSData(String payload, String CRIMENO, String OFFENDERNAME){
		boolean OFFENDER_STATUS = false;
		try{
			JSONObject JRMSpayloadObj = new JSONObject(payload);
			JSONArray PrisonersDataDetailsArray = (JSONArray)JRMSpayloadObj.get("PrisonersDataDetails");			
			
			for(int count=0; count<PrisonersDataDetailsArray.length(); count++){
				JSONObject datasourcePayload = new JSONObject();
				JSONObject prisonerDetailsObj = new JSONObject(PrisonersDataDetailsArray.getString(count));
				datasourcePayload.put("PSArrested", prisonerDetailsObj.getString("PSArrested"));
				datasourcePayload.put("PrisonerNo", prisonerDetailsObj.getString("PrisonerNo"));
				datasourcePayload.put("Admission_to_Jail", prisonerDetailsObj.getString("Admission_to_Jail"));
				datasourcePayload.put("CIN", prisonerDetailsObj.getString("CIN"));
				datasourcePayload.put("Addr_DuringRelease", prisonerDetailsObj.getString("Addr_DuringRelease"));
				datasourcePayload.put("Photo", prisonerDetailsObj.getString("Photo"));
				datasourcePayload.put("Gender", prisonerDetailsObj.getString("Gender"));
				datasourcePayload.put("HeadofCrime", prisonerDetailsObj.getString("HeadofCrime"));
				datasourcePayload.put("TypeofRelease", prisonerDetailsObj.getString("TypeofRelease"));
				datasourcePayload.put("Name", prisonerDetailsObj.getString("Name"));
				datasourcePayload.put("CrimeNos", prisonerDetailsObj.getString("CrimeNos"));
				datasourcePayload.put("JailName", prisonerDetailsObj.getString("JailName"));
				datasourcePayload.put("PhotoPath", prisonerDetailsObj.getString("PhotoPath"));
				datasourcePayload.put("PlaceofIdentificationMark", prisonerDetailsObj.getString("PlaceofIdentificationMark"));
				datasourcePayload.put("RlDtOrder", prisonerDetailsObj.getString("RlDtOrder"));
				datasourcePayload.put("IdentificationMark", prisonerDetailsObj.getString("IdentificationMark"));
				datasourcePayload.put("ReleaseDt", prisonerDetailsObj.getString("ReleaseDt"));
				datasourcePayload.put("STARTDATETIME", currentSysDateTime());
				datasourcePayload.put("ENDDATETIME", currentSysDateTime());
				datasourcePayload.put("LASTUPDATEDATETIME", currentSysDateTime());		
				System.out.println(datasourcePayload +"\n");
				if(prisonerDetailsObj.getString("CIN").contains(CRIMENO) || 
						prisonerDetailsObj.getString("Name").contains(OFFENDERNAME)){
					OFFENDER_STATUS = true;
				}else{
					OFFENDER_STATUS = false;
				}
			}
			
		}catch(Exception e){
			logger.error("{ Xception@ JRMSMainTest }	"+e);
		} 		
		return OFFENDER_STATUS;
	}
	
	public String currentSysDateTime(){
		String curDateTime = null;
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			curDateTime = simpleDateFormat.format(curDate);
		}catch(Exception e){
			logger.error("{	Xception @ sysDateTime	}	"+e);
		}
		return curDateTime;
	}
}
