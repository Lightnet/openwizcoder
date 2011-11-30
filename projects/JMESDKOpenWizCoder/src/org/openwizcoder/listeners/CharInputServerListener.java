package org.openwizcoder.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import org.openwizcoder.messages.CharInputMsg;

/**
 *
 * @author Lightnet
 */

public class CharInputServerListener extends BaseServerListener {
    
    @Override
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof CharInputMsg) {      
            if((getApp() !=null)&&(message != null)){
                System.out.print("\nuser input found...");
                if(getApp() !=null){
                    getApp().UpdatePlayerInputServer(source, message);
                }
            }else{
                //System.out.print("\nserver error shareobject");
            }                    
        } // else....
    }
    
}