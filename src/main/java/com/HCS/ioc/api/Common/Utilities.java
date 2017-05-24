package com.HCS.ioc.api.Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

/**
 * @author OohithVikramRao 23-May-2017
 *
 */
public class Utilities {
	static APIConfig config = ConfigFactory.create(APIConfig.class);
	static Logger logger = LoggerFactory.getLogger(Utilities.class);

	/**
	 * To create Json object for given keys[] and values[]
	 */
	public static String createJson(String[] jsonKey, String[] jsonValues) {
		JsonObject obj = new JsonObject();
		try {
			for (int i = 0; i < jsonValues.length; i++) {
				for (int j = 0; j < jsonKey.length; j++) {
					obj.addProperty(jsonKey[i], jsonValues[i]);
				}
			}
		} catch (Exception e) {
			logger.error("Exception @ CreateJson }   " + e);
		}
		return obj.toString();
	}

	/**
	 * To get current DATETIME in yyyy-MM-dd HH:mm:ss format
	 */
	public static String currentSysDateTimeForDB() {
		String curDateTime = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			curDateTime = simpleDateFormat.format(curDate);
		} catch (Exception e) {
			logger.error("Exception @ currentsysDateTime }	" + e);
		}
		return curDateTime;
	}

	/**
	 * To Execute Select Query
	 */
	public static PreparedStatement ExecuteSelectQuery(String SelectQuery) {
		Connection connection = Utilities.GetDataBaseConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SelectQuery);
		} catch (SQLException e) {
			logger.error("Exception @ ExecuteSelectQuery }" + e.toString());
			e.printStackTrace();
		}
		return preparedStatement;
	}

	/**
	 * To get DataBase Connection
	 */
	private static Connection GetDataBaseConnection() {
		Connection con = null;
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			con = DriverManager.getConnection(config.DB2ConnectionURL(), config.DB2Username(), config.DB2Usersecret());
			con.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			logger.error("Exception @ GetDataBaseConnection }" + e.toString());

		}
		return con;
	}

}
