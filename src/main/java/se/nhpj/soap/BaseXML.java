package se.nhpj.soap;

import se.nhpj.soap.services.SoapResponseXML;
//import se.nhpj.soap.utils.Test_properties;
import java.io.*;
import java.net.ContentHandlerFactory;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.mail.smime.SMIMEException;
import org.bouncycastle.mail.smime.SMIMESignedGenerator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import se.nhpj.database.DB_Access;
import se.nhpj.soap.utils.XmlFormatter;

/**
 * Special basklass för prestandatester mot eHälsomyndighetens olika tjänster
 * Denna klass används bara via arv (extends) för att få tillgång till de metoder 
 * som är speciellt framtagna för detta ändamål.
 * 
 * @author nhpj
 * @version 0.02
 * 
 * https://examples.javacodegeeks.com/core-java/xml/xpath/java-xpath-examples/
 * 
 */
public abstract class BaseXML {
    
    private String _XML = null;     // The XML as a string
    private Document _doc = null;   // The XML as a DOM object
    private String _soapEndpointUrl = ""; // Holds the endpoint for the soap request
    private String propFile = null; // Holds the name ov the properties file.
    private static final String KEYSTORE_TYPE = "BKS";
    private static final String KEYSTORE_PROVIDER = "BC";

    /**
     * This is a constructor of this class that takes one parameter that is a string containing XML
     * @param xml - This parameter contains a XML as a String
     */
    protected BaseXML(String xml) {
        this.setXML(xml);
        this.createDocument();
    }
    /**
     * This tha a construktor for this class
     */    
    protected BaseXML() {
        this.createDocument();
    }
    
