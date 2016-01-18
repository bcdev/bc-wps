package com.bc.wps.api.utils;


import com.bc.wps.api.schema.AllowedValues;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.DomainMetadataType;
import com.bc.wps.api.schema.InputDescriptionType;
import com.bc.wps.api.schema.LanguageStringType;
import com.bc.wps.api.schema.LiteralInputType;

import java.util.List;

/**
 * @author hans
 */
public class InputDescriptionTypeBuilder {

    private CodeType identifier;
    private LanguageStringType title;
    private LanguageStringType abstractValue;
    private LiteralInputType literalInputType;

    private InputDescriptionTypeBuilder() {
        this.identifier = new CodeType();
        this.title = new LanguageStringType();
        this.abstractValue = new LanguageStringType();
        this.literalInputType = new LiteralInputType();
    }

    public static InputDescriptionTypeBuilder create() {
        return new InputDescriptionTypeBuilder();
    }

    public InputDescriptionType build() {
        return new InputDescriptionType(this);
    }

    public InputDescriptionTypeBuilder withIdentifier(String identifierText) {
        this.identifier.setValue(identifierText);
        return this;
    }

    public InputDescriptionTypeBuilder withTitle(String title) {
        this.title.setValue(title);
        return this;
    }


    public InputDescriptionTypeBuilder withAbstract(String abstractText) {
        this.abstractValue.setValue(abstractText);
        return this;
    }

    public InputDescriptionTypeBuilder withDefaultValue(String defaultValue) {
        this.literalInputType.setDefaultValue(defaultValue);
        return this;
    }

    public InputDescriptionTypeBuilder withDataType(String dataType) {
        DomainMetadataType dataTypeValue = new DomainMetadataType();
        dataTypeValue.setValue(dataType);
        this.literalInputType.setDataType(dataTypeValue);
        return this;
    }

    public InputDescriptionTypeBuilder withAllowedValues(List<String> allowedValuesList) {
        AllowedValues allowedValues = new AllowedValues();
        for (String allowedValue : allowedValuesList) {
            allowedValues.getValueOrRange().add(allowedValue);
        }
        this.literalInputType.setAllowedValues(allowedValues);
        return this;
    }

    public CodeType getIdentifier() {
        return identifier;
    }

    public LanguageStringType getTitle() {
        return title;
    }

    public LanguageStringType getAbstractValue() {
        return abstractValue;
    }

    public LiteralInputType getLiteralInputType() {
        return literalInputType;
    }
}
