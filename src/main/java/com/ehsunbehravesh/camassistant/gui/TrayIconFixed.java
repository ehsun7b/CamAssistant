package com.ehsunbehravesh.camassistant.gui;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.TrayIcon;

/**
 *
 * @author Ehsun Behravesh
 */
public class TrayIconFixed extends TrayIcon {

    public TrayIconFixed(Image image) {
        super(image);
    }

    public TrayIconFixed(Image image, String tooltip) {
        super(image, tooltip);
    }

    public TrayIconFixed(Image image, String tooltip, PopupMenu popup) {
        super(image, tooltip, popup);
    }

    @Override
    public String toString() {
        return "123";
    }
    
    
}
