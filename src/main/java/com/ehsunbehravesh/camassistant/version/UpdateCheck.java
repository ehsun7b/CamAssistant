package com.ehsunbehravesh.camassistant.version;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ehsun Behravesh
 */
public class UpdateCheck extends Observable implements Runnable {

    private static final Logger log = Logger.getLogger(UpdateCheck.class);

    private URL url;

    public UpdateCheck() {
    }

    @Override
    public void run() {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            Properties config = new Properties();
            config.load(is);

            Version stable = null, alpha = null, beta = null;

            url = new URL(config.getProperty("update_url").concat("?ts=" + System.currentTimeMillis()));
            URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches(false);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(in);

            // getting stable version
            Element docElement = doc.getDocumentElement();
            NodeList list = docElement.getElementsByTagName("stable");

            if (list.getLength() > 0) {
                Element stableElement = (Element) list.item(0);
                list = stableElement.getElementsByTagName("version");

                if (list.getLength() > 0) {
                    Element versionElement = (Element) list.item(0);
                    String textContent = versionElement.getTextContent();

                    try {
                        int versionNumber = Integer.parseInt(textContent);
                        String versionName = ((Element) stableElement.getElementsByTagName("versionName")
                                .item(0)).getTextContent();
                        String releaseDate = ((Element) stableElement.getElementsByTagName("releaseDate")
                                .item(0)).getTextContent();

                        stable = new Version(versionNumber, versionName, VersionType.STABLE, releaseDate);

                    } catch (NumberFormatException | DOMException ex) {
                        log.error("Error in parsing version number.", ex);
                    }
                }
            }

            // getting beta version            
            list = docElement.getElementsByTagName("beta");

            if (list.getLength() > 0) {
                Element betaElement = (Element) list.item(0);
                list = betaElement.getElementsByTagName("version");

                if (list.getLength() > 0) {
                    Element versionElement = (Element) list.item(0);
                    String textContent = versionElement.getTextContent();

                    try {
                        int versionNumber = Integer.parseInt(textContent);
                        String versionName = ((Element) betaElement.getElementsByTagName("versionName")
                                .item(0)).getTextContent();
                        String releaseDate = ((Element) betaElement.getElementsByTagName("releaseDate")
                                .item(0)).getTextContent();

                        beta = new Version(versionNumber, versionName, VersionType.BETA, releaseDate);

                    } catch (NumberFormatException | DOMException ex) {
                        log.error("Error in parsing version number.", ex);
                    }
                }
            }

            // getting alpha version            
            list = docElement.getElementsByTagName("alpha");

            if (list.getLength() > 0) {
                Element alphaElement = (Element) list.item(0);
                list = alphaElement.getElementsByTagName("version");

                if (list.getLength() > 0) {
                    Element versionElement = (Element) list.item(0);
                    String textContent = versionElement.getTextContent();

                    try {
                        int versionNumber = Integer.parseInt(textContent);
                        String versionName = ((Element) alphaElement.getElementsByTagName("versionName")
                                .item(0)).getTextContent();
                        String releaseDate = ((Element) alphaElement.getElementsByTagName("releaseDate")
                                .item(0)).getTextContent();

                        alpha = new Version(versionNumber, versionName, VersionType.ALPHA, releaseDate);

                    } catch (NumberFormatException | DOMException ex) {
                        log.error("Error in parsing version number.", ex);
                    }
                }
            }

            setChanged();
            notifyObservers(new Version[]{stable, beta, alpha});
        } catch (Exception ex) {            
            log.error("Error in loading app config parameters. File config.properties must be in the classpath.", ex);
        }
    }

    public static void main(String[] args) {
        UpdateCheck uc = new UpdateCheck();
        uc.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object arg) {
                if (arg != null) {
                    Version[] versions = (Version[]) arg;

                    for (Version version : versions) {
                        if (version != null) {
                            System.out.println(version);
                        }
                    }
                }
            }
        });
        Thread thread = new Thread(uc);
        thread.start();
    }
}
