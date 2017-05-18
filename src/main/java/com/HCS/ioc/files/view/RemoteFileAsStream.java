package com.HCS.ioc.files.view;

import java.io.BufferedInputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
/**
 * @author BALA CHANDER May 27, 2016 
 *
 */
public class RemoteFileAsStream {
	static Logger logger = LoggerFactory.getLogger(RemoteFileAsStream.class);
	public BufferedInputStream getFileStream(String userName,String userPassword,String domainName,String filePath) throws SMBFileCustomException {
		NtlmPasswordAuthentication auth ;
		SmbFile smbFile;
		try{
			jcifs.Config.setProperty("jcifs.smb.client.disablePlainTextPasswords","false");
			auth = new NtlmPasswordAuthentication(domainName, userName, userPassword);
			smbFile = new SmbFile(filePath,auth);
			SmbFileInputStream sfis = new SmbFileInputStream(smbFile);
			BufferedInputStream buf = new BufferedInputStream(sfis);
	        return buf;
		}catch(SmbException smbException){
			switch( smbException.getNtStatus() ) {
				case SmbException.NT_STATUS_ACCESS_DENIED:
				case SmbException.NT_STATUS_WRONG_PASSWORD:
			    case SmbException.NT_STATUS_LOGON_FAILURE:
			    case SmbException.NT_STATUS_PASSWORD_EXPIRED:
			    case SmbException.NT_STATUS_ACCOUNT_DISABLED:
			    case SmbException.NT_STATUS_ACCOUNT_LOCKED_OUT:
			    case SmbException.NT_STATUS_ACCOUNT_RESTRICTION:
			    case SmbException.NT_STATUS_CANT_ACCESS_DOMAIN_INFO:
			    case SmbException.NT_STATUS_INVALID_COMPUTER_NAME:
			    case SmbException.NT_STATUS_NETWORK_ACCESS_DENIED:
			    case SmbException.NT_STATUS_NO_SUCH_DOMAIN:
			}
			logger.error("[ SmbException.. ] 	",smbException);
			throw new SMBFileCustomException(smbException.getMessage());
		} 
		catch (MalformedURLException | UnknownHostException e) {
			logger.error("[ MalformedURLException | UnknownHostException ]	",e);
			throw new SMBFileCustomException(e.getMessage());
		} catch (Exception e) {
			logger.error("[ Exception.. ]	",e);
			throw new SMBFileCustomException(e.getMessage());
		}
	}

}
