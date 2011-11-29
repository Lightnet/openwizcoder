package org.openwizcoder.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import org.openwizcoder.OpenWizCoderApp;
import org.openwizcoder.messages.HelloMsg;

public class BaseServerListener implements MessageListener<HostedConnection> {
    public static OpenWizCoderApp app;
    
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof HelloMsg) {
            // do something with the message
            HelloMsg helloMessage = (HelloMsg) message;
            System.out.println("\nServer received '" +helloMessage.getSomething() +"' from client #"+source.getId());
            
            Message message2 = new HelloMsg("Hello World! From Server!");
            source.send(message2);
        } // else....
    }
    
    public static void setApp(OpenWizCoderApp _app){
        app = _app;
    }
    
    public static OpenWizCoderApp getApp(){
        return app;
    }
    
}