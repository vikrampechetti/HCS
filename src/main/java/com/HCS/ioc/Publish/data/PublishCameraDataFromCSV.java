
package com.HCS.ioc.Publish.data;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.aeonbits.owner.ConfigFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.DataBase.DB2Access.FetchCameraIDFromDatabase;
import com.HCS.ioc.api.Common.APIConfig;
import com.HCS.ioc.api.Common.Utilities;

/**
 * @author OohithVikramRao 19-May-2017
 *
 */
public class PublishCameraDataFromCSV {

	static APIConfig config = ConfigFactory.create(APIConfig.class);
	static Logger logger = LoggerFactory.getLogger(PublishCameraDataFromCSV.class);

	public static void main(String[] args) throws JSONException {
		run();
	}
	
	private static void run() throws JSONException {
		try {
			BufferedReader bufferreader = null;
			String filePath = config.cameraDataXLSFileAbsPath();
			if (!new File(filePath).exists()) {
				throw new FileNotFoundException("No such file:" + filePath);
			}
			bufferreader = new BufferedReader(new FileReader(filePath));
			String headersLine = bufferreader.readLine();
			String valuesFromSecondLine = null;
			String[] jsonKeys = headersLine.split(",");
			valuesFromSecondLine = bufferreader.readLine();

			System.out.println(config.CamerasDB2Table());
			String SelectQuery = "SELECT * FROM "+config.CamerasDB2Table();
			PreparedStatement preparedStatement = Utilities.ExecuteSelectQuery(SelectQuery);
			ResultSet resultset = null;

			while (true) {
				resultset = preparedStatement.executeQuery();
				String[] values = null;
				if (valuesFromSecondLine != null) {
					values = valuesFromSecondLine.split(",");
					String jsonstring = Utilities.createJson(jsonKeys, values);
					JSONObject jsonObject = new JSONObject(jsonstring);
					String currentSysDateTime = Utilities.currentSysDateTimeForDB();
					String cameraUrl = "<a target=\"_blank\" href=\"" + jsonObject.get("camers_feed_url") + "\">"
							+ jsonObject.get("camers_feed_url") + "</a>";
					String[] jsonString = (jsonObject.get("S.NO") + "," + currentSysDateTime + "," + currentSysDateTime
										+ "," + currentSysDateTime + "," + jsonObject.get("TIMEZONEOFFSET") + ","
										+ jsonObject.get("LOCATION") + "," + jsonObject.get("LATITUDE") + ","
										+ jsonObject.get("LONGITUDE") + "," + jsonObject.get("Camera_name") + "," + cameraUrl + ","
										+ jsonObject.get("camera_ID") + "," + jsonObject.get("location_name")).split(",");
					String DataSourcePayload = Utilities.createJson(jsonKeys, jsonString);
					logger.info(DataSourcePayload);
					FetchCameraIDFromDatabase.fetchCameraDataFromDB2(DataSourcePayload, resultset);
				}
				if (valuesFromSecondLine == null) {
					break;
				}
				valuesFromSecondLine = bufferreader.readLine();
			}
			bufferreader.close();
			resultset.close();
		} catch (IOException e) {
			logger.error("Exception @ run }	" + e);
		} catch (SQLException ex) {
			logger.error("Exception @ run }	SQLException information");
			while (ex != null) {
				logger.error("Error msg: " + ex.getMessage());
				logger.error("SQLSTATE: " + ex.getSQLState());
				logger.error("Error code: " + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
	}

}
