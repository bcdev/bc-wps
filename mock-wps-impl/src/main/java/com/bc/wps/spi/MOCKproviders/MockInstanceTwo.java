package com.bc.wps.spi.mockproviders;

import static com.bc.wps.api.schema.ProcessDescriptionType.*;
import static com.bc.wps.api.utils.WpsTypeConverter.str2CodeType;
import static com.bc.wps.api.utils.WpsTypeConverter.str2LanguageStringType;
import static com.bc.wps.api.utils.WpsTypeConverter.str2OnlineResourceType;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServiceException;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.schema.AddressType;
import com.bc.wps.api.schema.AnyValue;
import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.ComplexDataCombinationType;
import com.bc.wps.api.schema.ComplexDataCombinationsType;
import com.bc.wps.api.schema.ComplexDataDescriptionType;
import com.bc.wps.api.schema.ContactType;
import com.bc.wps.api.schema.DCP;
import com.bc.wps.api.schema.DomainMetadataType;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.HTTP;
import com.bc.wps.api.schema.InputDescriptionType;
import com.bc.wps.api.schema.LanguageStringType;
import com.bc.wps.api.schema.Languages;
import com.bc.wps.api.schema.Languages.Default;
import com.bc.wps.api.schema.LanguagesType;
import com.bc.wps.api.schema.LiteralInputType;
import com.bc.wps.api.schema.LiteralOutputType;
import com.bc.wps.api.schema.Operation;
import com.bc.wps.api.schema.OperationsMetadata;
import com.bc.wps.api.schema.OutputDataType;
import com.bc.wps.api.schema.OutputDescriptionType;
import com.bc.wps.api.schema.ProcessBriefType;
import com.bc.wps.api.schema.ProcessDescriptionType;
import com.bc.wps.api.schema.ProcessOfferings;
import com.bc.wps.api.schema.ProcessStartedType;
import com.bc.wps.api.schema.RequestMethodType;
import com.bc.wps.api.schema.ResponsiblePartySubsetType;
import com.bc.wps.api.schema.ServiceIdentification;
import com.bc.wps.api.schema.ServiceProvider;
import com.bc.wps.api.schema.StatusType;
import com.bc.wps.api.schema.SupportedComplexDataInputType;
import com.bc.wps.api.schema.TelephoneType;
import com.bc.wps.api.schema.ValueType;
import com.bc.wps.api.utils.CapabilitiesBuilder;
import com.bc.wps.api.utils.InputDescriptionTypeBuilder;
import com.bc.wps.api.utils.WpsTypeConverter;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This is a mock implementation of a WPS instance. This is intended as a reference when implementing the WpsServiceInstance class.
 *
 * @author hans
 */
public class MockInstanceTwo implements WpsServiceInstance {

    @Override
    public Capabilities getCapabilities(WpsRequestContext context) throws WpsServiceException {
        return getMockCapabilities();
    }

    @Override
    public List<ProcessDescriptionType> describeProcess(WpsRequestContext context, String processId) throws WpsServiceException {
        List<ProcessDescriptionType> processes = new ArrayList<>();
        processes.add(getMockProcess("Process1"));
        processes.add(getMockProcess("Process2"));
        return processes;
    }

    @Override
    public ExecuteResponse doExecute(WpsRequestContext context, Execute executeRequest) throws WpsServiceException {
        String processId = executeRequest.getIdentifier().getValue();
        return getMockAcceptedResponse(context, processId);
    }

    @Override
    public ExecuteResponse getStatus(WpsRequestContext context, String jobId) throws WpsServiceException {
        return getMockStartedResponse();
    }

    @Override
    public void dispose() {

    }

    private ExecuteResponse getMockStartedResponse() throws WpsServiceException {
        ExecuteResponse startedResponse = new ExecuteResponse();
        StatusType startedStatus = new StatusType();
        ProcessStartedType processStarted = new ProcessStartedType();
        processStarted.setValue("RUNNING");
        processStarted.setPercentCompleted(65);
        startedStatus.setProcessStarted(processStarted);
        startedStatus.setCreationTime(getXmlGregorianCalendar());
        startedResponse.setStatus(startedStatus);
        startedResponse.setServiceInstance("http://companyUrl/serviceName?");
        startedResponse.setService("WPS");
        startedResponse.setVersion("1.0.0");
        startedResponse.setLang("en");
        return startedResponse;
    }

