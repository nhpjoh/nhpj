package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.BaseXML;


/**
 * @author Paj
 */
public class SoapResponseXML extends BaseXML {
    
    public SoapResponseXML(String xml) {
        super(xml);
        this.createDocument();
    }

    public SoapResponseXML() {
        this.setXML(XML);
        this.createDocument();
    }
    
    private String XML =    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}


