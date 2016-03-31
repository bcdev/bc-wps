package com.bc.wps;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.bc.wps.exceptions.InvalidRequestException;
import org.junit.*;
import org.junit.rules.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author hans
 */
public class WpsServiceTest {

    private WpsService wpsService;
    private HttpServletRequest mockServletRequest;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        mockServletRequest = mock(HttpServletRequest.class);
        Principal mockUserPrincipal = mock(Principal.class);
        when(mockUserPrincipal.getName()).thenReturn("mockName");
        when(mockServletRequest.getUserPrincipal()).thenReturn(mockUserPrincipal);
        wpsService = new WpsService();
    }

    @Test
    public void canGetCapabilitiesFromMockInstanceWithValidRequest() throws Exception {
        String response = wpsService.getWpsService("mock2", "WPS", "GetCapabilities", "", "", "", "", "", mockServletRequest);

        assertThat(response, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<wps:Capabilities service=\"WPS\" xml:lang=\"en\" version=\"1.0.0\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                     "    <ows:ServiceIdentification>\n" +
                                     "        <ows:Title>A mock WPS server</ows:Title>\n" +
                                     "        <ows:Abstract>A mock WPS server to be used as a reference for any WPS implementations.</ows:Abstract>\n" +
                                     "        <ows:ServiceType>WPS</ows:ServiceType>\n" +
                                     "        <ows:ServiceTypeVersion>0.1</ows:ServiceTypeVersion>\n" +
                                     "        <ows:ServiceTypeVersion>1.0</ows:ServiceTypeVersion>\n" +
                                     "        <ows:Fees>gratis</ows:Fees>\n" +
                                     "    </ows:ServiceIdentification>\n" +
                                     "    <ows:ServiceProvider>\n" +
                                     "        <xs:ProviderName>Fantasy World</xs:ProviderName>\n" +
                                     "        <ows:ProviderSite xlink:href=\"http://fantasy-world.com\"/>\n" +
                                     "        <ows:ServiceContact>\n" +
                                     "            <ows:IndividualName>John Doe</ows:IndividualName>\n" +
                                     "            <ows:PositionName>System Administrator</ows:PositionName>\n" +
                                     "            <ows:ContactInfo>\n" +
                                     "                <ows:Phone>\n" +
                                     "                    <Voice>+49 12345 6789</Voice>\n" +
                                     "                    <Facsimile>+49 98765 4321</Facsimile>\n" +
                                     "                </ows:Phone>\n" +
                                     "                <ows:Address>\n" +
                                     "                    <xs:DeliveryPoint>Room 1, Building A, Fantasy Avenue</xs:DeliveryPoint>\n" +
                                     "                    <xs:City>Fantasyville</xs:City>\n" +
                                     "                    <xs:AdministrativeArea>FF</xs:AdministrativeArea>\n" +
                                     "                    <xs:PostalCode>1234</xs:PostalCode>\n" +
                                     "                    <xs:Country>Kingdom of Fantasy</xs:Country>\n" +
                                     "                    <xs:ElectronicMailAddress>admin@fantasy-world.com</xs:ElectronicMailAddress>\n" +
                                     "                </ows:Address>\n" +
                                     "            </ows:ContactInfo>\n" +
                                     "        </ows:ServiceContact>\n" +
                                     "    </ows:ServiceProvider>\n" +
                                     "    <ows:OperationsMetadata>\n" +
                                     "        <ows:Operation name=\"GetCapabilities\">\n" +
                                     "            <ows:DCP>\n" +
                                     "                <ows:HTTP>\n" +
                                     "                    <ows:Get xlink:href=\"http://companyUrl/serviceName?\"/>\n" +
                                     "                </ows:HTTP>\n" +
                                     "            </ows:DCP>\n" +
                                     "        </ows:Operation>\n" +
                                     "        <ows:Operation name=\"DescribeProcess\">\n" +
                                     "            <ows:DCP>\n" +
                                     "                <ows:HTTP>\n" +
                                     "                    <ows:Get xlink:href=\"http://companyUrl/serviceName?\"/>\n" +
                                     "                </ows:HTTP>\n" +
                                     "            </ows:DCP>\n" +
                                     "        </ows:Operation>\n" +
                                     "        <ows:Operation name=\"Execute\">\n" +
                                     "            <ows:DCP>\n" +
                                     "                <ows:HTTP>\n" +
                                     "                    <ows:Post xlink:href=\"http://companyUrl/serviceName\"/>\n" +
                                     "                </ows:HTTP>\n" +
                                     "            </ows:DCP>\n" +
                                     "        </ows:Operation>\n" +
                                     "        <ows:Operation name=\"GetStatus\">\n" +
                                     "            <ows:DCP>\n" +
                                     "                <ows:HTTP>\n" +
                                     "                    <ows:Get xlink:href=\"http://companyUrl/serviceName?\"/>\n" +
                                     "                </ows:HTTP>\n" +
                                     "            </ows:DCP>\n" +
                                     "        </ows:Operation>\n" +
                                     "    </ows:OperationsMetadata>\n" +
                                     "    <ProcessOfferings>\n" +
                                     "        <Process wps:processVersion=\"0.1\">\n" +
                                     "            <ows:Identifier>process1</ows:Identifier>\n" +
                                     "            <ows:Abstract>This is a mock process from mock provider 2</ows:Abstract>\n" +
                                     "        </Process>\n" +
                                     "    </ProcessOfferings>\n" +
                                     "    <Languages>\n" +
                                     "        <Default>\n" +
                                     "            <ows:Language>EN</ows:Language>\n" +
                                     "        </Default>\n" +
                                     "        <Supported>\n" +
                                     "            <ows:Language>EN</ows:Language>\n" +
                                     "            <ows:Language>DE</ows:Language>\n" +
                                     "        </Supported>\n" +
                                     "    </Languages>\n" +
                                     "</wps:Capabilities>\n"));
    }

    @Test
    public void canReturnExceptionGetCapabilitiesWithInvalidService() throws Exception {
        String wpsResponse = wpsService.getWpsService("mock2", "invalidService", "GetCapabilities", "", "", "", "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                        "<wps:ExceptionReport version=\"version\" xml:lang=\"Lang\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                        "    <Exception exceptionCode=\"InvalidParameterValue\" locator=\"Service\">\n" +
                                        "        <ExceptionText>Invalid value of parameter 'Service'</ExceptionText>\n" +
                                        "    </Exception>\n" +
                                        "</wps:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenDescribeProcessWithNoProcessId() throws Exception {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "DescribeProcess", "", "", "", "1.0.0", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                        "<wps:ExceptionReport version=\"version\" xml:lang=\"Lang\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                        "    <Exception exceptionCode=\"MissingParameterValue\" locator=\"Identifier\">\n" +
                                        "        <ExceptionText>Missing parameter value</ExceptionText>\n" +
                                        "    </Exception>\n" +
                                        "</wps:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenDescribeProcessWithNoVersionNumber() throws Exception {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "DescribeProcess", "", "", "bundle~name~version", "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                        "<wps:ExceptionReport version=\"version\" xml:lang=\"Lang\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                        "    <Exception exceptionCode=\"MissingParameterValue\" locator=\"Version\">\n" +
                                        "        <ExceptionText>Missing parameter value</ExceptionText>\n" +
                                        "    </Exception>\n" +
                                        "</wps:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenGetStatusWithoutJobId() throws Exception {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "GetStatus", "", "", "", "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                        "<wps:ExceptionReport version=\"version\" xml:lang=\"Lang\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                        "    <Exception exceptionCode=\"MissingParameterValue\" locator=\"JobId\">\n" +
                                        "        <ExceptionText>Missing parameter value</ExceptionText>\n" +
                                        "    </Exception>\n" +
                                        "</wps:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenNoServiceParameter() throws Exception {
        String wpsResponse = wpsService.getWpsService("mock2", "", "", "", "", "", "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                        "<wps:ExceptionReport version=\"version\" xml:lang=\"Lang\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                        "    <Exception exceptionCode=\"MissingParameterValue\" locator=\"Service\">\n" +
                                        "        <ExceptionText>Missing parameter value</ExceptionText>\n" +
                                        "    </Exception>\n" +
                                        "</wps:ExceptionReport>\n"));
    }

    @Test
    public void canReturnExceptionWhenNoRequestTypeParameter() throws Exception {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "", "", "", "", "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                        "<wps:ExceptionReport version=\"version\" xml:lang=\"Lang\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                        "    <Exception exceptionCode=\"MissingParameterValue\" locator=\"Request\">\n" +
                                        "        <ExceptionText>Missing parameter value</ExceptionText>\n" +
                                        "    </Exception>\n" +
                                        "</wps:ExceptionReport>\n"));
    }

    @Test
    public void canGetStatusFromMockWps() throws Exception {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "GetStatus", "", "", "", "", "dummyJobId", mockServletRequest);

        assertThat(wpsResponse, containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                               "<wps:ExecuteResponse xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n"));
        assertThat(wpsResponse, containsString("        <ProcessStarted percentCompleted=\"65\">RUNNING</ProcessStarted>\n" +
                                               "    </Status>\n</wps:ExecuteResponse>\n"));
    }

    @Test
    public void canReturnExceptionWhenRequestIsUnknown() throws Exception {
        String wpsResponse = wpsService.getWpsService("mock2", "WPS", "InvalidService", "", "", "", "", "", mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                        "<wps:ExceptionReport version=\"version\" xml:lang=\"Lang\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                        "    <Exception exceptionCode=\"InvalidParameterValue\" locator=\"Request\">\n" +
                                        "        <ExceptionText>Invalid value of parameter 'Request'</ExceptionText>\n" +
                                        "    </Exception>\n" +
                                        "</wps:ExceptionReport>\n"));
    }

    @Test
    public void canExecuteWithValidXmlRequestInMockWps() throws Exception {
        configureMockUser();
        String wpsResponse = wpsService.postExecuteService("mock2", getValidExecuteRequest(), mockServletRequest);

        assertThat(wpsResponse, containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                               "<wps:ExecuteResponse statusLocation=\"null/mockUserName\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n"));
        assertThat(wpsResponse, containsString("        <ProcessAccepted>The request has been accepted. The job is being handled by processor 'beam-idepix~2.0.9~Idepix.Water'.</ProcessAccepted>\n" +
                                               "    </Status>\n" +
                                               "</wps:ExecuteResponse>\n"));
    }

    @Test
    public void canGetDefaultServiceAndVersionInRequestXml() throws Exception {
        configureMockUser();
        String wpsResponse = wpsService.postExecuteService("mock2", getExecuteRequestWithoutServiceAndVersion(), mockServletRequest);

        assertThat(wpsResponse, containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                               "<wps:ExecuteResponse statusLocation=\"null/mockUserName\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n"));
        assertThat(wpsResponse, containsString("        <ProcessAccepted>The request has been accepted. The job is being handled by processor 'beam-idepix~2.0.9~Idepix.Water'.</ProcessAccepted>\n" +
                                               "    </Status>\n" +
                                               "</wps:ExecuteResponse>\n"));
    }

    @Test
    public void canReturnExceptionWhenNoProcessorId() throws Exception {
        String wpsResponse = wpsService.postExecuteService("mock2", getExecuteRequestWithoutIdentifier(), mockServletRequest);

        assertThat(wpsResponse, equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                                        "<wps:ExceptionReport version=\"version\" xml:lang=\"Lang\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                        "    <Exception exceptionCode=\"MissingParameterValue\" locator=\"Identifier\">\n" +
                                        "        <ExceptionText>Missing parameter value</ExceptionText>\n" +
                                        "    </Exception>\n" +
                                        "</wps:ExceptionReport>\n"));
    }

    @Test
    public void canThrowExceptionWhenExecuteWithMalformedXmlRequest() throws Exception {
        thrown.expect(InvalidRequestException.class);
        thrown.expectMessage("Invalid Execute request. Content is not allowed in prolog.");

        wpsService.postExecuteService("mock2", "requestXml", mockServletRequest);
    }

    @Test
    public void canThrowExceptionWhenExecuteWithUnknownXmlRequest() throws Exception {
        thrown.expect(InvalidRequestException.class);
        thrown.expectMessage("Invalid Execute request. unexpected element (uri:\"http://java.sun.com/xml/ns/j2ee\", local:\"web-app\"). " +
                             "Expected elements are <{http://www.opengis.net/wps/1.0.0}AllowedValues>," +
                             "<{http://www.opengis.net/wps/1.0.0}AnyValue>," +
                             "<{http://www.opengis.net/wps/1.0.0}Capabilities>,<{http://www.opengis.net/wps/1.0.0}DCP>," +
                             "<{http://www.opengis.net/wps/1.0.0}DescribeProcess>,<{http://www.opengis.net/wps/1.0.0}ExceptionReport>," +
                             "<{http://www.opengis.net/wps/1.0.0}Execute>,<{http://www.opengis.net/wps/1.0.0}ExecuteResponse>," +
                             "<{http://www.opengis.net/wps/1.0.0}GetCapabilities>,<{http://www.opengis.net/wps/1.0.0}HTTP>," +
                             "<{http://www.opengis.net/wps/1.0.0}Languages>,<{http://www.opengis.net/wps/1.0.0}NoValues>," +
                             "<{http://www.opengis.net/wps/1.0.0}Operation>,<{http://www.opengis.net/wps/1.0.0}OperationsMetadata>," +
                             "<{http://www.opengis.net/wps/1.0.0}ProcessDescriptions>,<{http://www.opengis.net/wps/1.0.0}ProcessOfferings>," +
                             "<{http://www.opengis.net/ows/1.1}ServiceIdentification>,<{http://www.opengis.net/wps/1.0.0}ServiceProvider>," +
                             "<{http://www.opengis.net/wps/1.0.0}ValuesReference>,<{http://www.opengis.net/wps/1.0.0}WSDL>," +
                             "<{http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd}parameters>");

        wpsService.postExecuteService("mock2", getUnknownXmlRequest(), mockServletRequest);
    }

    @Test
    public void canThrowExceptionWhenExecuteWithMissingElement() throws Exception {
        thrown.expect(InvalidRequestException.class);
        thrown.expectMessage("Invalid Execute request. unexpected element (uri:\"\", local:\"ProcessDescriptions\"). " +
                             "Expected elements are <{http://www.opengis.net/wps/1.0.0}AllowedValues>," +
                             "<{http://www.opengis.net/wps/1.0.0}AnyValue>," +
                             "<{http://www.opengis.net/wps/1.0.0}Capabilities>," +
                             "<{http://www.opengis.net/wps/1.0.0}DCP>," +
                             "<{http://www.opengis.net/wps/1.0.0}DescribeProcess>," +
                             "<{http://www.opengis.net/wps/1.0.0}ExceptionReport>," +
                             "<{http://www.opengis.net/wps/1.0.0}Execute>," +
                             "<{http://www.opengis.net/wps/1.0.0}ExecuteResponse>," +
                             "<{http://www.opengis.net/wps/1.0.0}GetCapabilities>," +
                             "<{http://www.opengis.net/wps/1.0.0}HTTP>," +
                             "<{http://www.opengis.net/wps/1.0.0}Languages>," +
                             "<{http://www.opengis.net/wps/1.0.0}NoValues>," +
                             "<{http://www.opengis.net/wps/1.0.0}Operation>," +
                             "<{http://www.opengis.net/wps/1.0.0}OperationsMetadata>," +
                             "<{http://www.opengis.net/wps/1.0.0}ProcessDescriptions>," +
                             "<{http://www.opengis.net/wps/1.0.0}ProcessOfferings>," +
                             "<{http://www.opengis.net/ows/1.1}ServiceIdentification>," +
                             "<{http://www.opengis.net/wps/1.0.0}ServiceProvider>," +
                             "<{http://www.opengis.net/wps/1.0.0}ValuesReference>," +
                             "<{http://www.opengis.net/wps/1.0.0}WSDL>," +
                             "<{http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd}parameters>");

        wpsService.postExecuteService("mock2", getInvalidExecuteRequest(), mockServletRequest);
    }

    private String getInvalidExecuteRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
               "<ProcessDescriptions service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xmlns:bc=\"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
               "\t<ProcessDescription storeSupported=\"true\" statusSupported=\"true\" wps:processVersion=\"2.1.3-SNAPSHOT\">\n" +
               "\t\t<ows:Identifier>beam-idepix~2.1.3-SNAPSHOT~Idepix.Land</ows:Identifier>\n" +
               "\t\t<ows:Title>beam-idepix~2.1.3-SNAPSHOT~Idepix.Land</ows:Title>\n" +
               "\t\t<ows:Abstract>\n" +
               "\t\t</ows:Abstract>\n" +
               "\t\t<DataInputs>\n" +
               "\t\t\t<Input>\n" +
               "\t\t\t\t<ows:Identifier>productionName</ows:Identifier>\n" +
               "\t\t\t\t<ows:Title>Production name</ows:Title>\n" +
               "\t\t\t\t<LiteralData>\n" +
               "\t\t\t\t\t<ows:DataType>string</ows:DataType>\n" +
               "\t\t\t\t</LiteralData>\n" +
               "\t\t\t</Input>\n" +
               "\t\t</DataInputs>\n" +
               "\t\t<ProcessOutputs>\n" +
               "\t\t\t<Output>\n" +
               "\t\t\t\t<ows:Identifier>productionResults</ows:Identifier>\n" +
               "\t\t\t\t<ows:Title>URL to the production result(s)</ows:Title>\n" +
               "\t\t\t\t<ComplexOutput>\n" +
               "\t\t\t\t</ComplexOutput>\n" +
               "\t\t\t</Output>\n" +
               "\t\t</ProcessOutputs>\n" +
               "\t</ProcessDescription>\n" +
               "</ProcessDescriptions>";
    }

    private String getUnknownXmlRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
               "<web-app version=\"2.4\" xmlns=\"http://java.sun.com/xml/ns/j2ee\"\n" +
               "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
               "         xsi:schemaLocation=\"http://java.sun.com/xml/ns/j2ee\n" +
               "http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd\">\n" +
               "\n" +
               "    <display-name>Calvalus WPS</display-name>\n" +
               "    <description>Calvalus WPS</description>\n" +
               "</web-app>";
    }

    private String getExecuteRequestWithoutIdentifier() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "\n" +
               "<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
               "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
               "\t\t\t xmlns:cal= \"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\"\n" +
               "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
               "</wps:Execute>";
    }

    private String getExecuteRequestWithoutServiceAndVersion() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "\n" +
               "<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
               "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
               "\t\t\t xmlns:cal= \"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\"\n" +
               "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
               "\n" +
               "        <ows:Identifier>beam-idepix~2.0.9~Idepix.Water</ows:Identifier>\n" +
               "</wps:Execute>";
    }

    private String getValidExecuteRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "\n" +
               "<wps:Execute service=\"WPS\"\n" +
               "             version=\"1.0.0\"\n" +
               "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
               "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
               "\t\t\t xmlns:cal= \"http://www.brockmann-consult.de/calwps/calwpsL3Parameters-schema.xsd\"\n" +
               "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
               "\n" +
               "        <ows:Identifier>beam-idepix~2.0.9~Idepix.Water</ows:Identifier>\n" +
               "</wps:Execute>";
    }

    private void configureMockUser() {
        Principal mockUserPrincipal = mock(Principal.class);
        when(mockUserPrincipal.getName()).thenReturn("mockUserName");
        when(mockServletRequest.getUserPrincipal()).thenReturn(mockUserPrincipal);
    }

}