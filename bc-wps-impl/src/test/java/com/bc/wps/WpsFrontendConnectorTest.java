package com.bc.wps;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.exceptions.OptionNotSupportedException;
import com.bc.wps.api.exceptions.WpsServiceException;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.LanguageStringType;
import com.bc.wps.api.schema.ProcessBriefType;
import com.bc.wps.api.schema.StatusType;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.verification.VerificationModeFactory;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

//import static org.junit.matchers.JUnitMatchers.*;

public class WpsFrontendConnectorTest {

    private HttpServletRequest mockServletRequest;
    private WpsFrontendConnector wpsFrontendConnector;

    @Before
    public void setUp() throws Exception {
        Locale.setDefault(Locale.ENGLISH);

        Principal mockUserPrincipal = mock(Principal.class);
        when(mockUserPrincipal.getName()).thenReturn("mockUserName");

        mockServletRequest = mock(HttpServletRequest.class);
        when(mockServletRequest.getUserPrincipal()).thenReturn(mockUserPrincipal);

        wpsFrontendConnector = new WpsFrontendConnector();
    }

    @Test
    public void testThat_postExecuteService_ExecuteWithValidXmlRequest_InMockWps() throws WpsServiceException {
        //preparation
        final WpsServiceInstance mockWpsServiceInstance = mock(WpsServiceInstance.class);
        final WpsRequestContextImpl requestContext = new WpsRequestContextImpl(mockServletRequest);
        when(mockWpsServiceInstance.doExecute(same(requestContext), any(Execute.class))).thenReturn(getExecuteResponse());

        //execution
        String wpsResponse = wpsFrontendConnector.postExecuteService(getValidExecuteRequest(), mockServletRequest, mockWpsServiceInstance, requestContext);

        //verification
//        XmlValidator.validateString(wpsResponse);
        assertThat(wpsResponse, containsString(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<wps:ExecuteResponse serviceInstance=\"http://companyUrl/serviceName?\" statusLocation=\"test location\" service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <wps:Process wps:processVersion=\"1.0\">\n" +
                "        <ows:Identifier>process1</ows:Identifier>\n" +
                "        <ows:Title>Process 1</ows:Title>\n" +
                "        <ows:Abstract>Process 1 description</ows:Abstract>\n" +
                "    </wps:Process>\n" +
                "    <wps:Status creationTime=\"2018-11-22T03:04:05.007+01:00\">\n" +
                "        <wps:ProcessAccepted>test accepted message</wps:ProcessAccepted>\n" +
                "    </wps:Status>\n" +
                "</wps:ExecuteResponse>\n"));
        final ArgumentCaptor<Execute> captor = ArgumentCaptor.forClass(Execute.class);
        verify(mockWpsServiceInstance, VerificationModeFactory.times(1)).doExecute(same(requestContext), captor.capture());
        final List<Execute> allValues = captor.getAllValues();
        assertThat(allValues.size(), is(equalTo(1)));
        final Execute execute = allValues.get(0);
        assertMarshalledValidExecute(execute);
    }

