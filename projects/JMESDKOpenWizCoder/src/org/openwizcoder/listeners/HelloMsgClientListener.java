package org.openwizcoder.listeners;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import org.openwizcoder.JMEClientMain;
import org.openwizcoder.OpenWizCoderApp;
import org.openwizcoder.messages.ObjectShareMsg;

public class HelloMsgClientListener implements MessageListener<Client> {
    
    OpenWizCoderApp app;
    
    public void messageReceived(Client source, Message message) {
        if (message instanceof ObjectShareMsg) {
                // do something with the message
                ObjectShareMsg helloMessage = (ObjectShareMsg) message;
                //System.out.println("Client #"+source.getId()+" received: '"+helloMessage.getSomething()+"'");
            if(app !=null){
                //app.UserJoin(source, helloMessage);
                
                //if(app.user == null){
                    //app.user = new ObjectShare();
                    //app.user.userid = Integer.toString(source.getId());
                    //System.out.print("\nCLIENT SMOBJ");
                //}
            }                  
        }
    }
    
    public void SetApp(JMEClientMain app){
        this.app = app;
    }
    
}