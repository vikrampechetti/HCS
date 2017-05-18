/**
 * 
 */
package com.ksdma.ioc.file.download.config;

/**
 * @author BALA CHANDER Nov 15, 2016 
 *
 */
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"file:C:\\ioc\\APIConfig.properties",
		"classpath:APIConfig.properties",
		})
public interface HCSDownloadConfig  extends Config {
	String RemoteUserName();
	
	String RemotePassword();
	
	String RemoteDomain();
}
