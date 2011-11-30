package org.openwizcoder.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import org.openwizcoder.messages.RequestSpawnMsg;

/**
 *
 * @author Lightnet
 */

public class UserRequesttServerListener extends BaseServerListener {
    
    @Override
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof RequestSpawnMsg) {
            if((getApp() !=null)&&(message != null)){
                
            }else{
                //System.out.print("\nserver error shareobject");
            }                    
        }
    }
    
}