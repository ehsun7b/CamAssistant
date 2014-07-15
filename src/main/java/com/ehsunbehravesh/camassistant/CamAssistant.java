package com.ehsunbehravesh.camassistant;

import com.ehsunbehravesh.camassistant.gui.SplashFrame;
import com.ehsunbehravesh.camassistant.gui.TrayIconFixed;
import com.ehsunbehravesh.camassistant.version.Version;
import com.ehsunbehravesh.camassistant.version.VersionType;
import com.ehsunbehravesh.utils.JFrameUtils;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;

/**
 *
 * @author Ehsun Behravesh
 */
public class CamAssistant implements ActionListener {

    private static final Logger log = Logger.getLogger(CamAssistant.class);
    private final Version version;
    private SplashFrame splashFrame;
    private TrayIconFixed trayIcon;
    private Properties messages;
    private Locale locale;
    private JPopupMenu trayMenu;
    private JMenuItem mniExit, mniSettings;
    

    public CamAssistant() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            log.warn("System look and feel can not be detected.", ex);
        }

        showSplash();
        version = new Version(1, "0.0.1", VersionType.ALPHA, "July 15, 2014");

        init();
        showTrayIcon();

        hideSplash();
    }

    private void init() {
        locale = Locale.getDefault();

        if (locale == null) {
            locale = Locale.ENGLISH;
        }

        loadMessages();

        if (!SystemTray.isSupported()) {
            log.error("System Tray is not supported by your system!");
            JOptionPane.showMessageDialog(null, messages.getProperty("msg_system_tray_not_supported"), messages.getProperty("error"), JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    private void showSplash() {
        splashFrame = new SplashFrame();
        JFrameUtils.setCenterOfScreen(splashFrame);
        splashFrame.setVisible(true);
    }

    public static void main(String[] args) {
        CamAssistant camAssistant = new CamAssistant();
    }

    private void hideSplash() {
        splashFrame.setVisible(false);
        splashFrame.dispose();
    }

    private void showTrayIcon() {
        SystemTray systemTray = SystemTray.getSystemTray();
        trayIcon = new TrayIconFixed(getTrayImage(), messages.getProperty("tlp_tray_icon"));
        trayIcon.setImageAutoSize(true);

        try {
            systemTray.add(trayIcon);
        } catch (AWTException ex) {
            log.error("Showing system tray icon failed!", ex);
            System.exit(-1);
        }

        trayMenu = new JPopupMenu();

        mniExit = new JMenuItem(messages.getProperty("lbl_exit"));
        mniSettings = new JMenuItem(messages.getProperty("lbl_settings"));

        mniSettings.addActionListener(this);
        mniExit.addActionListener(this);
        
        trayMenu.add(mniSettings);
        trayMenu.add(mniExit);
        

        trayIcon.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {                
                if (e.getButton() == MouseEvent.BUTTON3) {
                    System.out.println("clicked");
                    int x = e.getXOnScreen();
                    int y = e.getYOnScreen();
                                        
                    trayMenu.show(null, x, y);
                } else {
                    trayMenu.setVisible(false);
                }
            }
            
        });
    }

    private void loadMessages() {
        try {
            String langCode = locale.getLanguage();
            messages = new Properties();
            messages.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("msg_" + langCode + ".properties"));
        } catch (IOException ex) {
            log.warn("Messages for your locale is not available. English locale is used.", ex);
            try {
                messages.load(Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("msg_en.properties"));
            } catch (IOException ex1) {
                log.error("Messages for English locale is not available. Exit!", ex);
                System.exit(-1);
            }
        }
    }

    private Image getTrayImage() {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("tray_icon.png");
            ImageIcon icon = new ImageIcon(url);
            return icon.getImage();
        } catch (NullPointerException ex) {
            log.error("Tray icon image can not found! tray_icon.png. Exit!", ex);
            System.exit(-1);
        }

        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

}
