package org.openwizcoder;

import org.openwizcoder.controllers.ObjectPlayerController;
import org.openwizcoder.messages.ObjectShareMsg;
import org.openwizcoder.messages.HelloMsg;
import org.openwizcoder.listeners.ObjectShareServerListener;
import org.openwizcoder.listeners.HelloMsgServerListener;
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
 *
 * @author Lightnet
 */

public class JMEServerMain extends OpenWizCoderApp {
      
    public List<ObjectPlayerController> players = new ArrayList<ObjectPlayerController>();
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
        Serializer.registerClass(HelloMsg.class);
        Serializer.registerClass(ObjectShareMsg.class);
        try {
            Server myServer = Network.createServer(NAME, VERSION, PORT, UDP_PORT);                                    
            ObjectShareServerListener.setApp(this);
            ObjectShareServerListener objlistener = new ObjectShareServerListener();
            
            myServer.addMessageListener(new HelloMsgServerListener(), HelloMsg.class);            
            myServer.addMessageListener(objlistener, ObjectShareMsg.class);
            
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
        
        for (ObjectPlayerController player : players ){   
            if(source.getId() == Integer.parseInt( player.smobjshare.userid)){
                ObjectShareMsg shareobject = (ObjectShareMsg) message;                
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
            
            ObjectPlayerController newplayer = new ObjectPlayerController(spl);
            newplayer.smobjshare = new ObjectShareMsg();
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
        JMEServerMain app = new JMEServerMain();      
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