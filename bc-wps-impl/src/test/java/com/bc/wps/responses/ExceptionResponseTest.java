package com.bc.wps.responses;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.bc.wps.api.exceptions.InvalidParameterValueException;
import com.bc.wps.api.exceptions.MissingParameterValueException;
import com.bc.wps.api.exceptions.NoApplicableCodeException;
import com.bc.wps.api.exceptions.NotEnoughStorageException;
import com.bc.wps.api.exceptions.OptionNotSupportedException;
import com.bc.wps.api.schema.ExceptionReport;
import com.bc.wps.utilities.JaxbHelper;
import org.junit.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExceptionResponseTest {

    ExceptionResponse exceptionResponse;

    @Before
    public void setUp() throws Exception {
        exceptionResponse = new ExceptionResponse();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInvalidParameterValueException_withMessageAndThrowableAndLocator() throws JAXBException {
        final InvalidParameterValueException exception = new InvalidParameterValueException("Exception message.", new Throwable("AAAAA"), "ParameterName");

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"InvalidParameterValue\" locator=\"ParameterName\">",
                "        <ows:ExceptionText>Exception message. : parameter 'ParameterName' has an invalid value.</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testInvalidParameterValueException_withThrowableAndLocator() throws JAXBException {
        final InvalidParameterValueException exception = new InvalidParameterValueException(new Throwable("AAAAA"), "ParameterName");

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"InvalidParameterValue\" locator=\"ParameterName\">",
                "        <ows:ExceptionText>Parameter 'ParameterName' has an invalid value.</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testInvalidParameterValueException_withLocator() throws JAXBException {
        final InvalidParameterValueException exception = new InvalidParameterValueException("ParameterName");

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"InvalidParameterValue\" locator=\"ParameterName\">",
                "        <ows:ExceptionText>Parameter 'ParameterName' has an invalid value.</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testMissingParameterValueException_withMessageAndThrowableAndLocator() throws JAXBException {
        final MissingParameterValueException exception = new MissingParameterValueException("Exception message.", new Throwable("AAAAA"), "ParameterName");

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"ParameterName\">",
                "        <ows:ExceptionText>The value of parameter 'ParameterName' is missing.</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testMissingParameterValueException_withThrowableAndLocator() throws JAXBException {
        final MissingParameterValueException exception = new MissingParameterValueException(new Throwable("AAAAA"), "ParameterName");

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"ParameterName\">",
                "        <ows:ExceptionText>The value of parameter 'ParameterName' is missing.</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testMissingParameterValueException_withLocator() throws JAXBException {
        final MissingParameterValueException exception = new MissingParameterValueException("ParameterName");

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"MissingParameterValue\" locator=\"ParameterName\">",
                "        <ows:ExceptionText>The value of parameter 'ParameterName' is missing.</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testNotEnoughStorageException_withMessageAndTrowable() throws JAXBException {
        final Throwable throwable = new Throwable("AAAAAA");
        final NotEnoughStorageException exception = new NotEnoughStorageException("Not enough storage", throwable);

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"NoApplicableCode\">",
                "        <ows:ExceptionText>Not enough storage</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testNotEnoughStorageException_withTrowable() throws JAXBException {
        final Throwable throwable = new Throwable("AAAAAA");
        final NotEnoughStorageException exception = new NotEnoughStorageException(throwable);

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"NoApplicableCode\">",
                "        <ows:ExceptionText>java.lang.Throwable: AAAAAA</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testNoApplicableCodeException_withMessageAndTrowable() throws JAXBException {
        final Throwable throwable = new Throwable("AAAAAAA");
        final NoApplicableCodeException exception = new NoApplicableCodeException("Not applicable message", throwable);

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"NoApplicableCode\">",
                "        <ows:ExceptionText>Not applicable message</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testNoApplicableCodeException_withTrowable() throws JAXBException {
        final Throwable throwable = new Throwable("Message");
        final NoApplicableCodeException exception = new NoApplicableCodeException(throwable);

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);

        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"NoApplicableCode\">",
                "        <ows:ExceptionText>java.lang.Throwable: Message</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
    }

    @Test
    public void testOptionNotSupportedException() throws JAXBException {
        final OptionNotSupportedException exception = new OptionNotSupportedException("Exception Message.");

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"OptionNotSupported\">",
                "        <ows:ExceptionText>Exception Message.</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
    }

    @Test
    public void testAnyOtherException() throws JAXBException {
        final IOException exception = new IOException("Any IO Exception", new Throwable("AAAAAA"));

        final ExceptionReport response = exceptionResponse.getExceptionResponse(exception);

        final String marshal = JaxbHelper.marshal(response);
        assertThat(marshal, stringContainsInOrder(Arrays.asList(
                "<ows:ExceptionReport version=\"1.0.0\"",
                "    <ows:Exception exceptionCode=\"NoApplicableCode\">",
                "        <ows:ExceptionText>Any IO Exception : AAAAAA</ows:ExceptionText>",
                "    </ows:Exception>",
                "</ows:ExceptionReport>")));
        int count = countPattern(marshal, "<ows:ExceptionText>");
        assertThat(count, is(1));
    }

    protected int countPattern(String marshal, String subString) {
        final Pattern pattern = Pattern.compile(subString);
        final Matcher matcher = pattern.matcher(marshal);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
