/**
 * ASM7LoginServicesBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.neoris.operations.loginServices;

import java.rmi.RemoteException;

import es.neoris.operations.loginServices.interfaces.InterfaceLoginServices;

public abstract class LoginServicesBindingImpl 
implements InterfaceLoginServices{
	
    public boolean isValid(String ticket) throws RemoteException {
        return false;
    }

    public String login(String userName, String password, String appid, String env) throws RemoteException {
        return null;
    }

    public void logout(String ticket) throws java.rmi.RemoteException {
    }

}
