package com.HCS.ioc.files.view;

public class SMBFileCustomException extends Exception{

	public SMBFileCustomException() {
		super();
	}
	public SMBFileCustomException(String message){
		super(message);
	}
	public SMBFileCustomException(String message,String noSharedFolders){
		super(message);
	}
}
