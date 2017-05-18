package com.HCS.ioc.jrms.main;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.aeonbits.owner.ConfigFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.api.config.APIConfig;

/**
 * @author BALA CHANDER May 9, 2017 
 *
 */
public class JRMSMainTest {
	
	static Logger logger = LoggerFactory.getLogger(JRMSMainTest.class);
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	
	static String payload = "{\r\n  \"PrisonersDataDetails\": [\r\n    {\r\n      \"Addr_DuringRelease\": \" \",\r\n      \"Admission_to_Jail\": \"09/03/2016\",\r\n      \"CIN\": \"0000033485\",\r\n      \"CrimeNos\": \"43/2016\",\r\n      \"Gender\": \"Male\",\r\n      \"HeadofCrime\": \"Ordinary Theft\",\r\n      \"IdentificationMark\": \"Mole\",\r\n      \"JailName\": \"CHANCHALGUDA\",\r\n      \"Name\": \"MOHD JAMEED @ JAMBU /  / \",\r\n      \"PSArrested\": \"Abidroad\",\r\n      \"Photo\": null,\r\n      \"PhotoPath\": null,\r\n      \"PlaceofIdentificationMark\": \"ON RIGHT LEG KNEE & ON STOMACH\",\r\n      \"PrisonerNo\": \"UT 7630\",\r\n      \"ReleaseDt\": \"15/03/2016\",\r\n      \"RlDtOrder\": \"15-03-2016 00:00:00\",\r\n      \"TypeofRelease\": \"Out of Jail\"\r\n    },\r\n    {\r\n      \"Addr_DuringRelease\": \"8-3-164/A/1/8, 2ND FLOOR, SARATHA NIVAS, ERRAGADDA, HYD\",\r\n      \"Admission_to_Jail\": \"18/01/2016\",\r\n      \"CIN\": \"0000027823\",\r\n      \"CrimeNos\": \"589/2012\",\r\n      \"Gender\": \"Male\",\r\n      \"HeadofCrime\": \"Ordinary Theft\",\r\n      \"IdentificationMark\": \"Mole\",\r\n      \"JailName\": \"CHANCHALGUDA\",\r\n      \"Name\": \"R RAJA RATNAM / 9391094846 / \",\r\n      \"PSArrested\": \"Abidroad\",\r\n      \"Photo\": null,\r\n      \"PhotoPath\": null,\r\n      \"PlaceofIdentificationMark\": \"ON NECK & ON RIGHT ARM\",\r\n      \"PrisonerNo\": \"UT 6549\",\r\n      \"ReleaseDt\": \"04/03/2016\",\r\n      \"RlDtOrder\": \"04-03-2016 00:00:00\",\r\n      \"TypeofRelease\": \"Out of Jail\"\r\n    },\r\n    {\r\n      \"Addr_DuringRelease\": \"ANANTHAPUR, NARAYANPET, MAHABUBNAGAR DIST.\",\r\n      \"Admission_to_Jail\": \"10/07/2015\",\r\n      \"CIN\": \"0000061431\",\r\n      \"CrimeNos\": \"218/2015\",\r\n      \"Gender\": \"Male\",\r\n      \"HeadofCrime\": \"Ordinary Theft\",\r\n      \"IdentificationMark\": \"Mole\",\r\n      \"JailName\": \"CHANCHALGUDA\",\r\n      \"Name\": \"MALLIPATILA SHAVNA REDDY /  / \",\r\n      \"PSArrested\": \"Abidroad\",\r\n      \"Photo\": null,\r\n      \"PhotoPath\": null,\r\n      \"PlaceofIdentificationMark\": \"A MOLE ON LEFT SIDE STOMACH & ON NECK.\",\r\n      \"PrisonerNo\": \"UT 2814\",\r\n      \"ReleaseDt\": \"09/02/2016\",\r\n      \"RlDtOrder\": \"09-02-2016 00:00:00\",\r\n      \"TypeofRelease\": \"Out of Jail\"\r\n    },\r\n    {\r\n      \"Addr_DuringRelease\": \"\",\r\n      \"Admission_to_Jail\": \"25/09/2015\",\r\n      \"CIN\": \"0000029462\",\r\n      \"CrimeNos\": \"422/2015 , 383/2015 , 171/2015 , 749/2015 , 757/2015 , 177/2015 , 191/2015\",\r\n      \"Gender\": \"Male\",\r\n      \"HeadofCrime\": \"Ordinary Theft\",\r\n      \"IdentificationMark\": \"Mole\",\r\n      \"JailName\": \"CHANCHALGUDA\",\r\n      \"Name\": \"P SASI KUMAR /  / \",\r\n      \"PSArrested\": \"Abidroad\",\r\n      \"Photo\": null,\r\n      \"PhotoPath\": null,\r\n      \"PlaceofIdentificationMark\": \"ON RIGHT HAND RING FINGER & ON LEFT SIDE OF NECK\",\r\n      \"PrisonerNo\": \"UT 6198\",\r\n      \"ReleaseDt\": \"31/01/2016\",\r\n      \"RlDtOrder\": \"31-01-2016 00:00:00\",\r\n      \"TypeofRelease\": \"Out of Jail\"\r\n    },\r\n    {\r\n      \"Addr_DuringRelease\": \"\",\r\n      \"Admission_to_Jail\": \"25/09/2015\",\r\n      \"CIN\": \"0000029465\",\r\n      \"CrimeNos\": \"422/2015 , 383/2015 , 171/2015 , 749/2015 , 757/2015 , 177/2015 , 191/2015 , 306/2015\",\r\n      \"Gender\": \"Male\",\r\n      \"HeadofCrime\": \"Ordinary Theft\",\r\n      \"IdentificationMark\": \"Mole\",\r\n      \"JailName\": \"CHANCHALGUDA\",\r\n      \"Name\": \"  GUNASELAN NAGARJUN /  / \",\r\n      \"PSArrested\": \"Abidroad\",\r\n      \"Photo\": null,\r\n      \"PhotoPath\": null,\r\n      \"PlaceofIdentificationMark\": \"ON LEFT SIDE OF STOMACH & ON LEFT HAND RING FINGER\",\r\n      \"PrisonerNo\": \"UT 6199\",\r\n      \"ReleaseDt\": \"31/01/2016\",\r\n      \"RlDtOrder\": \"31-01-2016 00:00:00\",\r\n      \"TypeofRelease\": \"Out of Jail\"\r\n    },\r\n    {\r\n      \"Addr_DuringRelease\": \"\",\r\n      \"Admission_to_Jail\": \"25/09/2015\",\r\n      \"CIN\": \"0000029467\",\r\n      \"CrimeNos\": \"422/2015 , 383/2015 , 17/2015 , 749/2015 , 757/2015 , 177/2015 , 191/2015 , 306/2015\",\r\n      \"Gender\": \"Male\",\r\n      \"HeadofCrime\": \"Ordinary Theft\",\r\n      \"IdentificationMark\": \"Mole\",\r\n      \"JailName\": \"CHANCHALGUDA\",\r\n      \"Name\": \"S PRITHVIRAJ /  / \",\r\n      \"PSArrested\": \"Abidroad\",\r\n      \"Photo\": null,\r\n      \"PhotoPath\": null,\r\n      \"PlaceofIdentificationMark\": \"ON RIGHT HAND & ON RIGHT FOOT\",\r\n      \"PrisonerNo\": \"UT 6200\",\r\n      \"ReleaseDt\": \"31/01/2016\",\r\n      \"RlDtOrder\": \"31-01-2016 00:00:00\",\r\n      \"TypeofRelease\": \"Out of Jail\"\r\n    },\r\n    {\r\n      \"Addr_DuringRelease\": \"FLAT NO- 307 2nd FLOOR, KGN BUILDING, VIJAY NAGAR COLONY, HYD.\",\r\n      \"Admission_to_Jail\": \"24/08/2015\",\r\n      \"CIN\": \"0000061669\",\r\n      \"CrimeNos\": \"278/2015\",\r\n      \"Gender\": \"Male\",\r\n      \"HeadofCrime\": \"Ordinary Theft\",\r\n      \"IdentificationMark\": \"Mole\",\r\n      \"JailName\": \"CHANCHALGUDA\",\r\n      \"Name\": \"  ALAM KHAN JAVEED / 8801448077 / \",\r\n      \"PSArrested\": \"Abidroad\",\r\n      \"Photo\": null,\r\n      \"PhotoPath\": null,\r\n      \"PlaceofIdentificationMark\": \"A MOLE ON LEFT ELBOW.\",\r\n      \"PrisonerNo\": \"UT 3760\",\r\n      \"ReleaseDt\": \"24/01/2016\",\r\n      \"RlDtOrder\": \"24-01-2016 00:00:00\",\r\n      \"TypeofRelease\": \"Out of Jail\"\r\n    },\r\n    {\r\n      \"Addr_DuringRelease\": \"H.NO- 4-1-911, BOGULKUNTA, OPP. APGLIC BUILDING, ABIDS, HYD.\",\r\n      \"Admission_to_Jail\": \"02/01/2016\",\r\n      \"CIN\": \"0000062920\",\r\n      \"CrimeNos\": \"03/2016\",\r\n      \"Gender\": \"Male\",\r\n      \"HeadofCrime\": \"HB by Night \",\r\n      \"IdentificationMark\": \"Mole\",\r\n      \"JailName\": \"CHANCHALGUDA\",\r\n      \"Name\": \"T KARUNAKAR / 9989769440 / \",\r\n      \"PSArrested\": \"Abidroad\",\r\n      \"Photo\": null,\r\n      \"PhotoPath\": null,\r\n      \"PlaceofIdentificationMark\": \"A MOLE ON RIGHT SIDE OF CHEST & ON LEFT LEG KNEE.\",\r\n      \"PrisonerNo\": \"UT 6237\",\r\n      \"ReleaseDt\": \"06/01/2016\",\r\n      \"RlDtOrder\": \"06-01-2016 00:00:00\",\r\n      \"TypeofRelease\": \"Out of Jail\"\r\n    }\r\n  ]\r\n}";
	public static void main(String[] args) {
		JRMSMainTest jrmsMainTest = new JRMSMainTest();
		jrmsMainTest.createDataSourceRecordForJRMSData(payload);
	}
	
	public void createDataSourceRecordForJRMSData(String payload){
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
			}
			
		}catch(Exception e){
			logger.error("{ Xception@ JRMSMainTest }	"+e);
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
