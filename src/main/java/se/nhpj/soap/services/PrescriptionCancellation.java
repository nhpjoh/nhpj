package se.nhpj.soap.services;

import se.nhpj.soap.services.SoapResponseXML;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.nhpj.soap.BaseXML;
import se.nhpj.soap.utils.CurrentDateTime;

/**
 * @author nhpj
 */
public class PrescriptionCancellation extends BaseXML {
    
    public PrescriptionCancellation(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/PrescriptionCancellation.properties");
    }
   
    public PrescriptionCancellation() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/PrescriptionCancellation.properties");
    }

        @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en <XXXXXXXXX> returneras
        Integer retVal=0;
	    retVal = retVal + response.getTagCount("*//faultstring");
        try {
            if (retVal == 0 ) {
                System.out.println("PrescriptionCancellation hämtat OK ");
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

    private String XML =    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                            "<!-- ******************************************************************* -->\n" +
//                            "<!-- Testfall 10 enligt Apotekets testpaket -->\n" +
//                            "<!-- ******************************************************************* -->\n" +
                            "<nefmak:Interchange xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                            "	xsi:schemaLocation=\"https://pirr.receptpartner.se/prescription-1.3/PrescriptionCancellation.xsd\"\n" +
                            "	xmlns:nefmak=\"http://receptpartner.se/nefmak\" xmlns:nefcommon=\"http://receptpartner.se/nefcommon\">\n" +
                            "	<nefmak:MessageVersion>2.0</nefmak:MessageVersion>\n" +
                            "	<nefmak:MessageRoutingAddress>\n" +
                            "		<nefmak:Sender>5603268557</nefmak:Sender>\n" +
                            "		<nefmak:SenderQualifier>14</nefmak:SenderQualifier>\n" +
                            "		<nefmak:SubSender>1234</nefmak:SubSender>\n" +
                            "		<nefmak:SubSenderInfo>\n" +
                            "			<nefmak:SystemName>Melior</nefmak:SystemName>\n" +
                            "			<nefmak:SystemVersion>1.2.3</nefmak:SystemVersion>\n" +
                            "			<nefmak:ModuleVersion>1.2</nefmak:ModuleVersion>\n" +
                            "		</nefmak:SubSenderInfo>\n" +
                            "		<nefmak:InterchangeDate>2013-04-16</nefmak:InterchangeDate>\n" +
                            "		<nefmak:InterchangeTime>16:16:10</nefmak:InterchangeTime>\n" +
                            "		<nefmak:InterchangeRef idtype=\"UUID\">99876200-77d8-13e0-a1f0-0800209c1026</nefmak:InterchangeRef>\n" +
                            "	</nefmak:MessageRoutingAddress>\n" +
                            "	<nefmak:PrescriptionCancellationMessage>\n" +
                            "		<nefmak:GeneralCancellationMessageInformation>\n" +
                            "			<nefmak:IdOfMessageByOriginator>\n" +
                            "				<nefmak:IdOfMessageBySender idtype=\"UUID\">99876200-77d8-13e0-a1f0-0800209c1026</nefmak:IdOfMessageBySender>\n" +
                            "				<nefmak:IssueDateAndTimeOfMessage>2013-04-16T16:16:10</nefmak:IssueDateAndTimeOfMessage>\n" +
                            "			</nefmak:IdOfMessageByOriginator>\n" +
                            "		</nefmak:GeneralCancellationMessageInformation>\n" +
                            "		<nefmak:MessageSender>\n" +
                            "			<nefmak:HealthcareAgentInContextId>\n" +
                            "				<nefmak:Value>CNR</nefmak:Value>\n" +
                            "			</nefmak:HealthcareAgentInContextId>\n" +
                            "			<nefmak:HealthcareAgent>\n" +
                            "				<nefmak:HealthcareAgentId>\n" +
                            "					<nefcommon:IdScheme>VEN</nefcommon:IdScheme>\n" +
                            "					<nefcommon:Value>110301247</nefcommon:Value>\n" +
                            "				</nefmak:HealthcareAgentId>\n" +
                            "				<nefmak:HealthcareAgentId>\n" +
                            "					<nefcommon:IdScheme>UTF</nefcommon:IdScheme>\n" +
                            "					<nefcommon:Value>9000001</nefcommon:Value> <!-- 9000001-->\n" +
                            "				</nefmak:HealthcareAgentId>\n" +
                            "				<nefmak:HealthcareParty>\n" +
                            "					<nefmak:HealthcarePerson>\n" +
                            "						<nefmak:Name>Sten Frisk</nefmak:Name> <!--Sten Frisk-->\n" +
                            "					</nefmak:HealthcarePerson>\n" +
                            "				</nefmak:HealthcareParty>\n" +
                            "			</nefmak:HealthcareAgent>\n" +
                            "		</nefmak:MessageSender>\n" +
                            "		<nefmak:SubjectOfCare>\n" +
                            "			<nefmak:TypeOfSubjectOfCare>1</nefmak:TypeOfSubjectOfCare>\n" +
                            "			<nefmak:PatientMatchingInfo>\n" +
                            "				<nefmak:PersonNameDetails>\n" +
                            "					<nefmak:StructuredPersonName>\n" +
                            "						<nefmak:FamilyName>Boman</nefmak:FamilyName>\n" +
                            "						<nefmak:FirstGivenName>Oscar</nefmak:FirstGivenName>\n" +
                            "					</nefmak:StructuredPersonName>\n" +
                            "				</nefmak:PersonNameDetails>\n" +
                            "				<nefmak:PatientId>\n" +
                            "					<nefmak:IdScheme>PNR</nefmak:IdScheme>\n" +
                            "					<nefmak:IdValue>195909263070123123</nefmak:IdValue> <!--190609119821-->\n" +
                            "				</nefmak:PatientId>\n" +
                            "			</nefmak:PatientMatchingInfo>\n" +
                            "		</nefmak:SubjectOfCare>\n" +
                            "		<nefmak:CancellationSet>\n" +
                            "			<nefmak:Cancellation>\n" +
                            "				<nefmak:PrescriptionSetId idtype=\"UUID\">99976200-77d9-14e0-a1f0-0800209c1041</nefmak:PrescriptionSetId>\n" +
                            "				<nefmak:PrescriptionItemDetails>\n" +
                            "					<nefmak:PrescriptionItemId>1</nefmak:PrescriptionItemId>\n" +
                            "					<nefmak:Item>\n" +
                            "						<nefmak:CauseCode>04</nefmak:CauseCode>\n" +
                            "						<nefmak:Cause>orsakskod 04</nefmak:Cause>\n" +
                            "						<nefmak:Consent>\n" +
                            "							<nefmak:Value>1</nefmak:Value>\n" +
                            "						</nefmak:Consent>\n" +
                            "					</nefmak:Item>\n" +
                            "				</nefmak:PrescriptionItemDetails>\n" +
                            "			</nefmak:Cancellation>\n" +
                            "		</nefmak:CancellationSet>\n" +
                            "	</nefmak:PrescriptionCancellationMessage>\n" +
                            "</nefmak:Interchange>";
    
    

    public void setPersonnummer( String personnummer) {
        this.setTagValue("*//PatientId/IdValue", personnummer);
    }
    public void setTodaysDates() {
        this.setTagValue("*//InterchangeDate",            CurrentDateTime.getTodaysDate());
        this.setTagValue("*//InterchangeTime",            CurrentDateTime.getTimeNow());
        this.setTagValue("*//IssueDateAndTimeOfMessage",  CurrentDateTime.getTLong());
    }
    public void setAllUUID() {
        String uuid = BaseXML.getUUID();
        this.setTagValue("*//MessageRoutingAddress/InterchangeRef", uuid);
        this.setTagValue("*//GeneralCancellationMessageInformation/IdOfMessageByOriginator/IdOfMessageBySender", uuid);
        this.setTagValue("*//PrescriptionSetId", uuid);
    }

    
//    public static void main(String args[]) {
//        String PrescriptionSetId = "1234-12324-222222-0000000-0000";
//        PrescriptionCancellation receptCancel = new PrescriptionCancellation();
//        receptCancel.setStandardDefaultValues();
//
//        receptCancel.setPersonnummer("198903082389");
//        receptCancel.setTodaysDates();
//        receptCancel.setTagValue("*//FirstGivenName", "Kirtap");
//        receptCancel.setTagValue("*//FamilyName", "Nossnahoj");
//        receptCancel.setAllUUID();
//        receptCancel.setTagValue("*//MessageSender/HealthcareAgent/HealthcareAgentId[1]/Value", "0900000000002");
//        receptCancel.setTagValue("*//MessageSender/HealthcareAgent/HealthcareAgentId[2]/Value", "8880999");
//        receptCancel.setTagValue("*//MessageSender/HealthcareAgent/HealthcareParty/HealthcarePerson/Name", "Graaf" + " " + "Walter");
//
//        receptCancel.setTagValue("*//Cancellation/PrescriptionSetId",PrescriptionSetId);
//
//
//        System.out.println(receptCancel.getXML());
//    }

}
