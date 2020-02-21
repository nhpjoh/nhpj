/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nhpj.soap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Detta är en stödklass för Tickets klassen
 * @author nhpj
 */
public class TrustAllHosts implements HostnameVerifier{
    /**
     * Denna metod verifierar host och session
     * @param hostname - hoat
     * @param session - session
     * @return boolean - true/false
     */
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
