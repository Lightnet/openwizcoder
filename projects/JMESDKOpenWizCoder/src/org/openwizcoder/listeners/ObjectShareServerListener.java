package org.openwizcoder.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import org.openwizcoder.JMEServerMain;
import org.openwizcoder.OpenWizCoderApp;
import org.openwizcoder.messages.ObjectShareMsg;

/**
 *
 * @author Lightnet
 */

public class ObjectShareServerListener extends BaseServerListener {
    
    @Override
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof ObjectShareMsg) {
            // do something with the message
            //SMObjectShare helloMessage = (SMObjectShare) message;
            //System.out.println("Server received '" +helloMessage.getSomething() +"' from client #"+source.getId());
            
            //Message message2 = new HelloMessage("Hello World! From Server!");
            //source.send(message2);
            
            if((getApp() !=null)&&(message != null)){
                //app.UserJoin(source, message);
                //UpdatePlayerObjectClient
                getApp().UpdatePlayerObjectServer(source,message);
                System.out.print("Server got shareobject");
                //System.out.print("\nUSER JOIN SERVER!");
            }else{
                System.out.print("server error shareobject");
            }                    
        } // else....
    }
    
}