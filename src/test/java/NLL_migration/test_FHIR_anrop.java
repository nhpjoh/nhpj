package NLL_migration;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.Test;
import se.ehm.testdata.*;
import se.nhpj.LoadRunner.lr;
import se.nhpj.rest.AccessTokenHandler;
import se.nhpj.rest.Base64Converter;
import se.nhpj.rest.BaseRest;
import se.nhpj.rest.RestCall;
import se.nhpj.soap.Tickets;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.Skatteverket;
import se.nhpj.testdata.Transaction;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public class test_FHIR_anrop {
//    private String endpoint = "https://nll-fhir-server-nll-ext18-prj1.ext.ecp.receptpartner.se"; // Externa adressen
//    private String endpoint = "https://nll-fhir-server-s15-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint = "https://nll-fhir-server-s16-test1-deploy2.test.ecp.systest.receptpartner.se";
//    private String endpoint = "https://nll-fhir-server-s17-test1-deploy2.test.ecp.systest.receptpartner.se";
    private String endpoint = "https://nll-fhir-server-s17-test1-deploy6.test.ecp.systest.receptpartner.se";

    @Test
    public void get_RelatedPerson() {

        String response;
        BaseRest br = new BaseRest();

        String vars = "identifier=urn:oid:1.2.752.129.2.1.3.1|198405149280&patient.identifier=urn:oid:1.2.752.129.2.1.3.1|200510052392"; // S10
        vars = vars.replace("|", "%7C");

        response = br.getSSL(endpoint + "/fhir-server/fhir/RelatedPerson?" + vars);

        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(response));
    }

    @Test
    public void get_Patient() {

        String response;
        BaseRest br = new BaseRest();

//        response = br.getSSL(endpoint + "/fhir-server/fhir/Patient/?format=json&identifier=190110149812");
//        response = br.getSSL(endpoint + "/fhir-server/fhir/Patient/?_format=json&identifier=189002019819");
        response = br.getSSL(endpoint + "/fhir-server/fhir/Patient/?_format=json&identifier=193104219096");

        System.out.println("\n" +se.nhpj.rest.RestFormatter.prettyPrint(response));
        System.out.println("\n" +br.getHeaders().toString());

    }


    // Provenance // ???
    @Test
    public void get_Provenance() {

        //"4CDA7016-5E59-4C0E-BDC4-AF40899E8EB8"
        String forskrivning_ref = "3d4ef756-46f3-42ab-a062-03f6c80c705d";//"43867d50-c1cc-4a45-8dcd-07a7a6c469b4";
        String response;
        BaseRest br = new BaseRest();

        // Skapa en ticket
        Forskrivare forskrivare = ForskrivareHandler.getRndForskrivare("INT28");
        System.out.println("Förskrivare: " + forskrivare);
        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode(forskrivare.getForskrivarkod());
        ticket.setHealthcareProfessionalLicense("LK");

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(ticket));

        response = br.getSSL(endpoint + "/fhir-server/fhir/Provenance?target=MedicationRequest/" + forskrivning_ref);
        System.out.println(response);
    }


    // MedicationDispense // Uttag
    @Test
    public void post_MedicationDispense() {
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

        String X_Provenance = "{'resourceType' : 'Provenance',   " +
                              " 'meta' : { " +
                              "   'profile' : [ " +
                              "     'http://electronichealth.se/fhir/StructureDefinition/NLLProvenance' " +
                              "    ] " +
                              " }," +
                              " 'contained' : [ { " +
                              "   'resourceType' : 'Practitioner'," +
                              "   'id' : 'thePerformer'," +
                              "   'meta' : { " +
                              "     'profile' : [ 'http://electronichealth.se/fhir/StructureDefinition/NLLPractitioner' ] " +
                              "   }," +
                              " 'extension': [ { " +
                              "   'url' : 'http://electronichealth.se/fhir/StructureDefinition/NLLWorkplaceName'," +
                              "   'valueString' : '12334567' " +
                              "  } ]," +
                              " 'identifier' : [ { " +
                              "   'system' : 'http://electronichealth.se/identifier/legitimationskod'," +
                              "   'value' : 920007 " +
                              "  } ]," +
                              " 'name' : [ { 'given' : [ 'Inga-Lill' ]," +
                              "              'family': 'Ingesson' } ]," +
                              " 'qualification' : [ { " +
                              "   'code' : { " +
                              "     'coding' : [ { " +
                              "       'system' : 'http://electronichealth.se/fhir/ValueSet/profession-code', " +
                              "       'code' : 'AP' " +
                              "     } ] " +
                              "   } } ] } ]," +
                              " 'location' : { " +
                              "   'reference' : 'Location/7350045511999' " +
                              "  }, " +
                              " 'agent' : [ { " +
                              "   'type' : { " +
                              "     'coding' : [ { " +
                              "       'code' : 'author' " +
                              "     } ] " +
                              "   }, " +
                              "   'who' : { 'reference' : '#thePerformer' } " +
                              "   } " +
                              "  ] " +
                              "}";

        X_Provenance = X_Provenance.replace("'", "\"");
        br.addHeader("X-Provenance", X_Provenance);

        String json = "{" +
                "  'resourceType': 'MedicationDispense'," +
                "  'meta': {" +
                "    'profile': [" +
                "      'http://electronichealth.se/fhir/StructureDefinition/NLLMedicationDispense'" +
                "    ]" +
                "  }," +
                "  'extension': [" +
                "    {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLBenefitChoiceCode'," +
                "      'valueCoding': {" +
                "        'system': 'http://electronichealth.se/fhir/StructureDefinition/NLLBenefitChoiceCode'," +
                "        'code': 'no-benefit'," +
                "        'display': 'Utan förmån /Utom förmånen'" +
                "      }" +
                "    }," +
                "    {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLNumberOfPackagesDispensed'," +
                "      'valuePositiveInt': 1" +
                "    }," +
                "     {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLDeductedQuantity'," +
                "      'valueQuantity':{" +
                "        'value': 1" +
                "      }" +
                "    }," +
                "      {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/Version'," +
                "      'valueInteger': '1'" +
                "    }" +
                "  ]," +
                "  'identifier': [" +
                "    {" +
                "      'system': 'http://electronichealth.se/fhir/NamingSystem/actorDispatchId'," +
                "      'value': '123456881'" +
                "    }," +
                "    {" +
                "      'system': 'http://electronichealth.se/fhir/NamingSystem/dispatchId'," +
                "      'value': '12345681'" +
                "    }" +
                "  ]," +
                "  'medicationCodeableConcept': {" +
                "    'coding': [" +
                "        {" +
                "            'system': 'http://medication.ehm.se/nplpackid'," +
                "            'code': '19990924100093'" +
                "          }," +
                "          {" +
                "            'system': 'http://medication.ehm.se/nplid'," +
                "            'code': '19990924000041'" +
                "          }" +
                "    ]" +
                "  }," +
                "  'location': {" +
                "    'reference': 'Location/7350045511119'" +
                "  }," +
                "  'authorizingPrescription': [" +
                "    {" +
                "      'reference': 'e7f6b355-ae56-46d5-a5c2-699fd5eed8ef'" +
                "    }" +
                "  ]," +
                "  'quantity': {" +
                "    'value': 10.0" +
                "  }," +
                "  'whenHandedOver': '2019-10-30T10:11:54'," +
                "  'note': [" +
                "    {" +
                "      'text': 'Ok exp'" +
                "    }" +
                "  ]," +
                "  'substitution':{" +
                "    'wasSubstituted': false" +
                "  }" +
                "}";
        
        json = json.replace("'", "\"");
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint + "/fhir-server/fhir/MedicationDispense", json);

        System.out.println(response);
        System.out.println("\n\n" + br.getHeaders());

    }
    
    // MedicationRequest // förskrivning
    @Test
    public void post_MedicationRequest_S13() {
        String response;
        BaseRest br = new BaseRest();
        String uuid_Practitioner = getPractitionerUUID("8880999");
        String uuid_Patient = getPatientUUID("189002019819");


        String json = "{" +
                "    'resourceType': 'MedicationRequest'," +
                "    'meta': {" +
                "        'profile': [" +
                "            'https://fhir-profiles.ehalsomyndigheten.se/MedicationRequest'" +
                "        ]" +
                "    }," +
                "    'contained': [" +
                "        {" +
                "          'resourceType': 'Medication'," +
                "          'id': 'theMedicine'," +
                "          'code': {" +
                "            'coding': [" +
                "              {" +
                "                'system': 'http://medication.ehm.se/nplpackid'," +
                "                'code': '20100825100139'" +
                "              }" +
                "            ]" +
                "          }" +
                "        }," +
                "        {" +
                "            'resourceType': 'Organization'," +
                "            'id': 'thePharmacy'," +
                "              'identifier': [" +
                "                {" +
                "                  'value': '7350046611966'" +
                "                }" +
                "              ]" +
                "        }" +
                "      ]," +
                "    'status': 'active'," +
                "    'intent': 'plan'," +
                "     'medicationReference': {" +
                "        'reference': '#theMedicine'" +
                "    }," +
                "    'subject': {" +
                "    'reference': 'Patient/"+uuid_Patient+"'" +
                "  }," +
                "  'requester': {" +
                "    'reference': 'Practitioner/"+uuid_Practitioner+"'" +
                "  }," +
                "  'reasonCode': [" +
                "    {" +
                "      'coding': [" +
                "        {" +
                "          'system': 'http://snomed.info/sct'," +
                "          'code': '226013008'," +
                "          'display': 'Irregular pain'" +
                "        }" +
                "      ]," +
                "      'text': 'Irregular pain'" +
                "    }," +
                "    {" +
                "      'coding': [" +
                "        {" +
                "          'system': 'http://snomed.info/sct'," +
                "          'code': '0222'," +
                "          'display': 'Medical reason'" +
                "        }" +
                "      ]," +
                "      'text': 'Medical reason'" +
                "    }" +
                "  ]," +
                "    'dispenseRequest': {" +
                "        'extension' : [" +
                "            {" +
                "                'url': 'http://natmedlist.se/fhir/StructureDefinition/nll-workplacecode'," +
                "                'valueCode' : '123'" +
                "            }," +
                "              {" +
                "            'url' : 'http://ehalsomyndigheten.se/fhir/StructureDefinition/DispenseRequest#nllPatientPharmaceuticalBenefitsAct'," +
                "            'valueBoolean' : 'true'" +
                "         }," +
                "         {" +
                "            'url' : 'http://ehalsomyndigheten.se/fhir/StructureDefinition/DispenseRequest#nllConditionsPharmaceuticalBenefitsAct'," +
                "            'valueBoolean' : 'true'" +
                "         }" +
                "        ]," +
                "    'validityPeriod': {" +
                "      'start': '2019-07-22'," +
                "      'end': '2020-07-22'" +
                "    }," +
                "    'numberOfRepeatsAllowed': 1," +
                "    'quantity': {" +
                "      'value': 6," +
                "      'unit': 'Package'," +
                "      'system': 'http://unitsofmeasure.org'," +
                "      'code': '{Package}'" +
                "    }," +
                "    'expectedSupplyDuration': {" +
                "      'value': 5," +
                "      'unit': 'days'," +
                "      'system': 'http://unitsofmeasure.org'," +
                "      'code': 'd'" +
                "    }," +
//                "    'dispenseInterval': {" +
//                "      'value': 2," +
//                "      'system': 'http://unitsofmeasure.org'," +
//                "      'unit': 'day'" +
//                "    }," +
                "       'performer': {" +
                "            'reference': '#thePharmacy'" +
                "        }" +
                "    }," +
                "    'dosageInstruction': [" +
                "        {" +
                "          'extension': [" +
                "            {" +
                "              'url': 'http://natmedlist.se/fhir/StructureDefinition/nll-observandum-dose'," +
                "              'valueBoolean': true" +
                "            }" +
                "          ]," +
                "          'text': 'Dosinstruktioner 2 tabletter per dag tills smärta försvinner'," +
                "          'doseAndRate': [" +
                "            {" +
                "              'type': {" +
                "                'coding': [" +
                "                  {" +
                "                    'system': 'http://terminology.hl7.org/CodeSystem/dose-rate-type'," +
                "                    'code': 'ordered'," +
                "                    'display': 'Ordered'" +
                "                  }" +
                "                ]" +
                "              }," +
                "              'doseQuantity': {" +
                "                'value': 5," +
                "                'unit': 'dos'," +
                "                'system': 'http://unitsofmeasure.org'," +
                "                'code': '{dos}'" +
                "              }" +
                "            }" +
                "          ]" +
                "        }" +
                "      ]," +
                "    'authoredOn': '2019-08-07T14:14:14'" +
                "}";

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

        response = br.postSSL(endpoint + "/fhir-server/fhir/MedicationRequest?_format=json", json);
        System.out.println(response);
        System.out.println("\n\n" + br.getHeaders().get("Location"));


    }

    @Test
    public void post_MedicationRequestS14old() {
        String response;
        BaseRest br = new BaseRest();
        String uuid_Practitioner = getPractitionerUUID("8880999");
        String uuid_Patient = getPatientUUID("199802262387");
        String structureDefinitionVersion = get_StructureDefinitionVersion(uuid_Patient);


        String json = "{" +
                "   'resourceType' : 'MedicationRequest'," +
                "   'meta' : {" +
                "      'versionId' : '1'," +
                "      'profile' : [" +
                "         'http://natmedlist.se/fhir/StructureDefinition/nll-medicationrequest'" +
                "      ]" +
                "   }," +
                "   'extension': [" +
                "    {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/Version'," +
                "      'valueInteger': " + structureDefinitionVersion +
                "    }" +
                "  ]," +
                "   'identifier' : [" +
                "      {" +
                "         'value' : '12205'" +
                "      }" +
                "   ]," +
                "   'status' : 'cancelled'," +
                "   'medicationCodeableConcept' : {" +
                "      'coding' : [" +
                "         {" +
                "            'system' : 'http://medication.ehm.se/nplpackid'," +
                "            'code' : '20120223100203'" +
                "         }" +
                "      ]" +
                "   }," +
                "    'subject': {" +
                "         'reference': 'Patient/" + uuid_Patient + "'" +
                "    }," +
                "    'requester': {" +
                "        'reference': 'Practitioner/" + uuid_Practitioner + "'" +
                "      }," +
                "   'authoredOn': '2019-08-29T15:17:33'," +
                "   'reasonCode' : [" +
                "      {" +
                "         'coding' : [" +
                "            {" +
                "               'system' : 'http://snomed.info/sct'," +
                "               'code' : '226013008'" +
                "            }" +
                "         ]," +
                "         'text' : 'Irregular pain'" +
                "      }" +
                "   ]," +
                "   'dosageInstruction' : [" +
                "      {" +
                "         'extension' : [" +
                "            {" +
                "               'url' : 'http://natmedlist.se/fhir/StructureDefinition/nll-observandum-dose'," +
                "               'valueBoolean' : false" +
                "            }" +
                "         ]," +
                "         'text' : 'Dosinstruktioner - text'," +
                "         'timing' : {" +
                "            'repeat' : {" +
                "               'boundsPeriod' : {" +
                "              'start': '2019-08-29'," +
                "              'end': '2019-09-05'" +
                "               }," +
                "               'count' : 0," +
                "               'frequency' : 4," +
                "               'timeOfDay' : [" +
                "                  '12:00'" +
                "               ]" +
                "            }" +
                "         }," +
                "         'asNeededBoolean' : false," +
                "         'doseAndRate' : [" +
                "            {" +
                "               'doseQuantity' : {" +
                "                  'value' : 5.0," +
                "                  'system' : 'http://unitsofmeasure.org'," +
                "                  'code' : '{dos}'" +
                "               }" +
                "            }" +
                "         ]" +
                "      }" +
                "   ]," +
                "   'dispenseRequest' : {" +
                "      'extension' : [" +
                "         {" +
                "            'url' : 'http://electronichealth.se/fhir/StructureDefinition/NLLDosePackaging'," +
                "            'valueBoolean' : false" +
                "         }," +
                "         {" +
                "            'url' : 'http://electronichealth.se/fhir/StructureDefinition/NLLWorkplacecode'," +
                "            'valueCode' : '4000000000000'" +
                "         }," +
                "         {" +
                "            'url' : 'http://electronichealth.se/fhir/StructureDefinition/NLLPrescriptionCommunicableDiseasesAct'," +
                "            'valueBoolean' : true" +
                "         }," +
                "         {" +
                "            'url' : 'http://electronichealth.se/fhir/StructureDefinition/NLLPatientPharmaceuticalBenefitsAct'," +
                "            'valueBoolean' : false" +
                "         }," +
                "         {" +
                "            'url' : 'http://electronichealth.se/fhir/StructureDefinition/NLLConditionsPharmaceuticalBenefitsAct'," +
                "            'valueBoolean' : false" +
                "         }, " +
                "         { " +
                "            'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLNumberOfPackagesPrescribed'," +
                "            'valuePositiveInt': 1" +
                "         }" +
                "      ]," +
                "      'validityPeriod' : {" +
                "            'start': '2019-08-29'," +
                "            'end': '2020-08-29'" +
                "      }," +
                "      'numberOfRepeatsAllowed' : 0" +
                "   }" +
                "}";

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

        response = br.postSSL(endpoint + "/fhir-server/fhir/MedicationRequest?_format=json", json);
        System.out.println(response);
        System.out.println("\n\n" + br.getHeaders().get("Location"));


    }

    @Test
    public void post_MedicationRequestS14() {
        String response;
        BaseRest br = new BaseRest();
        Forskrivare forskrivare = se.ehm.testdata.ForskrivareHandler.getSpecialSlumpadForskrivarKod();

        String uuid_Patient = getPatientUUID("199802262387");
        String structureDefinitionVersion = get_StructureDefinitionVersion(uuid_Patient);  // Underlagversionen


        String json = "{" +
                "  'resourceType': 'MedicationRequest'," +
                "  'meta': {" +
                "    'profile': [" +
                "      'http://electronichealth.se/fhir/StructureDefinition/NLLMedicationRequest'" +
                "    ]" +
                "  }," +
                "  'extension': [" +
                "    {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/Version'," +
                "      'valueInteger': " + structureDefinitionVersion +
                "    }," +
                "    { " +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLPatientTreatmentReason'," +
                "      'valueString': 'NLLPatientTreatmentReason - Behandlingsändamål mr1'" +
                "    }" +
                "  ]," +
                "    'contained': [" +
                "    {" +
                "      'resourceType': 'Practitioner'," +
                "      'id': 'theRequester'," +
                "      'meta': {" +
                "        'profile': [" +
                "          'http://electronichealth.se/fhir/StructureDefinition/NLLPractitioner'" +
                "        ]" +
                "      }," +
                "      'extension': [" +
                "        {" +
                "          'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLExtProffessionCode'," +
                "          'valueCodeableConcept': {" +
                "            'coding': [" +
                "              {" +
                "                'system': 'http://electronichealth.se/ValueSet/ext-qualification-codes-1'," +
                "                'code': 'LF'" +
                "              }" +
                "            ]" +
                "          }" +
                "        }" +
                "      ]," +
                "      'identifier': [" +
                "        {" +
                "          'system': 'http://electronichealth.se/identifier/forskrivarkod'," +
                "          'value': '" + forskrivare.getForskrivarkod() + "'" +                                                        // Förskrivarkod :-)
                "        }" +
                "      ]," +
                "      'name': [" +
                "        {" +
                "          'given': [" +
                "            'Sweeney'" +
                "          ]," +
                "          'family': 'Todd'" +
                "        }" +
                "      ]," +
                "      'qualification': [" +
                "        {" +
                "          'code': {" +
                "            'coding': [" +
                "              {" +
                "                'system': 'http://electronichealth.se/fhir/ValueSet/profession-code'," +
                "                'code': 'LK'" +
                "              }" +
                "            ]" +
                "          }" +
                "        }" +
                "      ]" +
                "    },    " +
                "        {" +
                "          'resourceType': 'Medication'," +
                "          'id': 'theMedicine'," +
                "          'code': {" +
                "            'coding': [" +
                "              {" +
                "                'system': 'http://medication.ehm.se/nplpackid'," +
                "                'code': '20040607100127'" +
                "              }," +
                "              {" +
                "                'system' : 'http://medication.ehm.se/nplid'," +
                "                'code' : '20040607000182'" +
                "              }" +
                "            ]" +
                "          }" +
                "        }" +
                "      ]," +
                "  'intent': 'plan'," +
                "  'medicationReference': {" +
                "    'reference': '#theMedicine'" +
                "  }," +
                "  'subject': {" +
                "    'reference': 'Patient/" + uuid_Patient + "'" +     // Patienten
                "  }," +
                "  'requester': {" +
                "    'reference': '#theRequester'" +
                "  }," +
                " 'reasonCode': [" +
                "    {" +
                "      'coding': [" +
                "        {" +
                "          'system': 'http://snomed.info/sct'," +
                "          'code': '0222'" +
                "        }," +
                "        {" +
                "          'system': 'http://snomed.info/sct'," +
                "          'code': '0223'" +
                "        },   " +
                "        {" +
                "          'system': 'http://terminology.hl7.org/CodeSystem/v3-NullFlavor'," +
                "          'code': 'OTH'" +
                "        }" +
                "      ]," +
                "      'text': 'Beskrivning annan behandlingsorsak'" +
                "    }" +
                "  ]," +
                "  'dispenseRequest': {" +
                "    'extension': [" +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLDosePackaging'," +
                "        'valueBoolean': false" +
                "      }," +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLWorkplacecode'," +
                "        'valueCode': '4000000000000'" +
                "      }," +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLPatientPharmaceuticalBenefitsAct'," +
                "        'valueBoolean': 'false'" +
                "      }" +
                "    ]," +
                "    'dispenseInterval': {" +
                "      'value': 12," +
                "      'unit': 'day'," +
                "      'system': 'http://unitsofmeasure.org'," +
                "      'code': 'd'" +
                "    }," +
                "    'validityPeriod': {" +
                "      'start': '2019-09-19T14:35:09'," +
                "      'end': '2020-09-19'" +
                "    }" +
                "  }," +
                "  'dosageInstruction': [" +
                "    {" +
                "      'text': 'Dosinstruktioner - text'," +
                "      'timing': {" +
                "        'repeat': {" +
                "          'boundsPeriod': {" +
                "            'start': '2019-09-19T14:35:09'," +
                "            'end': '2020-09-19'" +
                "          }," +
                "          'frequency': 4," +
                "          'period': 1," +
                "          'periodUnit': 'd'," +
                "          'timeOfDay': [" +
                "            '12:00'" +
                "          ]" +
                "        }" +
                "      }," +
                "      'doseAndRate': [" +
                "        {" +
                "          'type': {" +
                "            'coding': [" +
                "              {" +
                "                'system': 'http://terminology.hl7.org/CodeSystem/dose-rate-type'," +
                "                'code': 'ordered'," +
                "                'display': 'Ordered'" +
                "              }" +
                "            ]" +
                "          }," +
                "          'doseQuantity': {" +
                "            'value': 5," +
                "            'unit': 'dos'," +
                "            'system': 'http://unitsofmeasure.org'," +
                "            'code': '{dos}'" +
                "          }" +
                "        }" +
                "      ]" +
                "    }" +
                "  ]," +
                "  'authoredOn': '2019-09-19T14:35:09'" +
                "}";

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

        response = br.postSSL(endpoint + "/fhir-server/fhir/MedicationRequest?_format=json", json);
        System.out.println(response);
        System.out.println("\n\n" + br.getHeaders().get("Location"));


    }

    @Test
    public void post_MedicationRequestS15() {
        String response;
        BaseRest br = new BaseRest();
        Forskrivare forskrivare = se.ehm.testdata.ForskrivareHandler.getSpecialSlumpadForskrivarKod();

        String uuid_Patient = getPatientUUID("189002119817");
        //String structureDefinitionVersion = get_StructureDefinitionVersion(uuid_Patient);  // Underlagversionen


        String json = "{" +
                "  'resourceType': 'MedicationRequest'," +
                "  'meta': {" +
                "    'profile': [" +
                "      'http://electronichealth.se/fhir/StructureDefinition/NLLMedicationRequest'" +
                "    ]" +
                "  }," +
                "  'extension': [" +
                "    {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLPatientTreatmentReason'," +
                "      'valueString': 'NLLPatientTreatmentReason - Behandlingsändamål frekvensdosering'" +
                "    }," +
                "    {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLPrescriberContactInfo'," +
                "        'valueReference': {" +
                "            'reference' : '#contact1'" +
                "        }" +
                "    }" +
                "" +
                "  ]," +
                "  'contained': [" +
                "    {" +
                "      'resourceType': 'Practitioner'," +
                "      'id': 'theRequester'," +
                "      'meta': {" +
                "        'profile': [" +
                "          'http://electronichealth.se/fhir/StructureDefinition/NLLPractitioner'" +
                "        ]" +
                "      }," +
                "      'identifier': [" +
                "        {" +
                "          'system': 'http://electronichealth.se/identifier/forskrivarkod'," +
                "          'value': " + forskrivare.getForskrivarkod() +
                "        }" +
                "      ]," +
                "      'name': [" +
                "        {" +
                "          'given': [" +
                "            '" + forskrivare.getFornamn() + "'" +
                "          ]," +
                "          'family': '" + forskrivare.getEfternamn() + "'" +
                "        }" +
                "      ]," +
                "      'qualification': [" +
                "        {" +
                "          'code': {" +
                "            'coding': [" +
                "              {" +
                "                'system': 'http://electronichealth.se/fhir/ValueSet/profession-code'," +
                "                'code': 'LK'" +
                "              }" +
                "            ]" +
                "          }" +
                "        }" +
                "      ]" +
                "    }," +
                "    {" +
                "      'resourceType': 'Medication'," +
                "      'id': 'theMedicine'," +
                "      'code': {" +
                "        'coding': [" +
                "          {" +
                "            'system': 'http://medication.ehm.se/nplpackid'," +
                "            'code': '19990924100093'" +
                "          }," +
                "          {" +
                "            'system': 'http://medication.ehm.se/nplid'," +
                "            'code': '19990924000041'" +
                "          }" +
                "        ]" +
                "      }" +
                "    }," +
                "    {" +
                "        'resourceType' :'Location'," +
                "          'meta': {" +
                "            'profile': [" +
                "              'http://electronichealth.se/fhir/StructureDefinition/NLLPrescriberLocation'" +
                "            ]" +
                "          }," +
                "        'id': 'contact1'," +
                "        'address': {" +
                "            'extension': [" +
                "                {" +
                "                    'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLAvailableToPatient'," +
                "                    'valueBoolean': true" +
                "                }  " +
                "            ]," +
                "             'line': 'Gatan 1'," +
                "             'postalCode': '12345'," +
                "             'city': 'Stockholm'," +
                "             'type': 'postal'" +
                "        }," +
                "          'telecom': [" +
                "            {" +
                "                'extension': [" +
                "                    {" +
                "                        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLAvailableToPatient'," +
                "                        'valueBoolean': true" +
                "                    }  " +
                "                ]," +
                "                'system' : 'phone'," +
                "                'rank': 2," +
                "                'value': '070-123456789'" +
                "            }" +
                "          ]," +
                "        'identifier': [" +
                "            {" +
                "                'system':'urn:oid:2.5.4.97'," +
                "                'value': '123456789'" +
                "            }" +
                "        ]" +
                "    }" +
                "  ]," +
                "  'intent': 'plan'," +
                "  'medicationReference': {" +
                "    'reference': '#theMedicine'" +
                "  }," +
                "  'subject': {" +
                "    'reference': 'Patient/" + uuid_Patient + "'" +
                "  }," +
                "  'requester': {" +
                "    'reference': '#theRequester'" +
                "  }," +
                "  'dispenseRequest': {" +
                "    'extension': [" +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLWorkplacecode'," +
                "        'valueCode': '4000000000000'" +
                "      }," +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLPatientPharmaceuticalBenefitsAct'," +
                "        'valueBoolean': 'false'" +
                "      }," +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLUrgentProduction'," +
                "        'valueBoolean': 'false'" +
                "      }," +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLNumberOfPackagesPrescribed'," +
                "       'valuePositiveInt': 5" +
                "      }" +
                "    ]," +
                "    'dispenseInterval': {" +
                "      'value': 12," +
                "      'unit': 'day'," +
                "      'system': 'http://unitsofmeasure.org'," +
                "      'code': 'd'" +
                "    }," +
                "    'validityPeriod': {" +
                "      'start': '2019-10-01'," +
                "      'end': '2020-10-01'" +
                "    }," +
                "    'numberOfRepeatsAllowed': 0" +
                "  }," +
                "    'dosageInstruction': [" +
                "    {" +
                "      'text': 'For insattning hos barnmorska'" +
                "    }" +
                "  ]," +
                "  'authoredOn': '2019-10-01T08:51:25'" +
                "}";

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

        response = br.postSSL(endpoint + "/fhir-server/fhir/MedicationRequest?_format=json", json);
        System.out.println(response);
        System.out.println("\n\n" + br.getHeaders().get("Location"));


    }

    @Test
    public void get_MedicationRequest_ID () {

        String response;
        BaseRest br = new BaseRest();

        // Skapa en ticket
        Forskrivare forskrivare = ForskrivareHandler.getRndForskrivare("INT28");
        System.out.println("Förskrivare: " + forskrivare);
        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode(forskrivare.getForskrivarkod());
        ticket.setHealthcareProfessionalLicense("LK");

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(ticket));

        response = br.getSSL(endpoint + "/fhir-server/fhir/MedicationRequest/7c292409-e3f2-4a57-a5e2-26b38f9aa8da");
        System.out.println(response);

    }

    @Test
    public void get_MedicationRequest_patient_id () {

        String response;
        BaseRest br = new BaseRest();
        String uuid_Patient = getPatientUUID("191003289822");

        // Skapa en ticket
        Forskrivare forskrivare = ForskrivareHandler.getRndForskrivare("INT28");
        System.out.println("Förskrivare: " + forskrivare);
        Tickets ticket = new Tickets();
        ticket.setPersonalPrescriptionCode(forskrivare.getForskrivarkod());
        ticket.setHealthcareProfessionalLicense("LK");

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(ticket));

        response = br.getSSL(endpoint + "/fhir-server/fhir/MedicationRequest?status=active&patient._id=" + uuid_Patient);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(response));

        // Lista ut antalet recept för personen
        JSONArray jsonArray = JsonPath.read(response, "$..search");
        System.out.println("\nAntal recept: " + jsonArray.size());
    }

    @Test // får denna variant finnas //
    public void get_MedicationRequest_patient_identifier () {
        Transaction transaction = new Transaction();
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

        transaction.start("anrop");
            response = br.getSSL(endpoint + "/fhir-server/fhir/MedicationRequest/?status=active&patient.identifier=199802262387");
        transaction.stop("anrop");

        System.out.println(response);

        // Lista ut antalet recept för personen och skriv ut svarstiden
        JSONArray jsonArray = JsonPath.read(response, "$..search");
        System.out.println("\nAntal recept: " + jsonArray.size() + " : Tid: " + transaction.getElapsedTime("anrop"));
    }

    // Consent // "SAMTYCKE"
    @Test
    public void post_Consent() {
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

            // 189002119817 //

        String json = "" +
                "{" +
                "  'resourceType': 'Consent'," +
                "  'meta': {" +
                "    'profile': [" +
                "      'http://electronichealth.se/fhir/StructureDefinition/NLLMultiDoseDispConsent'" +
                "    ]" +
                "  }," +
                "  'category': [" +
                "    {" +
                "      'coding': [" +
                "        {" +
                "          'code': 'consent-dose-disp'," +
                "          'system': 'http://electronichealth.se/fhir/ValueSet/consent-types-1'" +
                "        }" +
                "      ]" +
                "    }" +
                "  ]," +
                "  'patient': {" +
                "    'reference': 'Patient/2a5757da-7f59-4d62-b2e1-6babb3e661f4'" +
                "  }," +
                "  'dateTime': '2019-10-24'," +
                "  'provision': {" +
                "    'dataPeriod': {" +
                "      'start': '2019-10-25'," +
                "      'end': '2019-12-24'" +
                "    }" +
                "  }" +
                "}";

        json = json.replace("'", "\"");
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint + "/fhir-server/fhir/Consent", json);

        System.out.println(response);
        System.out.println("\n\n" + br.getHeaders());

    }

    @Test
    public void get_Consent() {
        String response;
        BaseRest br = new BaseRest();

        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));

        response = br.getSSL(endpoint + "/fhir-server/fhir/Consent/00007bde-bb25-41e5-839c-b932ce6577d7");

        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(response)+"\n\nHeaders:\n"+br.getHeaders());
    }

    // List
    @Test
    public void post_list() {
        // Skapar en SAMBI ticket
        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();

        String response;
        BaseRest br = new BaseRest();

        String token = accessTokenHandler.getAccessToken(AccessTokenHandler.FARMACEUT);
        System.out.println(token);
        br.addHeader("Authorization", "Bearer " + token);

        String json = "{" +
                "  'resourceType': 'List'," +
                "  'meta': { " +
                "    'profile': [" +
                "      'http://natmedlist.se/fhir/StructureDefinition/nll-dose-med-list'  " +
                "    ]" +
                "  }," +
                "  'extension': [  " +
                "    {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/Version'," +
                "      'valueInteger': 1  " +
                "    }" +
                "  ]," +
                "  'code': {  " +
                "    'coding': [    " +
                "      {" +
                "        'system': 'http://fhir.ehm.se', " +
                "        'code': 'multi-dose-disp-list'" +
                "      }  " +
                "    ]" +
                "  }," +
                "  'subject': {" +
                "    'reference': 'Patient/2a5757da-7f59-4d62-b2e1-6babb3e661f4'" +
                "  }" +
                "}";

        json = json.replace("'", "\"");

        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.postSSL(endpoint + "/fhir-server/fhir/List", json);

        System.out.println(response);

        System.out.println("\n\n" + br.getHeaders());

    }
    
    @Test
    public void put_list() {
        String response;
        BaseRest br = new BaseRest();

        String json = "{" +
                "  'resourceType': 'List'," +
                "  'id': '47641eec-6a74-42a8-a223-ebb2b1c4c060'," +
                "  'meta': { " +
                "    'profile': [" +
                "      'http://electronichealth.se/fhir/StructureDefinition/NLLDoseDispMedList'  " +
                "    ]" +
                "  }," +
                "   'extension' : [" +
                "      {" +
                "         'url' : 'http://electronichealth.se/fhir/StructureDefinition/Status'," +
                "         'valueString' : 'inactive'" +
                "      }," +
                "    {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/Version'," +
                "      'valueInteger': 0 " +
                "    }" +
                "   ]," +
                "  'code': {  " +
                "    'coding': [    " +
                "      {" +
                "        'system': 'http://fhir.ehm.se'," +
                "        'code': 'multi-dose-disp-list'" +
                "      }  " +
                "    ]" +
                "  }," +
                "  'subject': {" +
                "    'reference': 'Patient/2a5757da-7f59-4d62-b2e1-6babb3e661f4'" +
                "  }" +
                "}";

        json = json.replace("'", "\"");

        System.out.println(endpoint);
        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(json));

        response = br.putSSL(endpoint + "/fhir-server/fhir/List/47641eec-6a74-42a8-a223-ebb2b1c4c060", json);
        System.out.println("\n\n"+response+"\n\n"+br.getHeaders());
        
    }

    @Test
    public void get_list() {
        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();

        String response;
        BaseRest br = new BaseRest();
        String uuid_Patient = getPatientUUID("189002019819");

        response = br.getSSL(endpoint + "/fhir-server/fhir/List/" + uuid_Patient);

        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(response)+"\n\nHeaders:\n"+br.getHeaders());

        // Lista ut antalet recept för personen
        JSONArray jsonArray = JsonPath.read(response, "$..valueInteger");
        System.out.println("\nStructureDefinitionVersion value \"Underlagsversion\": " + jsonArray.get(0));
    }

    @Test
    public void revisitAllSkatteverketPatients() {
        String response;
        BaseRest br = new BaseRest();
        Skatteverket skatteverket = new Skatteverket();
        List<Person> personer = skatteverket.getPersons();
        Transaction transaction = new Transaction();
        int i = 1;

        Iterator itr = personer.iterator();
        while ( itr.hasNext() ) {
            Person p = (Person) itr.next();
            System.out.print("Hämtar peron: " + p.getPersonnummer() + " : svarstid: ");
            transaction.start("getPatient");
            response = br.getSSL(endpoint + "/fhir-server/fhir/Patient/?identifier=" + p.getPersonnummer());
            transaction.stop("getPatient");
            System.out.println(transaction.getElapsedTime("getPatient") + " : nr: " + i++);
        }


    }

    @Test
    public void revisitAllPractitioners() {
        String response;
        BaseRest br = new BaseRest();
        List lista = se.ehm.testdata.ForskrivareHandler.getAllForskrivare("INT28");

        Transaction transaction = new Transaction();
        int i = 1;

        Iterator itr = lista.iterator();
        while ( itr.hasNext() ) {
            Forskrivare forskrivare = (Forskrivare) itr.next();
            System.out.print("Hämtar läkare: " + forskrivare.getForskrivarkod() + " : svarstid: ");
            transaction.start("getPractitioner");
            String vars = "identifier=urn:http://ehalsomyndigheten.se/fhir/id/forskrivarkod|"+forskrivare.getForskrivarkod()+"&_format=json";
            vars = vars.replace("|", "%7C");
            response = br.getSSL(endpoint + "/fhir-server/fhir/Practitioner?" + vars);

            transaction.stop("getPractitioner");
            System.out.println(transaction.getElapsedTime("getPractitioner") + " : nr: " + i++);
        }
    }

