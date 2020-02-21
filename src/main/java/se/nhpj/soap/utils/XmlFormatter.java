/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nhpj.soap.utils;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import se.nhpj.soap.services.*;
import se.nhpj.soap.*;

/**
 *
 * @author nhpj
 *
 * Pretty-prints xml, supplied as a string.
 * eg.
 * <code>
 * String formattedXml = new XmlFormatter().format("&lt;tag&gt;&lt;nested&gt;hello&lt;/nested&gt;&lt;/tag&gt;");
 * </code>
 */

public class XmlFormatter {
    /**
     * Contructor ...
     */
    public XmlFormatter() {
    }
    /**
     * Denna metod formaterar en oformaterad XML lite snyggare 
     * @param unformattedXml - oformaterad XML
     * @return - Formaterad XML sträng
     */
    public String format(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Denna metod returnerar ett w3c DOM object av en XML sträng
     * @param in - XML som en sträng
     * @return - DOM Dokument
     */
    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
