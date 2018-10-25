package com.bc.wps;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
//import static org.junit.matchers.JUnitMatchers.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.exceptions.WpsServiceException;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.LanguageStringType;
import com.bc.wps.api.schema.ProcessBriefType;
import com.bc.wps.api.schema.StatusType;
import com.bc.wps.utilities.XmlValidator;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.verification.Times;
import org.mockito.internal.verification.VerificationModeFactory;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

public class WpsFrontendConnectorTest {

    private HttpServletRequest mockServletRequest;
    private WpsFrontendConnector classUnderTest;

    @Before
    public void setUp() throws Exception {
        Locale.setDefault(Locale.ENGLISH);

        Principal mockUserPrincipal = mock(Principal.class);
        when(mockUserPrincipal.getName()).thenReturn("mockUserName");

        mockServletRequest = mock(HttpServletRequest.class);
        when(mockServletRequest.getUserPrincipal()).thenReturn(mockUserPrincipal);

        classUnderTest = new WpsFrontendConnector();
    }

    @Test
    public void testThat_postExecuteService_ExecuteWithValidXmlRequest_InMockWps() throws WpsServiceException {
        //preparation
        final WpsServiceInstance mockServiceProvider = mock(WpsServiceInstance.class);
        final WpsRequestContextImpl requestContext = new WpsRequestContextImpl(mockServletRequest);
        when(mockServiceProvider.doExecute(same(requestContext), any(Execute.class))).thenReturn(getExecuteResponse());

        //execution
        String wpsResponse = classUnderTest.postExecuteService(getValidExecuteRequest(), mockServletRequest, mockServiceProvider, requestContext);

        //verification

        XmlValidator.validateString(wpsResponse);
        assertThat(wpsResponse, containsString(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<wps:ExecuteResponse serviceInstance=\"http://companyUrl/serviceName?\" statusLocation=\"test location\" service=\"WPS\" version=\"1.0.0\" xml:lang=\"en\" xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_response.xsd\" xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <wps:Process wps:processVersion=\"1.0\">\n" +
                "        <ows:Identifier>process1</ows:Identifier>\n" +
                "        <ows:Title>Process 1</ows:Title>\n" +
                "        <ows:Abstract>Process 1 description</ows:Abstract>\n" +
                "    </wps:Process>\n" +
                "    <wps:Status creationTime=\"2018-10-22T03:04:05.007\">\n" +
                "        <wps:ProcessAccepted>test accepted message</wps:ProcessAccepted>\n" +
                "    </wps:Status>\n" +
                "</wps:ExecuteResponse>\n"));
        final ArgumentCaptor<Execute> captor = ArgumentCaptor.forClass(Execute.class);
        verify(mockServiceProvider, VerificationModeFactory.times(1)).doExecute(same(requestContext), captor.capture());
        final List<Execute> allValues = captor.getAllValues();
        assertThat(allValues.size(), is(equalTo(1)));
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
        final XMLGregorianCalendarImpl creationTime = new XMLGregorianCalendarImpl();
        creationTime.setTime(3,4,5,7);
        creationTime.setYear(2018);
        creationTime.setMonth(10);
        creationTime.setDay(22);
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
//        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
//                "\n" +
//                "<wps:Execute service=\"WPS\"\n" +
//                "             version=\"1.0.0\"\n" +
//                "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
//                "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
//                "\t\t\t xmlns:cal= \"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"\n" +
//                "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
//                "\n" +
//                "        <ows:Identifier>beam-idepix~2.0.9~Idepix.Water</ows:Identifier>\n" +
//                "</wps:Execute>";

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n" +
               "\n" +
               "<wps:Execute service=\"WPS\"\n" +
               "             version=\"1.0.0\"\n" +
               "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"\n" +
               "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"\n" +
               "             xmlns:cal=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"\n" +
               "             xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
               "             xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
               "             xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_request.xsd\">\n" +
               "\n" +
               "        <ows:Identifier>beam-idepix~2.0.9~Idepix.Water</ows:Identifier>\n" +
               "</wps:Execute>";
    }

    private static class CM extends CoreMatchers{
    }
}