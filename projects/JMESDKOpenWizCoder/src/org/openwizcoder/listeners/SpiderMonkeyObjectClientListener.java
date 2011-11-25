package org.openwizcoder.listeners;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import org.openwizcoder.JMESpiderMonkeyClientMain;
import org.openwizcoder.messages.SMObjectShare;

public class SpiderMonkeyObjectClientListener implements MessageListener<Client> {
    JMESpiderMonkeyClientMain app;
    public void messageReceived(Client source, Message message) {
        if (message instanceof SMObjectShare) {
                // do something with the message
                SMObjectShare helloMessage = (SMObjectShare) message;
                //System.out.println("Client #"+source.getId()+" received: '"+helloMessage.getSomething()+"'");
            if(app !=null){
                app.UserJoin(source, helloMessage);
                
                if(app.user == null){
                    app.user = new SMObjectShare();
                    app.user.userid = Integer.toString(source.getId());
                    //System.out.print("\nCLIENT SMOBJ");
                }
            }                  
        }
    }
    
    public void SetApp(JMESpiderMonkeyClientMain app){
        this.app = app;
    }
    
}