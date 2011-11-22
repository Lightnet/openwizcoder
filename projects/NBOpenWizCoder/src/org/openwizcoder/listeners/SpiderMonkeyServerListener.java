package org.openwizcoder.listeners;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import org.openwizcoder.messages.HelloMessage;

public class SpiderMonkeyServerListener implements MessageListener<HostedConnection> {
    public void messageReceived(HostedConnection source, Message message) {
        if (message instanceof HelloMessage) {
            // do something with the message
            HelloMessage helloMessage = (HelloMessage) message;
            System.out.println("Server received '" +helloMessage.getSomething() +"' from client #"+source.getId());
            
            Message message2 = new HelloMessage("Hello World! From Server!");
            source.send(message2);
        } // else....
    }
}