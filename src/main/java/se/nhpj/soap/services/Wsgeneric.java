package se.nhpj.soap.services;

import se.nhpj.soap.services.SoapResponseXML;
import se.nhpj.soap.services.Prescription;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.soap.SOAPMessage;
import se.nhpj.soap.BaseXML;

/**
 * @author Nhpj
 */
public class Wsgeneric extends BaseXML {

    public Wsgeneric(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
    }

    public Wsgeneric() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
    }
    
    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en XXX returneras
        return null;
    }

    
    private String soapEndpointUrl = "https://prestanda/pi-wsgeneric-1.3/wsgeneric";
//    private String soapEndpointUrl = "https://test19.systest.receptpartner.se/pi-wsgeneric-1.3/wsgeneric";
//    private String soapEndpointUrl = "http://test19.systest.receptpartner.se:19280/pi-wsgeneric-1.3/wsgeneric";
    
    private String DATA = "";
    private String SERVICE = "EriNyttReceptNefService"; // MakuleraReceptService // EriNyttReceptNefService //
    public static final int MakuleraReceptService = 1;
    public static final int EriNyttReceptNefService = 0;

    public void setDATA(String DATA) {
        this.DATA = DATA;
        this.setTagValue("*//data", DATA);
        this.createXML();
    }
    public String getDATA() {
        return DATA;
    }
    public void setSERVICE(String SERVICE) {
        this.SERVICE = SERVICE;
        this.setTagValue("*//name", SERVICE);
        this.createXML();
    }
    public void setSERVICE(int SERVICE) {
        switch (SERVICE) {
            case 0:  { this.SERVICE = "NewPrescription"; break; }
            case 1:  { this.SERVICE = "MakuleraReceptService"; break; }
            case 2:  { this.SERVICE = "NewPrescriptionAnimal"; break; }
            default: { this.SERVICE = "NewPrescription"; break; }
        }
        this.setTagValue("*//name", this.SERVICE);
        this.createXML();
    }
    public String getSERVICE() {
        return SERVICE;
    }
    
    
    
    private String XML =    
                        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:wsg=\"http://pirr.receptpartner.se/pi-wsgeneric-1.3/wsgeneric?WSDL\" xmlns:java=\"java:se.receptpartner.pirr.service\">\n" +
                        "   <soapenv:Header>\n" +
                        "   </soapenv:Header>\n" +
                        "   <soapenv:Body>\n" +
                        "      <wsg:callService>\n" +
                        "         <java:serviceRequest>\n" +
                        "            <java:requestBody>\n" +
                        "               <java:requestDocuments>\n" +
                        "                  <!--1 or more repetitions:-->\n" +
                        "                  <java:RequestDocument>\n" +
                        "                     <java:contentLength>0</java:contentLength>\n" +
                        "                     <java:contentTransferEncoding>base64</java:contentTransferEncoding>\n" +
                        "                     <java:contentType>multipart/signed</java:contentType>\n" +
                        "                     <java:data></java:data>\n" +  // Här ska DATA in
                        "                  </java:RequestDocument>\n" +
                        "               </java:requestDocuments>\n" +
                        "            </java:requestBody>\n" +
                        "            <java:requestHeader>\n" +
                        "               <java:securityDescriptor xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
                        "               <java:serviceDescriptor>\n" +
                        "                  <java:checksum>\n" +
                        "                     <java:type xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
                        "                     <java:value>0</java:value>\n" +
                        "                  </java:checksum>\n" +
                        "                  <java:conversationId xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
                        "                  <java:name>NewPrescription</java:name>\n" + // Här ska SERVICE in                            // MakuleraReceptService // EriNyttReceptNefService //
                        "                  <java:priority xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>\n" +
                        "               </java:serviceDescriptor>\n" +
                        "            </java:requestHeader>\n" +
                        "         </java:serviceRequest>\n" +
                        "      </wsg:callService>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>";



// -----------------------------------------------------------------------------------------------------------------------------------------------------------
// Skapa Ordination via Pirr ---------------------------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------------------------------------------------------
//
//    public static void main(String args[]) {
//        Prescription recept = new Prescription();
//
//        recept.setTodaysDates();
//        recept.setAllUUID();
//
//        recept.setTagValue("*//Recipient", "7350045511119");
//        recept.setPersonnummer("198903082389");
//
//
////        recept.setTagValue("*//MessageRoutingAddress/TestIndicator","2");
////        recept.setTagValue("*//PrescriptionItemDetails/PrescribedMedicinalProduct/InstructionsForUse/UnstructuredInstructionsForUse/UnstructuredDosageAdmin","Dosering enligt anvisning.  Catapresan 50µg/ml samt Hydromorfon  3 mg/ml blandas till en volym av 60 ml. För intrathecalt bruk, infusion via IsoMed pump");
//
//
//
//
//        Wsgeneric PirrHandler = new Wsgeneric();
//
//        // Signera och Base64 encoda receptet
//        PirrHandler.setDATA(PirrHandler.encode(recept.getXML()));
////        PirrHandler.setSERVICE("EriNyttReceptNefService");
//
//        // Gör om XMLSträngen till ett soapMessage
//        SOAPMessage soapMessage = BaseXML.getSoapMessageFromString(PirrHandler.getXML());
//
//        // Skickar anropet
//        SOAPMessage soapResponse = BaseXML.sendPirrSoapRequest(PirrHandler.getSoapEndpointUrl(), soapMessage);
//
//        // Convert the response to XML response objekt
//        SoapResponseXML response = new SoapResponseXML( BaseXML.SoapResponseMsgToString( soapResponse ) );
//
//	// Hämta ut receptsvaret
//        String _base64 = response.getTagValue("*//data");
//
//        System.out.println(PirrHandler.decode(_base64));
//
//       // Konvertera tillbaka till läsbar text
//       SoapResponseXML pirrRecept = new SoapResponseXML(PirrHandler.decode(_base64));
//
//        System.out.println(pirrRecept.getTagValue("*//Description"));
//        System.out.println(pirrRecept.getTagValue("*//PrescriptionSetId"));
//
//    }
//
    
}
