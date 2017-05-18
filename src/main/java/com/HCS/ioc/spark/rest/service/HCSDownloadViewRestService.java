package com.HCS.ioc.spark.rest.service;

import static spark.Spark.get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.files.view.DownloadFile;
import com.HCS.ioc.files.view.ViewFileOnBrowser;

/**
 * @author BALA CHANDER Oct 26, 2016 
 *
 */
public class HCSDownloadViewRestService {
	public HCSDownloadViewRestService() {
	}
	static Logger logger = LoggerFactory.getLogger(HCSDownloadViewRestService.class);
	public static void main(String[] args) {
		// Returning file when solicit
		get("/download",(request,response)->{
			DownloadFile downloadFile = null;
			try{
				downloadFile = new DownloadFile();				
			}catch(Exception e){
				logger.error("[ Exception @KDMAFileDownloadService  ]	",e);
			}
			return downloadFile.service(request,response);
		});
		// view particular file
		get("/view",(request,response)->{
			ViewFileOnBrowser fileOnBrowser = null;
			try{
				fileOnBrowser = new ViewFileOnBrowser();				
			}catch(Exception e){
				logger.error("[ Exception @KDMAFileDownloadService  ]	",e);
			}
			return fileOnBrowser.service(request,response);
		});
		// View files in a directory
		get("/viewfiles",(request,response)->{
			ViewFileOnBrowser fileOnBrowser = null;
			try{
				fileOnBrowser = new ViewFileOnBrowser();				
			}catch(Exception e){
				logger.error("[ Exception @KDMAFileDownloadService  ]	",e);
			}
			return fileOnBrowser.service(request,response);
		});
		
	}
}