    @Test
    public void testThat_postExecuteService_serviceInstanceReturnsOptionNotSupportedException() throws WpsServiceException {
        //preparation
        final WpsServiceInstance mockWpsServiceInstance = mock(WpsServiceInstance.class);
        final WpsRequestContextImpl requestContext = new WpsRequestContextImpl(mockServletRequest);
        when(mockWpsServiceInstance.doExecute(same(requestContext), any(Execute.class))).thenThrow(new OptionNotSupportedException("MESSAGE", "NOT_SUPPORTED"));

        //execution
        String wpsResponse = wpsFrontendConnector.postExecuteService(getValidExecuteRequest(), mockServletRequest, mockWpsServiceInstance, requestContext);

        //verification
        assertThat(wpsResponse, is(equalToIgnoringWhiteSpace(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\"\n" +
                "                     xml:lang=\"en\"\n" +
                "                     xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\"\n" +
                "                     xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"\n" +
                "                     xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
                "                     xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                     xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
                "                     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "                     xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"OptionNotSupported\" locator=\"NOT_SUPPORTED\">\n" +
                "        <ows:ExceptionText>MESSAGE</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testThat_postExecuteService_withMissingIdentifier_returnsAnInvalidParameterValueReport() throws WpsServiceException {
        //preparation
        final WpsRequestContextImpl requestContext = new WpsRequestContextImpl(mockServletRequest);

        //execution
        String wpsResponse = wpsFrontendConnector.postExecuteService(getExecuteRequestWithoutIdentifer(), mockServletRequest, mock(WpsServiceInstance.class), requestContext);

        //verification
        assertThat(wpsResponse, is(equalToIgnoringWhiteSpace(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\"\n" +
                "                     xml:lang=\"en\"\n" +
                "                     xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\"\n" +
                "                     xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"\n" +
                "                     xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
                "                     xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                     xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
                "                     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "                     xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"InvalidParameterValue\" locator=\"Execute\">\n" +
                "        <ows:ExceptionText>XML schema fault: Element 'Identifier' missed in element 'Execute'.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testThat_postExecuteService_withMissingIdentifierValue_returnsAnInvalidParameterValueReport() throws WpsServiceException {
        //preparation
        final WpsRequestContextImpl requestContext = new WpsRequestContextImpl(mockServletRequest);

        //execution
        String wpsResponse = wpsFrontendConnector.postExecuteService(getExecuteRequestWithEmptyIdentifer(), mockServletRequest, mock(WpsServiceInstance.class), requestContext);

        //verification
        assertThat(wpsResponse, is(equalToIgnoringWhiteSpace(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ows:ExceptionReport version=\"1.0.0\"\n" +
                "                     xml:lang=\"en\"\n" +
                "                     xsi:schemaLocation=\"http://www.opengis.net/ows/1.1 http://schemas.opengis.net/ows/1.1.0/owsExceptionReport.xsd\"\n" +
                "                     xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"\n" +
                "                     xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
                "                     xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                     xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
                "                     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "                     xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"Identifier\">\n" +
                "        <ows:ExceptionText>The value of parameter 'Identifier' is missing.</ows:ExceptionText>\n" +
                "    </ows:Exception>\n" +
                "</ows:ExceptionReport>")));
    }

    protected ExecuteResponse getExecuteResponse() {
        final ProcessBriefType processBriefType = new ProcessBriefType();
        processBriefType.setProcessVersion("1.0");
        final CodeType identifier = new CodeType();
        identifier.setValue("process1");
        processBriefType.setIdentifier(identifier);
        final LanguageStringType title = new LanguageStringType();
        title.setValue("Process 1");
        processBriefType.setTitle(title);
        final LanguageStringType theAbstract = new LanguageStringType();
        theAbstract.setValue("Process 1 description");
        processBriefType.setAbstract(theAbstract);

        final StatusType status = new StatusType();
        final LocalDateTime ldt = LocalDateTime.of(2018, 11, 22, 3, 4, 5);
        final ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        final GregorianCalendar gc = GregorianCalendar.from(zdt);
        gc.set(Calendar.MILLISECOND, 7);
        final XMLGregorianCalendarImpl creationTime = new XMLGregorianCalendarImpl(gc);
        status.setCreationTime(creationTime);
        status.setProcessAccepted("test accepted message");

        final ExecuteResponse executeResponse = new ExecuteResponse();
        executeResponse.setServiceInstance("http://companyUrl/serviceName?");
        executeResponse.setStatusLocation("test location");
        executeResponse.setService("WPS");
        executeResponse.setVersion("1.0.0");
        executeResponse.setLang("en");
        executeResponse.setProcess(processBriefType);
        executeResponse.setStatus(status);
        return executeResponse;
    }

    private String getValidExecuteRequest() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "<wps:Execute service=\"WPS\"\n" +
               "             version=\"1.0.0\"\n" +
               "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
               "             xmlns:ows=\"http://www.opengis.net/ows/1.1\">\n" +
               "        <ows:Identifier>beam-idepix~2.0.9~Idepix.Water</ows:Identifier>\n" +
               "</wps:Execute>";
    }

    private void assertMarshalledValidExecute(Execute execute) {
        assertNotNull(execute);
        assertThat(execute.getService(), is(equalTo("WPS")));
        assertThat(execute.getVersion(), is(equalTo("1.0.0")));
        assertThat(execute.getIdentifier(), is(instanceOf(CodeType.class)));
        assertThat(execute.getIdentifier().getValue(), is(equalTo("beam-idepix~2.0.9~Idepix.Water")));
    }
    private String getExecuteRequestWithoutIdentifer() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "<wps:Execute service=\"WPS\"\n" +
               "             version=\"1.0.0\"\n" +
               "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
               "             xmlns:ows=\"http://www.opengis.net/ows/1.1\">\n" +
//               "        <ows:Identifier>beam-idepix~2.0.9~Idepix.Water</ows:Identifier>\n" +
               "</wps:Execute>";
    }
    private String getExecuteRequestWithEmptyIdentifer() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "<wps:Execute service=\"WPS\"\n" +
               "             version=\"1.0.0\"\n" +
               "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
               "             xmlns:ows=\"http://www.opengis.net/ows/1.1\">\n" +
               "        <ows:Identifier></ows:Identifier>\n" +
               "</wps:Execute>";
    }

    private static class CM extends CoreMatchers {

    }
}