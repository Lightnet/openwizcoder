package org.openwizcoder.listeners;

import com.jme3.network.Client;
import com.jme3.network.Message;
import org.openwizcoder.messages.ObjectShareMsg;

public class ObjectShareClientListener extends BaseClientListener{
    
    @Override
    public void messageReceived(Client source, Message message) {
        if (message instanceof ObjectShareMsg) {
             if((getApp() !=null)&&(message != null)){
                getApp().UpdatePlayerObjectClient(source,message);
                System.out.print("\nClient got shareobject");
                //if(getApp().ClientID == null){
                   getApp().ClientID = source.getId();
                //}
            }else{
                 System.out.print("\nClient error shareobject 2 con");
             }
        }else{
            System.out.print("\nClient error shareobject");
        }
    }    
}