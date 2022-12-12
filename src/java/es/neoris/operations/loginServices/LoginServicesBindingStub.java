
/**
 * LoginServicesBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.neoris.operations.loginServices;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

import es.neoris.operations.loginServices.interfaces.LoginServicesRemote;

public class LoginServicesBindingStub 
extends org.apache.axis.client.Stub 
implements LoginServicesRemote {

	private Vector cachedSerClasses = new Vector();
	private Vector cachedSerQNames = new Vector();
	private Vector cachedSerFactories = new Vector();
	private Vector cachedDeserFactories = new Vector();

	private String m_nameSpace; 
	
	private static final String xmlSchema = "http://www.w3.org/2001/XMLSchema";
	private static final String LIT_STRING = "string";
	private static final String LIT_BOOLEAN = "boolean";
	private static final String LIT_BOOLEAN_RET ="booleanReturn";
	private static final String LIT_TICKET = "ticket";
	private static final String LIT_NULL = "";
	
	
	static OperationDesc [] _operations;

	static {
		_operations = new org.apache.axis.description.OperationDesc[3];
		_initOperationDesc1();
	}

	private static void _initOperationDesc1(){

		OperationDesc oper = new OperationDesc();        
		oper.setName("isValid");

		ParameterDesc param = new ParameterDesc(new QName(LIT_NULL, LIT_TICKET), ParameterDesc.IN, new QName(xmlSchema, LIT_STRING), String.class, false, false);
		param.setOmittable(true);

		oper.addParameter(param);
		oper.setReturnType(new QName(xmlSchema, LIT_BOOLEAN));
		oper.setReturnClass(boolean.class);
		oper.setReturnQName(new QName(LIT_NULL, LIT_BOOLEAN_RET));
		oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
		oper.setUse(org.apache.axis.constants.Use.LITERAL);
		_operations[0] = oper;

		oper = new OperationDesc();
		oper.setName("login");
		param = new ParameterDesc(new javax.xml.namespace.QName(LIT_NULL, "UserName"), ParameterDesc.IN, new QName(xmlSchema, LIT_STRING), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new javax.xml.namespace.QName(LIT_NULL, "Password"), ParameterDesc.IN, new QName(xmlSchema, LIT_STRING), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new javax.xml.namespace.QName(LIT_NULL, "appid"), ParameterDesc.IN, new QName(xmlSchema, LIT_STRING), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		param = new ParameterDesc(new javax.xml.namespace.QName(LIT_NULL, "env"), ParameterDesc.IN, new QName(xmlSchema, LIT_STRING), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName(xmlSchema, LIT_STRING));
		oper.setReturnClass(String.class);
		oper.setReturnQName(new QName(LIT_NULL, LIT_TICKET));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[1] = oper;

		oper = new OperationDesc();
		oper.setName("logout");
		param = new ParameterDesc(new QName(LIT_NULL, LIT_TICKET), ParameterDesc.IN, new QName(xmlSchema, LIT_STRING), String.class, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(XMLType.AXIS_VOID);
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[2] = oper;

	}

	public LoginServicesBindingStub() throws AxisFault {
		this(null);
	}

	public LoginServicesBindingStub(URL endpointURL, Service service) throws AxisFault {
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public LoginServicesBindingStub(Service service) throws AxisFault {
		if (service == null) {
			super.service = new org.apache.axis.client.Service();
		} else {
			super.service = service;
		}
		((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
	}

	protected Call createCall() throws RemoteException {
		try {
			Call _call = super._createCall();
			if (super.maintainSessionSet) {
				_call.setMaintainSession(super.maintainSession);
			}
			if (super.cachedUsername != null) {
				_call.setUsername(super.cachedUsername);
			}
			if (super.cachedPassword != null) {
				_call.setPassword(super.cachedPassword);
			}
			if (super.cachedEndpoint != null) {
				_call.setTargetEndpointAddress(super.cachedEndpoint);
			}
			if (super.cachedTimeout != null) {
				_call.setTimeout(super.cachedTimeout);
			}
			if (super.cachedPortName != null) {
				_call.setPortName(super.cachedPortName);
			}
			Enumeration keys = super.cachedProperties.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				_call.setProperty(key, super.cachedProperties.get(key));
			}
			return _call;
		}
		catch (Throwable _t) {
			throw new AxisFault("Failure trying to get the Call object", _t);
		}
	}

	public boolean isValid(java.lang.String ticket) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}

		Call _call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(LIT_NULL);
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName(getnameSpace(), "isValid"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {        Object _resp = _call.invoke(new Object[] {ticket});

		if (_resp instanceof RemoteException) {
			throw (RemoteException)_resp;
		}
		else {
			extractAttachments(_call);
			try {
				return ((Boolean) _resp).booleanValue();
			} catch (Exception _exception) {
				return ((Boolean) JavaUtils.convert(_resp, boolean.class)).booleanValue();
			}
		}
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public String login(String userName, String password, String appid, String env) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		
		Call _call = createCall();
		_call.setOperation(_operations[1]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(LIT_NULL);
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName(getnameSpace(), "login"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {        Object _resp = _call.invoke(new Object[] {userName, password, appid, env});

		if (_resp instanceof RemoteException) {
			throw (RemoteException)_resp;
		}
		else {
			extractAttachments(_call);
			try {
				return (String) _resp;
			} catch (Exception _exception) {
				return (String) JavaUtils.convert(_resp, java.lang.String.class);
			}
		}
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public void logout(String ticket) throws RemoteException {
		if (super.cachedEndpoint == null) {
			throw new NoEndPointException();
		}
		
		Call _call = createCall();
		_call.setOperation(_operations[2]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI(LIT_NULL);
		_call.setEncodingStyle(null);
		_call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
		_call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
		_call.setOperationName(new QName(getnameSpace(), "logout"));

		setRequestHeaders(_call);
		setAttachments(_call);
		try {        Object _resp = _call.invoke(new Object[] {ticket});

		if (_resp instanceof RemoteException) {
			throw (RemoteException)_resp;
		}
		extractAttachments(_call);
		} catch (AxisFault axisFaultException) {
			throw axisFaultException;
		}
	}

	public String getnameSpace() {
		return m_nameSpace;
	}

	public void setnameSpace(String nameSpace) {
		m_nameSpace = nameSpace;
	}

}