    private ExecuteResponse getMockAcceptedResponse(WpsRequestContext context, String processId) throws WpsServiceException {
        ExecuteResponse mockAcceptedResponse = new ExecuteResponse();

        ProcessBriefType process = new ProcessBriefType();
        process.setProcessVersion("1.0");
        process.setIdentifier(str2CodeType("process1"));
        process.setTitle(str2LanguageStringType("Process 1"));
        process.setAbstract(str2LanguageStringType("Process 1 description"));
        mockAcceptedResponse.setProcess(process);

        StatusType acceptedStatus = new StatusType();
        acceptedStatus.setProcessAccepted("The request has been accepted. The job is being handled by processor '" + processId + "'.");
        acceptedStatus.setCreationTime(getXmlGregorianCalendar());
        mockAcceptedResponse.setStatus(acceptedStatus);
        mockAcceptedResponse.setStatusLocation(context.getServerContext().getHostAddress() + "/" + context.getUserName());
        mockAcceptedResponse.setServiceInstance("http://companyUrl/serviceName?");
        mockAcceptedResponse.setService("WPS");
        mockAcceptedResponse.setVersion("1.0.0");
        mockAcceptedResponse.setLang("en");
        return mockAcceptedResponse;
    }

    private XMLGregorianCalendar getXmlGregorianCalendar() throws WpsServiceException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException exception) {
            throw new WpsServiceException("Unable to create new Gregorian Calendar.", exception);
        }
    }

    private Capabilities getMockCapabilities() {

        ServiceIdentification mockServiceIdentification = getMockServiceIdentification();
        ServiceProvider mockServiceProvider = getMockServiceProvider();
        OperationsMetadata mockOperationsMetadata = getMockOperationsMetadata();
        ProcessOfferings mockProcessOfferings = getMockProcessOfferings();
        Languages mockLanguages = getMockLanguages();

        return CapabilitiesBuilder.create()
                    .withServiceIdentification(mockServiceIdentification)
                    .withServiceProvider(mockServiceProvider)
                    .withOperationsMetadata(mockOperationsMetadata)
                    .withProcessOfferings(mockProcessOfferings)
                    .withLanguages(mockLanguages)
                    .build();
    }

    private Languages getMockLanguages() {
        Languages mockLanguages = new Languages();
        Default defaultLanguage = new Default();
        defaultLanguage.setLanguage("EN");
        mockLanguages.setDefault(defaultLanguage);
        LanguagesType supportedLanguages = new LanguagesType();
        supportedLanguages.getLanguage().add("EN");
        supportedLanguages.getLanguage().add("DE");
        mockLanguages.setSupported(supportedLanguages);
        return mockLanguages;
    }

    private OperationsMetadata getMockOperationsMetadata() {
        OperationsMetadata mockOperationsMetadata = new OperationsMetadata();

        Operation operation1 = new Operation();
        operation1.setName("GetCapabilities");
        DCP getCapabilitiesDcp = new DCP();
        HTTP getCapabilitiesHttp = new HTTP();
        RequestMethodType getCapabilitiesRequestMethod = new RequestMethodType();
        getCapabilitiesRequestMethod.setHref("http://companyUrl/serviceName?");
        getCapabilitiesHttp.setGet(getCapabilitiesRequestMethod);
        getCapabilitiesDcp.setHTTP(getCapabilitiesHttp);
        operation1.getDCP().add(getCapabilitiesDcp);
        mockOperationsMetadata.getOperation().add(operation1);

        Operation operation2 = new Operation();
        operation2.setName("DescribeProcess");
        DCP describeProcessDcp = new DCP();
        HTTP describeProcessHttp = new HTTP();
        RequestMethodType describeProcessRequestMethod = new RequestMethodType();
        describeProcessRequestMethod.setHref("http://companyUrl/serviceName?");
        describeProcessHttp.setGet(describeProcessRequestMethod);
        describeProcessDcp.setHTTP(describeProcessHttp);
        operation2.getDCP().add(describeProcessDcp);
        mockOperationsMetadata.getOperation().add(operation2);

        Operation operation3 = new Operation();
        operation3.setName("Execute");
        DCP executeDcp = new DCP();
        HTTP executeHttp = new HTTP();
        RequestMethodType executeRequestMethod = new RequestMethodType();
        executeRequestMethod.setHref("http://companyUrl/serviceName");
        executeHttp.setPost(executeRequestMethod);
        executeDcp.setHTTP(executeHttp);
        operation3.getDCP().add(executeDcp);
        mockOperationsMetadata.getOperation().add(operation3);

        Operation operation4 = new Operation();
        operation4.setName("GetStatus");
        DCP getStatusDcp = new DCP();
        HTTP getStatusHttp = new HTTP();
        RequestMethodType getStatusRequestMethod = new RequestMethodType();
        getStatusRequestMethod.setHref("http://companyUrl/serviceName?");
        getStatusHttp.setGet(getStatusRequestMethod);
        getStatusDcp.setHTTP(getStatusHttp);
        operation4.getDCP().add(getStatusDcp);
        mockOperationsMetadata.getOperation().add(operation4);
        return mockOperationsMetadata;
    }

    private ServiceProvider getMockServiceProvider() {
        ServiceProvider mockServiceProvider = new ServiceProvider();
        mockServiceProvider.setProviderName("Fantasy World");
        mockServiceProvider.setProviderSite(str2OnlineResourceType("http://fantasy-world.com"));
        ResponsiblePartySubsetType mockServiceContact = new ResponsiblePartySubsetType();
        mockServiceContact.setIndividualName("John Doe");
        mockServiceContact.setPositionName("System Administrator");
        ContactType mockContactInfo = new ContactType();
        TelephoneType phoneInfo = new TelephoneType();
        phoneInfo.getVoice().add("+49 12345 6789");
        phoneInfo.getFacsimile().add("+49 98765 4321");
        mockContactInfo.setPhone(phoneInfo);
        AddressType mockAddress = new AddressType();
        mockAddress.getDeliveryPoint().add("Room 1, Building A, Fantasy Avenue");
        mockAddress.setCity("Fantasyville");
        mockAddress.setAdministrativeArea("FF");
        mockAddress.setPostalCode("1234");
        mockAddress.setCountry("Kingdom of Fantasy");
        mockAddress.getElectronicMailAddress().add("admin@fantasy-world.com");
        mockContactInfo.setAddress(mockAddress);
        mockServiceContact.setContactInfo(mockContactInfo);
        mockServiceProvider.setServiceContact(mockServiceContact);
        return mockServiceProvider;
    }

    private ServiceIdentification getMockServiceIdentification() {
        ServiceIdentification mockServiceIdentification = new ServiceIdentification();
        mockServiceIdentification.setTitle(str2LanguageStringType("A mock WPS server"));
        mockServiceIdentification.setAbstract(str2LanguageStringType("A mock WPS server to be used as a reference for any WPS implementations."));
        mockServiceIdentification.setServiceType(str2CodeType("WPS"));
        mockServiceIdentification.getServiceTypeVersion().add("1.0.0");
        mockServiceIdentification.getServiceTypeVersion().add("2.0.0");
        mockServiceIdentification.setFees("gratis");
        return mockServiceIdentification;
    }

    private ProcessOfferings getMockProcessOfferings() {
        ProcessOfferings processOfferings = new ProcessOfferings();
        ProcessBriefType process = new ProcessBriefType();
        process.setIdentifier(WpsTypeConverter.str2CodeType("process1"));
        process.setTitle(WpsTypeConverter.str2LanguageStringType("Process 1"));
        process.setAbstract(WpsTypeConverter.str2LanguageStringType("This is a mock process from mock provider 2"));
        process.setProcessVersion("0.1");
        processOfferings.getProcess().add(process);
        return processOfferings;
    }

    private ProcessDescriptionType getMockProcess(String processName) {
        ProcessDescriptionType process = new ProcessDescriptionType();
        process.setProcessVersion("1.0");

        process.setIdentifier(WpsTypeConverter.str2CodeType(processName));
        process.setTitle(WpsTypeConverter.str2LanguageStringType(processName));
        process.setAbstract(WpsTypeConverter.str2LanguageStringType("Description"));

        DataInputs dataInputs = new DataInputs();
        List<Object> allowedValues = new ArrayList<>();
        ValueType valueType = new ValueType();
        valueType.setValue("allowedValue");
        allowedValues.add(valueType);
        allowedValues.add(valueType);
        InputDescriptionType input = InputDescriptionTypeBuilder.create()
                    .withIdentifier("input1")
                    .withTitle("input title")
                    .withAbstract("input description")
                    .withDataType("String")
                    .withDefaultValue("default")
                    .withAllowedValues(allowedValues)
                    .build();
        dataInputs.getInput().add(input);

        InputDescriptionType inputWithoutAllowedValues = InputDescriptionTypeBuilder.create()
                    .withIdentifier("input2")
                    .withTitle("input without allowed values")
                    .withAbstract("input without allowed values description")
                    .withDataType("String")
                    .build();
        dataInputs.getInput().add(inputWithoutAllowedValues);

        InputDescriptionType complexInput = getComplexInputTypeWithSchema("schema.xsd");
        dataInputs.getInput().add(complexInput);

        process.setDataInputs(dataInputs);

        ProcessOutputs processOutputs = new ProcessOutputs();
        OutputDescriptionType processOutput = new OutputDescriptionType();
        processOutput.setIdentifier(WpsTypeConverter.str2CodeType("output1"));
        processOutput.setTitle(WpsTypeConverter.str2LanguageStringType("Output 1"));
        processOutput.setAbstract(WpsTypeConverter.str2LanguageStringType("This is output 1"));
        LiteralOutputType literalOutputType = new LiteralOutputType();
        DomainMetadataType outputDataType = getDomainMetadataType();
        literalOutputType.setDataType(outputDataType);
        processOutput.setLiteralOutput(literalOutputType);
        processOutputs.getOutput().add(processOutput);
        process.setProcessOutputs(processOutputs);
        return process;
    }

    private DomainMetadataType getDomainMetadataType() {
        DomainMetadataType dataType = new DomainMetadataType();
        dataType.setValue("data type");
        dataType.setReference("data type reference");
        return dataType;
    }

    private InputDescriptionType getComplexInputTypeWithSchema(String schemaUrl) {
        InputDescriptionType l3ParametersComplexType = new InputDescriptionType();

        l3ParametersComplexType.setMinOccurs(BigInteger.ZERO);
        l3ParametersComplexType.setMaxOccurs(BigInteger.ONE);
        l3ParametersComplexType.setIdentifier(str2CodeType("complex.parameter"));
        l3ParametersComplexType.setTitle(str2LanguageStringType("An example of complex parameter"));
        l3ParametersComplexType.setAbstract(str2LanguageStringType("Description for the parameter"));

        SupportedComplexDataInputType l3Parameters = new SupportedComplexDataInputType();
        ComplexDataCombinationType complexDataCombinationType = new ComplexDataCombinationType();
        ComplexDataDescriptionType complexDataDescriptionType = new ComplexDataDescriptionType();
        complexDataDescriptionType.setMimeType("application/xml");
        complexDataDescriptionType.setSchema(schemaUrl);
        complexDataCombinationType.setFormat(complexDataDescriptionType);
        l3Parameters.setDefault(complexDataCombinationType);

        ComplexDataCombinationsType complexDataCombinationsType = new ComplexDataCombinationsType();
        complexDataCombinationsType.getFormat().add(complexDataDescriptionType);
        l3Parameters.setSupported(complexDataCombinationsType);
        l3ParametersComplexType.setComplexData(l3Parameters);
        return l3ParametersComplexType;
    }
}
