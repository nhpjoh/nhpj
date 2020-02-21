package se.ehm.sol.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.services.SoapResponseXML;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SkapaProduktionsinformationDosaktor extends BaseXML {

    public SkapaProduktionsinformationDosaktor(String xml) {
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/sol/soap/services/SkapaProduktionsinformationDosaktor.properties");
    }

    public SkapaProduktionsinformationDosaktor() {
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/sol/soap/services/SkapaProduktionsinformationDosaktor.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;

//        if (!response.getTagValue("*//resultatkod").contains("INFO")) {retVal=retVal+1;}
        retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println( "SkapaProduktionsinformationDosaktor: " + response.getTagValue("*//meddelandetext") );
                return true;
            } else {
                System.err.println("Request: " + this.getXML());
                System.err.println("\nResponse: " + response.getXML());
                System.err.println();
                throw new Exception("<<<<< " + response.getTagValue("*//meddelandetext") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "https://prestanda/sol-service-provider/SkapaProduktionsinformationDosaktorService/V1";

    private String XML =   "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                            "   <soapenv:Header>\n" +
                            "   </soapenv:Header>\n" +
                            "   <soapenv:Body>\n" +
                            "      <skap:skapaProduktionsinformation xmlns:skap=\"http://skapaproduktionsinformation.service.sol.apotekensservice.se:1\">\n" +
                            "         <arg0>\n" +
                            "            <produktionsinformation>\n" +
                            "               <dosapotek></dosapotek>\n" +
                            "               <dosapotekid></dosapotekid>\n" +
                            "               <stopptidbestallning></stopptidbestallning>\n" +
                            "               <stopptidordination></stopptidordination>\n" +
                            "               <forstadosdag></forstadosdag>\n" +
                            "               <dosvecka></dosvecka>\n" +
                            "               <standardschema>\n" +
                            "                  <periodlangd></periodlangd>\n" +
                            "                  <intagstillfalle>\n" +
                            "                     <intagstillfalleKl></intagstillfalleKl>\n" +
                            "                     <dagIPeriod></dagIPeriod>\n" +
                            "                  </intagstillfalle>\n" +
                            "               </standardschema>\n" +
                            "               <dosmottagareid></dosmottagareid>\n" +
                            "               <dosmottagarenamn></dosmottagarenamn>\n" +
                            "            </produktionsinformation>\n" +
                            "            <behorighetsinformationDosaktor>\n" +
                            "               <fornamn></fornamn>\n" +
                            "               <efternamn></efternamn>\n" +
                            "               <legitimationskod></legitimationskod>\n" +
                            "               <yrkesroll></yrkesroll>\n" +
                            "               <dosapotekid></dosapotekid>\n" +
                            "            </behorighetsinformationDosaktor>\n" +
                            "         </arg0>\n" +
                            "      </skap:skapaProduktionsinformation>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>";


}