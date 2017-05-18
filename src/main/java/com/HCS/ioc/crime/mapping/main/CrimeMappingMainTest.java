/**
 * 
 */
package com.HCS.ioc.crime.mapping.main;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;
import com.HCS.ioc.createDatasource.CreateDataSourceRecord;

/**
 * @author BALA CHANDER May 3, 2017 
 *
 */
public class CrimeMappingMainTest {
	
	static Logger logger = LoggerFactory.getLogger(CrimeMappingMainTest.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	static String payload = "{\r\n  \"CrimeDetails\": [\r\n    {\r\n      \"Crimedate\": \"Apr 18 2017 10:00PM\",\r\n      \"Description\": \"The facts of the case are that that on 18-04-2017 at 2220 hours while he was waiting at Traffic Signal, beneath of Telugu Talli Flyover, Hyderabad on his two wheeler vehicle along with his wife. Meantime one unknown person snatched his wife\u2019s handbag, which containing Apple I-Phone 6 Series bearing IMEI No. 352091072806295 with mobile No. 9550081644, one Andhra Bank Debit Card and Net Cash of Rs. 600/-. Hence he requested for taking necessary action.\",\r\n      \"Latitude\": \"17.408\",\r\n      \"Location\": \"Excate beneath of Telugu Talli Fly over singal point\",\r\n      \"Longitude\": \"78.4722\",\r\n      \"OffenceSubType\": \"Snatching\",\r\n      \"OffenceType\": \"Property Offence\",\r\n      \"Pscode\": \"SAIF\"\r\n    },\r\n    {\r\n      \"Crimedate\": \"Apr  9 2017 10:00AM\",\r\n      \"Description\": \"The facts of the case complainant stated that he is  running a chicken shop at Opp: Quarter No 193, Adarsh Nagar Hyderabad since 2013.  Few days back he got a friendship with one person by name Arshad, who came from Nizambad to meet Ex-MP Sri D.Srinivas and used to come daily to his chicken shop and sit hours.  On 09-04-2017 at around 0800 hours, as usally he came to his shop on complainant\u2019s motor cycle and went inside the shop by parking it in front of the said shop.  After one hour i.e., 1000 hours he along with Arshad went to Adarsh Tiffin Center to bring Tiffin on his motor cycle, while the Arshad was riding the bike and he sat on back side seat.  When they reached in front of Tiffin center and he got down from the motor cycle and went inside the tiffin centre.  After 15 minutes when he came out and found his bike was missing along with Arshad.  Immediately he searched at all possible places but no use.  At last he came to conclusion that his friend Arshad taken away his Motor Cycle CB Shine Red Color, bearing No: TS-12EA-8050, Chassis No: ME4JC36JAE7743325 & Engine No. JC36E73209575, Color: Red Model 2014.  Since, then he was awaiting for Arshad may be return along with bike but till now he could not came. So, today he came to PS and given a complaint against him.\",\r\n      \"Latitude\": \"17.4042\",\r\n      \"Location\": \"infront of Adarsh Tiffin Center, Adarsh Nagar, Hyd\",\r\n      \"Longitude\": \"78.4706\",\r\n      \"OffenceSubType\": \"Diverting Attention\",\r\n      \"OffenceType\": \"Property Offence\",\r\n      \"Pscode\": \"SAIF\"\r\n    }\r\n  ],\r\n  \"Message\": null\r\n}";
	public void createDSRecordForCrimeMapping(String payload){
		try{
			JSONObject payloadObj = new JSONObject(payload);
			JSONArray crimeDetailsArr = (JSONArray) payloadObj.get("CrimeDetails");
			
			String response = null;
			CreateDataSourceRecord createDSRecord = new CreateDataSourceRecord();
			
			for(int count=0; count<crimeDetailsArr.length(); count++){	
				JSONObject crimeMappingDSPayload = new JSONObject();
				JSONObject crimeDetailsObj = new JSONObject(crimeDetailsArr.getString(count));
				
				crimeDetailsObj.getString("Location");
				
				crimeMappingDSPayload.put("STARTDATETIME", currentSysDateTime());
				crimeMappingDSPayload.put("ENDDATETIME", currentSysDateTime());
				crimeMappingDSPayload.put("LASTUPDATEDATETIME", currentSysDateTime());				
				String location = "POINT("+crimeDetailsObj.getString("Longitude")+" "+crimeDetailsObj.getString("Latitude")+")";							
				crimeMappingDSPayload.put("LOCATION", location);
				crimeMappingDSPayload.put("DESCRIPTION", crimeDetailsObj.getString("Description"));
				crimeMappingDSPayload.put("OffenceSubType", crimeDetailsObj.getString("OffenceSubType"));
				crimeMappingDSPayload.put("OffenceType", crimeDetailsObj.getString("OffenceType"));
				// Pscode is of NAME in IOC
				crimeMappingDSPayload.put("NAME", crimeDetailsObj.getString("Pscode"));
				
				response = createDSRecord.createDataSourceRecord(config.crimeMapDataSourceID(), crimeMappingDSPayload.toString());
				logger.info("{ Crime Data Payload }  "+crimeMappingDSPayload.toString());
				logger.info("{	Response of Crime Mapping	}	"+response);
			}
		}catch(Exception e){
			logger.error("{	Xception@CrimeMappingMainTest }	"+e);
		}		
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
