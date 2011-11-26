/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import com.jme3.network.Client;
import com.jme3.network.Server;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author HP_Administrator
 */
public class UIBasicScreenController implements ScreenController{
    public Nifty nifty;
    public Screen screen;
    public String ScreenNameStart = "start";
    public String ScreenNameEnd = "end";
    
    public Client MyClient;
    public Server MyServer;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    public String getScreenNameStart(){
        return ScreenNameStart;        
    }
    
    public String getScreenNameEnd(){
        return ScreenNameEnd; 
    }
    
    public void SetClient(Client myClient) {
        this.MyClient = myClient;
    }
    
    public void SetServer(Server server) {
        this.MyServer = server;
    }
        
}
