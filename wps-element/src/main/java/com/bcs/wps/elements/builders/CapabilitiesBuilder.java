package com.bcs.wps.elements.builders;

import com.bcs.wps.elements.Capabilities;
import com.bcs.wps.elements.Languages;
import com.bcs.wps.elements.OperationsMetadata;
import com.bcs.wps.elements.ProcessOfferings;
import com.bcs.wps.elements.ServiceIdentification;
import com.bcs.wps.elements.ServiceProvider;

/**
 * @author hans
 */
public class CapabilitiesBuilder {

    private OperationsMetadata operationsMetadata;
    private ServiceIdentification serviceIdentification;
    private ServiceProvider serviceProvider;
    private ProcessOfferings processOfferings;
    private Languages languages;

    private CapabilitiesBuilder() {
    }

    public static CapabilitiesBuilder create() {
        return new CapabilitiesBuilder();
    }

    public Capabilities build() {
        return new Capabilities(this);
    }

    public CapabilitiesBuilder withOperationsMetadata(OperationsMetadata operationsMetadata) {
        this.operationsMetadata = operationsMetadata;
        return this;
    }

    public CapabilitiesBuilder withServiceIdentification(ServiceIdentification serviceIdentification) {
        this.serviceIdentification = serviceIdentification;
        return this;
    }

    public CapabilitiesBuilder withServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        return this;
    }

    public CapabilitiesBuilder withProcessOfferings(ProcessOfferings processOfferings) {
        this.processOfferings = processOfferings;
        return this;
    }

    public CapabilitiesBuilder withLanguages(Languages languages) {
        this.languages = languages;
        return this;
    }

    public OperationsMetadata getOperationsMetadata() {
        return operationsMetadata;
    }

    public ServiceIdentification getServiceIdentification() {
        return serviceIdentification;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public ProcessOfferings getProcessOfferings() {
        return processOfferings;
    }

    public Languages getLanguages() {
        return languages;
    }
}
