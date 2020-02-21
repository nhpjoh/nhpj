package se.nhpj.testdata;

import java.sql.*;
public class PersonHandler {

    public static String getPersonLogicId(Person person, String ServiceNamne) {

        Connection con = se.nhpj.database.DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + ServiceNamne, "ETCDBA", "ETCDBA");

        String sql = "select i.IDENTITET, lower(regexp_replace(rawtohex(p.LOGISKT_ID), '([A-F0-9]{8})([A-F0-9]{4})([A-F0-9]{4})([A-F0-9]{4})([A-F0-9]{12})', '\\1-\\2-\\3-\\4-\\5')) as LOGISKT_ID_FORMATTED, p.LOGISKT_ID\n" +
                "from folk_prod.person p, FOLK_PROD.PERSONIDENTITET i\n" +
                "where p.PERSON_ID = i.PERSON_ID\n" +
                "and i.IDENTITET='" + person.getPersonnummer() + "'";

        return se.nhpj.database.DB_Access.getStringValue(con,sql,"LOGISKT_ID_FORMATTED");
    }
}
