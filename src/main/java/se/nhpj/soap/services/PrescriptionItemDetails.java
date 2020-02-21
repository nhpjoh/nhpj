package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author nhpj
 */
//public class PrescriptionItemDetails extends BaseXML {
public class PrescriptionItemDetails extends Prescription {
    
    public PrescriptionItemDetails(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/PrescriptionItemDetails.properties");
    }
   
    public PrescriptionItemDetails() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/PrescriptionItemDetails.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en PrescriptionItemDetails returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("PrescriptionItemDetails hämtat OK ");
                return true;
            } else {
                    System.err.println("Request: " + this.getXML());
                    System.err.println("\nResponse: " + response.getXML());
                    System.err.println();
                    throw new Exception("<<<<< " + response.getTagValue("*//faultstring") + " >>>>>");
            }
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "Assert 'Kontroll av response'", ex);
        }
        return null;
    }

    private String soapEndpointUrl = "<serviceEndpoint>";

    private String XML =    "<PrescriptionItemDetails>\n" +
                            "<PrescriptionItemId>1-1</PrescriptionItemId>\n" +
                            "<!--LatestRequestedTimeForDispensing>2015-06-05</LatestRequestedTimeForDispensing-->\n" +
                            "<!--PrescriptionItemAuthTime>2015-02-01</PrescriptionItemAuthTime-->\n" +
                            "<PaymentDetails>\n" +
                            "   <ServiceAgreementType>STA</ServiceAgreementType>\n" +
                            "   <PaymentConditions>\n" +
                            "       <CareCoverageType>U</CareCoverageType>\n" +
                            "   </PaymentConditions>\n" +
                            "</PaymentDetails>\n" +
                            "<PrescribedMedicinalProduct>\n" +
                            "   <MedicinalProduct>\n" +
                            "       <Status>1</Status>\n" +
                            "       <ProductType>1</ProductType>\n" +
                            "       <ManufacturedProductId>\n" +
                            "           <ProductId></ProductId>\n" +
                            "           <IdType>NPLPACK</IdType>\n" +
                            "       </ManufacturedProductId>\n" +
                            "       <ManufacturedProductId>\n" +
                            "           <ProductId></ProductId>\n" +
                            "           <IdType>NPLID</IdType>\n" +
                            "       </ManufacturedProductId>\n" +
                            "       <Package>\n" +
                            "           <NumberOfPackages>1</NumberOfPackages>\n" +
                            "       </Package>\n" +
                            "   </MedicinalProduct>\n" +
                            "   <InstructionsForUse>\n" +
                            "       <UnstructuredInstructionsForUse>\n" +
                            "           <TypeOfInstruction>1</TypeOfInstruction>\n" +
                            "           <UnstructuredDosageAdmin>Defult dostext</UnstructuredDosageAdmin>\n" +
                            "       </UnstructuredInstructionsForUse>\n" +
                            "       <LanguageOfLabel>SV</LanguageOfLabel>\n" +
                            "   </InstructionsForUse>\n" +
                            "       <RepeatDispensingInstructions>\n" +
                            "           <DispensingRepeatNumber>1</DispensingRepeatNumber>\n" +
//                            "             <TimeInterval>\n" +
//                            "                 <TimeValue>50</TimeValue>\n" +
//                            "                 <TimeUnit>V</TimeUnit>\n" +
//                            "             </TimeInterval>\n" +
                            "       </RepeatDispensingInstructions>\n" +
                            "   </PrescribedMedicinalProduct>\n" +
                            "</PrescriptionItemDetails>\n";

    public void setPrescriptionItemId(String value) {
        this.setTagValue("*//PrescriptionItemId", value);
    }

    public void setNplPackId(String id) {
        this.setTagValue("*//ManufacturedProductId[1]/ProductId", id);
    }
    
    public void setNplId(String id) {
        this.setTagValue("*//ManufacturedProductId[2]/ProductId", id);
    }
}
