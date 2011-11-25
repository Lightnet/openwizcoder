package org.openwizcoder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.openwizcoder.controllers.SMObjectPlayerController;
import org.openwizcoder.messages.SMObjectShare;
import org.openwizcoder.messages.HelloMessage;
import org.openwizcoder.listeners.SpiderMonkeyClientListener;
import org.openwizcoder.listeners.SpiderMonkeyObjectClientListener;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openwizcoder.ui.UIClientScreenController;


/**
 * you can embed a jme canvas inside a swing application
 * @author pgi
 */
public class JMESpiderMonkeyClientMain extends SimpleApplication implements ScreenController {
      
    public List<SMObjectPlayerController> players = new ArrayList<SMObjectPlayerController>();
    public static final String NAME = "App Server";
    public static final int VERSION = 1;
    public static final int PORT = 5110;
    public static final int UDP_PORT = 5110;    
    public Client myClient;
    public Nifty nifty;
    public SMObjectShare user;
    
    @Override
    public void simpleInitApp() {
        
        //java.awt.EventQueue.invokeLater(new Runnable() {
            //public void run() {
                try {
                    Init_client();
                } catch (Exception e) {
                }
            //}
        //});
                Init_nifty();
        // activate windowed input behaviour
        flyCam.setDragToRotate(false);
        mouseInput.setCursorVisible(true);
        this.pauseOnFocus = false;
        //this.flyCam.setDragToRotate(false);
        this.flyCam.setEnabled(false);
        //viewPort.setBackgroundColor(ColorRGBA.Blue);        
        
        inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
        //inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                                      //new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(analogListener, new String[]{"Left", "Right", "Rotate"}); 
    }
    
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Rotate")) {
                System.out.print("\nrotate");
                //player.rotate(0, value*speed, 0);
            }
            if (name.equals("Right")) {
                System.out.print("\nright");
                MoveObject(0.01f,0.0f,0.0f);
            }
            if (name.equals("Left")) {
                MoveObject(-0.01f,0.0f,0.0f);
                System.out.print("\nleft");
            }
        }
    };
        
    public void Init_nifty(){
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort);
        nifty = niftyDisplay.getNifty();
        
        UIClientScreenController screenControl = new UIClientScreenController();
        nifty.fromXml("Interface/UI_Client.xml", "start", screenControl);
        //nifty.fromXml("Interface/Chat.xml", "start", screenControl); 
        //nifty.fromXml("ui/UI_Client.xml", "start", this);                
        //UIClientScreenController screenControl = (UIClientScreenController) nifty.getScreen("start").getScreenController();
        if( screenControl !=null){
            screenControl.SetClient(myClient);
        }
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);        
    }
    
    public void bind(Nifty nifty, Screen screen) {
        System.out.println("bind( " + screen.getScreenId() + ")");
    }

    public void onStartScreen() {
        System.out.println("onStartScreen");
    }

    public void onEndScreen() {
        System.out.println("onEndScreen");
    }

    public void quit(){
        nifty.gotoScreen("end");
    }
    
    public void Init_client() throws IOException{
        Serializer.registerClass(HelloMessage.class);
        Serializer.registerClass(SMObjectShare.class);
        try {
            myClient = Network.connectToServer(JMESpiderMonkeyServerMain.NAME, JMESpiderMonkeyServerMain.VERSION,
                "127.0.0.1", JMESpiderMonkeyServerMain.PORT, JMESpiderMonkeyServerMain.UDP_PORT);            
            myClient.start();
            //new ClientListener();
            SpiderMonkeyClientListener handler = new SpiderMonkeyClientListener();
            SpiderMonkeyObjectClientListener objplayerhandler = new SpiderMonkeyObjectClientListener();
            objplayerhandler.SetApp(this);
            myClient.addMessageListener(handler, HelloMessage.class);
            myClient.addMessageListener(objplayerhandler, SMObjectShare.class);
        } catch (Exception e) {
            
        }
    }
    
    /*
    private class ListenHandler implements MessageListener<Client> {
        public void messageReceived(Client source, Message message) {
            if (message instanceof HelloMessage) {
                // do something with the message
                HelloMessage helloMessage = (HelloMessage) message;
                System.out.println("Client #"+source.getId()+" received: '"+helloMessage.getSomething()+"'");
            }
        }
    }
    */
    
    public void UserJoin(Client source,SMObjectShare smobj){
        boolean bfound = false;
        for (SMObjectPlayerController player : players ){   
            if(source.getId() == Integer.parseInt( player.smobjshare.userid)){
                SMObjectShare shareobject = (SMObjectShare)smobj;
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
            //spl.addControl(newplayer);//need to be add in the object to able to update function
            //newplayer.userid = Integer.toString(source.getId());
            newplayer.smobjshare = smobj;
            newplayer.smobjshare.userid = Integer.toString(source.getId());
            
            source.send(newplayer.smobjshare);
            rootNode.attachChild(newplayer.getSpatial());
            players.add(newplayer);
        }        
    }
    
    public void MoveObject(float x,float y,float z){
        if(user == null){
            //System.out.print("nulll...");
            return;
        }
        //System.out.print("\nposition");
        SMObjectPlayerController currentplayer = null;
        System.out.print("PLAYERS:" + players.size());
        for (SMObjectPlayerController player : players){
            //System.out.print("\n[player ID:"+player.shareobject.userid + " CURRENT ID:" +user.userid + "]\n");
            if(player.smobjshare.userid.equalsIgnoreCase(user.userid) == true){
                //System.out.print("FOUND PLAYER!");
                currentplayer = player;
                break;
            }           
        }
        
       if(currentplayer !=null){
            //System.out.print("\nmove position");
            currentplayer.smobjshare.x =  currentplayer.getSpatial().getLocalTranslation().x + x * speed;
            currentplayer.smobjshare.y =  currentplayer.getSpatial().getLocalTranslation().y + y * speed;
            currentplayer.smobjshare.z =  currentplayer.getSpatial().getLocalTranslation().z + z * speed;
            if(myClient !=null){
                myClient.send(currentplayer.smobjshare);
                System.out.print("sending...");
            }
        }
    }
    
    
    //testing for material change
    public void PersonSetSkinColor(ColorRGBA color){
        //Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat.setColor("m_Color", color);
        //geomperson.setMaterial(mat);      
    }
  
    public static void main(String[] args) {      
        JMESpiderMonkeyClientMain app = new JMESpiderMonkeyClientMain();      
        // create new JME appsettings
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Client");
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