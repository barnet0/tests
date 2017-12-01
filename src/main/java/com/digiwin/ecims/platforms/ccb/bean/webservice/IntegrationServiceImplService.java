package com.digiwin.ecims.platforms.ccb.bean.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.5
 * 2016-03-16T15:32:18.549+08:00
 * Generated source version: 3.1.5
 * 
 */
@WebServiceClient(name = "IntegrationServiceImplService", 
                  wsdlLocation = "http://buy.ccb.com/integration/IntegrationService?wsdl",
                  targetNamespace = "http://index.interfaces.ccb.com/") 
public class IntegrationServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://index.interfaces.ccb.com/", "IntegrationServiceImplService");
    public final static QName IntegrationServiceImplPort = new QName("http://index.interfaces.ccb.com/", "IntegrationServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://buy.ccb.com/integration/IntegrationService?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(IntegrationServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://buy.ccb.com/integration/IntegrationService?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public IntegrationServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public IntegrationServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public IntegrationServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public IntegrationServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public IntegrationServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public IntegrationServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns IntegrationService
     */
    @WebEndpoint(name = "IntegrationServiceImplPort")
    public IntegrationService getIntegrationServiceImplPort() {
        return super.getPort(IntegrationServiceImplPort, IntegrationService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IntegrationService
     */
    @WebEndpoint(name = "IntegrationServiceImplPort")
    public IntegrationService getIntegrationServiceImplPort(WebServiceFeature... features) {
        return super.getPort(IntegrationServiceImplPort, IntegrationService.class, features);
    }

}
