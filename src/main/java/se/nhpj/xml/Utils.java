package se.nhpj.xml;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
//import se.nhpj.soap.BaseXML;

/**
 * @author nhpj
 */
public class Utils {
    
    private ArrayList diff;
    
    /**
     * Constructor
     */
    public Utils() {
        diff = new ArrayList();
    }
    
    /**
     * This method returns a List object containing info regarding differenses between checked objects
     * @return List
     */
    public List getDiffList() {return diff;}
    
    /**
     * This method compares two XML strings
     * @param xml1 som en sträng
     * @param xml2 som en sträng
     * @return returns false or true if on diff.
     */
    public boolean compareXML(String xml1, String xml2) {
        Map map1 = XMLtoMap(xml1);
        Map map2 = XMLtoMap(xml2);
        boolean retVal = mapsIsEqual(map1,map2);
        if ( retVal == true ){ diff.clear(); diff.add(true);}
        return retVal;
    }
    
    /**
     * This method creats a HashMap from a XML String
     * @param xml XML String
     * @return Map object
     */    
    public Map XMLtoMap(String xml) {
        Document doc = createDocument(xml);
        Element element=doc.getDocumentElement();
        NodeList sList=element.getChildNodes();
        Map<String,String> map = new HashMap<>();
        for (int i = 0; i < sList.getLength(); i++) {
            Node node = sList.item(i);
            map.put(node.getNodeName(),node.getTextContent());
            // Vandrar djupare i DOM trädet...
            if(sList.item(i).hasChildNodes()) {
                NodeList sListChild=sList.item(i).getChildNodes();
                for (int c = 0; c < sListChild.getLength(); c++) {
                    Node childNode = sListChild.item(c);
                    if(!childNode.getNodeName().contains("#text"))
                    map.put(childNode.getNodeName(),childNode.getTextContent());
                }
            }
        }
        return map;
    }

    /**
     * Creates the DOM-object document from a string containing XML
     * @param xml A XML as a string
     * @return a DOM Document
     */
    public Document createDocument(String xml){
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new InputSource(new StringReader( xml )));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }

    /**
     * This method compares to Map objects
     * @param mapA a first Map
     * @param mapB a second Map
     * @return true if same, else return diff between Map objects
     */
    public boolean mapsIsEqual(Map<String, String> mapA, Map<String, String> mapB){
            boolean retVal = true;
            Iterator itr1 = mapA.keySet().iterator();
            while (itr1.hasNext()) {
                String key = (String)itr1.next();
                String val = mapA.get(key);
                if(!mapB.containsKey(key)) {diff.add("mapA:key: " + key + " : sakans i mapB"); retVal = false;}
            }
            Iterator itr2 = mapB.keySet().iterator();
            while (itr2.hasNext()) {
                String key = (String)itr2.next();
                String val = mapB.get(key);
                if(!mapA.containsKey(key)) {diff.add("mapB:key: " + key + " : sakans i mapA"); retVal = false;}
            }
            itr1 = mapA.keySet().iterator();
            while (itr1.hasNext()) {
                String key = (String)itr1.next();
                String val = mapA.get(key);
                if (!mapA.get(key).equals(mapB.get(key))) {diff.add("mapA:key: " + key + " : " + mapA.get(key) + " <> mapB:key " + key + " : " + mapB.get(key)); retVal = false;}
            }
            itr2 = mapB.keySet().iterator();
            while (itr2.hasNext()) {
                String key = (String)itr2.next();
                String val = mapB.get(key);
                if (!mapB.get(key).equals(mapA.get(key))) {diff.add("mapB:key: " + key + " : " + mapB.get(key) + " <> mapA:key " + key + " : " + mapA.get(key)); retVal = false;}
            }
            if ( retVal == true ){ diff.clear(); diff.add(true);}
            return retVal;
    }
}
