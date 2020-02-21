/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nhpj.soap;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * Detta är en stödklass för Tickets
 * @author nhpj
 */
public class TrustAllCertificates implements X509TrustManager {
    /**
     * Denna metod sätter  
     * @param certs - certs
     * @param authType -  authType
     */
    public void checkClientTrusted(X509Certificate[] certs, String authType) {
    }
    /**
     * Denna metod sätter 
     * @param certs - certs
     * @param authType - authType
     */
    public void checkServerTrusted(X509Certificate[] certs, String authType) {
    }
    /**
     * Denna metod retyrnerar null;
     * @return - null
     */
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}
