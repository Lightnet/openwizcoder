package org.openwizcoder.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import org.openwizcoder.messages.ChatMsg;

/**
 *
 * @author Lightnet
 */

public class ChatServerListener extends BaseServerListener {
    
    @Override
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof ChatMsg) {
            if((getApp() !=null)&&(message != null)){
                getApp().UpdatePlayerObjectServer(source,message);
            }else{
                //System.out.print("\nserver error shareobject");
            }                    
        }
    }
    
}