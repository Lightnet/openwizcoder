/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.ui;

import com.jme3.network.Client;
import com.jme3.network.Message;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import org.openwizcoder.messages.HelloMessage;
import org.openwizcoder.messages.SMObjectShare;

/**
 *
 * @author HP_Administrator
 */
public class UIConsoleScreenController implements ScreenController{
    public Client myClient;
    
    @Override
    public void bind(Nifty nifty, Screen screen) {
        
    }
    
    @Override
    public void onStartScreen() {
        
    }
    
    @Override
    public void onEndScreen() {
        
    }
    
    public void SendClientMessage(){
        System.out.print("\nUI SCREEN PRINT");
        if(myClient !=null){
            Message message = new HelloMessage("Hello World!");
            myClient.send(message);
            
            
            SMObjectShare smobj = new SMObjectShare();
            smobj.userid = Integer.toString(myClient.getId());
            myClient.send(smobj);
            System.out.print("PASS");
        }else{
            System.out.print("FAIL");
        }
    }

    public void SetClient(Client myClient) {
        this.myClient = myClient;
    }
}
