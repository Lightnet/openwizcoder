/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder;

import com.jme3.system.AppSettings;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP_Administrator
 */
public class OpenWizCoderMain {
    public static void main(String[] args) {   
        Logger.getLogger("").setLevel(Level.SEVERE);
        //Logger.getLogger("de.lessvoid").setLevel(Level.SEVERE);
        //Logger.getLogger("de.lessvoid.*").setLevel(Level.SEVERE);
        //Logger.getLogger("com.jme3").setLevel(Level.SEVERE);
        OpenWizCoderApp app = new OpenWizCoderApp();      
        // create new JME appsettings
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Open Wiz Coder");
        settings.setWidth(640);
        settings.setHeight(480);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();  
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.print("Testing...");
            }
        });
    }
}
