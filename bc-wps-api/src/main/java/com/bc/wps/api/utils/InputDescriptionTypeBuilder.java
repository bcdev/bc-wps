package com.bc.wps.api.utils;


import com.bc.wps.api.schema.AllowedValues;
import com.bc.wps.api.schema.AnyValue;
import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.DomainMetadataType;
import com.bc.wps.api.schema.InputDescriptionType;
import com.bc.wps.api.schema.LanguageStringType;
import com.bc.wps.api.schema.LiteralInputType;

import java.math.BigInteger;
import java.util.List;

/**
 * @author hans
 */
public class InputDescriptionTypeBuilder {

    private CodeType identifier;
    private LanguageStringType title;
    private LanguageStringType abstractValue;
    private LiteralInputType literalInputType;
    private BigInteger minOccurs;
    private BigInteger maxOccurs;

    private InputDescriptionTypeBuilder() {
        this.identifier = new CodeType();
        this.title = new LanguageStringType();
        this.abstractValue = new LanguageStringType();
        this.literalInputType = new LiteralInputType();
        this.minOccurs = BigInteger.ZERO;
        this.maxOccurs = BigInteger.ONE;
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

    public InputDescriptionTypeBuilder setMinOccurs(BigInteger minOccurs) {
        this.minOccurs = minOccurs;
        return this;
    }

    public InputDescriptionTypeBuilder setMaxOccurs(BigInteger maxOccurs) {
        this.maxOccurs = maxOccurs;
        return this;
    }

    public InputDescriptionTypeBuilder withDefaultValue(String defaultValue) {
        this.literalInputType.setAnyValue(new AnyValue());
        this.literalInputType.setDefaultValue(defaultValue);
        return this;
    }

    public InputDescriptionTypeBuilder withDataType(String dataType) {
        DomainMetadataType dataTypeValue = new DomainMetadataType();
        dataTypeValue.setValue(dataType);
        this.literalInputType.setDataType(dataTypeValue);
        this.literalInputType.setAnyValue(new AnyValue());
        this.literalInputType.setDefaultValue("");
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

    public BigInteger getMinOccurs() {
        return minOccurs;
    }

    public BigInteger getMaxOccurs() {
        return maxOccurs;
    }
}