/* ---------------------------------------------------------------------------- */
/* -                                   Flöden                                 - */
/* ---------------------------------------------------------------------------- */
    @Test
    public void run_flode_MedicationRequest() {
        Skatteverket skatten = new Skatteverket();

        for (int i = 0; i < 1; i++) {
            Person person = skatten.getSlumpadPerson();
            String personnummer = person.getPersonnummer(); //"189104179818"; // "189709129812"; //
//            personnummer = "199802262387" ;

            System.out.print( "Iter: "+ (i+1) +" : " + personnummer + " : ");
            flode_MedicationRequest(personnummer);
            lr.think_time(0.250);
        }
    }

    public void flode_MedicationRequest( String personnummer) {

        Forskrivare forskrivare = se.ehm.testdata.ForskrivareHandler.getRndForskrivare("INT28");
        Lakemedel lakemedel = LakemedelHandler.getRndLakemedel("INT28");
        Arbetsplats arbetsplats = ArbetsplatsHandler.getRndArbetsplats("INT28");

        System.out.println("forskrivare: " + forskrivare);
        System.out.println(lakemedel);
        System.out.println(arbetsplats);

        String uuid_Patient = getPatientUUID(personnummer);

        String response;
        BaseRest br = new BaseRest();

        String json = "{" +
                "  'resourceType': 'MedicationRequest'," +
                "  'meta': {" +
                "    'profile': [" +
                "      'http://electronichealth.se/fhir/StructureDefinition/NLLMedicationRequest'" +
                "    ]" +
                "  }," +
                "  'extension': [" +
                "    {" +
                "      'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLPatientTreatmentReason'," +
                "      'valueString': 'NLLPatientTreatmentReason - Behandlingsändamål frekvensdosering'" +
                "    }," +
                "    {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLPrescriberContactInfo'," +
                "        'valueReference': {" +
                "            'reference' : '#contact1'" +
                "        }" +
                "    }" +
                "" +
                "  ]," +
                "  'contained': [" +
                "    {" +
                "      'resourceType': 'Practitioner'," +
                "      'id': 'theRequester'," +
                "      'meta': {" +
                "        'profile': [" +
                "          'http://electronichealth.se/fhir/StructureDefinition/NLLPractitioner'" +
                "        ]" +
                "      }," +
                "      'identifier': [" +
                "        {" +
                "          'system': 'http://electronichealth.se/identifier/forskrivarkod'," +
                "          'value': " + forskrivare.getForskrivarkod() +
                "        }" +
                "      ]," +
                "      'name': [" +
                "        {" +
                "          'given': [" +
                "            '" + forskrivare.getFornamn() + "'" +
                "          ]," +
                "          'family': '" + forskrivare.getEfternamn() + "'" +
                "        }" +
                "      ]," +
                "      'qualification': [" +
                "        {" +
                "          'code': {" +
                "            'coding': [" +
                "              {" +
                "                'system': 'http://electronichealth.se/fhir/ValueSet/profession-code'," +
                "                'code': 'LK'" +
                "              }" +
                "            ]" +
                "          }" +
                "        }" +
                "      ]" +
                "    }," +
                "    {" +
                "      'resourceType': 'Medication'," +
                "      'id': 'theMedicine'," +
                "      'code': {" +
                "        'coding': [" +
                "          {" +
                "            'system': 'http://medication.ehm.se/nplpackid'," +
                "            'code': '"+lakemedel.getNPLPACKID()+"'" +
                "          }," +
                "          {" +
                "            'system': 'http://medication.ehm.se/nplid'," +
                "            'code': '"+lakemedel.getNPLID()+"'" +
                "          }" +
                "        ]" +
                "      }" +
                "    }," +
                "    {" +
                "        'resourceType' :'Location'," +
                "          'meta': {" +
                "            'profile': [" +
                "              'http://electronichealth.se/fhir/StructureDefinition/NLLPrescriberLocation'" +
                "            ]" +
                "          }," +
                "        'id': 'contact1'," +
                "        'address': {" +
                "            'extension': [" +
                "                {" +
                "                    'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLAvailableToPatient'," +
                "                    'valueBoolean': true" +
                "                }  " +
                "            ]," +
                "             'line': '"+ arbetsplats.getPostAdress() +"'," +
                "             'postalCode': '"+ arbetsplats.getPostNummer() +"'," +
                "             'city': '" + arbetsplats.getPostOrt() + "'," +
                "             'type': 'postal'" +
                "        }," +
                "          'telecom': [" +
                "            {" +
                "                'extension': [" +
                "                    {" +
                "                        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLAvailableToPatient'," +
                "                        'valueBoolean': true" +
                "                    }  " +
                "                ]," +
                "                'system' : 'phone'," +
                "                'rank': 2," +
                "                'value': '070-123456789'" +
                "            }" +
                "          ]," +
                "        'identifier': [" +
                "            {" +
                "                'system':'urn:oid:2.5.4.97'," +
                "                'value': '" + se.nhpj.testdata.RndData.getNumbers(10) + "'" +
                "            }" +
                "        ]" +
                "    }" +
                "  ]," +
                "  'intent': 'plan'," +
                "  'medicationReference': {" +
                "    'reference': '#theMedicine'" +
                "  }," +
                "  'subject': {" +
                "    'reference': 'Patient/" + uuid_Patient + "'" +
                "  }," +
                "  'requester': {" +
                "    'reference': '#theRequester'" +
                "  }," +
                "  'dispenseRequest': {" +
                "    'extension': [" +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLWorkplacecode'," +
                "        'valueCode': '" + arbetsplats.getKod() + "'" +
                "      }," +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLPatientPharmaceuticalBenefitsAct'," +
                "        'valueBoolean': 'false'" +
                "      }," +
                "      {" +
                "        'url': 'http://electronichealth.se/fhir/StructureDefinition/NLLNumberOfPackagesPrescribed'," +
                "       'valuePositiveInt': 5" +
                "      }" +
                "    ]," +
                "    'dispenseInterval': {" +
                "      'value': 12," +
                "      'unit': 'day'," +
                "      'system': 'http://unitsofmeasure.org'," +
                "      'code': 'd'" +
                "    }," +
                "    'validityPeriod': {" +
                "      'start': '2019-10-01'," +
                "      'end': '2020-10-01'" +
                "    }," +
                "    'numberOfRepeatsAllowed': 0" +
                "  }," +
                "    'dosageInstruction': [" +
                "    {" +
                "      'text': 'For insattning hos barnmorska och annan åäöÅÄÖ personal'" +
                "    }" +
                "  ]," +
                "  'authoredOn': '" + se.nhpj.soap.utils.CurrentDateTime.getTLong() + "'" +
                "}";

        String postURL = endpoint + "/fhir-server/fhir/MedicationRequest?_format=json";

//        System.out.println(postURL);
        response = br.postSSL(postURL, json);

//        System.out.println(response);
        List l = (List) br.getHeaders().get("Location");
        String s = (String) l.get(0);
        System.out.println("Recepie created: " + s.replace("http:", "https:"));
        System.out.println();
    }



