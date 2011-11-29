/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openwizcoder.listeners;

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import org.openwizcoder.OpenWizCoderApp;
import org.openwizcoder.messages.ObjectShareMsg;

/**
 *
 * @author Lightnet
 */

public class BaseServerConnectionListener implements ConnectionListener{
    
    public static OpenWizCoderApp app;
    
    //client connect
    public void connectionAdded(Server server, HostedConnection conn) {
        System.out.print("\nUser connected:" + conn.getAddress());
        System.out.print(" ID: " + conn.getId());
        ObjectShareMsg objshare = new ObjectShareMsg();
        objshare.userid = Integer.toString(conn.getId());
        if(getApp() !=null){
            System.out.print("\nconnect var server pass shareobject");
            getApp().ServerJoinSO(conn,objshare);
            conn.send(objshare);
            
        }else{
            System.out.print("\nconnect var server error shareobject");
        }
    }
    //client disconnector lose connection
    public void connectionRemoved(Server server, HostedConnection conn) {
         System.out.print("\nUser disconnected:" + conn.getAddress());
         System.out.print(" ID: " + conn.getId());
    }
    
    public static void setApp(OpenWizCoderApp _app){
        app = _app;
    }
    
    public static OpenWizCoderApp getApp(){
        return app;
    }
    
}
