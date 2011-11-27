package org.openwizcoder.listeners;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import org.openwizcoder.JMESpiderMonkeyServerMain;
import org.openwizcoder.messages.ObjectShare;

public class SpiderMonkeyObjPlayerServerListener implements MessageListener<HostedConnection> {
    
    JMESpiderMonkeyServerMain app;
    
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof ObjectShare) {
            // do something with the message
            //SMObjectShare helloMessage = (SMObjectShare) message;
            //System.out.println("Server received '" +helloMessage.getSomething() +"' from client #"+source.getId());
            
            //Message message2 = new HelloMessage("Hello World! From Server!");
            //source.send(message2);
            
            if((app !=null)&&(message != null)){
                app.UserJoin(source, message);
                //System.out.print("\nUSER JOIN SERVER!");
            }                        
        } // else....
    }
    
    public void SetApp(JMESpiderMonkeyServerMain app){
        this.app = app;
    }
}