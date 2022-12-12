package es.neoris.operations.loginServices.interfaces;

import javax.xml.rpc.Service;


public interface InterfaceLoginServices 
extends Service {
	
    public String getAddress();

    public LoginServicesRemote getLoginServices() throws javax.xml.rpc.ServiceException;

    public LoginServicesRemote getLoginServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
