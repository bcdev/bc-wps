package com.bc.wps.utilities;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.LanguageStringType;
import com.bc.wps.api.schema.ObjectFactory;
import com.bc.wps.api.schema.ServiceIdentification;
import org.junit.*;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author hans
 */
public class JaxbHelperTest {

    @Test
    public void testMarshal() throws Exception {
        Capabilities capabilities = createMockCapabilities();

        assertThat(JaxbHelper.marshal(capabilities),
                   equalTo(                                                                                                                                                                                                                                                                                                                                                                                                          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"                                                                                                                                                                                                                                                                                                                                         +
                           "<wps:Capabilities service=\"WPS\" xml:lang=\"en\" version=\"1.0.0\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                           "    <ows:ServiceIdentification>\n"                                                                                                                                                                                                                                                                                                                                                                       +
                           "        <ows:Title>Calvalus WPS Server</ows:Title>\n"                                                                                                                                                                                                                                                                                                                                                    +
                           "        <ows:Abstract>Web Processing Service for Calvalus</ows:Abstract>\n"                                                                                                                                                                                                                                                                                                                              +
                           "        <ows:ServiceType>WPS</ows:ServiceType>\n"                                                                                                                                                                                                                                                                                                                                                        +
                           "        <ows:ServiceTypeVersion>1.0.0</ows:ServiceTypeVersion>\n"                                                                                                                                                                                                                                                                                                                                        +
                           "    </ows:ServiceIdentification>\n"                                                                                                                                                                                                                                                                                                                                                                      +
                           "</wps:Capabilities>\n"));
    }

    @Test
    public void testUnmarshal() throws Exception {
        String executeResponseXmlString = getMockAcceptedResponseXmlString();
        InputStream requestInputStream = new ByteArrayInputStream(executeResponseXmlString.getBytes());

        ExecuteResponse executeResponse = (ExecuteResponse) JaxbHelper.unmarshal(requestInputStream, new ObjectFactory());

        assertThat(executeResponse.getVersion(), equalTo("1.0.0"));
        assertThat(executeResponse.getService(), equalTo("WPS"));
        assertThat(executeResponse.getLang(), equalTo("en"));
        assertThat(executeResponse.getStatusLocation(), equalTo("http://calvalustomcat:9080/calwps/wps?Service=WPS&Request=GetStatus&JobId=20150915093239_L3_14292f372703fc"));
        assertThat(executeResponse.getStatus().getProcessAccepted(), equalTo("The request has been accepted. The status of the process can be found in the URL."));

    }

    @Test(expected = JAXBException.class)
    public void canThrowExceptionWhenUnmarshalling() throws Exception {
        String executeResponseXmlString = getMalformedAcceptedResponseXmlString();
        InputStream requestInputStream = new ByteArrayInputStream(executeResponseXmlString.getBytes());

        JaxbHelper.unmarshal(requestInputStream, new ObjectFactory());

    }

    private String getMalformedAcceptedResponseXmlString() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
               "<wps:ExecuteResponseXXX statusLocation=\"http://calvalustomcat:9080/calwps/wps?Service=WPS&amp;Request=GetStatus&amp;JobId=20150915093239_L3_14292f372703fc\" service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
               "    <Status creationTime=\"2015-09-15T09:32:42.225+02:00\">\n" +
               "        <ProcessAccepted>The request has been accepted. The status of the process can be found in the URL.</ProcessAccepted>\n" +
               "    </Status>\n" +
               "</wps:ExecuteResponseXXX>";
    }

    private String getMockAcceptedResponseXmlString() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
               "<wps:ExecuteResponse statusLocation=\"http://calvalustomcat:9080/calwps/wps?Service=WPS&amp;Request=GetStatus&amp;JobId=20150915093239_L3_14292f372703fc\" service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
               "    <Status creationTime=\"2015-09-15T09:32:42.225+02:00\">\n" +
               "        <ProcessAccepted>The request has been accepted. The status of the process can be found in the URL.</ProcessAccepted>\n" +
               "    </Status>\n" +
               "</wps:ExecuteResponse>";
    }

    private Capabilities createMockCapabilities() {
        Capabilities capabilities = new Capabilities();
        capabilities.setLang("en");
        capabilities.setService("WPS");
        capabilities.setVersion("1.0.0");

        ServiceIdentification serviceIdentification = new ServiceIdentification();
        LanguageStringType title = new LanguageStringType();
        title.setValue("Calvalus WPS Server");
        serviceIdentification.setTitle(title);
        LanguageStringType abstractText = new LanguageStringType();
        abstractText.setValue("Web Processing Service for Calvalus");
        serviceIdentification.setAbstract(abstractText);
        CodeType serviceType = new CodeType();
        serviceType.setValue("WPS");
        serviceIdentification.setServiceType(serviceType);
        serviceIdentification.getServiceTypeVersion().add("1.0.0");
        capabilities.setServiceIdentification(serviceIdentification);

        return capabilities;
    }
}