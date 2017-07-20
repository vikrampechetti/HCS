package com.HCS.ioc.Publish.data;

import com.HCS.ioc.DataSourceActions.CreateDataSource.CreateDataSourceRecord;

/**
 * @Author OohithVikramRao 09-Jul-2017
 *
 */
public class Alarm {
public static void main(String[] args) {
	String x="{\"AlarmID\":\"16651\",\"LOCATION\":\"POINT(21.3228 73.1862)\",\"ImageFile\":\"Airport (10.11.2.82)-Cam-1/2017-07-09/ALARM_Airport (10.11.2.82)-Cam-1_2017_07_09_10_47_12.jpg\",\"ChannelHeight\":\"240\",\"ObjectSize\":\"Tiny\",\"VideoFile\":\"Airport (10.11.2.82)-Cam-1/2017-07-09/ALARM_Airport (10.11.2.82)-Cam-1_2017_07_09_10_47_12.mp4\",\"Source\":\"Entrance\",\"ObjectType\":\"Unidentified\",\"CamGUID\":\"BBE5000A-89B9-4A1E-954E-39D68F70E33C\",\"AlarmName\":\"TRIPWIRE\",\"AlarmDescription\":\"TRIPWIRE\",\"RegionName\":\"Region 0\",\"TIMEZONEOFFSET\":\"0\",\"CamName\":\"Airport (10.11.2.82)-Cam-1\",\"LASTUPDATEDATETIME\":\"2017-07-09 15:57:41\",\"ENDDATETIME\":\"2017-07-09 15:57:41\",\"ObjectColor\":\"UnIdentified\",\"ChannelWidth\":\"320\",\"STARTDATETIME\":\"2017-07-09 15:57:41\",\"RegionPriority\":\"VERY_LOW\"}";
	CreateDataSourceRecord createDataSourceRecord=new CreateDataSourceRecord();
	System.out.println(createDataSourceRecord.createDataSourceRecord("27", x));
}
}
