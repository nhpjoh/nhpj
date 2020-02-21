package se.nhpj.soap.services;

import se.nhpj.soap.BaseXML;
import se.nhpj.soap.utils.CurrentDateTime;

/**
 *
 * @author nhpj
 */
public class Prescription extends BaseXML {

    public Prescription(String xml) { 
        super(xml);
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.createDocument();
        super.setStandardDefaultFileName("/soap/services/Prescription.properties");
    }
   
    public Prescription() { 
        super.setXML(XML);
        super.createDocument();
        super.setSoapEndpointUrl(soapEndpointUrl);
        super.setStandardDefaultFileName("/soap/services/Prescription.properties");
    }

    @Override
    public Boolean checkResponse(SoapResponseXML response) {
        // Kontrolera att en xxx returneras
        return null;
    }

    private String soapEndpointUrl = "<serviceEndpoint>";

    private String XML =    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                            "<!-- ******************************************************************* -->\n" +
//                            "<!-- Testfall 10 enligt Apotekets testpaket                              -->\n" +
//                            "<!-- ******************************************************************* -->\n\n" +
                            "<Interchange xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " + 
                            "   xsi:noNamespaceSchemaLocation=\"https://pirr.receptpartner.se/prescription-1.3/Prescription.xsd\">\n" +
                            "	<MessageRoutingAddress>\n" +
                            "		<Sender>5603268557</Sender>\n" +
                            "		<SenderQualifier>14</SenderQualifier>\n" +
                            "		<SubSender>1234</SubSender>\n" +
                            "		<SubSenderInfo>\n" +
                            "			<SystemName>Melior</SystemName>\n" +
                            "			<SystemVersion>1.2.3</SystemVersion>\n" +
                            "			<ModuleVersion>1.2</ModuleVersion>\n" +
                            "		</SubSenderInfo>\n" +
                            "		<Recipient>7350045511119</Recipient>\n" +
                            "		<RecipientQualifier>14</RecipientQualifier>\n" +
                            "		<InterchangeDate>2017-12-15</InterchangeDate>\n" +
                            "		<InterchangeTime>16:10:10</InterchangeTime>\n" +
                            "		<InterchangeRef idtype=\"UUID\">99976200-77d9-14e0-a1f0-0800209c1041</InterchangeRef>\n" +
                            "		<MessageType receipt=\"no\">e-recept</MessageType>\n" +
                            "		<MessageVersion>2.0</MessageVersion>\n" +
                            "		<TestIndicator>1</TestIndicator>\n" +
                            "	</MessageRoutingAddress>\n" +
                            "	<NewPrescriptionMessage>\n" +
                            "		<PrescriptionMessage>\n" +
                            "			<GeneralPrescriptionMessageInformation>\n" +
                            "				<IdOfMessageByOriginator>\n" +
                            "                  <IdOfMessageBySender idtype=\"UUID\">99976200-77d9-14e0-a1f0-0800209c1041</IdOfMessageBySender>\n" +
                            "				   <IssueDateAndTimeOfMessage>2017-12-15T16:10:10</IssueDateAndTimeOfMessage>\n" +
                            "				</IdOfMessageByOriginator>\n" +
                            "				<UrgencyOfMessage>N</UrgencyOfMessage>\n" +
                            "				<MessageReceiptAcknowledgementRequest>AL</MessageReceiptAcknowledgementRequest>\n" +
                            "			</GeneralPrescriptionMessageInformation>\n" +
                            "			<FunctionOfMessage>1</FunctionOfMessage>\n" +
                            "			<MessageSender>\n" +
                            "				<HealthcareAgentInContextId>\n" +
                            "					<Value>SND</Value>\n" +
                            "				</HealthcareAgentInContextId>\n" +
                            "				<HealthcareAgent>\n" +
                            "					<HealthcareAgentId>\n" +
                            "						<IdScheme>VEN</IdScheme>\n" +
                            "						<Value>4000000000000</Value>\n" +
                            "					</HealthcareAgentId>\n" +
                            "					<HealthcareAgentId>\n" +
                            "						<IdScheme>UTF</IdScheme>\n" +
                            "						<Value>9000027</Value> <!--1111111-->\n" +
                            "					</HealthcareAgentId>\n" +
                            "					<HealthcareParty>\n" +
                            "						<HealthcarePerson>\n" +
                            "							<Name>Angelica Frost</Name> <!--Frost, Angelica-->\n" +
                            "							<Qualification>LAK</Qualification>\n" +
                            "						</HealthcarePerson>\n" +
                            "						<Address>\n" +
                            "							<PostalCode>818 88</PostalCode>\n" +
                            "							<StructuredAddress>\n" +
                            "								<NumberOrNameOfHouse>Vårdcentralen</NumberOrNameOfHouse>\n" +
                            "								<StreetName>Storgatan 88</StreetName>\n" +
                            "								<City>Storstad</City>\n" +
                            "							</StructuredAddress>\n" +
                            "						</Address>\n" +
                            "						<Telecommunication>08-22 22 22</Telecommunication>\n" +
                            "					</HealthcareParty>\n" +
                            "				</HealthcareAgent>\n" +
                            "			</MessageSender>\n" +
                            "			<DesignatedMessageReceiver>\n" +
                            "				<HealthcareAgentInContextId>\n" +
                            "					<Value>RCV</Value>\n" +
                            "				</HealthcareAgentInContextId>\n" +
                            "				<HealthcareAgent>\n" +
                            "					<HealthcareAgentId>\n" +
                            "						<IdScheme>EAN</IdScheme>\n" +
                            "						<Value>7350045511119</Value>\n" +
                            "					</HealthcareAgentId>\n" +
                            "				</HealthcareAgent>\n" +
                            "			</DesignatedMessageReceiver>\n" +
                            "			<Prescriber>\n" +
                            "				<HealthcareAgentInContextId>\n" +
                            "					<Value>PRESCRIBER</Value>\n" +
                            "				</HealthcareAgentInContextId>\n" +
                            "				<HealthcareAgent>\n" +
                            "					<HealthcareAgentId>\n" +
                            "						<IdScheme>VEN</IdScheme>\n" +
                            "						<Value>4000000000000</Value>\n" +
                            "					</HealthcareAgentId>\n" +
                            "					<HealthcareAgentId>\n" +
                            "						<IdScheme>UTF</IdScheme>\n" +
//                            "						<Value>9000001</Value> <!--1111111-->\n" +
                            "						<Value>9000027</Value> <!--1111111-->\n" +
                            "					</HealthcareAgentId>\n" +
                            "					<HealthcareParty>\n" +
                            "						<HealthcarePerson>\n" +
                            "							<Name>Olivia Läkare</Name> <!--Frost, Angelica-->\n" +
//                            "							<Qualification>ATL</Qualification>\n" +
                            "							<Qualification>LAK</Qualification>\n" +
                            "						</HealthcarePerson>\n" +
                            "						<Address>\n" +
                            "							<PostalCode>818 88</PostalCode>\n" +
                            "							<StructuredAddress>\n" +
                            "								<NumberOrNameOfHouse>Vårdcentralen</NumberOrNameOfHouse>\n" +
                            "								<StreetName>smågatan 88</StreetName>\n" +
                            "								<City>Storstad</City>\n" +
                            "							</StructuredAddress>\n" +
                            "						</Address>\n" +
                            "						<Telecommunication>08-22 22 22</Telecommunication>\n" +
                            "					</HealthcareParty>\n" +
                            "				</HealthcareAgent>\n" +
                            "			</Prescriber>\n" +
                            "		</PrescriptionMessage>\n" +
                            "		<SubjectOfCare>\n" +
                            "			<TypeOfSubjectOfCare>1</TypeOfSubjectOfCare>\n" +
                            "			<PatientMatchingInfo>\n" +
                            "				<PersonNameDetails>\n" +
                            "					<StructuredPersonName>\n" +
                            "						<FamilyName>Boman</FamilyName>\n" +
                            "						<FirstGivenName>Oscar</FirstGivenName>\n" +
                            "					</StructuredPersonName>\n" +
                            "				</PersonNameDetails>\n" +
                            "				<PatientId>\n" +
                            "					<IdScheme>PNR</IdScheme> <!--FDA-->\n" +
                            "					<IdValue>198501169885</IdValue> <!--190609119821-->\n" +
                            "				</PatientId>\n" +
                            "				<Address>\n" +
                            "					<PostalCode>188 88</PostalCode>\n" +
                            "					<StructuredAddress>\n" +
                            "						<StreetName>Testgatan 1</StreetName>\n" +
                            "						<City>Storstad</City>\n" +
                            "					</StructuredAddress>\n" +
                            "				</Address>\n" +
                            "				<Sex>1</Sex>\n" +
                            "			</PatientMatchingInfo>\n" +
                            "		</SubjectOfCare>\n" +
                            "		<PrescriptionSet>\n" +
                            "			<PrescriptionSetId idtype=\"UUID\">99976200-77d9-14e0-a1f0-0800209c1041</PrescriptionSetId>\n" +
                            "			<PrescriptionSetIssueTime>2017-12-15T10:16</PrescriptionSetIssueTime>\n" +
                            "			<PrescriptionItemDetails>\n" +
                            "				<PrescriptionItemId>1-1</PrescriptionItemId>\n" +
                            "				<!--LatestRequestedTimeForDispensing>2015-06-05</LatestRequestedTimeForDispensing-->\n" +
                            "				<!--PrescriptionItemAuthTime>2015-02-01</PrescriptionItemAuthTime-->\n" +
                            "				<PaymentDetails>\n" +
                            "					<ServiceAgreementType>STA</ServiceAgreementType>\n" +
                            "					<PaymentConditions>\n" +
                            "						<CareCoverageType>U</CareCoverageType>\n" +
                            "					</PaymentConditions>\n" +
                            "				</PaymentDetails>\n" +
                            "				<PrescribedMedicinalProduct>\n" +
                            "					<MedicinalProduct>\n" +
                            "						<Status>1</Status>\n" +
                            "						<ProductType>1</ProductType>\n" +
                            "						<ManufacturedProductId>\n" +
//                            "							<ProductId>20030307100749</ProductId>\n" +
                            "							<ProductId></ProductId>\n" +
                            "							<IdType>NPLPACK</IdType>\n" +
                            "						</ManufacturedProductId>\n" +
                            "						<ManufacturedProductId>\n" +
//                            "							<ProductId>20030307000193</ProductId>\n" +
                            "							<ProductId></ProductId>\n" +
                            "							<IdType>NPLID</IdType>\n" +
                            "						</ManufacturedProductId>\n" +
                            "						<Package>\n" +
                            "							<NumberOfPackages>1</NumberOfPackages>\n" +
                            "						</Package>\n" +
                            "					</MedicinalProduct>\n" +
                            "					<InstructionsForUse>\n" +
                            "						<UnstructuredInstructionsForUse>\n" +
                            "							<TypeOfInstruction>1</TypeOfInstruction>\n" +
                            "							<UnstructuredDosageAdmin>Defult dostext</UnstructuredDosageAdmin>\n" +
                            "						</UnstructuredInstructionsForUse>\n" +
                            "						<LanguageOfLabel>SV</LanguageOfLabel>\n" +
                            "					</InstructionsForUse>\n" +
                            "					<RepeatDispensingInstructions>\n" +
                            "						<DispensingRepeatNumber>1</DispensingRepeatNumber>\n" +
//                            "						<TimeInterval> \n" +
//                            "							<TimeValue>50</TimeValue> \n" +
//                            "							<TimeUnit>V</TimeUnit> \n" +
//                            "						</TimeInterval>\n" +
                            "					</RepeatDispensingInstructions>\n" +
                            "				</PrescribedMedicinalProduct>\n" +
                            "			</PrescriptionItemDetails>\n" +
                            "		</PrescriptionSet>\n" +
                            "	</NewPrescriptionMessage>\n" +
                            "</Interchange>";
    
