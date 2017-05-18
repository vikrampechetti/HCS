package com.HCS.ioc.files.view;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.HCS.ioc.files.config.HCSDownloadConfig;

import spark.Request;
import spark.Response;

/**
 * @author BALA CHANDER Oct 26, 2016 
 *
 */
public class DownloadFile {

    static final Logger logger = LoggerFactory.getLogger(DownloadFile.class);

    public HttpServletResponse service(Request request, Response response) throws IOException, SMBFileCustomException{
//    	KdmaDownloadConfig serverConfig  = ConfigFactory.create(KdmaDownloadConfig.class);
    	HCSDownloadConfig serverConfig = ConfigFactory.create(HCSDownloadConfig.class);
        String fileName = request.queryParams("filePath");
        RemoteFileAsStream remoteFileAsStream = new RemoteFileAsStream();
        
        BufferedInputStream bufferedInputStream =remoteFileAsStream.getFileStream(serverConfig.RemoteUserName(),
                serverConfig.RemotePassword(),
                serverConfig.RemoteDomain(),
                fileName);
        HttpServletResponse httpServletResponse = response.raw();
        httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+ FilenameUtils.getName(fileName));
        httpServletResponse.setHeader("Content-Transfer-Encoding", "binary");
        byte[] b = new byte[16 * 1024 * 1024];
        int n;
        while ((n = bufferedInputStream.read(b)) > 0) {
        	try{
            	httpServletResponse.getOutputStream().write(b,0,n);
        	}catch(Exception e){
        		logger.error("{ @ Exception in DownloadFile }	",e);
        	}
        }
        httpServletResponse.getOutputStream().flush();
        httpServletResponse.getOutputStream().close();
        return response.raw();
    }
}

