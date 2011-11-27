package org.openwizcoder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.openwizcoder.controllers.SMObjectPlayerController;
import org.openwizcoder.messages.ObjectShare;
import org.openwizcoder.messages.HelloMessage;
import org.openwizcoder.listeners.SpiderMonkeyObjPlayerServerListener;
import org.openwizcoder.listeners.SpiderMonkeyServerListener;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openwizcoder.listeners.BaseServerConnectionListener;

/**
 * you can embed a jme canvas inside a swing application
 * @author pgi
 */
public class JMESpiderMonkeyServerMain extends OpenWizCoderMain {
      
    public List<SMObjectPlayerController> players = new ArrayList<SMObjectPlayerController>();
    public static final String NAME = "App Server";
    public static final int VERSION = 1;
    public static final int PORT = 5110;
    public static final int UDP_PORT = 5110;    
    public Server server;
    
    @Override
    public void simpleInitApp() {
        //java.awt.EventQueue.invokeLater(new Runnable() {
            //public void run() {
                try {
                    Init_server();
                } catch (Exception e) {
                }
            //}
        //});
      
        // activate windowed input behaviour
        flyCam.setDragToRotate(false);
        mouseInput.setCursorVisible(true);
        this.pauseOnFocus = false;
        //this.flyCam.setDragToRotate(false);
        this.flyCam.setEnabled(false);
        //viewPort.setBackgroundColor(ColorRGBA.Blue);        
    }
    
    public void Init_server() throws IOException{
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(ObjectShare.class);
        try {
            Server myServer = Network.createServer(NAME, VERSION, PORT, UDP_PORT);                                    
            SpiderMonkeyObjPlayerServerListener objlistener = new SpiderMonkeyObjPlayerServerListener();
            objlistener.SetApp(this);
            
            myServer.addMessageListener(new SpiderMonkeyServerListener(), HelloMessage.class);            
            myServer.addMessageListener(objlistener, ObjectShare.class);
            
            //this code deal with dis/connect from client
            BaseServerConnectionListener serverlistenconnection = new BaseServerConnectionListener();
            myServer.addConnectionListener(serverlistenconnection);
            
            myServer.start();                        
        } catch (Exception e) {
            
        }
    }
    
    
    public void UserJoin(HostedConnection source,Message message){
        boolean bfound = false;
        
        //SMObjectShare SMObjshare = (SMObjectShare)smobj;
        
        for (SMObjectPlayerController player : players ){   
            if(source.getId() == Integer.parseInt( player.smobjshare.userid)){
                ObjectShare shareobject = (ObjectShare) message;                
                player.smobjshare = shareobject;                
                //player.getSpatial().setLocalTranslation(shareobject.x, shareobject.y, shareobject.z);                
                bfound = true;
            }    
        }
        
        if(!bfound){
            Box objplayer = new Box(Vector3f.ZERO, 1, 1, 1);
            Geometry geomplayer = new Geometry("Box", objplayer);
            Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("m_Color", ColorRGBA.Brown);
            geomplayer.setMaterial(mat);        
            Spatial spl = (Spatial) geomplayer;
            
            SMObjectPlayerController newplayer = new SMObjectPlayerController(spl);
            newplayer.smobjshare = new ObjectShare();
            newplayer.smobjshare.userid = Integer.toString(source.getId());            
            newplayer.smobjshare.userid = Integer.toString(source.getId());
            
            source.send(newplayer.smobjshare);
            rootNode.attachChild(newplayer.getSpatial());
            players.add(newplayer);
        }        
    }
    
    
    //testing for material change
    public void PersonSetSkinColor(ColorRGBA color){
        //Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.setColor("m_Color", color);
        //geomperson.setMaterial(mat);      
    }
  
    public static void main(String[] args) {      
        JMESpiderMonkeyServerMain app = new JMESpiderMonkeyServerMain();      
        // create new JME appsettings
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Server");
        settings.setWidth(640);
        settings.setHeight(480);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.start();  
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.print("Testing...");
            }
        });
    }
}