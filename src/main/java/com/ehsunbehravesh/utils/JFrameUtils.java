package com.ehsunbehravesh.utils;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Ehsun Behravesh
 */
public class JFrameUtils {

    public static void setCenterOfScreen(JDialog dialog) {
        Dimension size = dialog.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point position = new Point(screenSize.width / 2 - (size.width / 2), screenSize.height / 2 - (size.height / 2));
        dialog.setLocation(position);
    }
    
    public static void setCenterOfScreen(JFrame frame) {
        Dimension size = frame.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Point position = new Point(screenSize.width / 2 - (size.width / 2), screenSize.height / 2 - (size.height / 2));
        frame.setLocation(position);
    }

    public static void setCenterOfParent(JFrame parent, JDialog dialog) {
        Point parentPosition = parent.getLocation();
        Dimension parentSize = parent.getSize();
        Dimension size = dialog.getSize();
        Point position = new Point(parentPosition.x + (parentSize.width / 2 - size.width / 2), parentPosition.y + (parentSize.height / 2 - size.height / 2));
        dialog.setLocation(position);
    }

    public static void setCenterOfParent(JDialog parent, JDialog dialog) {
        Point parentPosition = parent.getLocation();
        Dimension parentSize = parent.getSize();
        Dimension size = dialog.getSize();
        Point position = new Point(parentPosition.x + (parentSize.width / 2 - size.width / 2), parentPosition.y + (parentSize.height / 2 - size.height / 2));
        dialog.setLocation(position);
    }
}