    public void setAllUUID() {
        String uuid = BaseXML.getUUID();
        this.setTagValue("*//InterchangeRef", uuid);
        this.setTagValue("*//IdOfMessageBySender", uuid);
        this.setTagValue("*//PrescriptionSetId", uuid);
    }
 
    public void setTodaysDates() {
        this.setTagValue("*//InterchangeDate",            CurrentDateTime.getTodaysDate());
        this.setTagValue("*//InterchangeTime",            CurrentDateTime.getTimeNow());
        this.setTagValue("*//IssueDateAndTimeOfMessage",  CurrentDateTime.getTLong());
        this.setTagValue("*//PrescriptionSetIssueTime",   CurrentDateTime.getTShort());
    }
    
    public void setPersonnummer( String personnummer) {
        this.setTagValue("*//PatientId/IdValue",          personnummer);
    }
    
    public void setAntalUttag(String antalUttag) {
        this.setTagValue("*//DispensingRepeatNumber", antalUttag);
    }

    public void setNumberOfPackages(String NumberOfPackages) {
        this.setTagValue("*//NumberOfPackages", NumberOfPackages);
    }
    
    public void setNplPackId(String id) {
        this.setTagValue("*//ManufacturedProductId[1]/ProductId", id);
    }
    
    public void setNplId(String id) {
        this.setTagValue("*//ManufacturedProductId[2]/ProductId", id);
    }
    public void setInstructionsForUse(String value) {
        this.setTagValue("*//UnstructuredDosageAdmin", value);
    }

}
