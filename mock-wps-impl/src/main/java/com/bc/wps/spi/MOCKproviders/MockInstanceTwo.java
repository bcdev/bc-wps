package com.bc.wps.spi.mockproviders;

import static com.bc.wps.api.utils.WpsTypeConverter.str2CodeType;
import static com.bc.wps.api.utils.WpsTypeConverter.str2LanguageStringType;
import static com.bc.wps.api.utils.WpsTypeConverter.str2OnlineResourceType;

import com.bc.wps.api.WpsRequestContext;
import com.bc.wps.api.WpsServiceException;
import com.bc.wps.api.WpsServiceInstance;
import com.bc.wps.api.schema.AddressType;
import com.bc.wps.api.schema.Capabilities;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.ContactType;
import com.bc.wps.api.schema.DCP;
import com.bc.wps.api.schema.Execute;
import com.bc.wps.api.schema.ExecuteResponse;
import com.bc.wps.api.schema.HTTP;
import com.bc.wps.api.schema.InputDescriptionType;
import com.bc.wps.api.schema.LanguageStringType;
import com.bc.wps.api.schema.Languages;
import com.bc.wps.api.schema.Languages.Default;
import com.bc.wps.api.schema.LanguagesType;
import com.bc.wps.api.schema.Operation;
import com.bc.wps.api.schema.OperationsMetadata;
import com.bc.wps.api.schema.ProcessBriefType;
import com.bc.wps.api.schema.ProcessDescriptionType;
import com.bc.wps.api.schema.ProcessOfferings;
import com.bc.wps.api.schema.ProcessStartedType;
import com.bc.wps.api.schema.RequestMethodType;
import com.bc.wps.api.schema.ResponsiblePartySubsetType;
import com.bc.wps.api.schema.ServiceIdentification;
import com.bc.wps.api.schema.ServiceProvider;
import com.bc.wps.api.schema.StatusType;
import com.bc.wps.api.schema.TelephoneType;
import com.bc.wps.api.utils.CapabilitiesBuilder;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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
    public List<ProcessDescriptionType> describeProcess(WpsRequestContext context, String processorId) throws WpsServiceException {
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
        return startedResponse;
    }

    private ExecuteResponse getMockAcceptedResponse(WpsRequestContext context, String processId) throws WpsServiceException {
        ExecuteResponse mockAcceptedResponse = new ExecuteResponse();
        StatusType acceptedStatus = new StatusType();
        acceptedStatus.setProcessAccepted("The request has been accepted. The job is being handled by processor '" + processId + "'.");
        acceptedStatus.setCreationTime(getXmlGregorianCalendar());
        mockAcceptedResponse.setStatus(acceptedStatus);
        mockAcceptedResponse.setStatusLocation(context.getServerContext().getHostAddress() + "/" + context.getUserName());
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
        mockServiceIdentification.getServiceTypeVersion().add("0.1");
        mockServiceIdentification.getServiceTypeVersion().add("1.0");
        mockServiceIdentification.setFees("gratis");
        return mockServiceIdentification;
    }

    private ProcessOfferings getMockProcessOfferings() {
        ProcessOfferings processOfferings = new ProcessOfferings();
        ProcessBriefType process = new ProcessBriefType();
        CodeType processId = new CodeType();
        processId.setValue("process1");
        process.setIdentifier(processId);
        LanguageStringType processAbstract = new LanguageStringType();
        processAbstract.setValue("This is a mock process from mock provider 2");
        process.setAbstract(processAbstract);
        process.setProcessVersion("0.1");
        processOfferings.getProcess().add(process);
        return processOfferings;
    }

    private ProcessDescriptionType getMockProcess(String processName) {
        ProcessDescriptionType process = new ProcessDescriptionType();
        LanguageStringType title = new LanguageStringType();
        title.setValue(processName);
        process.setTitle(title);

        LanguageStringType abstractValue = new LanguageStringType();
        abstractValue.setValue("Description");
        process.setAbstract(abstractValue);

        ProcessDescriptionType.DataInputs dataInputs = new ProcessDescriptionType.DataInputs();
        InputDescriptionType input = new InputDescriptionType();
        CodeType input1 = new CodeType();
        input1.setValue("input1");
        input.setIdentifier(input1);

        LanguageStringType inputAbstract = new LanguageStringType();
        inputAbstract.setValue("input description");
        input.setAbstract(inputAbstract);

        dataInputs.getInput().add(input);
        process.setDataInputs(dataInputs);
        return process;
    }
}
