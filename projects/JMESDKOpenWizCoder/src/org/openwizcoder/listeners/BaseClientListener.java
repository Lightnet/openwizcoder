package org.openwizcoder.listeners;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import org.openwizcoder.OpenWizCoderApp;
import org.openwizcoder.messages.HelloMsg;

/**
 *
 * @author Lightnet
 */

public class BaseClientListener implements MessageListener<Client> {
    public static OpenWizCoderApp app;
    
    public void messageReceived(Client source, Message message) {
        //if (message instanceof HelloMsg) {
            // do something with the message
            //HelloMsg helloMessage = (HelloMsg) message;
            //System.out.print("\nClient #"+source.getId()+" received: '"+helloMessage.getSomething()+"'");
        //}
    }
    
    public static void setApp(OpenWizCoderApp _app){
        app = _app;
    }
    
    public static OpenWizCoderApp getApp(){
        return app;
    }
}