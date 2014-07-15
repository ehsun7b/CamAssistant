package com.ehsunbehravesh.camassistant.gui;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.SystemTray;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.apache.log4j.Logger;

/**
 *
 * @author Ehsun Behravesh
 */
public class SplashFrame extends JFrame {

    private final Logger log = Logger.getLogger(SplashFrame.class);
    private final JLabel lblImage;

    public SplashFrame() throws HeadlessException {
        setTitle("sdf");
        
        setSize(new Dimension(640, 480));
        lblImage = new JLabel(getImage());
        setContentPane(lblImage);
        setUndecorated(true);        
    }

    private ImageIcon getImage() {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("splash.jpg");
            return new ImageIcon(url);
        } catch (NullPointerException ex) {
            log.warn("Splash screen not found! splash.jpg", ex);
        } 
        
        return null;
    }
}
