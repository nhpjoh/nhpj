package se.nhpj.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;
import se.nhpj.testdata.Bolag;
import se.nhpj.testdata.Person;
import se.nhpj.testdata.TestPerson;

@WebService
@SOAPBinding(style = Style.DOCUMENT)
public interface  WebServiceInterface {
    @WebMethod
    String printMessage(String name);

    @WebMethod
    Person getSoap();

    @WebMethod
    List getSoapBig();

    @WebMethod
    List<Bolag> getCompanies(@WebParam(name = "namn") String namn);

    @WebMethod
    List<Person> getPersons(@WebParam(name = "pnr") String pnr, @WebParam(name = "fnamn") String fnamn, @WebParam(name = "enamn") String enamn);

    @WebMethod
    TestPerson getTestPerson();

}
