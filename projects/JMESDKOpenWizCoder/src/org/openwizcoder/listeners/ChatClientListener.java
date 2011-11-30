package org.openwizcoder.listeners;

import com.jme3.network.Client;
import com.jme3.network.Message;
import org.openwizcoder.messages.ChatMsg;

/**
 *
 * @author Lightnet
 */

public class ChatClientListener extends BaseClientListener{
    
    @Override
    public void messageReceived(Client source, Message message) {
        if (message instanceof ChatMsg) {
             if((getApp() !=null)&&(message != null)){
                
                 
                 
            }else{
                 //System.out.print("\nClient error shareobject 2 con");
             }
        }else{
            //System.out.print("\nClient error shareobject");
        }
    }    
}