// --------------------------------------------------------------------------------------------------------------------//
// ---------------------------------------------- Hjälpfunktioner -----------------------------------------------------//
// --------------------------------------------------------------------------------------------------------------------//

    public String getPractitionerUUID(String forskrivarkod) {
        String response;
        BaseRest br = new BaseRest();

        String vars = "identifier=urn:http://ehalsomyndigheten.se/fhir/id/forskrivarkod|" + forskrivarkod + "&_format=json";
        vars = vars.replace("|", "%7C");

        response = br.getSSL(endpoint + "/fhir-server/fhir/Practitioner?" + vars);

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(response);
        JSONArray a = JsonPath.read(document, "$..resource.id");
        return (String) a.get(0);
    }

    public String getPatientUUID(String personnummer) {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint + "/fhir-server/fhir/Patient/?_format=json&identifier=" + personnummer );

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(response);
        JSONArray a = JsonPath.read(document, "$..resource.id");
        System.out.println("(getPatientUUID) " + personnummer + " : " + a.get(0));
        return (String) a.get(0);
    }

    public static String getPatientUUID(String endpoint, String personnummer) {
        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint + "/fhir-server/fhir/Patient/?_format=json&identifier=" + personnummer );

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(response);
        JSONArray a = JsonPath.read(document, "$..resource.id");
        return (String) a.get(0);
    }

    public String get_StructureDefinitionVersion(String PatientRef) {
//        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();

        String response;
        BaseRest br = new BaseRest();

        response = br.getSSL(endpoint + "/fhir-server/fhir/List/" + PatientRef);

//        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(response)+"\n\nHeaders:\n"+br.getHeaders());

        // Lista ut antalet recept för personen
        JSONArray jsonArray = JsonPath.read(response, "$..valueInteger");
        if (jsonArray.size() > 0) {
            System.out.println("StructureDefinitionVersion value \"Underlagsversion\": " + jsonArray.get(0).toString());
            return jsonArray.get(0).toString();
        }
        else {
            System.out.println("StructureDefinitionVersion value \"Underlagsversion\": 0");
            return "0";
        }
    }

    @Test
    public void test_getPatientUUID() {
        System.out.println(getPatientUUID("189002019819"));
        System.out.println(test_FHIR_anrop.getPatientUUID("https://nll-fhir-server-s15-test1-deploy1.test.ecp.systest.receptpartner.se","189001019802"));

    }

    @Test
    public void test_get_StructureDefinitionVersion() {
        String uuid_Patient = getPatientUUID("201812302394");
        String structureDefinitionVersion = get_StructureDefinitionVersion(uuid_Patient);
    }

//    @Test
//    public void get_Practitioner() {
//
//        String response;
//        BaseRest br = new BaseRest();
//
//        AccessTokenHandler accessTokenHandler = new AccessTokenHandler();
//        br.addHeader("Authorization", "Bearer " + accessTokenHandler.getAccessToken(AccessTokenHandler.FORSKRIVARE));
//
//        String vars = "identifier=urn:http://ehalsomyndigheten.se/fhir/id/forskrivarkod|8880999&_format=json";
//        vars = vars.replace("|", "%7C");
//
//        response = br.getSSL(endpoint + "/fhir-server/fhir/Practitioner?" + vars);
//
//        System.out.println(se.nhpj.rest.RestFormatter.prettyPrint(response));
//    }
//

}
