/**
 * LoginServicesRemote.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.neoris.operations.loginServices.interfaces;

public interface LoginServicesRemote 
extends java.rmi.Remote {
	
    public boolean isValid(java.lang.String ticket) throws java.rmi.RemoteException;
    
    public String login(java.lang.String userName, java.lang.String password, java.lang.String appid, java.lang.String env) throws java.rmi.RemoteException;
    
    public void logout(java.lang.String ticket) throws java.rmi.RemoteException;
    
}
