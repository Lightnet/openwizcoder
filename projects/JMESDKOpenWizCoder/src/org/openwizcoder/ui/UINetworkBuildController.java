/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.TextFieldChangedEvent;
import de.lessvoid.nifty.screen.Screen;

/**
 *
 * @author Lightnet
 */

public class UINetworkBuildController extends UIBasicScreenController{
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
    
    @NiftyEventSubscriber(id = "servername")
    public void onServerNameChanged(final String id, final TextFieldChangedEvent event) {
        TextField score = event.getTextFieldControl();
        Servername = event.getText();
        //System.out.print("text:"+text);
    }
    
    @NiftyEventSubscriber(id = "serverip")
    public void onServerIPChanged(final String id, final TextFieldChangedEvent event) {
        TextField score = event.getTextFieldControl();
        Serverip = event.getText();
        //System.out.print("text:"+text);
    }
    
    @NiftyEventSubscriber(id = "tcpport")
    public void onTCPChanged(final String id, final TextFieldChangedEvent event) {
        TextField score = event.getTextFieldControl();
        Servertcp = event.getText();
        //System.out.print("text:"+text);
    }
    
    @NiftyEventSubscriber(id = "udpport")
    public void onUDPChanged(final String id, final TextFieldChangedEvent event) {
        TextField score = event.getTextFieldControl();
        Serverudp = event.getText();
        //System.out.print("text:"+text);
    }
    
    @NiftyEventSubscriber(id = "BtHost")
    public void BtHostClicked(final String id, final ButtonClickedEvent event) {        
        //nifty.gotoScreen("end");
        GetApp().Init_ServerConfig(Servername,Serverip, Integer.parseInt(Servertcp), Integer.parseInt(Serverudp));
        System.out.print("\nButton press host");
        
    }
    
    @NiftyEventSubscriber(id = "BtConnect")
    public void BtConnectClicked(final String id, final ButtonClickedEvent event) {        
        GetApp().Init_ClientConfig(Servername,Serverip, Integer.parseInt(Servertcp), Integer.parseInt(Serverudp));
        System.out.print("\nButton press connect");
        //nifty.gotoScreen("end");
    }
    
    @NiftyEventSubscriber(id = "BtDisconnect")
    public void BtDisconnectClicked(final String id, final ButtonClickedEvent event) {
        System.out.print("\nButton press");
    }
            
}
