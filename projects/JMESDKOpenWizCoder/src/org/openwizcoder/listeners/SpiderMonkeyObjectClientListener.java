package org.openwizcoder.listeners;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import org.openwizcoder.JMESpiderMonkeyClientMain;
import org.openwizcoder.messages.ObjectShare;

public class SpiderMonkeyObjectClientListener implements MessageListener<Client> {
    JMESpiderMonkeyClientMain app;
    public void messageReceived(Client source, Message message) {
        if (message instanceof ObjectShare) {
                // do something with the message
                ObjectShare helloMessage = (ObjectShare) message;
                //System.out.println("Client #"+source.getId()+" received: '"+helloMessage.getSomething()+"'");
            if(app !=null){
                app.UserJoin(source, helloMessage);
                
                if(app.user == null){
                    app.user = new ObjectShare();
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