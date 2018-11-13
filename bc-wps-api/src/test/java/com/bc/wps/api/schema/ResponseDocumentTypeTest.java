package com.bc.wps.api.schema;

import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class ResponseDocumentTypeTest {

    @Test
    public void testMarshaling() throws JAXBException {
        final ResponseDocumentType responseDocumentType = new ResponseDocumentType();
        responseDocumentType.setStoreExecuteResponse(true);
        responseDocumentType.setStatus(true);

        final String marshalled = marshal(responseDocumentType);

        assertThat(marshalled, is(equalToIgnoringWhiteSpace(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> " +
                "<wps:Execute xmlns:bc=\"http://www.brockmann-consult.de/bc-wps/calwpsL3Parameters-schema.xsd\"" +
                "             xmlns:ows=\"http://www.opengis.net/ows/1.1\"" +
                "             xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"" +
                "             xmlns:wps=\"http://www.opengis.net/wps/1.0.0\"" +
                "             xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                "             xmlns:xlink=\"http://www.w3.org/1999/xlink\">" +
                "    <wps:ResponseForm>" +
                "        <wps:ResponseDocument storeExecuteResponse=\"true\" status=\"true\"/>" +
                "    </wps:ResponseForm> " +
                "</wps:Execute>")));
    }


    private static String marshal(ResponseDocumentType responseDocument) throws JAXBException {
        final ResponseFormType responseForm = new ResponseFormType();
        responseForm.setResponseDocument(responseDocument);
        final Execute execute = new Execute();
        execute.setResponseForm(responseForm);

        JAXBContext jaxbContext = JAXBContext.newInstance(execute.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(execute, stringWriter);
        return stringWriter.toString();
    }
}
