/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.listeners;

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

/**
 *
 * @author John
 */
public class BaseServerConnectionListener implements ConnectionListener{
    //client connect
    public void connectionAdded(Server server, HostedConnection conn) {
        System.out.print("\nUser connected:" + conn.getAddress());
        System.out.print(" ID: " + conn.getId());
    }
    //client disconnector lose connection
    public void connectionRemoved(Server server, HostedConnection conn) {
         System.out.print("\nUser disconnected:" + conn.getAddress());
         System.out.print(" ID: " + conn.getId());
    }
    
}