    /**
     * Creates the DOM-object document if changing the XML String object. Must be used when the String XML object is 
     * changed to ensure that the DOM object and the String XML object is in sync
     */
    protected void createDocument(){
        if (_XML != null) {
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                this._doc = dBuilder.parse(new InputSource(new StringReader( this._XML )));
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Creates the XML-string document if changing the DOM object. Must be used when the DOM object is 
     * changed to ensure that the DOM object and the String XML object is in sync
     */
    protected void createXML() {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(_doc), new StreamResult(writer));
            _XML = writer.getBuffer().toString();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Retuns the endpoint set for this object
     * @return endponint 
     */
    public String getSoapEndpointUrl() { return _soapEndpointUrl; }
    
    /**
     * Sets this object endpoint
     * @param soapEndpointUrl - a URL
     */
    public void setSoapEndpointUrl( String soapEndpointUrl) { _soapEndpointUrl = soapEndpointUrl; }

    /**
     * Returns a UUID a String
     * @return UUID as a String
     */
    public static String getUUID() { return UUID.randomUUID().toString(); }
    
    /**
     * Returns the clas built in DOM object
     * @return a org.w3c.dom.Document object 
     */
    Document getDocument() { return this._doc; }

    /**
     * Returns ths clas built in XML as a String
     * @return a XML as a String
     */
    public String getXML() { 
        this.createXML();
        return this._XML; 
    }

    /**
     * Writes this objects XML, using Logger
     */
    public void logXML() {
        XmlFormatter f = new XmlFormatter();
        Logger logger = Logger.getLogger(getClass().getCanonicalName());
        logger.log(Level.INFO, f.format(getXML()));
    }
    /**
     * Writes this objects XML to file
     */
    public void logXML( String filename ) {
        XmlFormatter f = new XmlFormatter();
        try {
            File file = new File(filename);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(f.format(this.getXML()));
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        } catch ( java.io.IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * sets the XML as a string
     * @param xml - a XML
     */
    public void setXML( String xml ) { 
        this._XML = xml;
        this.createDocument();
    }
    
    /**
     * Returns the number of tags within the cals loaded XML
     * @param xPathExpression of the tag you searching for
     * @return int Number of tags
     */
    public int getTagCount(String xPathExpression) {
        int count = -1;
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(xPathExpression);//all release elements
            NodeList nodes = (NodeList) expr.evaluate(this._doc, XPathConstants.NODESET);
            count = nodes.getLength();
        } catch (XPathExpressionException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    /**
     * Returns the value of the tag searched bye the xPathExpression
     * @param xPathExpression of the tag you searching for.
     * @return tagvalue
     */
    public String getTagValue(String xPathExpression) {
        String value = null;
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(xPathExpression);//all release elements
            NodeList nodes = (NodeList) expr.evaluate(this._doc, XPathConstants.NODESET);
            if(nodes.getLength()== 1) {
                value = nodes.item(0).getTextContent();
            } else {
                throw new Exception("More than one nodes returned by XPathExpression : " + "nodes.getLength: " + nodes.getLength() + ", getTagValue: " + xPathExpression + "\n" + this._XML);
            }    // exeption ??
        } catch (XPathExpressionException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "xPathExpression: "+ xPathExpression, ex);
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "xPathExpression: "+ xPathExpression, ex);
        }
        return value;
    }
    
    /**
     * Sets the value of the tag specified by PathExpression
     * @param xPathExpression of the tag
     * @param value to be set
     */
    public void setTagValue(String xPathExpression, String value) { 
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(xPathExpression);//all release elements
            NodeList nodes = (NodeList) expr.evaluate(this._doc, XPathConstants.NODESET);
            if(nodes.getLength()== 1) {
                // Sätter första nodens värde 
                nodes.item(0).setTextContent(value);
            } else { 
                System.out.println("Node: " + nodes.item(0).getNodeName() + " value: " + nodes.item(0).getNodeValue() + " nodes.getLength: " + nodes.getLength()); 
                throw new Exception("More than one nodes returned by XPathExpression : " + xPathExpression);
            }    // exeption ??
        } catch (XPathExpressionException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "xPathExpression: "+ xPathExpression, ex);
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, "xPathExpression: "+ xPathExpression, ex);
        }
    }
    
    /**
     * This method inserts a XML TAG after a specific XML tag in the built in XML/DOM object.
     * @param insertAfterTag - ex. "&lt;HEADER&gt;"
     * @param tag - tag to insert
     * @param value - tags value
     */
    public void insertAfterTag(String insertAfterTag, String tag, String value) {
        if (value != null && value.length() > 0) {
            _XML = this._XML.replace(insertAfterTag, insertAfterTag + "\n<" + tag +">"+ value + "</" +tag+">");
            this.createDocument();
        } else {
            _XML = this._XML.replace(insertAfterTag, insertAfterTag + "\n<" + tag +"/>");
            this.createDocument();
        }
    }

    /**
     * This method inserts a String of XML after a specific XML tag in the built in DOM/XML object 
     * @param insertAfterTag - ex. "&lt;HEADER&gt;"
     * @param xmlParts - XML to insert
     */
    public void insertXmlAfterTag(String insertAfterTag, String xmlParts) {
             _XML = this._XML.replace(insertAfterTag, insertAfterTag + xmlParts);
            this.createDocument();
    }

    /**
     * This method removes a XML tag in the built in DOM/XML object
     * @param xPathExpression - a expression
     */
    public void removeTag( String xPathExpression ){
        try {
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();
            XPathExpression expr = xpath.compile(xPathExpression);//all release elements
            NodeList nodes = (NodeList) expr.evaluate(this._doc, XPathConstants.NODESET);
            if(nodes.getLength()== 1) {
                Node removeChild = nodes.item(0).getParentNode().removeChild(nodes.item(0));
                
                _doc.getDocumentElement().normalize();
                this.createXML();
            } else { 
                System.out.println("nodes.getLength: " + nodes.getLength()); 
                throw new Exception("More than one nodes returned by XPathExpression");
            }    // exeption ??
        } catch (XPathExpressionException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method uses a property file to load values for the built in DOM/XML object
     * @param propertiesFile - a propertie file
     */
    public void setDefaultValues(String propertiesFile) {
        FileInputStream inStream = null;
        try {
            String rootPath = "";
            Properties props = new Properties();
            inStream = new FileInputStream (rootPath + propertiesFile);
            props.load(inStream);

            Enumeration prop = props.propertyNames();
            while (prop.hasMoreElements()) {
                String propertyName = prop.nextElement().toString();
                this.setTagValue("*//"+propertyName, props.getProperty(propertyName));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * This method loads values from a propperties file to the built in DOM/XML object
     */
    public void setStandardDefaultValues(){
        InputStream inStream = null;
        try {
            inStream = this.getClass().getResourceAsStream( this.getStandardDefaultFileName() );
            Properties props = new Properties();
            props.load(inStream);
            Enumeration prop = props.propertyNames();
            while (prop.hasMoreElements()) {
                String propertyName = prop.nextElement().toString();
                this.setTagValue("*//"+propertyName, props.getProperty(propertyName));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                inStream.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    /**
     * This method returns the property file used to load values for the built in DOM/XML object
     * @return properties fila as a String
     */
    public String getStandardDefaultFileName() { return this.propFile; }
    /**
     * This method setts the standard Default properties file
     * @param filename - a filename
     */
    public void setStandardDefaultFileName( String filename ) { this.propFile = filename;}

    /**
     * This method must be implemented in classes drived from this class
     * @param response - a SoapResponseXML
     * @return - true/false depending on the status of testes implemented.
     */
    public abstract Boolean checkResponse(SoapResponseXML response);
    
//    public String getAttribute(String attribute) {        String value = null;        return value;    }
//    public void setAttribute(String attribute, String value){ }

    /**
     * This method returns a SOAPMessage based on String containing XML
     * @param xml - A String containing XML
     * @return - a SOAPMessage object
     */
    public static SOAPMessage getSoapMessageFromString(String xml) {
        SOAPMessage message = null;
        try {
            MessageFactory factory = MessageFactory.newInstance();
            message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
        } catch (SOAPException | IOException ex) {
            Logger.getLogger(BaseXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
    /**
     * This method returns the Soap Response as a String
     * @param soapResponse - A SoapResponse
     * @return - a String
     */
    public static String SoapResponseMsgToString( SOAPMessage soapResponse ) {
        // Convert the response to XML in a string
        StringWriter sw = new StringWriter();
        try {
            TransformerFactory.newInstance().newTransformer().transform( new DOMSource(soapResponse.getSOAPPart()), new StreamResult(sw) );
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return sw.toString();        
    }

    /**
    * Open HTTPS connection
    * Opening HTTPS connection is done with Java’s HttpsURLConnection. 
    * Instruction to trust all hosts is done with setHostnameVerifier(new TrustAllHosts()) method. Re-factored code is:
    * 
    * Sends SOAP request and saves it in a queue.
    *
    * @param endpointUrl - a URL
    * @param request SOAP Message request object
    * @return SOAP Message response object
    */
    public static SOAPMessage sendSoapRequest(String endpointUrl, SOAPMessage request)  {
        try {
            final boolean isHttps = endpointUrl.toLowerCase().startsWith("https");
            HttpsURLConnection httpsConnection = null;
            // Open HTTPS connection
            if (isHttps) {
                // Create SSL context and trust all certificates
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] trustAll = new TrustManager[] {new TrustAllCertificates()};
                sslContext.init(null, trustAll, new java.security.SecureRandom());
                // Set trust all certificates context to HttpsURLConnection
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                // Open HTTPS connection
                URL url = new URL(endpointUrl);
                httpsConnection = (HttpsURLConnection) url.openConnection();
                // Trust all hosts
                httpsConnection.setHostnameVerifier(new TrustAllHosts());
                // Connect
                httpsConnection.connect();
            }
            // Send HTTP SOAP request and get response
            SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage response = soapConnection.call(request, endpointUrl);
            // Close connection
            soapConnection.close();
            // Close HTTPS connection
            if (isHttps) {
                httpsConnection.disconnect();
            }
            return response;
        } catch (SOAPException | IOException
                | NoSuchAlgorithmException | KeyManagementException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }    

    /**
    * Open HTTPS connection
    * Opening HTTPS connection is done with Java’s HttpsURLConnection. 
    * Instruction to trust all hosts is done with setHostnameVerifier(new TrustAllHosts()) method. Re-factored code is:
    * 
    * Sends SOAP request and saves it in a queue.
    *
    * @param endpointUrl - En URL
    * @param request SOAP Message request object
    * @param access_token - The access_token
    * @return SOAP Message response object
    */
    public static SOAPMessage sendSoapRequestOauth(String endpointUrl, SOAPMessage request, String access_token )  {
        try {
            final boolean isHttps = endpointUrl.toLowerCase().startsWith("https");
            HttpsURLConnection httpsConnection = null;
            
            // Open HTTPS connection
            if (isHttps) {
                // Create SSL context and trust all certificates
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] trustAll = new TrustManager[] {new TrustAllCertificates()};
                sslContext.init(null, trustAll, new java.security.SecureRandom());
                // Set trust all certificates context to HttpsURLConnection
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
                // Open HTTPS connection
                URL url = new URL(endpointUrl);
                httpsConnection = (HttpsURLConnection) url.openConnection();
                // Trust all hosts
                httpsConnection.setHostnameVerifier(new TrustAllHosts());
                // Connect
                httpsConnection.connect();
            }
            // TestKod
            
            
            // Send HTTP SOAP request and get response
            SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();
            request.getMimeHeaders().addHeader("Authorization", "Bearer " + access_token);
            SOAPMessage response = soapConnection.call(request, endpointUrl);
            // Close connection
            soapConnection.close();
            // Close HTTPS connection
            if (isHttps) {
                httpsConnection.disconnect();
            }
            return response;
        } catch (SOAPException | IOException
                | NoSuchAlgorithmException | KeyManagementException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }    



    /**
    * 
    * Open HTTPS connection
    * Opening HTTPS connection is done with Java’s HttpsURLConnection. 
    * Instruction to trust all hosts is done with setHostnameVerifier(new TrustAllHosts()) method. Re-factored code is:
    * 
    * Sends SOAP request and saves it in a queue.
    *
    * @param endpointUrl - En URL
    * @param request SOAP Message request object
    * @return SOAP Message response object
    *    
    */
    public static SOAPMessage sendPirrSoapRequest(String endpointUrl, SOAPMessage request)  {
        try {
            String authorization = new sun.misc.BASE64Encoder().encode(("pirruser:pirruser1").getBytes());
            // borttaget för att få oAuth att funka ...
//            MimeHeaders hd = request.getMimeHeaders();
//            hd.addHeader("Authorization", "Basic " + authorization);
            
            final boolean isHttps = endpointUrl.toLowerCase().startsWith("https");
            HttpsURLConnection httpsConnection = null;
            // Open HTTPS connection
            if (isHttps) {
                // Create SSL context and trust all certificates
                SSLContext sslContext = SSLContext.getInstance("SSL");
                TrustManager[] trustAll = new TrustManager[] {new TrustAllCertificates()};
                sslContext.init(null, trustAll, new java.security.SecureRandom());
                // Set trust all certificates context to HttpsURLConnection
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

                // Open HTTPS connection
                URL url = new URL(endpointUrl);
                httpsConnection = (HttpsURLConnection) url.openConnection();

               // Trust all hosts
                httpsConnection.setHostnameVerifier(new TrustAllHosts());
                // Connect
                httpsConnection.connect();
            }
            // Send HTTP SOAP request and get response
            SOAPConnection soapConnection = SOAPConnectionFactory.newInstance().createConnection();
            SOAPMessage response = soapConnection.call(request, endpointUrl);
            // Close connection
            soapConnection.close();
            // Close HTTPS connection
            if (isHttps) {
                httpsConnection.disconnect();
            }
            return response;
        } catch (SOAPException | IOException
                | NoSuchAlgorithmException | KeyManagementException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }    



// From PirrTestClient
    /**
     * Signs a String content with keystore and add double encoded base64 on top
     *
     * @param content - Stuff to be encode
     * @return - Returns encoded string
     */
    public String encode(String content) {
        String contentType = "application/xml";  // Set default ContentType
        Security.addProvider(new BouncyCastleProvider());
        String encodedText = "";
        byte[] data = signMessage(content, contentType);
        try {
            encodedText = new String(org.bouncycastle.util.encoders.Base64.encode(new String(org.bouncycastle.util.encoders.Base64.encode(data)).getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedText;
    }

    /***
     * Possible to provide ContentType, for test only)
     *
     * @param content - 
     * @param ContentType - 
     * @return - Returns encoded string
     * @throws java.io.UnsupportedEncodingException - not solved in method
     */
    public String encode(String content, String ContentType) throws UnsupportedEncodingException {
        Security.addProvider(new BouncyCastleProvider());
        String encodedText = "";
        byte[] data = signMessage(content, ContentType);
        try {
            encodedText = new String(org.bouncycastle.util.encoders.Base64.encode(new String(org.bouncycastle.util.encoders.Base64.encode(data)).getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedText;
    }

    /**
     * Decode a message twice with base64
     *
     * @param text as String object
     * @return - Returnerar decoded string
     */
    public String decode(String text) {
        String decodedText = "";
        try {
            decodedText = new String(org.bouncycastle.util.encoders.Base64.decode(new String(org.bouncycastle.util.encoders.Base64.decode(text))), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedText;
    }

    /**
     * Method for signing a message from file
     *
     * @param msgContent
     * @param ContentType
     * @return
     */
    private byte[] signMessage(String msgContent, String ContentType) {
        //FileInputStream xmlFile = null;
        try {
            String keystorePassword = "secret";
            String certFileAlias = "PIRR-testklient";
            String privateKeyAlias = "testklient-ext-keypair";
            String privateKeyPassword = "secret";

            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE, KEYSTORE_PROVIDER);
            
//            File keystoreFile = new File( _keystoreFile );
//            File keystoreFile = new File("C:/keystore.bks");
//            InputStream is = new FileInputStream(keystoreFile);            
            InputStream is = BaseXML.class.getResourceAsStream("/soap/keystore.bks");
            keyStore.load(is, keystorePassword.toCharArray());

            X509Certificate cert = (X509Certificate) keyStore.getCertificate(certFileAlias);
            ArrayList certsAndCRLs = new ArrayList();
            certsAndCRLs.add(cert);
            CertStore certStore = CertStore.getInstance("Collection",
                    new CollectionCertStoreParameters(certsAndCRLs),
                    KEYSTORE_PROVIDER);
            PrivateKey pkey = (PrivateKey) keyStore.getKey(privateKeyAlias,
                    privateKeyPassword.toCharArray());

            SMIMESignedGenerator gen = new SMIMESignedGenerator();

            gen.addSigner(pkey, cert, SMIMESignedGenerator.DIGEST_SHA1);
            gen.addCertificatesAndCRLs(certStore);

            MimeBodyPart msg;
            byte[] content = msgContent.getBytes("UTF-8");

            InternetHeaders ih = new InternetHeaders();
            ih.setHeader("Content-Type", ContentType);
            ih.setHeader("Content-Transfer-Encoding", "binary");
            msg = new MimeBodyPart(ih, content);

            MimeMultipart mm = gen.generate(msg, KEYSTORE_PROVIDER);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write("Content-Type: ".getBytes());
            bos.write(mm.getContentType().getBytes());
            bos.write("\n".getBytes());
            bos.write("\n".getBytes());
            mm.writeTo(bos);

            return bos.toByteArray();
        } catch (SMIMEException e) {
            try {
                throw e.getUnderlyingException();
            } catch (CMSException e2) {
                e2.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "NOTHING SIGNED".getBytes();
    }
    /**
     * This method uses SQL to get the OrdinationsId direct from the database
     * @param ereceptid Created from Pirr when creates a new recepie
     * @param OracleServiceName - ex. INT6 / PTRR 
     * @return - Ett OrdinationsId
     */
    public static String getOrdinationsId( String ereceptid, String OracleServiceName ) {
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        String OrdinationsId = DB_Access.getStringValue(con, "select r.ID from RR_PROD.RECEPT r, RR_PROD.FORSKRORIG f where r.RRFORSKRORIGINAL = f.id and f.ERECRECEPTID = '" + ereceptid + "'", "ID");
        DB_Access.closeConnection(con);
      
        return OrdinationsId;
    }
    
    /**
     * This method uses SQL to check if the patient is a DOS patient direct from the database
     * @param personnummer - Ett personnummer
     * @param OracleServiceName - ex. INT6 / PTRR
     * @return - true/false
     */
    public static boolean isDosPatient( String personnummer, String OracleServiceName ) {
        Boolean isdospatient = false;
        Connection con = DB_Access.getConnection("jdbc:oracle:thin:@td02-scan.systest.receptpartner.se:1521/" + OracleServiceName, "ETCDBA", "ETCDBA");
        String retVal = DB_Access.getStringValue(con, "SELECT COUNT(*) antal FROM RR_PROD.ORDINATIONSLISTA WHERE DOSUNDERLAGSVERSION IS NOT NULL AND PERSONNUMMER = '" + personnummer + "'", "antal");
        DB_Access.closeConnection(con);
        int antal = Integer.parseInt(retVal);
        if (antal > 0) { isdospatient = true; }
        return isdospatient;
    }
}
