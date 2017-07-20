package com.IOC.CCTV;

import java.io.*;
import org.aeonbits.owner.ConfigFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.HCS.ioc.DataSourceActions.CreateDataSource.CreateDataSourceRecord;
import com.HCS.ioc.api.Common.APIConfig;
import com.HCS.ioc.api.Common.Utilities;

/**
 * @Author OohithVikramRao 06-Jul-2017
 *
 */
public class ExtractDataFromXML {
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	static Logger logger = LoggerFactory.getLogger(ExtractDataFromXML.class);

	public static void main(String[] args) {
		JSONObject jsonObject=null;
		try {
			jsonObject = new JSONObject(XMLtoJSON(readFile("C:/Users/oohith/Desktop/CCTV.xml"), 4));
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		run(jsonObject);
	}

	/**
	 * @param jsonObject 
	 *
	 */
	private static void run(JSONObject jsonObject) {
		try {

			JSONObject payload = new JSONObject();
			JSONObject MetadataHeader = jsonObject.getJSONObject("AnalyticsAlert").getJSONObject("MetadataHeader");
			payload.put("MetaID", MetadataHeader.getJSONObject("MetaID").get("content"));
			payload.put("MetaLink", MetadataHeader.getJSONObject("MetaLink").get("content"));
			payload.put("MetaPriority", MetadataHeader.getJSONObject("MetaPriority").get("content"));
			payload.put("MetaSourceID", MetadataHeader.getJSONObject("MetaSourceID").get("content"));
			payload.put("MetaSourceLocalID", MetadataHeader.getJSONObject("MetaSourceLocalID").get("content"));
			payload.put("MetaTime", MetadataHeader.getJSONObject("MetaTime").get("content"));

			JSONObject ObjectList = jsonObject.getJSONObject("AnalyticsAlert").getJSONObject("ObjectList")
					.getJSONArray("Object").getJSONObject(0);
			payload.put("ID", ObjectList.getString("ID"));
			payload.put("Classification", ObjectList.get("Classification").toString());
			JSONObject RuleList = jsonObject.getJSONObject("AnalyticsAlert").getJSONObject("RuleList")
					.getJSONObject("Rule");
			payload.put("RuleID", RuleList.get("RuleID"));
			payload.put("RuleName", RuleList.get("RuleName"));
			payload.put("ActionType", RuleList.getJSONObject("Action").keys().next());
			payload.put("ActionDescription",
					RuleList.getJSONObject("Action").get(RuleList.getJSONObject("Action").keys().next() + ""));
			payload.put("RuleType",
					(RuleList.getJSONObject("RuleElementList").getJSONObject("RuleElement")).get("RuleType"));
			payload.put("SnapshotURL", jsonObject.getJSONObject("AnalyticsAlert").getJSONObject("Snapshots")
					.getJSONArray("Snapshot").getJSONObject(0).get("xlink:href"));
			String Latitude = jsonObject.getJSONObject("AnalyticsAlert").getJSONObject("ObjectMotionList")
					.getJSONObject("ObjectMotion").getJSONObject("CalibratedMotion").getJSONObject("Location")
					.get("Latitude").toString();
			String Longitude = jsonObject.getJSONObject("AnalyticsAlert").getJSONObject("ObjectMotionList")
					.getJSONObject("ObjectMotion").getJSONObject("CalibratedMotion").getJSONObject("Location")
					.get("Longitude").toString();
			payload.put("Location", "POINT(" + "78.4798" + " " + "17.4051" + ")");
			payload.put("Latitude", Latitude);
			payload.put("Longitude", Longitude);
			String time = Utilities.currentSysDateTimeForDB();
			payload.put("STARTDATETIME", time);
			payload.put("ENDDATETIME", time);
			payload.put("LASTUPDATEDATETIME", time);
			payload.put("TIMEZONEOFFSET", 0);
			System.out.println(payload.toString().replaceAll("\n", "").replaceAll("\t", ""));
			CreateDataSourceRecord createDataSourceRecord = new CreateDataSourceRecord();
			System.out.println(createDataSourceRecord.createDataSourceRecord(config.CCTVDataSourceID(), payload.toString()));

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static String readFile(String path) throws IOException {
		String sb = "";
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String x = br.readLine();
			while ((x) != null) {
				sb += x + "\n";
				x = br.readLine();
			}
		}
		return sb;
	}

	public static String XMLtoJSON(String TEST_XML_STRING, int PRETTY_PRINT_INDENT_FACTOR) {
		try {
			JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
			String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
			return jsonPrettyPrintString;
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
		return null;
	}
}
