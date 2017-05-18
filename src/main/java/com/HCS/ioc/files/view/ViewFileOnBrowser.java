/**
 * 
 */
package com.HCS.ioc.files.view;

import java.io.BufferedInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ksdma.ioc.file.download.config.HCSDownloadConfig;

import spark.Request;
import spark.Response;

/**
 * @author BALA CHANDER Nov 18, 2016 
 *
 */
public class ViewFileOnBrowser {

	static final Logger logger = LoggerFactory.getLogger(ViewFileOnBrowser.class);

    public HttpServletResponse service(Request request, Response response) throws IOException, SMBFileCustomException{

    	HCSDownloadConfig serverConfig = ConfigFactory.create(HCSDownloadConfig.class);
        String fileName = request.queryParams("filePath");
        RemoteFileAsStream remoteFileAsStream = new RemoteFileAsStream();
        
        BufferedInputStream bufferedInputStream =remoteFileAsStream.getFileStream(serverConfig.RemoteUserName(),
                serverConfig.RemotePassword(),
                serverConfig.RemoteDomain(),
                fileName);
        HttpServletResponse httpServletResponse = response.raw();
        httpServletResponse.setHeader("Content-Disposition", "inline;filename="+ FilenameUtils.getName(fileName));
//        httpServletResponse.setHeader("Content-Transfer-Encoding", "binary");
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
