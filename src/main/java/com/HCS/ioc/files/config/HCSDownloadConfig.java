/**
 * 
 */
package com.HCS.ioc.files.config;

/**
 * @author BALA CHANDER Nov 15, 2016 
 *
 */
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"file:C:\\hcs\\HCSAPIConfig.properties",
		"classpath:APIConfig.properties",
		})
public interface HCSDownloadConfig  extends Config {
	String RemoteUserName();
	
	String RemotePassword();
	
	String RemoteDomain();
}
