package com.bc.wps.utilities;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * This is a helper class to do Jaxb marshalling or unmarshalling.
 *
 * @author hans
 */
public class JaxbHelper {

    /**
     * @param object The object to be marshaled.
     * @return XML representation of the object in String format.
     * @throws JAXBException
     */
    public static String marshal(Object object) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(object, stringWriter);
        return stringWriter.toString();
    }

    /**
     *
     * @param inputStream The stream that contains the XML.
     * @param objectFactory A factory that can produce classes to be recognized by the JAXBContext.
     * @return un-marshaled object.
     * @throws JAXBException
     */
    public static Object unmarshal(InputStream inputStream, Object objectFactory) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(objectFactory.getClass());
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(inputStream);
    }

}
