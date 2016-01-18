package com.bc.wps.api.utils;

import com.bc.wps.api.schema.CodeType;
import com.bc.wps.api.schema.LanguageStringType;
import com.bc.wps.api.schema.LanguagesType;
import com.bc.wps.api.schema.OnlineResourceType;
import com.bc.wps.api.schema.TelephoneType;

/**
 * @author hans
 */
public class WpsTypeConverter {

    public static CodeType str2CodeType(String text) {
        CodeType codeType = new CodeType();
        codeType.setValue(text);
        return codeType;
    }

    public static LanguageStringType str2LanguageStringType(String text) {
        LanguageStringType languageStringType = new LanguageStringType();
        languageStringType.setValue(text);
        return languageStringType;
    }

    public static OnlineResourceType str2OnlineResourceType(String text) {
        OnlineResourceType onlineResourceType = new OnlineResourceType();
        onlineResourceType.setHref(text);
        return onlineResourceType;
    }
}
