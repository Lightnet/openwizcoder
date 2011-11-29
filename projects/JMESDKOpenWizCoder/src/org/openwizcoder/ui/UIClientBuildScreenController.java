/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Lightnet
 */

public class UIClientBuildScreenController extends UIBasicScreenController{
    public String Servername = "App Server";
    public String Serverip = "127.0.0.1";
    public String Servertcp = "5110";
    public String Serverudp = "5110";
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    @NiftyEventSubscriber(id = "BtRequestSpawn")
    public void BtRequestSpawnnClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("Button press");
    }
}
